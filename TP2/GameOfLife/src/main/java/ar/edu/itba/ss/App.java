package ar.edu.itba.ss;

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

        // Run the simulation
        try(LifeGameRunner runner = new LifeGameRunner(initialCells, inputData)) {
            runner.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
