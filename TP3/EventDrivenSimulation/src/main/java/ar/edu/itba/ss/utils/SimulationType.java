package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.BigParticleSimulation;
import ar.edu.itba.ss.ObstacleSimulation;
import ar.edu.itba.ss.Simulation;

public enum SimulationType {
    OBSTACLE {
        @Override
        public Simulation createSimulation(InputData inputData) {
            return new ObstacleSimulation(inputData.getPlane(), inputData.getN(), inputData.getMaxTime(), inputData.getObstacles(), inputData);
        }
    },
    BIG_PARTICLE {
        @Override
        public Simulation createSimulation(InputData inputData) {
            return new BigParticleSimulation(inputData.getPlane(), inputData.getN(), inputData.getMaxTime(), inputData.getParticles(), inputData);
        }
    };

    abstract public Simulation createSimulation(InputData inputData);
}
