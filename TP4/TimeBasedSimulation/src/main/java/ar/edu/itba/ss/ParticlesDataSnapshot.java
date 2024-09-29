package ar.edu.itba.ss;

import java.util.List;

public record ParticlesDataSnapshot(
        List<Double> particlesForce,
        List<Double> particlesR2,
        List<Double> particlesR3,
        List<Double> particlesR4,
        List<Double> particlesR5
) {}
