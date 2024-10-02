package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;
import ar.edu.itba.ss.utils.TimeEvent;

import java.io.IOException;

/**
 *
 *
 */
public class App {

    public static void main( String[] args ) {
        final String inputFileName = System.getProperty("input");
        if(inputFileName == null) {
            throw new IllegalArgumentException("Input file not found");
        }
        InputData inputData = new InputData(inputFileName);

        final Runner runner = new Runner(inputData);
        runner.run();
        System.out.println("Simulation finished");
    }
}