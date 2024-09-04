package ar.edu.itba.ss;

import java.util.List;

public abstract class Plane {
    protected double l;
    protected List<Particle> particles;
    protected List<Obstacle> obstacles;

    abstract boolean generateParticle(Velocity v0, double mass, double r);
    abstract boolean generateParticle(Velocity v0, double mass, double r, Position p);
    abstract Velocity getParticleVelocityAfterCrash(Particle p);

    

    void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }


}
