package ar.edu.itba.ss;

public class Particle {
    private static long NEXT_ID = 1;

    private final long id;
    private Position position;
    private final double radius;
    private Velocity velocity;
    private final double mass;

    public Particle(Position position, double radius, Velocity velocity, double mass) {
        this.position = position;
        this.radius = radius;
        this.velocity = velocity;
        this.mass = mass;
        this.id = NEXT_ID++;
    }

    public Position getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public long getId() {
        return id;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    void advance(double time) {
        double newX = this.position.getX() + this.velocity.getvX() * time;
        double newY = this.position.getY() + this.velocity.getvY() * time;
        this.position.setX(newX);
        this.position.setY(newY);
    }

    boolean areOverlaped(Particle p) {
        double x2 = Math.pow(this.position.getX() - p.position.getX(), 2);
        double y2 = Math.pow(this.position.getY() - p.position.getY(), 2);
        double distance = Math.sqrt(x2 + y2);
        double minDistance = this.radius + p.radius;
        return distance < minDistance;
    }

    Crash crashWith(Particle p) {
        return new ParticlesCrash(this, p);
    }

    Crash crashWith(Plane p) {
        return new WallCrash(this, p);
    }

    Crash crashWith(Obstacle o) {
        return new ObstacleCrash(this, o);
    }
}
