package ar.edu.itba.ss.tp5.collision;

import ar.edu.itba.ss.tp5.vector.Vector;

public abstract class Collision {

    protected Vector getVersor(double ex, double ey) {
        return new Vector(ex, ey).getVersor();
    }

    public abstract Vector getEscapeVersor();
}
