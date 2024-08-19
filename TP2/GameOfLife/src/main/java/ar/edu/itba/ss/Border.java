package ar.edu.itba.ss;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Border {

    // TODO: Check what does 3D mean
    private final long xMin;
    private final long xMax;
    private final long yMin;
    private final long yMax;
    private final long zMin;
    private final long zMax;


    public Border(long xMin, long xMax, long yMin, long yMax, long zMin, long zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    public long getXMin() {
        return xMin;
    }

    public long getXMax() {
        return xMax;
    }

    public long getYMax() {
        return yMax;
    }

    public long getYMin() {
        return yMin;
    }

    public long getZMax() {
        return zMax;
    }

    public long getZMin() {
        return zMin;
    }

    public boolean isPositionOutside(Position position) {
        return position.getX() > xMax || position.getX() < xMin || position.getY() > yMax || position.getY() < yMin || position.getZ() > zMax || position.getZ() < zMin;
    }

    public Border getInnerBorder(boolean is3D, double areaPercentage) {
        if (areaPercentage <= 0 || areaPercentage >= 1) {
            throw new IllegalArgumentException("invalid areaPercentage");
        }
        long xDist = xMax - xMin == 0 ? 1 : xMax - xMin;
        long yDist = yMax - yMin == 0 ? 1 : yMax - yMin;
        long zDist = (!is3D || zMax - zMin == 0) ? 1 : zMax - zMin;
        long adg = xDist * yDist * zDist;
        long adi = (long) Math.floor(adg * areaPercentage);
        long rdi = (long) Math.floor(
                Math.pow(adi, is3D ? 1.0/3 : 1.0/2) / 2
        );
        long xCc = xDist / 2;
        long yCc = yDist / 2;
        long zCc = zDist / 2;
        return new Border(
                xCc - rdi,
                xCc + rdi,
                yCc - rdi,
                yCc + rdi,
                is3D? zCc - rdi : 0,
                is3D? zCc + rdi : 0
        );
    }

    public Set<Position> generateInitialPositions(double percentage) {
        if(percentage<0.0 || percentage> 1)
            return Collections.emptySet();
        long amountOfCellsAlive = (long) Math.floor(getAmountOfCells() * percentage);

        Random rand = new Random();
        Set<Position> positions = new HashSet<>();
        int counter = 0;
        while(counter<amountOfCellsAlive) {
            Position p = new Position(xMin + (long) (Math.random() * (xMax - xMin)),yMin + (long) (Math.random() * (yMax - yMin)), zMin + (long) (Math.random() * (zMax - zMin)));
            if(positions.contains(p))
                continue;
            positions.add(p);
            counter++;
        }

        return positions;
    }

    private long getAmountOfCells() {
        return zMax ==0 && zMin == 0 ? (xMax - xMin) * (yMax - yMin) : (zMax - zMin) * (xMax - xMin) * (yMax - yMin);
    }


}
