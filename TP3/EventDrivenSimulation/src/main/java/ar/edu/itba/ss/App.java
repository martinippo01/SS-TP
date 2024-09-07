package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;

import java.io.IOException;
import java.util.function.BiConsumer;

public class App {
    public static void main(String[] args) {
        // Get program properties
        final String inputFileName = System.getProperty("input");

        // Read input data
        final InputData inputData = new InputData(inputFileName);
        final OutputData outputData;
        //
        final BiConsumer<Simulation, Event> writeEventToJSON;
        try{
            outputData =  new OutputData(inputData.getOutputDir(), inputData, inputData.getPretty());
            writeEventToJSON = (sim, ev) -> {
                // Save event to JSON
                try {
                    outputData.writeEvent(
                            new EventOutput(
                                    ev.getCrash(),
                                    ev.getCrash().getCrashedParticles(),
                                    sim.getPlane().getParticles(),
                                    ev.getTc()
                            )
                    );
                }catch (IOException e){
                    e.printStackTrace();
                }
            };
        }catch (IOException e){
            System.err.println("Error while trying to create output files: " + e.getMessage());
            return;
        }



        Simulation s = new Simulation(
                inputData.getPlane(),
                inputData.getN(),
                inputData.getMaxTime(),
                writeEventToJSON,
                (sim) -> {}
        );
        s.prepare(inputData.getM(), inputData.getR(), inputData.getV0(), inputData.getObstacles());
        s.run();
    }
}
