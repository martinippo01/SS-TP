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

    CIRCULAR {
        // Credits: https://math.stackexchange.com/questions/562835/bouncing-of-a-ball-from-circular-boundary
        @Override
        public void setParticleVelocityAfterCrash(Particle p) {
            // asumimos centro del círculo en (0,0)
            double vx = p.getVx(), vy = p.getVy(), x = p.getX(), y = p.getY();
            double normalizationValue = Math.sqrt(x * x + y * y);
            double n1 = x/normalizationValue, n2 = y/normalizationValue;
            p.setVelocity(
                    new Velocity((n2*n2-n1*n1)*vx - (2*n1*n2)*vy, -(2*n1*n2)*vx + (n1*n1-n2*n2)*vy)
            );
        }
    };

    public abstract void setParticleVelocityAfterCrash(Particle p);
}
