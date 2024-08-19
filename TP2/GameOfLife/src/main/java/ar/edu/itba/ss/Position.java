package ar.edu.itba.ss;

import java.util.Objects;

public class Position {
    private final long x;

    private final long y;

    private final long z;

    public Position(long x, long y) {
        this(x, y, 0);
    }

    public Position(long x, long y, long z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y && z == position.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
