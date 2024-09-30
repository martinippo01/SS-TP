package ar.edu.itba.ss;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class Simulation {
    private final double k;
    private final double A;
    private final double timeMax;
    private final double dt;
    private final double mass;
    private final AlgorithmType algorithmType;
    private final BiConsumer<List<Particle>, Long> onStep;

    public Simulation(double k, double A, double timeMax, double dt, double mass, AlgorithmType algorithmType, BiConsumer<List<Particle>, Long> onStep) {
        this.k = k;
        this.A = A;
        this.timeMax = timeMax;
        this.dt = dt;
        this.mass = mass;
        this.algorithmType = algorithmType;
        this.onStep = onStep;
    }

    abstract Algorithm getAlgorithm();

    public void run() {
        Algorithm algorithm = getAlgorithm();
        double time = 0;
        long counter = 0;
        while (time <= timeMax) {
            algorithm.evolve(dt);
            time+=dt;
            List<Particle> particles = algorithm.getParticles();
            onStep.accept(particles, counter);
            counter++;
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

    public double getTimeMax() {
        return timeMax;
    }

    public double getDt() {
        return dt;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }
}
