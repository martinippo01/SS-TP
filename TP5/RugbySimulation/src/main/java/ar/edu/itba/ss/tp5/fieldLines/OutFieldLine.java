package ar.edu.itba.ss.tp5.fieldLines;

import ar.edu.itba.ss.tp5.vector.Position;
import ar.edu.itba.ss.tp5.events.EndEventType;

// Línea Horizontal
public class OutFieldLine extends FieldLine {

    public OutFieldLine(Position start, Position end) {
        super(start, end);
    }

    @Override
    public EndEventType getEndEventType() {
        return EndEventType.OUT;
    }
}
