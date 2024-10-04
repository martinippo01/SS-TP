package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Runner {
    private final InputData inputData;

    public Runner(InputData inputData) {
        this.inputData = inputData;
    }

    private void runIteration(InputData.InputFile.DynamicField dynamicField) {
        int dt2 = inputData.getDtJumps();
        String fileName = dynamicField.getId();
        boolean isPrettyPrint = inputData.isPrettyPrint();
        SimulationType simulationType = inputData.getSimulationType();
        SimulationParams iterationParams = new SimulationParams(inputData, dynamicField);
        try (OutputData outputData = new OutputData(fileName, isPrettyPrint)) {
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

    private boolean doIterationsHaveId() {
        List<InputData.InputFile.DynamicField> dynamicFields = inputData.getDynamic();
        return dynamicFields.stream().allMatch(dynamicField -> dynamicField.getId() != null);
    }

    public void run() {
        if (!doIterationsHaveId()) {
            throw new IllegalArgumentException("All iterations must have an id");
        }
        List<InputData.InputFile.DynamicField> dynamicFields = inputData.getDynamic();
        dynamicFields.parallelStream().forEach(this::runIteration);
    }
}