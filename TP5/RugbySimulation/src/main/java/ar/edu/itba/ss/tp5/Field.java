package ar.edu.itba.ss.tp5;

import ar.edu.itba.ss.tp5.fieldLines.FieldLine;
import ar.edu.itba.ss.tp5.fieldLines.OutFieldLine;
import ar.edu.itba.ss.tp5.fieldLines.TryFieldLine;
import ar.edu.itba.ss.tp5.vector.Position;

import java.util.List;

public class Field {

    private final TryFieldLine left;
    private final OutFieldLine top, right, bottom;

    public Field(double length, double width) {
        this.left = new TryFieldLine(new Position(0, 0), new Position(0, width));
        this.top = new OutFieldLine(new Position(0, width), new Position(length, width));
        this.right = new OutFieldLine(new Position(length, width), new Position(length, 0));
        this.bottom = new OutFieldLine(new Position(length, 0), new Position(0, 0));
    }

    public FieldLine getLeft() {
        return left;
    }

    public FieldLine getTop() {
        return top;
    }

    public FieldLine getRight() {
        return right;
    }

    public FieldLine getBottom() {
        return bottom;
    }

    public List<FieldLine> getLines() {
        return List.of(left, top, right, bottom);
    }

    public List<OutFieldLine> getOutLines() {
        return List.of(top, right, bottom);
    }
}
