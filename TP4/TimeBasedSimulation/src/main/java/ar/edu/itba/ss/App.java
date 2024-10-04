package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;

public class App {

    public static void main( String[] args ) {
        final String inputFileName = System.getProperty("input");
        if(inputFileName == null) {
            throw new IllegalArgumentException("Input file not found");
        }
        InputData inputData = new InputData(inputFileName);

        final Runner runner = new Runner(inputData);
        try {
            runner.run();
            System.out.println("Simulation finished");
        } catch (Exception e) {
            System.err.println("Error running simulation");
            throw new RuntimeException(e);
        }
    }
}
