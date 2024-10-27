package ar.edu.itba.ss.tp5.players;

import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.SimulationContext;
import ar.edu.itba.ss.tp5.vector.Vector;
import ar.edu.itba.ss.tp5.vector.Velocity;

public class BluePlayer extends Player {

    public BluePlayer(String id, Position pos, Velocity vel, double radius, double minRadius, double maxRadius, double desiredVel, double escapeVel, double reactionTime) {
        super(id, pos, vel, radius, minRadius, maxRadius, desiredVel, escapeVel, reactionTime);
    }

    @Override
    public Vector getTargetVersor() {
        SimulationContext context = SimulationContext.get(this);
        RedPlayer redPlayer = context.getRedPlayer();
        return new Vector(redPlayer.getX() - getX(), redPlayer.getY() - getY()).getVersor();
    }
}
