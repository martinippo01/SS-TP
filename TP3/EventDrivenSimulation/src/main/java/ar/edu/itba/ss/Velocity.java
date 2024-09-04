package ar.edu.itba.ss;

public class Velocity {
    private final double vX;
    private final double vY;

    public Velocity(double speed) {

    }

    public Velocity(double vX, double vY) {
        this.vX = vX;
        this.vY = vY;
    }

    public double getvX() {
        return vX;
    }

    public double getvY() {
        return vY;
    }
}
