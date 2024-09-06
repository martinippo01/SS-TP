package ar.edu.itba.ss;

import java.util.Collections;
import java.util.List;

public class ObstacleCrash extends Crash {
    private final static String NAME = "OBSTACLE";
    private final double CN = 1;
    private final double CT = 1;

    private final Particle particle;
    private final Obstacle obstacle;

    public ObstacleCrash(Particle particle, Obstacle obstacle) {
        this.particle = particle;
        this.obstacle = obstacle;
    }

    @Override
    void execute() {
        double hypotenuse = Math.sqrt(Math.pow(particle.getX() - obstacle.getX(), 2) + Math.pow(particle.getY() - obstacle.getY(), 2));
        double cos = (particle.getX() - obstacle.getX()) / hypotenuse;
        double cos2 = Math.pow(cos, 2);
        double sin = (particle.getY() - obstacle.getY()) / hypotenuse;
        double sin2 = Math.pow(sin, 2);
        double a00 = - CN * cos2 + CT * sin2;
        double a01 = - (CN + CT) * cos * sin;
        double a10 = - (CN + CT) * cos * sin;
        double a11 = - CN * sin2 + CT * cos2;
        double vax = particle.getVx();
        double vay = particle.getVy();
        double vfx = a00 * vax + a01 * vay;
        double vfy = a10 * vax + a11 * vay;
        particle.setVelocity(new Velocity(vfx, vfy));
    }

    @Override
    String getName() {
        return NAME;
    }

    @Override
    List<Particle> getCrashedParticles() {
        return Collections.singletonList(particle);
    }
}
