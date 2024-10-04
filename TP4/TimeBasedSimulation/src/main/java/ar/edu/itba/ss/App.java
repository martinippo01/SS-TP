package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class App {

    public static void main( String[] args ) {
        final String inputFileName = System.getProperty("input");
        if(inputFileName == null) {
            throw new IllegalArgumentException("Input file not found");
        }
        InputData inputData = new InputData(inputFileName);

        LocalDateTime startTime = LocalDateTime.now();
        String outputDir = inputData.getOutputDir();
        Path outputDirPath = Path.of(outputDir, OutputData.getTimestampDirName(startTime));
        try {
            OutputData.setAndCreateOutputDir(outputDirPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final Runner runner = new Runner(inputData);
        runner.run();
        System.out.println("Simulation finished");
    }
}
