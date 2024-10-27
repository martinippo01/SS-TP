package ar.edu.itba.ss.tp5.events;

import ar.edu.itba.ss.tp5.players.BluePlayer;
import ar.edu.itba.ss.tp5.players.RedPlayer;

import java.util.List;

public class EndEvent extends Event{

    private EndEventType endEventType;

    public EndEvent(EndEventType endEventType, double time, List<BluePlayer> bluePlayers, RedPlayer redPlayer) {
        super(endEventType.name(), time, bluePlayers, redPlayer);
        this.endEventType = endEventType;
    }
}
