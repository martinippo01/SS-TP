package ar.edu.itba.ss.damped;

import ar.edu.itba.ss.*;

public enum DampedAlgorithmType {
    VERLET {
        @Override
        public Algorithm getAlgorithm(double k, double gamma, Particle particle) {
            return new DampedVerletAlgorithm(k, gamma, particle);
        }
    },
    BEEMAN {
        @Override
        public Algorithm getAlgorithm(double k, double gamma, Particle particle) {
            return new DampedBeemanAlgorithm(k, gamma, particle);
        }
    },
    GEAR_FIVE {
        @Override
        public Algorithm getAlgorithm(double k, double gamma, Particle particle) {
            return new DampedGearFiveAlgorithm(k, gamma, particle);
        }
    };

    public abstract Algorithm getAlgorithm(double k, double gamma, Particle particle);
}
