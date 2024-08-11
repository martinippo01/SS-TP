package ar.edu.itba.ss;

import java.util.Objects;

public class Particle {
    private final Point point;
    private final double radius;
    private final int id;

    public Particle(int id, double x, double y, double radius) {
        this.id = id;
        this.radius = radius;
        point = new Point(x,y);
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return Objects.equals(point, particle.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }
}
