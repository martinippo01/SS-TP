package ar.edu.itba.ss;

import java.util.Objects;

public class Position {
    private final int x;

    private final int y;

    private final int z;

    public Position(int x, int y) {
        this(x, y, 0);
    }

    public Position(int x, int y, int z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
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

    public Position getTop() {
        return new Position(x, y-1, z);
    }

    public Position getBottom() {
        return new Position(x, y+1, z);
    }

    public Position getLeft() {
        return new Position(x-1, y, z);
    }

    public Position getRight() {
        return new Position(x+1, y, z);
    }

    public Position getUp() {
        return new Position(x, y, z+1);
    }

    public Position getDown() {
        return new Position(x, y, z-1);
    }
}
