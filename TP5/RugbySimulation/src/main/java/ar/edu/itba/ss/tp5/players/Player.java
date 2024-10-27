package ar.edu.itba.ss.tp5.players;

import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.vector.Vector;
import ar.edu.itba.ss.tp5.vector.Velocity;

import java.util.Objects;

public abstract class Player {
    final private String id;
    private Position pos;
    private Velocity vel;
    private double radius;
    private final double minRadius, maxRadius;
    private final double desiredVel, escapeVel;
    private final double reactionTime;

    public Player(String id, Position pos, Velocity vel, double radius, double minRadius, double maxRadius, double desiredVel, double escapeVel, double reactionTime) {
        this.id = id;
        this.pos = pos;
        this.vel = vel;
        this.radius = radius;
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        this.desiredVel = desiredVel;
        this.escapeVel = escapeVel;
        this.reactionTime = reactionTime;
    }

    public String getId() {
        return id;
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
        vel.setX(vx);
    }

    public double getVx() {
        return vel.getX();
    }

    public void setVy(double vy) {
        vel.setY(vy);
    }

    public double getVy() {
        return vel.getY();
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public double getDesiredVel() {
        return desiredVel;
    }

    public double getEscapeVel() {
        return escapeVel;
    }

    public double getReactionTime() {
        return reactionTime;
    }

    abstract public Vector getTargetVersor();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
