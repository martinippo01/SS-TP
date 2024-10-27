package ar.edu.itba.ss.tp5.players;

import ar.edu.itba.ss.tp5.Position;
import ar.edu.itba.ss.tp5.SimulationContext;
import ar.edu.itba.ss.tp5.Velocity;

public class BluePlayer extends Player {

    public BluePlayer(Position position, Velocity velocity, double radius) {
        super(position, velocity, radius);
    }

    @Override
    public Position getMainTargetPosition() {
        SimulationContext context = SimulationContext.get();
        RedPlayer redPlayer = context.getRedPlayer();
        return redPlayer.getPos();
    }
}
