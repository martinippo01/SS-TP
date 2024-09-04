package ar.edu.itba.ss;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        // Get program properties
        final String inputFileName = System.getProperty("input");

        // Read input data
        final InputData iD = new InputData(inputFileName);


        Simulation s = new Simulation(iD.getN(), iD.getPlane());
        s.prepare(iD.getMass(), iD.getRadious(), iD.getSpeed(), iD.getObstacles());
        s.run();
    }
}
