package ar.edu.itba.ss.coupled;

import ar.edu.itba.ss.*;

import java.util.List;

public enum CoupledAlgorithmType {
    VERLET {
        @Override
        public Algorithm getAlgorithm(double k, double a, double w, int n, List<Particle> particles) {
            return new CoupledVerletAlgorithm(k, a, w, n, particles);
        }
    },
    BEEMAN {
        @Override
        public Algorithm getAlgorithm(double k, double a, double w, int n, List<Particle> particles) {
            return new CoupledBeemanAlgorithm(k, a, w, n, particles);
        }
    },
    GEAR_FIVE {
        @Override
        public Algorithm getAlgorithm(double k, double a, double w, int n, List<Particle> particles) {
            return new CoupledGearFiveAlgorithm(k, a, w, n, particles);
        }
    };

    public abstract Algorithm getAlgorithm(double k, double a, double w, int n, List<Particle> particles);
}
