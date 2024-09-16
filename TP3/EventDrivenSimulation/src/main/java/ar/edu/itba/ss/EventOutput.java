package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;

import java.util.List;

public class EventOutput {

    private String event;
    private List<Particle> particlesCrashed;
    private List<Particle> particles;
    private double tc;

    public EventOutput(double tc, List<Particle> particles, String event) {
        this(tc, particles, event, null);
    }

    public EventOutput(double tc, List<Particle> particles, Crash crash, InputData inputData) {
        this(tc, (inputData.getShowParticles()) ? particles : null, crash.getName(), (inputData.getShowParticlesCrashed()) ? crash.getCrashedParticles() : null);
    }

    public EventOutput(double tc, List<Particle> particles, String event, List<Particle> particlesCrashed) {
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

    public List<Particle> getParticlesCrashed() {
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