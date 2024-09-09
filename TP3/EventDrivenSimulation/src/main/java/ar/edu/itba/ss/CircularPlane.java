package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.WallCrashType;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CircularPlane extends Plane {

    private final double radius;

    // (0,0) is the center of the circle
    public CircularPlane(double l) {
        super(l);
        this.radius = l/2;
    }

    // https://stackoverflow.com/questions/5837572/generate-a-random-point-within-a-circle-uniformly
    @Override
    public boolean generateParticle(double v0, double mass, double r) {
        Random rand = new Random();
        double rad = (radius - r) * Math.sqrt(rand.nextDouble());
        double angle = 2 * Math.PI * rand.nextDouble();
        double x = rad * Math.cos(angle);
        double y = rad * Math.sin(angle);
        Position p = new Position(x, y);
        return generateParticle(v0, mass, r, p);
    }

    @Override
    boolean isInside(Particle p) {
        double rad = p.getRadius();
        return Double.compare(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2),  Math.pow(radius - rad, 2)) <= 0;
    }

    // Cuadrática resuelta en Excalidraw
    @Override
    public List<Event> getCrashEventWithBorders(Particle p) {
        double vx = p.getVx(), vy = p.getVy(), x = p.getX(), y = p.getY(), rp = p.getRadius();
        double a = vy*vy + vx*vx;
        double b = 2*vx*x + 2*vy*y;
        double c = x*x + y*y - (radius-rp)*(radius-rp);
        Event event = new Event((-b+Math.sqrt(b*b-4*a*c))/(2*a), new WallCrash(p, WallCrashType.CIRCULAR));
        return Collections.singletonList(event);
    }

}
