package ar.edu.itba.ss;

public class DampedVerletAlgorithm extends DampedAlgorithm {
    private double previousR;

    public DampedVerletAlgorithm(double k, double gamma, Particle particle, double phantomR) {
        super(k, gamma, particle);
        this.previousR = phantomR;
    }

    public void evolve(double dt) {
        Particle particle = getParticle();
        double k = getK();
        double gamma = getGamma();

        // ri(t + delta(t)) = 2ri(t) - ri(t - delta(t)) + delta(t)^2 / mi * fi(t) + O(delta(t)^4)
        double currentR = particle.getPosition().getY();
        double force = -k*currentR - gamma * particle.getVelocity().getY();
        double newR = 2*currentR - previousR + (dt*dt/particle.getMass())*force;
        double newV = (newR - previousR)/(2*dt);
        previousR = currentR;
        particle.getVelocity().setY(newV);
        particle.getPosition().setY(newR);
    }


}
