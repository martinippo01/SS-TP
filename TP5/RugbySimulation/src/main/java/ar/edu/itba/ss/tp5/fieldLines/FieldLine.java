package ar.edu.itba.ss.tp5.fieldLines;

import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.events.EndEventType;

public abstract class FieldLine {

    final private Position start;
    final private Position end;

    public FieldLine(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public abstract EndEventType getEndEventType();

    public Position getClosestPosition(Position pos) {
        // Primer caso si es la de arriba o abajo, segundo caso si es la de la derecha
        return getStart().getY()==getEnd().getY()? new Position(pos.getX(),getStart().getY()) : new Position(getStart().getX(), pos.getY());
    }

}
