package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.OutputData;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Get program properties
        double length = Double.parseDouble(System.getProperty("L"));
        int m = Integer.parseInt(System.getProperty("M"));
        int n = Integer.parseInt(System.getProperty("N"));
        boolean pacman = Boolean.parseBoolean(System.getProperty("PACMAN"));
        double radiusC = Double.parseDouble(System.getProperty("RC"));
        double maxRadius = Double.parseDouble(System.getProperty("MAXRADIUS"));

        // Validate inequation L/M > r_c + 2R
        if (length / m <= radiusC + 2 * maxRadius) {
            throw new IllegalArgumentException("m is too big");
        } else if (m <= 1) {
            throw new IllegalArgumentException("m must be greater or equal to 1");
        }

        // Create plane
        Plane plane = new Plane(length, m, pacman);

        // Generate random particles and add them to the plane
        Random r = new Random();
        for(int i=0; i<n; i++) {
            double random_x = (length) * r.nextDouble();
            double random_y = (length) * r.nextDouble();
            double random_r = (maxRadius) * r.nextDouble();
            Particle p = new Particle(i, random_x, random_y, random_r);
            plane.addParticle(p);
        }

        // Time CIM algorithm
        Cim cim = new Cim(plane, radiusC);
        long startTime = System.nanoTime();
        ParticleNeighbours particleNeighbours = cim.getParticleNeighbours();
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        Gson gson = new Gson();
        OutputData outputData = new OutputData(length, m, n, totalTime, plane);
        try(FileWriter fileWriter = new FileWriter("results.json")){
            gson.toJson(outputData, fileWriter);
            System.out.println("JSON with output data created succesfully");
        }catch (IOException e){
            System.err.println("Error while creating JSON file" + e.getMessage());
        }


    }
}
