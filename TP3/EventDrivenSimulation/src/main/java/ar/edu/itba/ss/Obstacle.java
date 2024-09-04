package ar.edu.itba.ss;

public class Obstacle {
    Position p;
    double radius;
    double mass;

    public Obstacle(Position p, double radius, double mass){
        this.p = p;
        this.radius = radius;
        this.mass = mass;
    }
}
