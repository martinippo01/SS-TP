package ar.edu.itba.ss;

import java.util.function.BiFunction;

public class DampedGearFiveAlgorithm extends DampedAlgorithm {

    private double currentR2, currentR3, currentR4, currentR5;

    public DampedGearFiveAlgorithm(double k, double gamma, Particle particle) {
        super(k,gamma,particle);
        double currentR = particle.getPosition().getY();
        double currentR1 = particle.getVelocity().getY();
        double mass = particle.getMass();
        BiFunction<Double, Double, Double> currentRnFn = (rnMinus2, rnMinus1) -> (- (k / mass) * rnMinus2 - (gamma / mass) * rnMinus1);
        this.currentR2 = currentRnFn.apply(currentR, currentR1);
        this.currentR3 = currentRnFn.apply(currentR1, currentR2);
        this.currentR4 = currentRnFn.apply(currentR2, currentR3);
        this.currentR5 = currentRnFn.apply(currentR3, currentR4);
    }

    public void evolve(double dt) {
        Particle particle = getParticle();
        double k = getK();
        double gamma = getGamma();


        double currentR = particle.getPosition().getY();
        double currentR1 = particle.getVelocity().getY();
        double mass = particle.getMass();

        // Predict
        double predNextR = currentR + currentR1 * dt + currentR2 * (dt * dt / 2) + currentR3 * (dt * dt * dt / (2 * 3)) + currentR4 * (Math.pow(dt, 4) / (2 * 3 * 4)) + currentR5 * (Math.pow(dt, 5) / (2 * 3 * 4 * 5));
        double predNextR1 = currentR1 + currentR2 * dt + currentR3 * (dt * dt / 2) + currentR4 * (Math.pow(dt, 3) / (2 * 3)) + currentR5 * (Math.pow(dt, 4) / (2 * 3 * 4));
        double predNextR2 = currentR2 + currentR3 * dt + currentR4 * (dt * dt / 2) + currentR5 * (Math.pow(dt, 3) / (2 * 3));
        double predNextR3 = currentR3 + currentR4 * dt + currentR5 * (dt * dt / 2);
        double predNextR4 = currentR4 + currentR5 * dt;
        double predNextR5 = currentR5;

        // Evaluate
        double nextForce = - k * predNextR - gamma * predNextR1;
        double nextA = nextForce / mass;
        double deltaR2 = ((nextA - predNextR2)*dt*dt)/2;

        // Correct
        double nextR = predNextR + (3/16.0) * deltaR2;
        double nextR1 = predNextR1 + (251/360.0) * deltaR2 * (1 / dt);
        double nextR2 = predNextR2 + 1 * deltaR2 * (2 / Math.pow(dt, 2));
        double nextR3 = predNextR3 + (11/18.0) * deltaR2 * ((2 * 3) / Math.pow(dt, 3));
        double nextR4 = predNextR4 + (1/6.0) * deltaR2 * ((2 * 3 * 4) / Math.pow(dt, 4));
        double nextR5 = predNextR5 + (1/60.0) * deltaR2 * ((2 * 3 * 4 * 5) / Math.pow(dt, 5));

        // Update
        particle.getPosition().setY(nextR);
        particle.getVelocity().setY(nextR1);
        this.currentR2 = nextR2;
        this.currentR3 = nextR3;
        this.currentR4 = nextR4;
        this.currentR5 = nextR5;
    }
}
