package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Particle;
import ar.edu.itba.ss.ParticleNeighbours;
import ar.edu.itba.ss.Plane;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 {
    l: 0.0,
    m: 0,
    n: 0,
    rc: 0.0,
    maxRadius: 0.0,
    pacman: true,
    particles: [
        {
            id: 0,
            x: 0.0,
            y: 0.0,
            r: 0.0
        },
        {}
    ],
    neighbours: {
        0: [1, 2, 3],
        1: [0, 2, 3],
        2: [0, 1, 3],
        3: [0, 1, 2]
    }
 }
 */

public class OutputData {

    private final double l;
    private final int m;
    private final int n;
    private final double rc;
    private final long time;
    private final boolean pacman;
    private final String outputFilePrefix;
    private final List<Particle> particles;
    private final Map<Long, List<Long>> neighbours = new HashMap<>();

    public OutputData(InputData inputData, long time, Plane plane, ParticleNeighbours particleNeighbours) {
        this.l = inputData.getLength();
        this.m = inputData.getM();
        this.n = inputData.getN();
        this.rc = inputData.getRadiusC();
        this.pacman = inputData.isPacman();
        this.time = time;
        this.particles = plane.getParticles();
        this.outputFilePrefix = inputData.getOutputFilePrefix();

        for (Map.Entry<Particle, List<Particle>> entry : particleNeighbours.getMap().entrySet()) {
            // Extract id from key
            long keyId = entry.getKey().getId();

            // Extract ids from the list of Particles
            List<Long> valueIds = entry.getValue().stream()
                    .map(Particle::getId)
                    .collect(Collectors.toList());

            // Put the ids into the new map
            this.neighbours.put(keyId, valueIds);
        }
    }

    public String getFileName() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = String.format("%d-%02d-%02d_%02d-%02d-%02d",
                now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
                now.getHour(), now.getMinute(), now.getSecond());
        return String.format("%s_%s.json", outputFilePrefix, timestamp);
    }

    public double getL() {
        return l;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public double getRc() {
        return rc;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public long getTime() {
        return time;
    }

    public boolean isPacman() {
        return pacman;
    }

    public Map<Long, List<Long>> getNeighbours() {
        return neighbours;
    }
}

