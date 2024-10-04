package ar.edu.itba.ss.coupled;

import ar.edu.itba.ss.Particle;
import ar.edu.itba.ss.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CoupledVerletAlgorithm extends CoupledAlgorithm {

    private final List<Double> previousRs;

    public CoupledVerletAlgorithm(double k, double a, double w, int n, List<Particle> particles) {
        super(k, a, w, n, particles);
        this.previousRs = null;
    }

    private List<Double> getPhantomRs(double dt) {
//        List<Particle> particles = getParticles();
//        List<Double> phantomRs = new ArrayList<>();
//        BiFunction<Particle, Double, Double> getPhantomR = (particle, force) -> {
//            double r0 = particle.getPosition().getY();
//            double v0 = particle.getVelocity().getY();
//            double mass = particle.getMass();
//            double phantomV = v0 - (dt/mass)*force;
//            return r0 - phantomV * dt + dt * dt * force / (2 * mass);
//        };
//
//        double firstForce = getFirstForce();
//        double firstPhantomR = getPhantomR.apply(particles.get(0), firstForce);
//        phantomRs.add(firstPhantomR);
//
//        for (int i = 1; i < getN()-1; i++) {
//            double force = getIntermediateForce(i);
//            double currentPhantomR = getPhantomR.apply(particles.get(i), force);
//            phantomRs.add(currentPhantomR);
//        }
//        return phantomRs;

        return getParticles().stream()
                .map(Particle::getPosition)
                .map(Position::getY)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void updateParticle(int i, double force, double dt) {
        List<Double> previousRs = Optional.ofNullable(this.previousRs).orElseGet(() -> getPhantomRs(dt));
        Particle current = getParticles().get(i);
        double currentR = current.getPosition().getY();
        double previousR = previousRs.get(i);
        double newR = 2 * currentR - previousR + (dt * dt / current.getMass()) * force;
        double newV = (newR - previousR) / (2 * dt);
        previousRs.set(i, currentR);
        current.getVelocity().setY(newV);
        current.getPosition().setY(newR);
    }

    @Override
    public void evolve(double dt) {
        // First particle
        double firstForce = getFirstForce();
        updateParticle(0, firstForce, dt);

        // Intermediate particles
        for(int i = 1; i < getN()-1; i++) {
            double force = getIntermediateForce(i);
            updateParticle(i, force, dt);
        }

        // Last particle
        updateLastParticle(dt);
    }
}
