package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        // Get program properties
        final String inputFileName = System.getProperty("input");

        // Read input data
        final InputData inputData = new InputData(inputFileName);
        final OutputData outputData;
        try{
            outputData =  new OutputData(inputData.getOutputDir(), inputData, inputData.getPretty());
        }catch (IOException e){
            System.err.println("Error while trying to create output files: " + e.getMessage());
        }

        Simulation s = new Simulation(
                inputData.getPlane(),
                inputData.getN(),
                inputData.getMaxCollisions(),
                (sim, e) -> {},
                (sim) -> {},
                outputData
        );
        s.prepare(inputData.getM(), inputData.getR(), inputData.getV0(), inputData.getObstacles());
        s.run();
    }
}
