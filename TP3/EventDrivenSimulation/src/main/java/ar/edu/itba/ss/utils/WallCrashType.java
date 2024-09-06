package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Particle;
import ar.edu.itba.ss.Velocity;

public enum WallCrashType {
    X {
        // vertical
        @Override
        public void setParticleVelocityAfterCrash(Particle p) {
            p.setVelocity(
                    new Velocity(-p.getVx(), p.getVy())
            );
        }
    },
    Y {
        // horizontal
        @Override
        public void setParticleVelocityAfterCrash(Particle p) {
            p.setVelocity(
                    new Velocity(p.getVx(), -p.getVy())
            );
        }
    },
    // TODO: Implement this
    CIRCULAR {
        @Override
        public void setParticleVelocityAfterCrash(Particle p) {}
    };

    public abstract void setParticleVelocityAfterCrash(Particle p);
}
