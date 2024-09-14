package ar.edu.itba.ss;

import java.util.List;

public class EventOutput {

    private String event;
    private List<Long> particlesCrashed;
    private List<Particle> particles;
    private double tc;

    public EventOutput(double tc, List<Particle> particles, String event) {
        this(tc, particles, event, null);
    }

    public EventOutput(double tc, List<Particle> particles, Crash crash) {
        this(tc, particles, crash.getName(), crash.getCrashedParticles().stream().map(Particle::getId).toList());
    }

    public EventOutput(double tc, List<Particle> particles, String event, List<Long> particlesCrashed) {
        this.tc = tc;
        this.particles = particles;
        this.event = event;
        this.particlesCrashed = particlesCrashed;
    }

    // Getters and Setters
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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
}