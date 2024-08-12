package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Plane {

    private final double L;
    private final int M;
    private final boolean pacman;
    private final List<List<Cell>> matrix;

    public Plane(double L, int M, boolean pacman){
        this.L = L;
        this.M = M;
        this.pacman = pacman;
        this.matrix = new ArrayList<>();
        int cellId = 0;
        for (int row = 0; row < M ; row++) {
            matrix.add(new ArrayList<>());
            for (int column = 0; column < M ; column++) {
                matrix.get(row).add(new Cell(cellId, L/M, new Point(column * ((double) L/M),row * ((double) L/M))));
                cellId++;
            }
        }
    }

    public Iterable<Cell> getCellsIterator() {
        List<Cell> matrixCopy = new ArrayList<>();
        for (int row = 0; row < M; row++) {
            for (int column = 0; column < M; column++) {
                Cell cell = this.matrix.get(row).get(column);
                matrixCopy.add(cell.getCopy());
            }
        }
        return matrixCopy;
    }


    public void addParticle(Particle particle) {
        int row = (int) Math.floor(particle.getY()/(L/M));
        int col = (int) Math.floor(particle.getX()/(L/M));
        matrix.get(row).get(col).addParticle(particle);
    }

    private Cell getVirtualCell(Cell cell, double xOffset, double yOffset) {
        Point bottomLeft = new Point(cell.getBottomLeft().getX() + xOffset, cell.getBottomLeft().getY() + yOffset);
        List<Particle> innerParticles = cell.getInnerParticles().stream().map(
                (particle) -> new Particle(particle.getId(), particle.getX() + xOffset, particle.getY() + yOffset, particle.getRadius())
        ).collect(Collectors.toList());
        return new Cell(cell.getId(), cell.getLength(), bottomLeft, innerParticles);
    }

    public Cell getTopCell(Cell cell){
        int cellId = cell.getId();
        if(pacman) {
            if (cellId + M >= M*M) {
                Cell topCell = getCellById(cellId % M);
                return getVirtualCell(topCell, 0, L);
            }
            return getCellById(cellId + M);
        }
        return (cellId + M >= M * M) ? null : getCellById(cell.getId() + M);
    }

    public Cell getTopRightCell(Cell cell){
        int cellId = cell.getId();
        if(pacman) {
            // esquina derecha
            if(cellId == M*M - 1) {
                return getVirtualCell(getCellById(0), L, L);
            }
            // fila superior
            if(cellId+M >= M*M) {
                return getVirtualCell(getCellById(cellId % M + 1), 0, L);
            }
            // lado derecho
            if(cellId%M==M-1) {
                return getVirtualCell(getCellById(cellId + 1), L, 0);
            }
        }
        return (cellId + M >= M*M || (cellId + 1) % M == 0) ? null : getCellById(cellId + M + 1);
    }

    public Cell getRightCell(Cell cell){
        int cellId = cell.getId();
        if(pacman) {
            if ((cellId + 1) % M == 0) {
                return getVirtualCell(getCellById(cellId - M + 1), L, 0);
            }
            return getCellById(cellId + 1);
        }
        return ((cellId + 1) % M == 0) ? null : getCellById(cellId + 1);
    }

    public Cell getBottomRightCell(Cell cell){
        int cellId = cell.getId();
        if(pacman) {
            // Bottom right corner
            if(cellId==M-1) {
                return getVirtualCell(getCellById(M * (M - 1)), L, -L);
            }
            // Right side
            if(cellId%M==M-1){
                return getVirtualCell(getCellById((cellId/M -1)*M), L, 0);
            }
            // Bottom side
            if (cellId - M + 1 < 0) {
                return getVirtualCell(getCellById(M * (M - 1) + 1 + cellId), 0, -L);
            }
            return getCellById(cellId - M + 1);
        }
        if (cellId % M == M-1) {
            return null;
        }
        return (cellId - M + 1 < 0) ? null : getCellById(cell.getId() - M + 1);
    }

    private Cell getCellById(int id) {
        int row = id / M;
        int col = id % M;
        if(row >= M || row < 0 || col >= M || col < 0) {
            return null;
        }
        return matrix.get(row).get(col);
    }

    public List<Particle> getParticles(){
        List<Particle> toReturn = new ArrayList<>();
        matrix.forEach((r) -> r.forEach((cell) -> toReturn.addAll(cell.getInnerParticles())));
        return toReturn;
    }
}



