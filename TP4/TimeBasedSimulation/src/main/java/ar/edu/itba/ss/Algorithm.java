package ar.edu.itba.ss;

import java.util.List;

public abstract class Algorithm {

    public abstract void evolve(double dt);

    public abstract List<Particle> getParticles();
}
