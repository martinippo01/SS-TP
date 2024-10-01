package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.SimulationParams;

import java.util.List;
import java.util.function.BiConsumer;

public enum SimulationType {
    DAMPED {
        @Override
        Simulation getSimulation(SimulationParams params, BiConsumer<List<Particle>, Long> onStep) {
            return new DampedSimulation(
                    params.getK(),
                    params.getA(),
                    params.getTf(),
                    params.getDt(),
                    params.getMass(),
                    params.getAlgorithmType(),
                    onStep,
                    params.getGamma()
            );
        }
    },
    COUPLED{
        @Override
        Simulation getSimulation(SimulationParams params, BiConsumer<List<Particle>, Long> onStep) {
            return null;
        }
    };

    abstract Simulation getSimulation(SimulationParams params, BiConsumer<List<Particle>, Long> onStep);
}
