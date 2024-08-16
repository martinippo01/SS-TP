package ar.edu.itba.ss;

import java.util.Objects;

public class Particle {
    private Point point;
    private final double radius;
    private final long id;

    private static long nextId = 0;

    public Particle(double x, double y, double radius) {
        this(nextId++, x, y, radius);
    }

    private Particle(long id, double x, double y, double radius) {
        this.id = id;
        this.radius = radius;
        point = new Point(x,y);
    }

    public static Particle copy(Particle p) {
        return new Particle(p.getId(), p.getX(), p.getY(), p.getRadius());
    }

    public Particle moveX(double offset) {
        point = new Point(point.getX() + offset, point.getY());
        return this;
    }

    public Particle moveY(double offset) {
        point = new Point(point.getX(), point.getY() + offset);
        return this;
    }

    public long getId() {
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
        return this.id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
