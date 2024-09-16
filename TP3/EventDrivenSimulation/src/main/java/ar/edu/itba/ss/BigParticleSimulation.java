package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;

import java.util.List;

public class BigParticleSimulation extends Simulation {

    private final List<Particle> bigParticles;

    public BigParticleSimulation(Plane p, long n, double maxTime, List<Particle> bigParticles, InputData inputData) {
        super(p, n, maxTime, inputData);
        this.bigParticles = bigParticles;
    }

    @Override
    public void prepare(double mass, double radius, double speed) {
        Plane plane = getPlane();
        plane.addParticles(bigParticles);
        super.prepare(mass, radius, speed);
    }
}
