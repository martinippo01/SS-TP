package ar.edu.itba.ss.tp5.events;

import ar.edu.itba.ss.tp5.players.BluePlayer;
import ar.edu.itba.ss.tp5.players.RedPlayer;

import java.util.List;

public abstract class Event {

    private final String name;
    private final double time;
    private final List<BluePlayer> bluePlayers;
    private final RedPlayer redPlayer;

    public Event(String name, double time, List<BluePlayer> bluePlayers, RedPlayer redPlayer) {
        this.name = name;
        this.time = time;
        this.bluePlayers = bluePlayers;
        this.redPlayer = redPlayer;
    }
}
