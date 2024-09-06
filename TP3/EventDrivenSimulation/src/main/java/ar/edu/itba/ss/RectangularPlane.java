package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.WallCrashType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RectangularPlane extends Plane {

    // (0,0) is the bottom left corner
    public RectangularPlane(double l) {
        super(l);
    }

    @Override
    public boolean generateParticle(double v0, double mass, double r) {
        Random rand = new Random();
        double x = rand.nextDouble() * l;
        double y = rand.nextDouble() * l;
        Position p = new Position(x, y);
        return generateParticle(v0, mass, r, p);
    }

    @Override
    boolean isInside(Position p) {
        return p.getX() >= 0 && p.getX() <= l && p.getY() >= 0 && p.getY() <= l;
    }

    @Override
    public List<Event> getCrashEventWithBorders(Particle p) {
        double vy = p.getVy();
        double vx = p.getVx();
        double rad = p.getRadius();
        double ry = p.getY();
        double rx = p.getX();

        List<Event> events = new ArrayList<>();

        // pared horizontal (piso y techo)
        if(vy>0) {
            events.add(new Event((this.l-rad-ry)/vy, new WallCrash(p, WallCrashType.Y)));
        } else if (vy<0) {
            events.add(new Event((rad-ry)/vy, new WallCrash(p, WallCrashType.Y)));
        }

        if(vx>0) {
            events.add(new Event((this.l-rad-rx)/vx, new WallCrash(p, WallCrashType.X)));
        } else if (vx<0) {
            events.add(new Event((rad-rx)/vx, new WallCrash(p, WallCrashType.X)));
        }


        return events;
    }

}
