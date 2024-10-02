package ar.edu.itba.ss;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class Simulation {
    private final double k;
    private final double A;
    private final double tf;
    private final double dt;
    private final double mass;
    private final String algorithmType;
    private final BiConsumer<List<Particle>, Long> onStep;
    private Algorithm algorithm;

    public Simulation(double k, double A, double tf, double dt, double mass, String algorithmType, BiConsumer<List<Particle>, Long> onStep) {
        this.k = k;
        this.A = A;
        this.tf = tf;
        this.dt = dt;
        this.mass = mass;
        this.algorithmType = algorithmType;
        this.onStep = onStep;
        this.algorithm = null;
    }

    protected abstract Algorithm getAlgorithm();

    public void run() {
        algorithm = getAlgorithm();
        double time = 0;
        long counter = 0;
        while (time <= tf) {
            algorithm.evolve(dt);
            time+=dt;
            List<Particle> particles = algorithm.getParticles();
            onStep.accept(particles, ++counter);
        }
    }

    public double getMass() {
        return mass;
    }

    public double getK() {
        return k;
    }

    public double getA() {
        return A;
    }

    public double getDt() {
        return dt;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public List<Particle> getParticles() {
        return algorithm != null ? algorithm.getParticles() : null;
    }
}
