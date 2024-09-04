package ar.edu.itba.ss;

import java.util.Random;

public class RectangularPlane extends Plane {

    @Override
    public boolean generateParticle(double v0, double mass, double r) {
        Random rand = new Random();
        double x = rand.nextDouble() * l;
        double y = rand.nextDouble() * l;
        Position p = new Position(x, y);
        return generateParticle(v0, mass, r, p);
    }

    @Override
    public boolean generateParticle(double v0, double mass, double r, Position p) {
        Particle newP = new Particle(p, r, v0, mass);
        for (Particle particle: particles) {

        }

    }

    @Override
    public Velocity getParticleVelocityAfterCrash(Particle p) {
        return null;
    }

    @Override
    boolean generateObstacle(double mass, double r, Position p) {
        return false;
    }
}
