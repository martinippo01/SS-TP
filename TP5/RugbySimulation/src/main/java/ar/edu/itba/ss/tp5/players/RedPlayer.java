package ar.edu.itba.ss.tp5.players;

import ar.edu.itba.ss.tp5.Position;
import ar.edu.itba.ss.tp5.Velocity;

public class RedPlayer extends Player {

    public RedPlayer(Position pos, Velocity vel, double radius) {
        super(pos,vel,radius);
    }

    @Override
    public Position getMainTargetPosition() {
        return new Position(0, getY());
    }

}
