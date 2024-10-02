package ar.edu.itba.ss.coupled;

import ar.edu.itba.ss.Particle;

import java.util.ArrayList;
import java.util.List;

public class CoupledBeemanAlgorithm extends CoupledAlgorithm{

    private List<Double> previousAs;

    public CoupledBeemanAlgorithm(double k, double a, double w, int n, List<Particle> particles) {
        super(k, a, w, n, particles);
        this.previousAs = getPhantomAs();
    }

    private List<Double> getPhantomAs() {
        List<Particle> particles = getParticles();
        List<Double> phantomAs = new ArrayList<>();

        double firstForce = getFirstForce();
        double firstCurrentA = firstForce / particles.get(0).getMass();
        phantomAs.add(firstCurrentA);

        for (int i = 1; i < getN()-1; i++) {
            double force = getIntermediateForce(i);
            double currentA = force / particles.get(i).getMass();
            phantomAs.add(currentA);
        }

        return phantomAs;
    }

    private double getNewPosition(int i, double force, double dt){
        Particle current = getParticles().get(i);
        double currentR = current.getPosition().getY();
        double currentV = current.getVelocity().getY();
        double currentA = force / current.getMass();
        double previousA = previousAs.get(i);
        return currentR + currentV * dt + (2.0/3.0) * currentA * Math.pow(dt, 2) - (1.0/6.0) * previousA * Math.pow(dt, 2);
    }

    private void updateVelocity(int i, double newForce, double dt, List<Double> currentAs){
        // Update intermediate particles' velocity
        Particle particle = getParticles().get(i);
        double currentV = particle.getVelocity().getY();
        double newA = newForce / particle.getMass();
        double currentA = currentAs.get(i);
        double previousA = previousAs.get(i);
        double newV = currentV + (1.0/3.0) * newA * dt + (5.0/6.0) * currentA * dt - (1.0/6.0) * previousA * dt;
        getParticles().get(i).getVelocity().setY(newV);
    }

    public void evolve(double dt) {
        List<Particle> particles = getParticles();
        List<Double> newRs = new ArrayList<>();
        List<Double> currentAs = new ArrayList<>();

        // Get new first particle position
        Particle firstParticle = particles.get(0);
        double firstForce = getFirstForce();
        double firstCurrentA = firstForce / firstParticle.getMass();
        double newFirstR = getNewPosition(0, firstForce, dt);
        newRs.add(newFirstR);
        currentAs.add(firstCurrentA);

        // Get new intermediate particles position
        for(int i = 1; i < getN()-1; i++) {
            Particle currentParticle = particles.get(i);
            double force = getIntermediateForce(i);
            double currentA = force / currentParticle.getMass();
            double newR = getNewPosition(i, force, dt);
            newRs.add(newR);
            currentAs.add(currentA);
        }
        
        // Update first to second-to-last particles position
        for (int i = 0; i < getN()-1; i++) {
            particles.get(i).getPosition().setY(newRs.get(i));
        }
        
        // Update first particle's velocity
        updateVelocity(0, getFirstForce(), dt, currentAs);

        // Update intermediate particles' velocity
        for (int i = 1; i < getN()-1; i++){
            updateVelocity(i, getIntermediateForce(i), dt, currentAs);
        }

        // Update previousAs
        previousAs = currentAs;

        // Update last particle
        updateLastParticle(dt);
    }

}
