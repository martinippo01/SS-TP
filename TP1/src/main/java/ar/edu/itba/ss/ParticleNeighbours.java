package ar.edu.itba.ss;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Singleton because why not
public class ParticleNeighbours {

    private static ParticleNeighbours pn;

    private final Map<Particle, List<Particle>> map;

    private ParticleNeighbours() {
        this.map = new HashMap<>();
    }

    public static ParticleNeighbours getParticleNeighbours() {
        if(pn == null) { pn = new ParticleNeighbours(); }
        return pn;
    }

    public void addNeighbour(Particle p, Particle n) {
        map.putIfAbsent(p, new ArrayList<Particle>());
        map.get(p).add(n);
    }

    public List<Particle> getNeighbours(Particle p) {
        return new ArrayList<Particle>(map.get(p));
    }

    public Map<Particle, List<Particle>> getMap(){
        return new HashMap<>(map);
    }

}
