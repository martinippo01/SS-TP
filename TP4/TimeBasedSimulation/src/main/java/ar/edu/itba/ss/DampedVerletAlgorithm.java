package ar.edu.itba.ss;

import java.util.Optional;

public class DampedVerletAlgorithm extends DampedAlgorithm {
    private Double previousR;

    public DampedVerletAlgorithm(double k, double gamma, Particle particle) {
        super(k, gamma, particle);
        this.previousR = null;
    }

    private double getForce() {
        Particle particle = getParticle();
        double k = getK();
        double gamma = getGamma();
        return -k * particle.getPosition().getY() - gamma * particle.getVelocity().getY();
    }

    private double getPhantomR(double dt) {
        Particle particle = getParticle();
        double r0 = particle.getPosition().getY();
        double v0 = particle.getVelocity().getY();
        double mass = particle.getMass();
        double force = getForce();
        double phantomV = v0 - (dt/mass)*force;
        return r0 - phantomV * dt + dt * dt * force / (2 * mass);
    }

    public void evolve(double dt) {
        Particle particle = getParticle();
        double previousR = Optional.ofNullable(this.previousR).orElseGet(() -> getPhantomR(dt));

        // ri(t + delta(t)) = 2ri(t) - ri(t - delta(t)) + delta(t)^2 / mi * fi(t) + O(delta(t)^4)
        double currentR = particle.getPosition().getY();
        double force = getForce();
        double newR = 2*currentR - previousR + (dt*dt/particle.getMass())*force;
        double newV = (newR - previousR)/(2*dt);
        this.previousR = currentR;
        particle.getVelocity().setY(newV);
        particle.getPosition().setY(newR);
    }


}
