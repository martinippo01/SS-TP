package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;
import ar.edu.itba.ss.utils.SimulationParams;

import java.util.List;

public class Runner {
    private final InputData inputData;

    public Runner(InputData inputData) {
        this.inputData = inputData;
    }

    public void run() {
        SimulationType simulationType = inputData.getSimulationType();
        List<InputData.InputFile.DynamicField> dynamicFields = inputData.getDynamic();
        dynamicFields.parallelStream().forEach((dynamicField) -> {
            int dt2 = inputData.getDtJumps();
            SimulationParams iterationParams = new SimulationParams(inputData, dynamicField);
            // TODO: Replace inputData with SimulationParams and add the isPretty and outputDir params
            OutputData outputData = new OutputData(inputData);
            Simulation simulation = simulationType.getSimulation(iterationParams, (p, i) -> {
                if (i % dt2 == 0) {
                    // TODO: Create timeEvent
                    outputData.writeEvent();
                }
            });
            simulation.run();
        });
    }
}