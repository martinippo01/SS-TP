package ar.edu.itba.ss.tp5;

public abstract class Player {
    final private int id;
    private Position pos;
    private Velocity vel;
    private double radious;

    public Player(int id, Position pos, Velocity vel, double radious) {
        this.id = id;
        this.pos = pos;
        this.vel = vel;
        this.radious = radious;
    }
}
