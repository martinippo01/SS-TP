package ar.edu.itba.ss;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LifeGame {

    private final Set<Position> liveCellsByPosition;
    private final int r;
    private final NeighbourhoodCondition condition;
    private final Set<Integer> shouldKeepAlive;
    private final Set<Integer> shouldRevive;
    private final boolean is3D;
    private final Border border;
    private FinishCondition finishCondition;

    public LifeGame(Set<Position> liveCellsByPosition, InputData inputData) {
        this.liveCellsByPosition = liveCellsByPosition;
        this.r = inputData.getR();
        this.shouldKeepAlive = inputData.getShouldKeepAlive();
        this.condition = inputData.getCondition();
        this.shouldRevive = inputData.getShouldRevive();
        this.is3D = inputData.isIs3D();
        this.border = inputData.getBorder();
        this.finishCondition = null;
    }

    public Set<Position> getLiveCellsByPosition() {
        return Set.copyOf(liveCellsByPosition);
    }

    public Optional<FinishCondition> getFinishCondition() {
        return Optional.ofNullable(finishCondition);
    }

    public boolean evolve() {
        if (liveCellsByPosition.isEmpty()) {
            finishCondition = FinishCondition.ALL_DEAD;
            return true;
        }
        final Set<Position> deadCellsChecked = new HashSet<>();
        final Set<Position> deadCellsToRevive = new HashSet<>();
        final Set<Position> liveCellsToDie = new HashSet<>();
        for (Position cell : liveCellsByPosition) {
            if (border.isPositionOutside(cell)) {
                finishCondition = FinishCondition.BORDER;
                return true;
            }

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

        // Si no hay cambios, termina la simulación
        if (liveCellsToDie.isEmpty() && deadCellsToRevive.isEmpty()) {
            finishCondition = FinishCondition.NO_CHANGE;
            return true;
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

