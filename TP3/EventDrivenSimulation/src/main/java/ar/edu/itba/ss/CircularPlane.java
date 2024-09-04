package ar.edu.itba.ss;

public class CircularPlane extends Plane{

    @Override
    public boolean generateParticle(Velocity v0, double mass, double r) {
        return false;
    }

    @Override
    public boolean generateParticle(Velocity v0, double mass, double r, Position p) {
        return false;
    }

    @Override
    public Velocity getParticleVelocityAfterCrash(Particle p) {
        return null;
    }

}
