package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;

public class Cim {

    private final Plane plane;
    private final double rc;

    public Cim(Plane plane, double rc) {
        this.plane = plane;
        this.rc = rc;
    }

    private boolean areParticlesNeighbours(Particle p1, Particle p2) {
        // rc >= sqrt((x2 − x1)^2 + (y2 − y1)^2) − (r2 + r1)
        final double x1 = p1.getX(),
                x2 = p2.getX(),
                y1 = p1.getY(),
                y2 = p2.getY(),
                r1 = p1.getRadius(),
                r2 = p2.getRadius();
        final double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) - (r2 + r1);
        return distance <= rc;
    }

    private List<Particle> getNeighboursOfParticleFromList(Particle particle, List<Particle> maybeNeighbourParticles, int startIndex) {
        List<Particle> neighbourParticles = new ArrayList<>();
        for (int i = startIndex; i < maybeNeighbourParticles.size(); i++) {
            Particle maybeNeighbourParticle = maybeNeighbourParticles.get(i);
            if (areParticlesNeighbours(particle, maybeNeighbourParticle)) {
                neighbourParticles.add(maybeNeighbourParticle);
            }
        }
        return neighbourParticles;
    }

    private void addNeighboursOfParticleFromCell(ParticleNeighbours particleNeighbours, Particle particle, Cell cell, int startIndex) {
        if (cell == null) {
            return;
        }
        this.getNeighboursOfParticleFromList(particle, cell.getInnerParticles(), startIndex).forEach(
                (neighbourParticle) -> {
                    particleNeighbours.addNeighbour(particle, neighbourParticle);
                    particleNeighbours.addNeighbour(neighbourParticle, particle);
                }
        );
    }

    public ParticleNeighbours getParticleNeighbours() {
        ParticleNeighbours particleNeighbours = ParticleNeighbours.getParticleNeighbours();
        for (Cell cell: plane.getCellsIterator()) {
            final List<Particle> cellParticles = cell.getInnerParticles();
            for (int i = 0; i < cellParticles.size(); i++) {
                Particle currentParticle = cellParticles.get(i);

                // Check particles from same cell
                this.addNeighboursOfParticleFromCell(particleNeighbours, currentParticle, cell, i+1);

                // Check particles from top cell
                Cell topCell = this.plane.getTopCell(cell);
                this.addNeighboursOfParticleFromCell(particleNeighbours, currentParticle, topCell, 0);

                // Check particles from top right cell
                Cell topRightCell = this.plane.getTopRightCell(cell);
                this.addNeighboursOfParticleFromCell(particleNeighbours, currentParticle, topRightCell, 0);

                // Check particles from right cell
                Cell rightCell = this.plane.getRightCell(cell);
                this.addNeighboursOfParticleFromCell(particleNeighbours, currentParticle, rightCell, 0);

                // Check particles from bottom right cell
                Cell bottomRightCell = this.plane.getBottomRightCell(cell);
                this.addNeighboursOfParticleFromCell(particleNeighbours, currentParticle, bottomRightCell, 0);
            }
        }
        return particleNeighbours;
    }
}

