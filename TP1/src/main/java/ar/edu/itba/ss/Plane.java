package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;

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
        int cellId = 1;
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

    public Cell getTopCell(Cell cell){
        int cellId = cell.getId();
        if(pacman)
            return getCellById((cellId + M > M*M) ? cellId % M : cellId + M);
        return (cellId + M > M * M) ? null : getCellById(cell.getId() + M);
    }

    public Cell getTopRightCell(Cell cell){
        int cellId = cell.getId();
        if(pacman) {
            // esquina derecha
            if(cellId==M*M)
                return getCellById(0);
            // fila superior
            if(cellId+M > M*M)
                return getCellById(cellId%M + 1);
            // lado derecho
            if(cellId%M==M-1)
                return getCellById(cellId + M + 1);
        }
        return (cellId + M >= M*M || cellId%M==0)? null : getCellById(cellId + M + 1);
    }

    public Cell getRightCell(Cell cell){
        int cellId = cell.getId();
        if(pacman)
            return getCellById(((cellId + 1) % M == 0) ? cellId - M :  cellId + 1);
        return ((cellId + 1) % M == 0) ? null : getCellById(M + 1);
    }

    public Cell getBottomRightCell(Cell cell){
        int cellId = cell.getId();
        if(pacman) {
            if(cellId==M-1)
                return getCellById( M * (M - 1) + 1);
            if(cellId%M==M-1){
                return getCellById((cellId/M -1)*M);
            }
            return getCellById((cellId - M + 1 < 0) ? M * (M - 1) + 1 + cellId : cellId - M + 1);
        }
        return (cellId - M + 1 < 0) ? null : getCellById(cell.getId() - M + 1);
    }

    private Cell getCellById(int id){
        int row = id / M -1;
        int col = id % M;
        if(row >= M || row < 0 || col >= M || col < 0)
            return null;
        return matrix.get(row).get(col);
    }
}



