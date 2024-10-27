package ar.edu.itba.ss.tp5.vector;

public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector getVersor() {
        double module = Math.sqrt(x * x + y * y);
        return new Vector(x / module, y / module);
    }

    public double dot(Vector v) {
        return x * v.x + y * v.y;
    }

    public double module() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
