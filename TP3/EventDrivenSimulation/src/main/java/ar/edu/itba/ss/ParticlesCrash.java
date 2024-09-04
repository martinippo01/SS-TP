package ar.edu.itba.ss;

import java.util.List;

public class ParticlesCrash extends Crash {

    private static final String NAME = "PARTICLES";

    private final Particle p1, p2;

    public ParticlesCrash(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    void execute() {

    }

    @Override
    String getName() {
        return NAME;
    }

    @Override
    List<Particle> getCrashedParticles() {
        return List.of(p1, p2);
    }
}
