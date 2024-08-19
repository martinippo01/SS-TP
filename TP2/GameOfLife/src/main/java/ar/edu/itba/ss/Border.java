package ar.edu.itba.ss;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Border {

    // TODO: Check what does 3D mean
    private final long x_min;
    private final long x_max;
    private final long y_min;
    private final long y_max;
    private final long z_min;
    private final long z_max;


    public Border(long x_min, long x_max, long y_min, long y_max, long z_min, long z_max) {
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
        this.z_min = z_min;
        this.z_max = z_max;
    }

    public long getX_min() {
        return x_min;
    }

    public long getX_max() {
        return x_max;
    }

    public long getY_max() {
        return y_max;
    }

    public long getY_min() {
        return y_min;
    }

    public long getZ_max() {
        return z_max;
    }

    public long getZ_min() {
        return z_min;
    }

    public boolean isPositionInside(Position position) {
        return position.getX() > x_max || position.getX() < x_min || position.getY() > y_max || position.getY() < y_min || position.getZ() > z_max || position.getZ() < z_min;
    }

    public Border getInnerBorder(boolean is3D, double areaPercentage) {
        if (areaPercentage <= 0 || areaPercentage >= 1) {
            throw new IllegalArgumentException("invalid areaPercentage");
        }
        long x_dist = x_max - x_min == 0 ? 1 : x_max - x_min;
        long y_dist = y_max - y_min == 0 ? 1 : y_max - y_min;
        long z_dist = (!is3D || z_max - z_min == 0) ? 1 : z_max - z_min;
        long adg = x_dist * y_dist * z_dist;
        long adi = (long) Math.floor(adg * areaPercentage);
        long rdi = (long) Math.floor(
                Math.pow(adi, is3D ? 1.0/3 : 1.0/2) / 2
        );
        long x_cc = x_dist / 2;
        long y_cc = y_dist / 2;
        long z_cc = z_dist / 2;
        return new Border(
                x_cc - rdi,
                x_cc + rdi,
                y_cc - rdi,
                y_cc + rdi,
                z_cc - rdi,
                z_cc + rdi
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
            Position p = new Position(rand.nextLong(x_min, x_max+1), rand.nextLong(y_min, y_max+1), rand.nextLong(z_min, z_max+1));
            if(positions.contains(p))
                continue;
            positions.add(p);
            counter++;
        }

        return positions;
    }

    private long getAmountOfCells() {
        return z_max==0 && z_min == 0 ? (x_max-x_min) * (y_max-y_min) : (z_max-z_min) * (x_max-x_min) * (y_max-y_min);
    }


}
