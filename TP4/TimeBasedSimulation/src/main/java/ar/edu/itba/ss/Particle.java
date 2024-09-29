package ar.edu.itba.ss;

public class Particle {
    private final Position position;
    private final Velocity velocity;
    private final double mass;

    public Particle(Position position, Velocity velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
    }

    public Position getPosition() {
        return position;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }

}
