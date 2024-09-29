package ar.edu.itba.ss;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DampedSimulation extends Simulation {
    private final double gamma;

    public DampedSimulation(double k, double A, double timeMax, double dt, double mass, int dt2, double gamma) {
        super(k, A, timeMax, dt, mass, dt2);
        this.gamma = gamma;
    }

    private BiFunction<List<Particle>, Integer, ParticleFunctions> getParticleFunctionsGetter() {
        return (particles, position) -> {
            double k = this.getK();
            double mass = this.getMass();
            Particle particle = particles.get(position);
            double r = particle.getPosition().getY();
            double r1 = particle.getVelocity().getY();
            Function<Particle, Double> forceFn = (p) -> (-k * r - gamma * r1);
            BiFunction<Double, Double, Double> rnFn = (rnMinus2, rnMinus1) -> (-(k / mass) * rnMinus2 - (gamma / mass) * rnMinus1);
            return new ParticleFunctions(forceFn, rnFn, rnFn, rnFn, rnFn);
        };
    }

    @Override
    public Algorithm getAlgorithm(AlgorithmType algorithmType) {
        double k = this.getK();
        double A = this.getA();
        double mass = this.getMass();
        double r0 = A;
        double v0 = -A * gamma / (2 * mass);
        Particle p = new Particle(new Position(0,r0), new Velocity(0, v0), mass);
        double dt = this.getDt();

        return algorithmType.getAlgorithm(k, gamma, p, dt);
    }
}
