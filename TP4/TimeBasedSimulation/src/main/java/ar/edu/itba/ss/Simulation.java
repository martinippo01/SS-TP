package ar.edu.itba.ss;

import java.util.List;

public abstract class Simulation {
    private final double k;
    private final double A;
    private final double timeMax;
    private final double dt;
    private final double mass;
    private final int dt2;

    public Simulation(double k, double A, double timeMax, double dt, double mass, int dt2) {
        this.k = k;
        this.A = A;
        this.timeMax = timeMax;
        this.dt = dt;
        this.mass = mass;
        this.dt2 = dt2;
    }

    abstract Algorithm getAlgorithm(AlgorithmType algorithmType);

    public void runSimulation(AlgorithmType algorithmType) {
        Algorithm algorithm = getAlgorithm(algorithmType);
        double time = 0;
        int counter = 0;
        while (time <= timeMax) {
            algorithm.evolve(dt);
            time+=dt;
            if (counter % dt2 == 0) {
                // Output particles
                List<Particle> particles = algorithm.getParticles();
            }
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

    public int getDt2() {
        return dt2;
    }
}
