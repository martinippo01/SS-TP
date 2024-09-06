package ar.edu.itba.ss;

import java.util.List;

public class EventOutput {

    private Crash crash;
    private List<Particle> particlesCrashed;
    private List<Particle> particles;
    private double tc;
    private Particle bigParticle;

    // Constructor with bigParticle
    public EventOutput(Crash crash, List<Particle> particlesCrashed, List<Particle> particles, double tc, Particle bigParticle) {
        this.crash = crash;
        this.particlesCrashed = particlesCrashed;
        this.particles = particles;
        this.tc = tc;
        this.bigParticle = bigParticle;
    }

    // Constructor with NO bigParticle
    public EventOutput(Crash crash, List<Particle> particlesCrashed, List<Particle> particles, double tc) {
        this.crash = crash;
        this.particlesCrashed = particlesCrashed;
        this.particles = particles;
        this.tc = tc;
    }

    // Getters and Setters
    public Crash getCrash() {
        return crash;
    }

    public void setCrash(Crash crash) {
        this.crash = crash;
    }

    public List<Particle> getParticlesCrashed() {
        return particlesCrashed;
    }

    public void setParticlesCrashed(List<Particle> particlesCrashed) {
        this.particlesCrashed = particlesCrashed;
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

//
//{
//        events: [{
//        crashType: "WALL",
//        particlesCrashed: [],
//        tc: 20,
//        particles: [{
//        x: ,
//        y: ,
//        vx: ,
//        vy: ,
//        id:
//        }, ...],
//        bigParticle: {
//        x: ,
//        y: ,
//        vx: ,
//        vy: ,
//        }
//        }, ...]
//        }
//
//
