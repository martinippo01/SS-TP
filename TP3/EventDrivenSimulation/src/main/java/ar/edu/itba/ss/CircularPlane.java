package ar.edu.itba.ss;

import java.util.Collections;
import java.util.List;

public class CircularPlane extends Plane{

    public CircularPlane(double l) {
        super(l);
    }

    @Override
    public boolean generateParticle(double v0, double mass, double r) {
        return false;
    }

    @Override
    public boolean generateParticle(double v0, double mass, double r, Position p) {
        return false;
    }

    @Override
    public List<Event> getCrashEventWithBorders(Particle p) { return Collections.emptyList();}

}
