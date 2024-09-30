package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;
import ar.edu.itba.ss.utils.SimulationParams;

import java.util.List;

public class Runner {
    private final InputData inputData;
    private final SimulationParams defaultSimulationParams;

    public Runner(InputData inputData) {
        this.inputData = inputData;
        this.defaultSimulationParams = new SimulationParams(inputData);
    }

    private SimulationParams getIterationParams(InputData.InputFile.DynamicField dynamicFields) {
        SimulationParams iterationParams = defaultSimulationParams.getCopy();
        Double dynamicK = dynamicFields.getK();
        if (dynamicK != null) { iterationParams.setK(dynamicK); }
        Double dynamicTf = dynamicFields.getTf();
        if (dynamicTf != null) { iterationParams.setTf(dynamicTf); }
        Double dynamicR0 = dynamicFields.getR0();
        if (dynamicR0 != null) { iterationParams.setR0(dynamicR0); }
        Double dynamicV0 = dynamicFields.getV0();
        if (dynamicV0 != null) { iterationParams.setV0(dynamicV0); }
        Double dynamicDt = dynamicFields.getDt();
        if (dynamicDt != null) { iterationParams.setDt(dynamicDt); }
        Integer dynamicDtJumps = dynamicFields.getDt_jumps();
        if (dynamicDtJumps != null) { iterationParams.setDt_jumps(dynamicDtJumps); }
        AlgorithmType dynamicAlgorithmType = dynamicFields.getAlgorithmType();
        if (dynamicAlgorithmType != null) { iterationParams.setAlgorithmType(dynamicAlgorithmType); }
        Double dynamicTimeMax = dynamicFields.getTimeMax();
        if (dynamicTimeMax != null) { iterationParams.setTimeMax(dynamicTimeMax); }
        return iterationParams;
    }

    public void run() {
        SimulationType simulationType = inputData.getSimulationType();
        List<InputData.InputFile.DynamicField> dynamicFields = inputData.getDynamic();
        dynamicFields.parallelStream().forEach((iteration) -> {
            int dt2 = inputData.getDtJumps();
            SimulationParams iterationParams = getIterationParams(iteration);
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