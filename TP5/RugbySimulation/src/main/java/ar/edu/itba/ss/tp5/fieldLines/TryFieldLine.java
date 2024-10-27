package ar.edu.itba.ss.tp5.fieldLines;

import ar.edu.itba.ss.tp5.Position;
import ar.edu.itba.ss.tp5.events.EndEventType;

public class TryFieldLine extends FieldLine {

    public TryFieldLine(Position start, Position end) {
        super(start, end);
    }

    @Override
    public EndEventType getEndEventType() {
        return EndEventType.TRY;
    }
}
