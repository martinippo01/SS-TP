package ar.edu.itba.ss;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        // Get program properties
        final String inputFileName = System.getProperty("input");

        // Read input data
        final InputData inputData = new InputData(inputFileName);
        final Border initialDomain = inputData.getBorder()
                .getInnerBorder(inputData.isIs3D(), inputData.getInitialDomainProportion());
        Set<Position> initialCells = initialDomain.generateInitialPositions(inputData.getInitialLiveCellsProportion());

        OutputData outputData = new OutputData(inputData);

        // Run life game
        LifeGame lifeGame = new LifeGame(initialCells, inputData);
        outputData.addStep(initialCells);
        boolean finished;
        long iter = 0;
        long maxIter = inputData.getMaxIter();
        do {
            finished = lifeGame.evolve();
            Set<Position> liveCellsByPosition = lifeGame.getLiveCellsByPosition();
            outputData.addStep(liveCellsByPosition);
        } while (!finished && iter++<maxIter);

        // Write results
        try(FileWriter outputFile = new FileWriter(outputData.getFileName())) {
            Gson gson = outputData.getGson();
            OutputData.OutputContent content = outputData.getContent();
            gson.toJson(content, outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
