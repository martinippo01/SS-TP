package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Position;

import java.util.List;

public record TimeEvent(
        double time,
        List<Position> positions
) {}
