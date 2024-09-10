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
    abstract boolean isInside(Particle p);

    boolean generateParticle(double v0, double mass, double r, Position p) {
        Velocity v = new Velocity(v0);
        Particle newP = new Particle(p, r, v, mass);
        if (!isInside(newP)) {
            return false;
        }
        for (Particle particle: particles) {
            if (newP.isOverlappedWith(particle)) {
                return false;
            }
        }
        for (Obstacle obs : obstacles) {
            if (newP.isOverlappedWith(obs))
                return false;
        }
        this.particles.add(newP);
        return true;
    }

    void addParticles(List<Particle> particles) {
        this.particles.addAll(particles);
    }

    void addObstacles(List<Obstacle> obstacles) {
        this.obstacles.addAll(obstacles);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Event> getCrashEventWithBorders(Particle p) { return Collections.emptyList();}

}
