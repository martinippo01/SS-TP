package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Particle;
import ar.edu.itba.ss.Simulation;
import ar.edu.itba.ss.coupled.CoupledSimulation;
import ar.edu.itba.ss.damped.DampedSimulation;

import java.util.List;
import java.util.function.BiConsumer;

public enum SimulationType {
    DAMPED {
        @Override
        public Simulation getSimulation(SimulationParams params, BiConsumer<List<Particle>, Long> onStep) {
            return new DampedSimulation(
                    params.getK(),
                    params.getA(),
                    params.getTf(),
                    params.getDt(),
                    params.getMass(),
                    params.getAlgorithmType(),
                    params.getGamma(),
                    onStep
            );
        }
    },
    COUPLED{
        @Override
        public Simulation getSimulation(SimulationParams params, BiConsumer<List<Particle>, Long> onStep) {
            return new CoupledSimulation(
                    params.getK(),
                    params.getA(),
                    params.getTf(),
                    params.getDt(),
                    params.getMass(),
                    params.getAlgorithmType(),
                    params.getN(),
                    params.getW(),
                    params.getL0(),
                    onStep
            );
        }
    };

    public abstract Simulation getSimulation(SimulationParams params, BiConsumer<List<Particle>, Long> onStep);
}
