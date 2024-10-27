package ar.edu.itba.ss.tp5.collision;

import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.players.Player;
import ar.edu.itba.ss.tp5.vector.Vector;

public class PlayerCollision extends Collision{

    private final Player p1, p2;

    public PlayerCollision(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public Vector getEscapeVersor() {
        double eX = p1.getPos().getX() - p2.getPos().getX();
        double eY = p1.getPos().getY() - p2.getPos().getY();
        return getVersor(eX, eY);
    }


}
