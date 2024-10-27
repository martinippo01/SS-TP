package ar.edu.itba.ss.tp5.fieldLines;

import ar.edu.itba.ss.tp5.Position;
import ar.edu.itba.ss.tp5.events.EndEventType;

public abstract class FieldLine {

    final private Position start;
    final private Position end;

    public FieldLine(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public abstract EndEventType getEndEventType();

}
