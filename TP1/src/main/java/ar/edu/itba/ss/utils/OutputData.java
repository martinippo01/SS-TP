package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Particle;
import ar.edu.itba.ss.ParticleNeighbours;
import ar.edu.itba.ss.Plane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputData {

    private final double L;
    private final int M;
    private final int N;
    private final long time;
    private final List<Particle> particles;
    private final Map<Integer, List<Integer>> particleNeighbours = new HashMap<>();

    public OutputData(double L, int M, int N, long time, Plane plane){
        this.L = L;
        this.M = M;
        this.N = N;
        this.time = time;
        this.particles = plane.getParticles();

        for (Map.Entry<Particle, List<Particle>> entry : ParticleNeighbours.getParticleNeighbours().getMap().entrySet()) {
            // Extract id from key
            Integer keyId = entry.getKey().getId();

            // Extract ids from the list of Particles
            List<Integer> valueIds = entry.getValue().stream()
                    .map(Particle::getId)
                    .collect(Collectors.toList());

            // Put the ids into the new map
            particleNeighbours.put(keyId, valueIds);
        }
    }

    public double getL() {
        return L;
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public long getTime() {
        return time;
    }

    public Map<Integer, List<Integer>> getParticleNeighbours() {
        return particleNeighbours;
    }
}

