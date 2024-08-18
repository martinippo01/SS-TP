package ar.edu.itba.ss;

public class Border {

    private final int x_min;
    private final int x_max;
    private final int y_min;
    private final int y_max;
    private final int z_min;
    private final int z_max;


    public Border(int x_min, int x_max, int y_min, int y_max, int z_min, int z_max) {
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
        this.z_min = z_min;
        this.z_max = z_max;
    }

    public boolean isPositionInside(Position position) {
        return position.getX() > x_max || position.getX() < x_min || position.getY() > y_max || position.getY() < y_min || position.getZ() > z_max || position.getZ() < z_min;
    }

}
