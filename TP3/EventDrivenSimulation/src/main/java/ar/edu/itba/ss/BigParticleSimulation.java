package ar.edu.itba.ss;

import java.util.List;

public class BigParticleSimulation extends Simulation {

    private final List<Particle> bigParticles;

    public BigParticleSimulation(Plane p, long n, double maxTime, List<Particle> bigParticles) {
        super(p, n, maxTime);
        this.bigParticles = bigParticles;
    }

    @Override
    public void prepare(double mass, double radius, double speed) {
        Plane plane = getPlane();
        plane.addParticles(bigParticles);
        super.prepare(mass, radius, speed);
    }
}
