package ar.edu.itba.ss;

public enum AlgorithmType {
    DAMPED_VERLET {
        @Override
        public Algorithm getAlgorithm(double k, double gamma, Particle particle, double dt) {
            double r0 = particle.getPosition().getY();
            double v0 = particle.getVelocity().getY();
            double mass = particle.getMass();
            // TODO: How to calculate phantom force
            double force = - k * r0 - gamma * v0;
            double phantomR = r0 - v0 * dt + dt * dt * force / (2 * mass);
            return new DampedVerletAlgorithm(k, gamma, particle, phantomR);
        }
    },
    DAMPED_BEEMAN {
        @Override
        public Algorithm getAlgorithm(double k, double gamma, Particle particle, double dt) {
            return new DampedBeemanAlgorithm(k, gamma, particle);
        }
    },
    DAMPED_GEAR_FIVE {
        @Override
        public Algorithm getAlgorithm(double k, double gamma, Particle particle, double dt) {
            return new DampedGearFiveAlgorithm(k, gamma, particle);
        }
    };

    public abstract Algorithm getAlgorithm(double k, double gamma, Particle particle, double dt);
}
