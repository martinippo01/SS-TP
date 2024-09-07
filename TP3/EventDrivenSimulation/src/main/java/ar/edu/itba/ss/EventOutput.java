package ar.edu.itba.ss;

import java.util.List;

public class EventOutput {

    private String crashName;
    private List<Long> particlesCrashed;
    private List<Particle> particles;
    private double tc;
    private Particle bigParticle;

    // Constructor with bigParticle
    public EventOutput(Crash crash, List<Particle> particlesCrashed, List<Particle> particles, double tc, Particle bigParticle) {
        this.crashName = crash.getName();
        this.particlesCrashed = particlesCrashed.stream().map(Particle::getId).toList();
        this.particles = particles;
        this.tc = tc;
        this.bigParticle = bigParticle;
    }

    // Constructor with NO bigParticle
    public EventOutput(Crash crash, List<Particle> particlesCrashed, List<Particle> particles, double tc) {
        this(crash, particlesCrashed, particles, tc, null);
    }

    // Getters and Setters
    public String getCrash() {
        return crashName;
    }

    public List<Long> getParticlesCrashed() {
        return particlesCrashed;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void setParticles(List<Particle> particles) {
        this.particles = particles;
    }

    public double getTc() {
        return tc;
    }

    public void setTc(double tc) {
        this.tc = tc;
    }

    public Particle getBigParticle() {
        return bigParticle;
    }

    public void setBigParticle(Particle bigParticle) {
        this.bigParticle = bigParticle;
    }
}