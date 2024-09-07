package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.OutputData;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Simulation {

    private final Plane plane;
    private final PriorityQueue<Event> events;
    private final long n;
    private double tcAbsolute;
    private final double maxTime;
    private final BiConsumer<Simulation, Event> onEvent;
    private final Consumer<Simulation> onEnd;

    public Simulation(Plane plane, long n, long maxTime, BiConsumer<Simulation, Event> onEvent, Consumer<Simulation> onEnd) {
        this.tcAbsolute = 0;
        this.n = n;
        this.events = new PriorityQueue<>();
        this.plane = plane;
        this.maxTime = maxTime;
        this.onEvent = onEvent;
        this.onEnd = onEnd;
    }

    public void prepare(double mass, double radius, double speed, List<Obstacle> obstacles){
        plane.setObstacles(obstacles);

        int i = 0;
        // Step 1: Generate n particles
        while(i < n) {
            if(plane.generateParticle(speed, mass, radius)) {
                i++;
            }
        }
    }

    private Optional<Event> getNextEventForParticle(Particle p, int particleIndexStart){
        PriorityQueue<Event> pEvents = new PriorityQueue<>();
        plane.getCrashEventWithBorders(p).stream()
                .peek(event -> event.setTc(event.getTc()+tcAbsolute))
                .forEach(pEvents::add);
        BiConsumer<Double, Crash> eventAdder = (tc, c) -> pEvents.add(new Event(tc + tcAbsolute, c));
        for (Obstacle o : plane.getObstacles()) {
            p.calculateTimeForCrash(o)
                    .ifPresent(tc -> eventAdder.accept(tc, new ObstacleCrash(p, o)));
        }
        for (int t = particleIndexStart; t < plane.getParticles().size(); t++) {
            Particle p2 = plane.getParticles().get(t);
            if (p.equals(p2)) {
                continue;
            }
            p.calculateTimeForCrash(p2)
                    .ifPresent(tc -> eventAdder.accept(tc, new ParticlesCrash(p, p2)));
        }
        return Optional.ofNullable(pEvents.poll());
    }

    public void run() {
        List<Particle> ps = plane.getParticles();

        // Step 2: Generate the first events for each particle
        for(int j = 0; j<ps.size() ; j++) {
            getNextEventForParticle(ps.get(j), j+1)
                    .ifPresent(events::add);
        }

        while(true) {
            // Step 3: Get the next event
            Event nextEvent = events.poll();
            if (nextEvent == null) {
                break;
            }

            double tc = nextEvent.getTc();
            if (tc > maxTime) {
                break;
            }

            // Step 4: Advance all particles to the time of the event
            double relativeTime = tc - tcAbsolute;
            for(Particle p : ps) {
                p.advance(relativeTime);
            }

            // Step 5: Execute the event
            Crash nextCrash = nextEvent.getCrash();
            nextCrash.execute();

            // Step 6: Output the event
            onEvent.accept(this, nextEvent);

            // Step 7: Remove all events involving the particles that crashed
            List<Particle> particlesInvolved = nextCrash.getCrashedParticles();
            events.removeIf(
                    e -> e.getCrash().getCrashedParticles().stream().anyMatch(particlesInvolved::contains)
            );

            // Step 8: Generate new events for the particles that crashed
            tcAbsolute = nextEvent.getTc();
            for(Particle p : particlesInvolved) {
                getNextEventForParticle(p, 0)
                        .ifPresent(events::add);
            }
        }
        onEnd.accept(this);
    }

    public Plane getPlane() {
        return plane;
    }

    public PriorityQueue<Event> getEvents() {
        return events;
    }

    public long getN() {
        return n;
    }

    public double getTcAbsolute() {
        return tcAbsolute;
    }

    public BiConsumer<Simulation, Event> getOnEvent() {
        return onEvent;
    }

    public Consumer<Simulation> getOnEnd() {
        return onEnd;
    }

}
