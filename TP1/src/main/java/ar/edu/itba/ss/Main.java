package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.InputData;
import ar.edu.itba.ss.utils.OutputData;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Main {

    private static List<Particle> generateRandomParticles(final InputData inputData) {
        final double length = inputData.getLength();
        final int n = inputData.getN();
        final double maxRadius = inputData.getMaxRadius();
        Random r = new Random();
        List<Particle> particles = new ArrayList<>();
        for(int i=0; i<n; i++) {
            double random_x = (length) * r.nextDouble();
            double random_y = (length) * r.nextDouble();
            double random_r = (maxRadius) * r.nextDouble();
            Particle p = new Particle(i, random_x, random_y, random_r);
            particles.add(p);
        }
        return particles;
    }

    public static void main(String[] args) {
        // Get program properties
        final String inputFileName = System.getProperty("input");

        // Read input data
        final InputData inputData = new InputData(inputFileName);

        final double length = inputData.getLength();
        final int m = inputData.getM();
        final double radiusC = inputData.getRadiusC();
        final double maxRadius = inputData.getMaxRadius();
        final boolean pacman = inputData.isPacman();
        final Optional<List<Particle>> maybeParticles = inputData.getParticles();

        // Validate inequation L/M > r_c + 2R
        if (length / m <= radiusC + 2 * maxRadius) {
            throw new IllegalArgumentException("m is too big");
        } else if (m < 1) {
            throw new IllegalArgumentException("m must be greater or equal to 1");
        }

        // Create plane
        Plane plane = new Plane(length, m, pacman);

        // Get particles
        maybeParticles
                .orElse(generateRandomParticles(inputData))
                .forEach(plane::addParticle);

        // Time CIM algorithm
        Cim cim = new Cim(plane, radiusC);
        long startTime = System.nanoTime();
        ParticleNeighbours particleNeighbours = cim.getParticleNeighbours();
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        Gson gson = new Gson();
        OutputData outputData = new OutputData(inputData, totalTime, plane, particleNeighbours);
        String outputFileName = outputData.getFileName();
        try(FileWriter fileWriter = new FileWriter(outputFileName)){
            gson.toJson(outputData, fileWriter);
            System.out.println("JSON with output data created successfully");
        }catch (IOException e){
            System.err.println("Error while creating JSON file" + e.getMessage());
        }


    }
}
