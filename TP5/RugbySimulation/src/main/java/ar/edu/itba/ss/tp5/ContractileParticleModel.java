package ar.edu.itba.ss.tp5;

import ar.edu.itba.ss.tp5.collision.Collision;
import ar.edu.itba.ss.tp5.collision.LineCollision;
import ar.edu.itba.ss.tp5.collision.PlayerCollision;
import ar.edu.itba.ss.tp5.fieldLines.FieldLine;
import ar.edu.itba.ss.tp5.players.Player;
import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.vector.Vector;
import ar.edu.itba.ss.tp5.vector.Velocity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractileParticleModel {

    private final double beta;
    private final List<Player> players;
    private final Field field;
    private double time;
    private final double dt;

    public ContractileParticleModel(List<Player> players, Field field, double beta) {
        this.beta = beta;
        this.players = players;
        this.field = field;
        this.time = 0;
        this.dt = getDt(players);
    }

    private static double getDt(List<Player> players) {
        double minDt = Double.MAX_VALUE;
        for (Player player : players) {
            double minRadius = player.getMinRadius();
            double maxSpeed = player.getDesiredVel();
            double escapeVel = player.getEscapeVel();
            double dt = minRadius / (2 * Math.max(maxSpeed, escapeVel));
            if (dt < minDt) {
                minDt = dt;
            }
        }
        return minDt;
    }

    public double getTime() {
        return time;
    }

    public void nextStep() {
        // Conseguir contactos y calcular Ve
        Map<Player, Velocity> escapeVelocities = getEscapeVelocities();

        // Ajustar los radios
        adjustRadii(escapeVelocities);

        // Conseguir los Vd
        Map<Player, Velocity> desiredVelocities = getDesiredVelocities();

        // Hacer Vd + Ve y actualizar X
        updateVelocitiesAndPositions(desiredVelocities, escapeVelocities);

        time += dt;
    }



    // ************************************************************************************************
    // Paso 1
    // ************************************************************************************************
    private Map<Player, Velocity> getEscapeVelocities() {
        Map<Player, List<Collision>> playerCollisions = new HashMap<>();
        for(Player p : players) {
            playerCollisions.put(p, new ArrayList<>());
        }

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            for(int j = i+1; j < players.size(); j++) {
                Player p2 = players.get(j);
                if(checkCollision(p, p2)) {
                    playerCollisions.get(p).add(new PlayerCollision(p, p2));
                    playerCollisions.get(p2).add(new PlayerCollision(p2, p));
                }
            }

            for (FieldLine line : field.getLines()) {
                if (checkCollision(p, line)) {
                    playerCollisions.get(p).add(new LineCollision(line, p));
                }
            }

        }

        Map<Player, Velocity> escapeVelocities = new HashMap<>();

        for(Player p : players) {

            List<Collision> collisions = playerCollisions.get(p);

            if (collisions.isEmpty()) continue;

            double x = 0, y = 0;
            for(Collision c : collisions) {
                Vector v = c.getEscapeVersor();
                x+=v.getX();
                y+=v.getY();
            }

            Vector escapeVelocityVersor = new Vector(x, y).getVersor();
            escapeVelocities.put(p,new Velocity(escapeVelocityVersor.getX() * p.getEscapeVel(),
                    escapeVelocityVersor.getY() * p.getEscapeVel()));

        }

        return escapeVelocities;
    }


    private boolean checkCollision(double x1, double y1, double x2, double y2, double minDistance) {
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)) < minDistance;
    }

    private boolean checkCollision(Player p1, Player p2) {
        double minDistance = p1.getRadius() + p2.getRadius();
        return checkCollision(p1.getX(), p1.getY(), p2.getX(), p2.getY(), minDistance);
    }

    private boolean checkCollision(Player p, FieldLine line) {
        Position closest = line.getClosestPosition(p.getPos());
        double minDistance = p.getRadius();
        return checkCollision(p.getX(), p.getY(), closest.getX(), closest.getY(), minDistance);
    }

    // ************************************************************************************************
    // Paso 2
    // ************************************************************************************************
    private void adjustRadii(Map<Player, Velocity> escapeVelocities) {
        for (Player p : players) {
            boolean hasCollided = escapeVelocities.get(p) != null;
            if (hasCollided)
                p.setRadius(p.getMinRadius());
            else {
                double deltaRadius = p.getMaxRadius() / (p.getReactionTime() / dt);
                p.setRadius(Math.min(p.getMaxRadius(), p.getRadius() + deltaRadius));
            }
        }
    }

    // ************************************************************************************************
    // Paso 3
    // ************************************************************************************************
    private Map<Player, Velocity> getDesiredVelocities() {
        Map<Player, Velocity> desiredVelocities = new HashMap<>();
        for (Player p : players) {
            Vector desiredVelocityVersor = p.getTargetVersor();
            double adjust = Math.pow((p.getRadius() - p.getMinRadius()) / (p.getMaxRadius() - p.getMinRadius()), beta);
            double desiredSpeed = p.getDesiredVel() * adjust;
            desiredVelocities.put(p, new Velocity(desiredVelocityVersor.getX() * desiredSpeed,
                    desiredVelocityVersor.getY() * desiredSpeed));
        }
        return desiredVelocities;
    }

    // ************************************************************************************************
    // Paso 4
    // ********************************************************************************
    private void updateVelocitiesAndPositions(Map<Player, Velocity> desiredVelocities,
                                              Map<Player, Velocity> escapeVelocities) {
        for (Player p : players) {
            Velocity desired = desiredVelocities.get(p), escape =
                    escapeVelocities.getOrDefault(p, new Velocity(0, 0));
            p.setVx(desired.getX() + escape.getX());
            p.setVy(desired.getY() + escape.getY());
            p.setX(p.getX() + p.getVx() * dt);
            p.setY(p.getY() + p.getVy() * dt);
        }
    }

}
