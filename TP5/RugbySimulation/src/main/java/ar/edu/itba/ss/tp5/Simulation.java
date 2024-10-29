package ar.edu.itba.ss.tp5;

import ar.edu.itba.ss.tp5.IO.SimulationParams;
import ar.edu.itba.ss.tp5.events.EndEvent;
import ar.edu.itba.ss.tp5.events.EndEventType;
import ar.edu.itba.ss.tp5.events.Event;
import ar.edu.itba.ss.tp5.events.StepEvent;
import ar.edu.itba.ss.tp5.fieldLines.FieldLine;
import ar.edu.itba.ss.tp5.players.BluePlayer;
import ar.edu.itba.ss.tp5.players.Player;
import ar.edu.itba.ss.tp5.players.RedPlayer;
import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.vector.Velocity;


import java.util.*;
import java.util.function.Consumer;

public class Simulation {

    private final String id;
    private final SimulationParams params;
    private final Consumer<Event> onEvent;
    private SimulationContext context;
    private ContractileParticleModel cpm;

    public Simulation(String id, SimulationParams params, Consumer<Event> onEvent) {
        this.id = id;
        this.params = params;
        this.context = null;
        this.onEvent = onEvent;
        this.cpm = null;
    }

    private BluePlayer generateBluePlayer(String id) {
        double radius = params.getRadiosMinAzul();
        double xMin = 0 + radius, xMax = params.getFieldW() - radius, yMin = 0 + radius, yMax = params.getFieldH() - radius;
        Random random = new Random();
        double x = xMin + (xMax - xMin) * random.nextDouble();
        double y = yMin + (yMax - yMin) * random.nextDouble();
        Position position = new Position(x, y);
        return new BluePlayer(id, position, new Velocity(), radius, params.getRadiosMinAzul(), params.getRadiosMaxAzul(), params.getvAzulMax(), params.getvAzulEscape(), params.getTauAzul());
    }

    private RedPlayer generateRedPlayer(String id) {
        double radius = params.getRadiosMinRojo();
        double x = params.getFieldW() - params.getRadiosMaxRojo(), y = params.getFieldH() / 2;
        Position position = new Position(x, y);
        return new RedPlayer(id, position, new Velocity(), radius, params.getRadiosMinRojo(), params.getRadiosMaxRojo(), params.getvRojoMax(), params.getvRojoEscape(), params.getTauRojo(), params.getAp(), params.getBp(), params.getVisibilityAngle());
    }

    public void prepare() {
        if (context != null) {
            throw new IllegalStateException("Simulation already prepared");
        }
        Field field = new Field(params.getFieldW(), params.getFieldH());
        RedPlayer redPlayer = generateRedPlayer(this.id + "-red");
        List<BluePlayer> bluePlayers = new ArrayList<>();
        double redMinDistance = params.getMin_distance_to_red();
        long nj = params.getNj();
        long i = 0;
        while (i < nj) {
            BluePlayer bluePlayer = generateBluePlayer(this.id + "-blue-" + i);
            if (!bluePlayer.isOverlapping(redPlayer, redMinDistance) && bluePlayers.stream().noneMatch(bluePlayer::isOverlapping)) {
                bluePlayers.add(bluePlayer);
                i++;
            }
        }
        context = new SimulationContext(field, redPlayer, bluePlayers, params.getForAnimation());
        List<Player> allPlayers = new ArrayList<>(bluePlayers);
        allPlayers.add(redPlayer);
        this.cpm = new ContractileParticleModel(allPlayers, field, params.getBeta());
    }

    private BluePlayer getCollidingBluePlayer(RedPlayer redPlayer) {
        return context.getBluePlayers().stream().filter(redPlayer::isOverlapping).findFirst().orElse(null);
    }

    private FieldLine getCollidingFieldLine(RedPlayer redPlayer) {
        return context.getField().getLines().stream().filter(redPlayer::isOverlapping).findFirst().orElse(null);
    }

    private EndEvent getEndEvent(RedPlayer redPlayer) {
        EndEventType type = null;
        BluePlayer bluePlayer = getCollidingBluePlayer(redPlayer);
        if (bluePlayer != null) {
            type = EndEventType.TACKLE;
        }
        FieldLine fieldLine = getCollidingFieldLine(redPlayer);
        if (fieldLine != null) {
            type = fieldLine.getEndEventType();
        }
        return type == null ? null : new EndEvent(type, cpm.getTime(), context.getBluePlayers(), redPlayer);
    }

    public void runSimulation(){
        if (context == null) {
            throw new IllegalStateException("Simulation not prepared");
        }
        RedPlayer redPlayer = context.getRedPlayer();
        while (true) {
            if (context.isForAnimation())
                onEvent.accept(new StepEvent(cpm.getTime(), context.getBluePlayers(), redPlayer));
            cpm.nextStep();
            EndEvent endEvent = getEndEvent(redPlayer);
            if (endEvent != null) {
                onEvent.accept(endEvent);
                break;
            }
        }
    }
}
