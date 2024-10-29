package ar.edu.itba.ss.tp5;

import ar.edu.itba.ss.tp5.fieldLines.FieldLine;
import ar.edu.itba.ss.tp5.fieldLines.OutFieldLine;
import ar.edu.itba.ss.tp5.fieldLines.TryFieldLine;
import ar.edu.itba.ss.tp5.vector.Position;

import java.util.List;

public class Field {

    private final TryFieldLine left;
    private final OutFieldLine top, right, bottom;

    public Field(double width, double height) {
        this.left = new TryFieldLine(new Position(0, 0), new Position(0, height));
        this.top = new OutFieldLine(new Position(0, height), new Position(width, height));
        this.right = new OutFieldLine(new Position(width, height), new Position(width, 0));
        this.bottom = new OutFieldLine(new Position(width, 0), new Position(0, 0));
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
