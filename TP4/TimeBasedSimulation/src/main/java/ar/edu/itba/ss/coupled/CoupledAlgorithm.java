package ar.edu.itba.ss.coupled;

import ar.edu.itba.ss.Algorithm;
import ar.edu.itba.ss.Particle;

import java.util.List;

public abstract class CoupledAlgorithm extends Algorithm {
    private final double k, a, w;
    private final int n;
    private final List<Particle> particles;
    private double time = 0;

    public CoupledAlgorithm(double k, double a, double w, int n, List<Particle> particles) {
        this.k = k;
        this.a = a;
        this.w = w;
        this.n = n;
        this.particles = particles;
    }

    public double getK() {
        return k;
    }

    public int getN() {
        return n;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public double getA() {
        return a;
    }

    public double getW() {
        return w;
    }

    public double getTime() {
        return time;
    }

    public void updateTime(double dt) {
        this.time+=dt;
    }

    protected double getFirstForce() {
        Particle first = getParticles().get(0);
        Particle second = getParticles().get(1);
        double currentFirstR = first.getPosition().getY();
        double currentSecondR = second.getPosition().getY();
        double k = getK();
        return -k * (currentFirstR - currentSecondR) - k * currentFirstR;
    }

    protected double getIntermediateForce(int i) {
        Particle previous = getParticles().get(i-1);
        double currentPreviousR = previous.getPosition().getY();
        Particle current = getParticles().get(i);
        double currentR = current.getPosition().getY();
        Particle next = getParticles().get(i+1);
        double currentNextR = next.getPosition().getY();
        double k = getK();
        return -k * (currentR - currentPreviousR) - k * (currentR - currentNextR);
    }

    protected void updateLastParticle(double dt){
        double a = getA();
        double w = getW();
        double time = getTime();
        Particle last = particles.get(getN()-1);
        double currentLastR = a * Math.sin(w * (time+dt));
        last.getPosition().setY(currentLastR);
        updateTime(dt);
    }
}
