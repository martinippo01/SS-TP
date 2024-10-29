package ar.edu.itba.ss.tp5;

import ar.edu.itba.ss.tp5.players.BluePlayer;
import ar.edu.itba.ss.tp5.players.Player;
import ar.edu.itba.ss.tp5.players.RedPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationContext {

    private static final Map<String, SimulationContext> CONTEXT_BY_PLAYER = new HashMap<>();
    private static final Map<Field, SimulationContext> CONTEXT_BY_FIELD = new HashMap<>();
    private static final Object lock = new Object();

    private final Field field;
    private final RedPlayer redPlayer;
    private final List<BluePlayer> bluePlayers;
    private final boolean forAnimation;

    public SimulationContext(Field field, RedPlayer redPlayer, List<BluePlayer> bluePlayers, boolean forAnimation) {
        this.field = field;
        this.redPlayer = redPlayer;
        this.bluePlayers = bluePlayers;
        synchronized (lock) {
            CONTEXT_BY_PLAYER.put(redPlayer.getId(), this);
            bluePlayers.forEach(player -> CONTEXT_BY_PLAYER.put(player.getId(), this));
            CONTEXT_BY_FIELD.put(field, this);
        }
        this.forAnimation = forAnimation;
    }

    public static SimulationContext get(Player player) {
        SimulationContext context;
        synchronized (lock) {
            context = CONTEXT_BY_PLAYER.get(player.getId());
        }
        if (context == null) {
            throw new IllegalArgumentException("Player not found in any context");
        }
        return context;
    }

    public static SimulationContext get(Field field) {
        SimulationContext context;
        synchronized (lock) {
            context = CONTEXT_BY_FIELD.get(field);
        }
        if (context == null) {
            throw new IllegalArgumentException("Field not found in any context");
        }
        return context;
    }

    public RedPlayer getRedPlayer() {
        return redPlayer;
    }

    public List<BluePlayer> getBluePlayers() {
        return bluePlayers;
    }

    public Field getField() {
        return field;
    }

    public boolean isForAnimation() {
        return forAnimation;
    }
}
