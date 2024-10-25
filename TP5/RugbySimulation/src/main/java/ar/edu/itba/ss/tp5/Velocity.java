package ar.edu.itba.ss.tp5;

public class Velocity {

    private double vX;
    private double vY;

    public Velocity(double vX, double vY) {
        this.vX = vX;
        this.vY = vY;
    }

    public void setVX(double vX) {
        this.vX = vX;
    }

    public void setVY(double vY) {
        this.vY = vY;
    }

    public double getVX() {
        return vX;
    }

    public double getVY() {
        return vY;
    }
}
