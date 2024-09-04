package ar.edu.itba.ss;

import java.util.Collections;
import java.util.List;

public class WallCrash extends Crash {

    private final static String NAME = "WALL";

    private final Particle particle;
    private final Plane plane;

    public WallCrash(Particle particle, Plane plane) {
        this.plane = plane;
        this.particle = particle;
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
        return Collections.singletonList(particle);
    }
}
