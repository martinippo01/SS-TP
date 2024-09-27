package ar.edu.itba.ss;

public enum Algorithms {

    GEAR5 {
        @Override
        public double apply() {
            return 1;
        }
    },

    BEEMAN {
        @Override
        public double apply() {
            return 1;
        }
    },

    OG_VERLER {
        @Override
        public double apply() {
            return 1;
        }
    },

    AMORT_OSCILATOR {
        // TODO: hacer que reciba los parámetros por argumento, quizás en una clase específica
        @Override
        public double apply() {
            double A =1, gamma=1, m=1, t=1, k=1;
            return A* Math.exp(-(gamma/(2*m)*t))*Math.cos(Math.pow((k/m - gamma*gamma / Math.pow(2*m,2)), 0.5) * t);
        }
    };

    public abstract double apply();


}
