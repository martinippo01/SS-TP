package ar.edu.itba.ss.tp5;

import ar.edu.itba.ss.tp5.players.BluePlayer;
import ar.edu.itba.ss.tp5.players.RedPlayer;

import java.util.List;

public class SimulationContext {

    private final Field field;
    private final RedPlayer redPlayer;
    private final List<BluePlayer> bluePlayers;

    private static SimulationContext SINGLETON = null;

    private SimulationContext(Field field, RedPlayer redPlayer, List<BluePlayer> bluePlayers) {
        this.field = field;
        this.redPlayer = redPlayer;
        this.bluePlayers = bluePlayers;
    }

    public static SimulationContext of(Field field, RedPlayer redPlayer, List<BluePlayer> bluePlayers) {
        return SINGLETON == null ?
                SINGLETON = new SimulationContext(field, redPlayer, bluePlayers) : SINGLETON;
    }

    public static SimulationContext get() {
        if (SINGLETON == null) {
            throw new IllegalStateException("Context does not exist");
        }
        return SINGLETON;
    }

    public RedPlayer getRedPlayer() {
        return this.get().redPlayer;
    }

    public List<BluePlayer> getBluePlayers() {
        return this.get().bluePlayers;
    }

    public Field getField() {
        return this.get().field;
    }
}
