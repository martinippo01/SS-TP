package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.WallCrashType;

import java.util.Collections;
import java.util.List;

public class WallCrash extends Crash {

    private final static String NAME = "WALL";

    private final Particle particle;
    private final WallCrashType wallCrashType;

    public WallCrash(Particle particle, WallCrashType wallCrashType) {
        this.particle = particle;
        this.wallCrashType = wallCrashType;
    }

    @Override
    void execute() {
        wallCrashType.setParticleVelocityAfterCrash(particle);
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
