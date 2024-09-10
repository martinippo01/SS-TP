package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;
import ar.edu.itba.ss.utils.SimulationType;

import java.io.IOException;
import java.util.function.Consumer;

public class App {
    public static void main(String[] args) {
        // Get program properties
        final String inputFileName = System.getProperty("input");
        if(inputFileName == null) {
            throw new IllegalArgumentException("Input file not found");
        }

        // Read input data
        InputData inputData = new InputData(inputFileName);

        try(OutputData outputData = new OutputData(inputData)) {
            // Write static file
            outputData.writeStaticFile();

            // Open dynamic file
            outputData.openDynamicFile();

            Consumer<EventOutput> onEvent = outputData::writeEvent;
            Consumer<Simulation> onStart = (simulation) -> {
                outputData.writeEvent(new EventOutput(0, simulation.getPlane().getParticles(), "START"));
            };

            SimulationType simType = inputData.getSimulationType();
            Simulation s = simType.createSimulation(inputData);
            s.prepare(inputData.getM(), inputData.getR(), inputData.getV0());
            s.run(onStart, onEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
