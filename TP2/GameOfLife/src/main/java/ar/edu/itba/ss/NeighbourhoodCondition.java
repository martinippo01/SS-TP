package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;

public enum NeighbourhoodCondition {

    VON_NEUMANN {
        @Override
        public boolean isNeighbour(Position center, Position other, int range) {
            // Manhattan distance in 2D or 3D
            return Math.abs(center.getX() - other.getX()) +
                    Math.abs(center.getY() - other.getY()) +
                    Math.abs(center.getZ() - other.getZ()) <= range;
        }
    },
    MOORE {
        @Override
        public boolean isNeighbour(Position center, Position other, int range) {
            // Chebyshev distance in 2D or 3D
            return Math.abs(center.getX() - other.getX()) <= range &&
                    Math.abs(center.getY() - other.getY()) <= range &&
                    Math.abs(center.getZ() - other.getZ()) <= range;
        }
    };

    public abstract boolean isNeighbour(Position center, Position other, int range);

    public List<Position> getNeighbours(Position center, int range, boolean is3D) {
        List<Position> toReturn = new ArrayList<>();

        for (long x = center.getX() - range; x <= center.getX() + range; x++) {
            for (long y = center.getY() - range; y <= center.getY() + range; y++) {
                if(is3D) {
                    for (long z = center.getZ() - range; z <= center.getZ() + range; z++) {
                        Position other = new Position(x, y, z);
                        if (!center.equals(other) && isNeighbour(center, other, range))
                            toReturn.add(other);
                    }
                }else{
                    Position other = new Position(x, y, 0);
                    if (!center.equals(other) && isNeighbour(center, other, range))
                        toReturn.add(other);
                }
            }
        }
        return toReturn;
    }
}
