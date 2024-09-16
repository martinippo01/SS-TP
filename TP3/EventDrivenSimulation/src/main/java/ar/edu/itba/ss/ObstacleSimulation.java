package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;

import java.util.List;

public class ObstacleSimulation extends Simulation {

    private final List<Obstacle> obstacles;

    public ObstacleSimulation(Plane p, long n, double maxTime, List<Obstacle> obstacles, InputData inputData) {
        super(p, n, maxTime, inputData);
        this.obstacles = obstacles;
    }

    @Override
    public void prepare(double mass, double radius, double speed) {
        Plane plane = getPlane();
        plane.addObstacles(obstacles);
        super.prepare(mass, radius, speed);
    }
}
