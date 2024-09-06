package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.CircularPlane;
import ar.edu.itba.ss.Plane;
import ar.edu.itba.ss.RectangularPlane;

public enum PlaneType {
    BOX {
        @Override
        public Plane createPlane(double l) {
            return new RectangularPlane(l);
        }
    },
    CIRCULAR {
        @Override
        public Plane createPlane(double l) {
            return new CircularPlane(l);
        }
    };

    public abstract Plane createPlane(double l);
}
