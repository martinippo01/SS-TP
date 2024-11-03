package ar.edu.itba.ss.tp5.players;

import ar.edu.itba.ss.tp5.Field;
import ar.edu.itba.ss.tp5.SimulationContext;
import ar.edu.itba.ss.tp5.fieldLines.FieldLine;
import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.vector.Vector;
import ar.edu.itba.ss.tp5.vector.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RedPlayer extends Player {

    private final double maxAngleVisibility, ap, bp;

    public RedPlayer(String id, Position pos, Velocity vel, double radius, double minRadius, double maxRadius, double desiredVel, double escapeVel, double reactionTime, double ap, double bp, double maxAngleVisibility) {
        super(id,pos,vel,radius,minRadius,maxRadius,desiredVel,escapeVel,reactionTime);
        this.ap = ap;
        this.bp = bp;
        this.maxAngleVisibility = maxAngleVisibility;
    }

    @Override
    public Vector getTargetVersor() {
        SimulationContext context = SimulationContext.get(this);
        List<BluePlayer> bluePlayers = context.getBluePlayers();
        Field field = context.getField();

        List<Vector> ncs = new ArrayList<>();
        for (BluePlayer bluePlayer : bluePlayers) {
            Optional<Vector> nc = getNcIfVisible(bluePlayer.getPos());
            nc.ifPresent(ncs::add);
        }

        for (FieldLine fieldLine : field.getOutLines()) {
            Position closestPosition = fieldLine.getClosestPosition(getPos());
            Optional<Vector> nc = getNcIfVisible(closestPosition);
            nc.ifPresent(ncs::add);
        }

        Vector nc = ncs.stream().reduce(Vector::add).orElse(new Vector(0, 0));
        Vector eit = new Vector(-1, 0).getVersor();
        return new Vector(nc.getX() + eit.getX(), nc.getY() + eit.getY()).getVersor();
    }

    private Optional<Vector> getNcIfVisible(Position obstaclePosition) {
        Vector obstacleVector = new Vector(obstaclePosition.getX() - getX(), obstaclePosition.getY() - getY());
        Vector desiredDirectionVersor = new Vector(-1, 0).getVersor();

        Vector obstacleVersor = obstacleVector.getVersor();
        double distance = obstacleVector.module();

        // https://en.wikipedia.org/wiki/Dot_product#Geometric_definition
//        double dotProduct = desiredDirectionVersor.dot(obstacleVersor);
//        double cosTheta = dotProduct / (desiredDirectionVersor.module() * obstacleVersor.module());
//        double theta = Math.acos(cosTheta);
//
//        if (obstacleVersor.getY() > 0) {
//            theta = - (Math.PI - theta);
//        } else {
//            theta = Math.PI - theta;
//        }
//
//        if (Math.abs(theta) > maxAngleVisibility) {
//            return Optional.empty();
//        }

        Vector eij = new Vector(-obstacleVersor.getX(), -obstacleVersor.getY()).getVersor();
        // We no longer take into account cos(theta)
        double magnitude = Math.abs(ap * Math.exp(-distance / bp));
        Vector nc = new Vector(magnitude * eij.getX(), magnitude * eij.getY());
        return Optional.of(nc);
    }

}
