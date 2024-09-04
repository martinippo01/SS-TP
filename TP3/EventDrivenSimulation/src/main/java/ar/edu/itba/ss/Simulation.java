package ar.edu.itba.ss;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Simulation {

    private final int MAX_ITER = 1000;
    private Plane plane;

    private PriorityQueue<Event> events;

    private long n;

    private double tcAbsolute;

    public Simulation(long n, String p) {
        this.tcAbsolute=0;
        this.n = n;
        this.events = new PriorityQueue<>();
        this.plane = "circular".equals(p)? new CircularPlane() : new RectangularPlane();
    }

    public void prepare(double mass, double radious, double speed, List<Obstacle> obstacles){
        plane.setObstacles(obstacles);
        Random rand = new Random();

        int i = 0;
        while(i < n) {
            double vx = rand.nextDouble(-speed, speed);
            Velocity v = new Velocity(vx, Math.sqrt(1-vx));
            if(plane.generateParticle(v, mass, radious))
                i++;
        }
    }

    public void run(){
        List<Particle> ps = plane.getParticles();
        List<Obstacle> os = plane.getObstacles();

        int i = 0;
        while(i<MAX_ITER) {


            i++;
        }
    }



}
