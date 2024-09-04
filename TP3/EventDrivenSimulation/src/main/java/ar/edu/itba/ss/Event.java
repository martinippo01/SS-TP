package ar.edu.itba.ss;

public class Event implements Comparable<Event>{
    private final double tc;
    private final Crash crash;

    public Event(double tc, Crash crash) {
        this.tc = tc;
        this.crash = crash;
    }

    @Override
    public int compareTo(Event other){
        return Double.compare(tc, other.tc);
    }

    public EventOutput toJSON(){
        // TODO
    }

}

