package ar.edu.itba.ss;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ParticleFunctions {
    private final Function<Particle, Double> forceFn;
    private final BiFunction<Double, Double, Double> r2Fn;
    private final BiFunction<Double, Double, Double> r3Fn;
    private final BiFunction<Double, Double, Double> r4Fn;
    private final BiFunction<Double, Double, Double> r5Fn;

    public ParticleFunctions(Function<Particle, Double> forceFn, BiFunction<Double, Double, Double> r2Fn, BiFunction<Double, Double, Double> r3Fn, BiFunction<Double, Double, Double> r4Fn, BiFunction<Double, Double, Double> r5Fn) {
        this.forceFn = forceFn;
        this.r2Fn = r2Fn;
        this.r3Fn = r3Fn;
        this.r4Fn = r4Fn;
        this.r5Fn = r5Fn;
    }


}
