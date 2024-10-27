package ar.edu.itba.ss.tp5.collision;

import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.fieldLines.FieldLine;
import ar.edu.itba.ss.tp5.players.Player;
import ar.edu.itba.ss.tp5.vector.Vector;

public class LineCollision extends Collision{
    private FieldLine line;
    private Player player;

    public LineCollision(FieldLine line, Player player) {
        this.line = line;
        this.player = player;
    }

    @Override
    public Vector getEscapeVersor() {
        Position nearestPoint = line.getClosestPosition(player.getPos());
        double eX = player.getPos().getX() - nearestPoint.getX();
        double eY = player.getPos().getY() - nearestPoint.getY();
        return getVersor(eX, eY);
    }
}
