package ar.edu.itba.ss;

import java.util.List;

public class ParticlesCrash extends Crash {

    private static final String NAME = "PARTICLES";

    private final Particle p1, p2;

    public ParticlesCrash(Particle p1, Particle p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    void execute() {
        double sigma = p1.getRadius() + p2.getRadius();
        double deltaVx = p2.getVx() - p1.getVx();
        double deltaVy = p2.getVy() - p1.getVy();
        double deltaX = p2.getX() - p1.getX();
        double deltaY = p2.getY() - p1.getY();
        double deltaVDotDeltaR = deltaVx * deltaX + deltaVy * deltaY;
        double J = 2 * p1.getMass() * p2.getMass() * deltaVDotDeltaR / (sigma * (p1.getMass() + p2.getMass()));
        double Jx = J * deltaX / sigma;
        double Jy = J * deltaY / sigma;
        p1.setVelocity(new Velocity(
                p1.getVx() + Jx / p1.getMass(),
                p1.getVy() + Jy / p1.getMass()
        ));
        p2.setVelocity(new Velocity(
                p2.getVx() - Jx / p2.getMass(),
                p2.getVy() - Jy / p2.getMass()
        ));
    }

    @Override
    String getName() {
        return NAME;
    }

    @Override
    List<Particle> getCrashedParticles() {
        return List.of(p1, p2);
    }
}
