package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private final int id;
    private final double length;
    private final Point bottomLeft;
    private final List<Particle> innerParticles;

    public Cell(int id, double length, Point bottomLeft) {
        this.id = id;
        this.length = length;
        this.bottomLeft = bottomLeft;
        this.innerParticles = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    private Point getBottomRight() {
        return new Point(this.bottomLeft.getX() + length, this.bottomLeft.getY());
    }

    private Point getTopLeft() {
        return new Point(this.bottomLeft.getX(), this.bottomLeft.getY() + length);
    }

    public Cell getCopy() {
        return new Cell(this.id, this.length, this.bottomLeft);
    }

    public List<Particle> getInnerParticles() {
        return new ArrayList<>(this.innerParticles);
    }

    public void addParticle(Particle particle) {
        final double particleX = particle.getX();
        final double particleY = particle.getY();
        final Point cellBottomRight = getBottomRight();
        final Point cellTopLeft = getTopLeft();
        if (particleX < this.bottomLeft.getX() ||
                particleX > cellBottomRight.getX() ||
                particleY < this.bottomLeft.getY() ||
                particleY > cellTopLeft.getY()) {
            throw new IllegalArgumentException("Particle is not inside the cell");
        }
        this.innerParticles.add(particle);
    }
}
