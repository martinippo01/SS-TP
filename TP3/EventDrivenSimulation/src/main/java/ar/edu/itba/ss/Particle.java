package ar.edu.itba.ss;

import java.util.Objects;
import java.util.Optional;

public class Particle {
    private static long NEXT_ID = 1;

    private final long id;
    private final Position position;
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

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getVx() {
        return velocity.getvX();
    }

    public double getVy() {
        return velocity.getvY();
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public long getId() {
        return id;
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

    boolean isOverlappedWith(Particle p) {
        double x2 = Math.pow(this.position.getX() - p.position.getX(), 2);
        double y2 = Math.pow(this.position.getY() - p.position.getY(), 2);
        double distance = Math.sqrt(x2 + y2);
        double minDistance = this.radius + p.radius;
        return distance < minDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    Optional<Double> calculateTimeForCrash(Particle p2) {
        double sigma = this.getRadius() + p2.getRadius();
        double deltaVx = p2.getVx() - this.getVx();
        double deltaVy = p2.getVy() - this.getVy();
        double deltaX = p2.getX() - this.getX();
        double deltaY = p2.getY() - this.getY();
        double deltaVdeltaR = deltaVx * deltaX + deltaVy * deltaY;

        if(Double.compare(deltaVdeltaR, 0.0) >= 0) {
            return Optional.empty();
        }

        double deltaVdeltaV = deltaVx*deltaVx + deltaVy*deltaVy;
        double deltaRdeltR = deltaX*deltaX + deltaY*deltaY;
        double d = deltaVdeltaR*deltaVdeltaR - (deltaVdeltaV)*(deltaRdeltR - sigma*sigma);

        if(Double.compare(d,0.0)<0) {
            return Optional.empty();
        }

        return Optional.of((-deltaVdeltaR+Math.sqrt(d))/deltaVdeltaV);
    }


}
