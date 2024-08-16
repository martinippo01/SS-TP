package ar.edu.itba.ss;

import java.util.Objects;

public class Point {

    private final static double EPSILON = 1e-5;
    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }
        Point otherPoint = (Point) obj;
        return Math.abs(this.x - otherPoint.x) < EPSILON && Math.abs(this.y - otherPoint.y) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
