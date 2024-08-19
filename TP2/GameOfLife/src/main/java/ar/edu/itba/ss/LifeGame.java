package ar.edu.itba.ss;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LifeGame {

    private final Set<Position> liveCellsByPosition;
    private final int r;
    private final NeighbourhoodCondition condition;
    private final Set<Integer> shouldKeepAlive;
    private final Set<Integer> shouldRevive;
    private final boolean is3D;
    private final Border border;

    public LifeGame(Set<Position> liveCellsByPosition, InputData inputData) {
        this.liveCellsByPosition = liveCellsByPosition;
        this.r = inputData.getR();
        this.shouldKeepAlive = inputData.getShouldKeepAlive();
        this.condition = inputData.getCondition();
        this.shouldRevive = inputData.getShouldRevive();
        this.is3D = inputData.isIs3D();
        this.border = inputData.getBorder();
    }

    public Set<Position> getLiveCellsByPosition() {
        return Set.copyOf(liveCellsByPosition);
    }

    public boolean evolve() {
        final Set<Position> deadCellsChecked = new HashSet<>();
        final Set<Position> deadCellsToRevive = new HashSet<>();
        final Set<Position> liveCellsToDie = new HashSet<>();
        for (Position cell : liveCellsByPosition) {
            if (!border.isPositionInside(cell))
                return true;

            List<Position> neighbours = condition.getNeighbours(cell, r, is3D);
            if (!shouldKeepAlive.contains(getAmountLiveNeighbours(neighbours)))
                liveCellsToDie.add(cell);

            for (Position neighbour : neighbours) {

                if (deadCellsChecked.contains(neighbour) || liveCellsByPosition.contains(neighbour))
                    continue;

                if (shouldRevive.contains(getAmountLiveNeighbours(neighbour)))
                    deadCellsToRevive.add(neighbour);

                deadCellsChecked.add(neighbour);
            }
        }

        // Efectuando cambios
        liveCellsByPosition.removeAll(liveCellsToDie);
        liveCellsByPosition.addAll(deadCellsToRevive);

        return false;
    }

    private int getAmountLiveNeighbours(Position cell) {
        return getAmountLiveNeighbours(condition.getNeighbours(cell, r, is3D));
    }

    private int getAmountLiveNeighbours(Iterable<Position> neighbours) {
        int aliveNeighbours = 0;
        for (Position neighbour : neighbours) {
            aliveNeighbours += liveCellsByPosition.contains(neighbour) ? 1 : 0;
        }
        return aliveNeighbours;
    }

}

