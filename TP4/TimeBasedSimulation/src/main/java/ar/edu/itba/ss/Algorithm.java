package ar.edu.itba.ss;

import java.util.List;

public abstract class Algorithm {

    abstract void evolve(double dt);

    abstract List<Particle> getParticles();
}
