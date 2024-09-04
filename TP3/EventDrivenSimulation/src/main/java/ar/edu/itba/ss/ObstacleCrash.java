package ar.edu.itba.ss;

import java.util.Collections;
import java.util.List;

public class ObstacleCrash extends Crash {
    private final static String NAME = "OBSTACLE";

    private final Particle particle;
    private final Obstacle obstacle;

    public ObstacleCrash(Particle particle, Obstacle obstacle) {
        this.particle = particle;
        this.obstacle = obstacle;
    }

    @Override
    void execute() {
        // TODO:
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
