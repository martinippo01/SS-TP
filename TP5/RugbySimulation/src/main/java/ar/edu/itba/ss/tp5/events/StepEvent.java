package ar.edu.itba.ss.tp5.events;

import ar.edu.itba.ss.tp5.players.BluePlayer;
import ar.edu.itba.ss.tp5.players.RedPlayer;

import java.util.List;

public class StepEvent extends Event {

    private static final String NAME = "STEP";

    public StepEvent(double time, List<BluePlayer> bluePlayers, RedPlayer redPlayer) {
        super(NAME, time, bluePlayers, redPlayer);
    }
}
