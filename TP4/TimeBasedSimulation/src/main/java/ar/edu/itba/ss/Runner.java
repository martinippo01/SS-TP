package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.*;

import java.io.IOException;
import java.util.List;

public class Runner {
    private final InputData inputData;

    public Runner(InputData inputData) {
        this.inputData = inputData;
    }

    private void runIteration(InputData.InputFile.DynamicField dynamicField) {
        int dt2 = inputData.getDtJumps();
        SimulationType simulationType = inputData.getSimulationType();
        SimulationParams iterationParams = new SimulationParams(inputData, dynamicField);
        try (OutputData outputData = new OutputData(inputData.getOutputDir(), inputData.isPrettyPrint())) {
            outputData.openDynamicFile();
            double dt = iterationParams.getDt();
            Simulation simulation = simulationType.getSimulation(iterationParams, (p, i) -> {
                if (i % dt2 == 0) {
                    TimeEvent timeEvent = new TimeEvent(i * dt, p.stream().map(Particle::getPosition).toList());
                    try {
                        outputData.writeEvent(timeEvent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            simulation.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        List<InputData.InputFile.DynamicField> dynamicFields = inputData.getDynamic();
        dynamicFields.parallelStream().forEach(this::runIteration);
    }
}