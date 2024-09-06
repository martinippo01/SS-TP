package ar.edu.itba.ss;

import java.util.List;

public abstract class Crash {

    abstract void execute();
    abstract String getName();
    abstract List<Particle> getCrashedParticles();

}
