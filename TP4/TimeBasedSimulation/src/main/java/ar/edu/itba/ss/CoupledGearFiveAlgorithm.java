package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.List;

public class CoupledGearFiveAlgorithm extends CoupledAlgorithm {

    private List<Double> currentRs, currentR1s, currentR2s, currentR3s, currentR4s, currentR5s;

    public CoupledGearFiveAlgorithm(double k, double a, double w, int n, List<Particle> particles) {
        super(k, a, w, n, particles);
        currentRs = particles.stream().map((p) -> p.getPosition().getY()).toList();
        currentR1s = particles.stream().map((p) -> p.getVelocity().getY()).toList();
    }


    private List<Double> getCurrentRs(List<Double> prev2Values) {
        List<Particle> particles = getParticles();
        List<Double> currentAlgo = new ArrayList<>();
        double mass = particles.get(0).getMass();
        double k = getK();


        // Añado la primera

        double pV0 = prev2Values.get(0);
        double pV1 = prev2Values.get(1);

        currentAlgo.add((-k*(pV0-0) - k*(pV0-pV1))/mass);

        for(int i = 1; i< getN()-1; i++) {
            double pY = prev2Values.get(i);
            double prevY = prev2Values.get(i-1);
            double nextY = prev2Values.get(i+1);
            // Añado el algo de la partícula i
            currentAlgo.add((-k*(pY-prevY) - k*(pY-nextY))/mass);
        }

        // No estamos agregando el último, responsabilidad de quien llama

        return currentAlgo;
    }

    @Override
    public void evolve(double dt) {
        List<Particle> particles = getParticles();
        double A = getA();
        double w = getW();
        double time = getTime();
        double k = getK();
        double mass = particles.get(0).getMass();
        List<Double> newCurrentRs = new ArrayList<>(), newCurrentR1s = new ArrayList<>(), newCurrentR2s = new ArrayList<>(),
                newCurrentR3s = new ArrayList<>(), newCurrentR4s = new ArrayList<>(), newCurrentR5s = new ArrayList<>();


        // Para todas falta la última
        currentR2s = getCurrentRs(currentRs);
        currentR2s.add(-A*Math.sin(w*time)*Math.pow(w, 2));

        currentR3s = getCurrentRs(currentR1s);
        currentR3s.add(-A*Math.cos(w*time)*Math.pow(w, 3));

        currentR4s = getCurrentRs(currentR2s);
        currentR5s = getCurrentRs(currentR3s);


        // Recorro dos veces, pero es para tener las posiciones para el deltar2
        List<Double> predNextR = new ArrayList<>();
        for(int i = 0 ; i < particles.size() -1 ; i++) {
            double currentR = currentRs.get(i), currentR1 = currentR1s.get(i), currentR2 = currentR2s.get(i),
                    currentR3 = currentR3s.get(i), currentR4 = currentR4s.get(i), currentR5 = currentR5s.get(i);
            predNextR.add(currentR + currentR1 * dt + currentR2 * (dt * dt / 2) + currentR3 * (dt * dt * dt / (2 * 3)) + currentR4 * (Math.pow(dt, 4) / (2 * 3 * 4)) + currentR5 * (Math.pow(dt, 5) / (2 * 3 * 4 * 5)));
        }
        // ya actualizo la última partícula
        updateLastParticle(dt);
        double lastPExactPosition = particles.get(particles.size()-1).getPosition().getY();
        predNextR.add(lastPExactPosition);


        for(int i = 0 ; i < particles.size() - 1 ; i++) {

            double currentR1 = currentR1s.get(i), currentR2 = currentR2s.get(i), currentR3 = currentR3s.get(i),
                    currentR4 = currentR4s.get(i), currentR5 = currentR5s.get(i);

            // Predecir

            double predNextR1 = currentR1 + currentR2 * dt + currentR3 * (dt * dt / 2) + currentR4 * (Math.pow(dt, 3) / (2 * 3)) + currentR5 * (Math.pow(dt, 4) / (2 * 3 * 4));
            double predNextR2 = currentR2 + currentR3 * dt + currentR4 * (dt * dt / 2) + currentR5 * (Math.pow(dt, 3) / (2 * 3));
            double predNextR3 = currentR3 + currentR4 * dt + currentR5 * (dt * dt / 2);
            double predNextR4 = currentR4 + currentR5 * dt;
            double predNextR5 = currentR5;

            // Evaluar

            double prevR0 = (i==0)? 0 : predNextR.get(i-1);
            double accInTDeltaT = (-k*(predNextR.get(i)-prevR0) - k*(predNextR.get(i)-predNextR.get(i+1)))/mass;


            double deltar2 = accInTDeltaT - predNextR2;
            double deltaR2 = (deltar2 * Math.pow(dt,2))/2;

            // Obtener


            double nextY = predNextR.get(i) + (3/16.0) * deltaR2;
            double nextV = predNextR1 + (251/360.0) * deltaR2 * (1 / dt);

            newCurrentRs.add(nextY);
            newCurrentR1s.add(nextV);
            newCurrentR2s.add(predNextR2 + 1 * deltaR2 * (2 / Math.pow(dt, 2)));
            newCurrentR3s.add(predNextR3 + (11/18.0) * deltaR2 * ((2 * 3) / Math.pow(dt, 3)));
            newCurrentR4s.add(predNextR4 + (1/6.0) * deltaR2 * ((2 * 3 * 4) / Math.pow(dt, 4)));
            newCurrentR5s.add(predNextR5 + (1/60.0) * deltaR2 * ((2 * 3 * 4 * 5) / Math.pow(dt, 5)));

            Particle currentP = particles.get(i);
            currentP.getPosition().setY(nextY);
            currentP.getVelocity().setY(nextV);
        }

        currentRs = newCurrentRs;
        currentR1s = newCurrentR1s;
        currentR2s = newCurrentR2s;
        currentR3s = newCurrentR3s;
        currentR4s = newCurrentR4s;
        currentR5s = newCurrentR5s;

        currentRs.add(lastPExactPosition);
        currentR1s.add(A*Math.cos(w*getTime())*w);
    }
}
