package ar.edu.itba.ss.coupled;

import ar.edu.itba.ss.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class CoupledSimulation extends Simulation {
    private final int n;
    private final double w;
    private final double l0;

    public CoupledSimulation(double k, double A, double tf, double dt, double mass, String algorithmType, int n, double w, double l0, BiConsumer<List<Particle>, Long> onStep) {
        super(k, A, tf, dt, mass, algorithmType, onStep);
        this.n = n;
        this.w = w;
        this.l0 = l0;
    }


    @Override
    public Algorithm getAlgorithm() {
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Position position = new Position((i+1) * l0, 0);
            particles.add(new Particle(position, new Velocity(), getMass()));
        }
        CoupledAlgorithmType algorithmType = CoupledAlgorithmType.valueOf(this.getAlgorithmType());
        return algorithmType.getAlgorithm(this.getK(), this.getA(), this.w, this.n, particles);
    }
}
