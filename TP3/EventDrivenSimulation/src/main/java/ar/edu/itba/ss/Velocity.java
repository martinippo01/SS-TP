package ar.edu.itba.ss;

import java.util.Random;

public class Velocity {
    private final double vX;
    private final double vY;

    public Velocity(double speed) {
        speed = Math.abs(speed);
        Random rand = new Random();
        double vx = rand.nextDouble() * (speed * 2) - speed;
        // v = sqrt(vx^2 + vy^2)
        double vy = Math.sqrt(speed * speed - vx * vx);
        if (rand.nextBoolean()) {
            vy = -vy;
        }
        this.vX = vx;
        this.vY = vy;
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
