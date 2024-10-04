package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class Runner {
    private final InputData inputData;
    private final LocalDateTime startTime = LocalDateTime.now();

    public Runner(InputData inputData) {
        this.inputData = inputData;
    }

    private void runIteration(InputData.InputFile.DynamicField dynamicField, Path outputDirPath) {
        int dt2 = inputData.getDtJumps();
        String fileName = dynamicField.getId();
        boolean isPrettyPrint = inputData.isPrettyPrint();
        SimulationType simulationType = inputData.getSimulationType();
        SimulationParams iterationParams = new SimulationParams(inputData, dynamicField);
        try (OutputData outputData = new OutputData(fileName, outputDirPath, isPrettyPrint)) {
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

    public void run() throws IOException {
        if (!doIterationsHaveId()) {
            throw new IllegalArgumentException("All iterations must have an id");
        }
        Path outputDirPath = Files.createDirectory(Path.of(inputData.getOutputDir(), OutputData.getTimestampDirName(startTime)));
        List<InputData.InputFile.DynamicField> dynamicFields = inputData.getDynamic();
        dynamicFields.parallelStream().forEach(dynamicField -> runIteration(dynamicField, outputDirPath));
    }
}