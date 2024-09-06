package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Plane {
    protected double l;
    protected List<Particle> particles;
    protected List<Obstacle> obstacles;

    public Plane(double l) {
        this.l = l;
        this.particles = new ArrayList<>();
        this.obstacles = new ArrayList<>();
    }

    abstract boolean generateParticle(double v0, double mass, double r);
    abstract boolean generateParticle(double v0, double mass, double r, Position p);

    void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Event> getCrashEventWithBorders(Particle p) { return Collections.emptyList();}

}
