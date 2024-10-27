package ar.edu.itba.ss.tp5.players;

import ar.edu.itba.ss.tp5.Position;
import ar.edu.itba.ss.tp5.Velocity;

public abstract class Player {
    private static long NEXT_PLAYER_ID = 1;

    final private long id;
    private Position pos;
    private Velocity vel;
    private double radius;

    public Player(Position pos, Velocity vel, double radius) {
        this.id = NEXT_PLAYER_ID++;
        this.pos = pos;
        this.vel = vel;
        this.radius = radius;
    }

    public void setX(double x) {
        pos.setX(x);
    }

    public double getX() {
        return pos.getX();
    }

    public void setY(double y) {
        pos.setY(y);
    }

    public double getY() {
        return pos.getY();
    }

    public Position getPos() {
        return pos;
    }

    public void setVx(double vx) {
        vel.setVX(vx);
    }

    public double getVx() {
        return vel.getVX();
    }

    public void setVy(double vy) {
        vel.setVY(vy);
    }

    public double getVy() {
        return vel.getVY();
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    abstract public Position getMainTargetPosition();
}
