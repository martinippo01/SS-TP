package ar.edu.itba.ss;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class CoupledSimulation extends Simulation {
    private final int n;
    private final double w;

    public CoupledSimulation(double k, double A, double timeMax, double dt, double mass, AlgorithmType algorithmType, BiConsumer<List<Particle>, Long> onStep, int dt2, int n, double w) {
        super(k, A, timeMax, dt, mass, algorithmType, onStep);
        this.n = n;
        this.w = w;
    }


    @Override
    public Algorithm getAlgorithm() {

        return null;
    }

    /*private ParticleFunctions getFirstParticleFunctionsGetter(List<Particle> particles) {
        Particle particle = particles.getFirst();
        double r = particle.getPosition().getY();
        double r1 = particle.getVelocity().getY();

        Particle nextParticle = particles.get(1);
        double nextR = nextParticle.getPosition().getY();
        double nextR1 = nextParticle.getVelocity().getY();

        double k = getK();
        double mass = getMass();

        Supplier<Double> forceSupplier = () -> (-k * r - k * (r - nextR));
        BiFunction<Double, Double, Double> rnFn = (rminus2, nextRMinus2) -> (- (k / mass) * (rminus2 - nextRMinus2) - (k / mass) * rminus2);
        Supplier<Double> r2Supplier = () -> rnFn.apply(r, nextR);
        Supplier<Double> r3Supplier = () -> rnFn.apply(r1, nextR1);
    }

    private ParticleFunctions getIntermediateParticleFunctionsGetter(List<Particle> particles, int position) {
        Particle mainP = particles.get(position);
        Particle prevP = particles.get(position-1);
        Particle nextP = particles.get(position+1);

        double k = getK();
        double mainR = mainP.getPosition().getY();
        double prevR = prevP.getPosition().getY();
        double nextR = nextP.getPosition().getY();
        Supplier<Double> forceSupplier = () -> (-k * (mainR - prevR) - k * (mainR - nextR));



    }

    private ParticleFunctions getLastParticleFunctionsGetter(List<Particle> particles) {

    }

    private BiFunction<List<Particle>, Integer, ParticleFunctions> getParticleFunctionsGetter() {
        return (particles, position) -> {
            if (position == 0)
                return getFirstParticleFunctionsGetter(particles);
            else if (position == n-1)
                return getLastParticleFunctionsGetter(particles);
            return getIntermediateParticleFunctionsGetter(particles, position);
        };
    }

    */
}
