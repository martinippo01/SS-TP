package ar.edu.itba.ss.damped;

import ar.edu.itba.ss.Particle;

public class DampedBeemanAlgorithm extends DampedAlgorithm {

    private double previousA;

    public DampedBeemanAlgorithm(double k, double gamma, Particle particle) {
        super(k,gamma,particle);
        double currentR = particle.getPosition().getY();
        double currentV = particle.getVelocity().getY();
        double force = - k * currentR - gamma * currentV;
        this.previousA = force / particle.getMass();    // Esto se puede dejar así o conseguir un vFantasma y rFantasma
    }

    @Override
    public void evolve(double dt) {
        Particle particle = getParticle();
        double k = getK();
        double gamma = getGamma();

        double currentR = particle.getPosition().getY();
        double currentV = particle.getVelocity().getY();
        double currentA = (-k*currentR - gamma * particle.getVelocity().getY())/ particle.getMass();

        double nextR = currentR + currentV*dt + (2.0/3.0)*currentA*dt*dt - (1/6.0)*previousA*dt*dt;

        double predNextV = currentV + (3/2.0)*currentA*dt - (1/2.0)*previousA*dt;
        double nextForce = -k * nextR - gamma * predNextV;
        double predNextA = nextForce / particle.getMass();

        double correctedNextV = currentV + (1/3.0) * predNextA * dt + (5/6.0) * currentA * dt - (1/6.0) * previousA * dt;

        this.previousA = currentA;
        particle.getPosition().setY(nextR);
        particle.getVelocity().setY(correctedNextV);
    }
}
