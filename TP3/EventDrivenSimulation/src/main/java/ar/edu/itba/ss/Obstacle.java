package ar.edu.itba.ss;

public class Obstacle extends Particle{
    public Obstacle(Position p, double radius, double mass){
        super(p, radius, new Velocity(0,0), mass);
    }

    @Override
    public void setVelocity(Velocity velocity) {
        // Do nothing
    }
}
