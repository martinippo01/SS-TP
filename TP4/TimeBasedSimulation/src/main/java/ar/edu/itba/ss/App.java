package ar.edu.itba.ss;

import java.io.IOException;

/**
 *
 *
 */
public class App {

    public static void main( String[] args ) {
        final String inputFileName = System.getProperty("input");
        if(inputFileName == null)
            throw new IllegalArgumentException("Input file not found");
        InputData inputData = new InputData(inputFileName);

        try (OutputData outputData = new OutputData(inputData)){

        }catch(IOException e){
            throw new RuntimeException(e);
        }


    }
}
