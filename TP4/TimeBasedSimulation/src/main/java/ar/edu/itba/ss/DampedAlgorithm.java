package ar.edu.itba.ss;

import java.util.List;

public abstract class DampedAlgorithm extends Algorithm {
    private final double k, gamma;
    private final Particle particle;

    public DampedAlgorithm(double k, double gamma, Particle particle) {
        this.k = k;
        this.gamma = gamma;
        this.particle = particle;
    }

    public double getK() {
        return k;
    }

    public double getGamma() {
        return gamma;
    }

    public Particle getParticle() {
        return particle;
    }

    public List<Particle> getParticles() {
        return List.of(particle);
    }
}
