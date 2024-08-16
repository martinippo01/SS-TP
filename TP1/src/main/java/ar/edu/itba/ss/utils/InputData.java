package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.Particle;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
 {
    l: 0.0,
    m: 0,
    n: 0,
    rc: 0.0,
    maxRadius: 0.0,
    outputFilePrefix: "",
    pacman: true,
    particles: [
        {
            x: 0.0,
            y: 0.0,
            r: 0.0
        },
        {}
    ],
 }
 */

public class InputData {

    private final double length;
    private final int m;
    private final int n;
    private final double rc;
    private final double maxRadius;
    private final boolean pacman;
    private final String outputFilePrefix;
    private final List<InputParticle> particles;

    public InputData(String inputFileName) {
        try(JsonReader fileReader = new JsonReader(new FileReader(inputFileName))){
            Gson gson = new Gson();
            InputFile inputData = gson.fromJson(fileReader, InputFile.class);
            this.length = inputData.l;
            this.m = inputData.m;
            this.n = inputData.n;
            this.rc = inputData.rc;
            this.maxRadius = inputData.maxRadius;
            this.pacman = Optional.ofNullable(inputData.pacman).orElse(false);
            this.outputFilePrefix = Optional.ofNullable(inputData.outputFilePrefix).orElse(".");
            this.particles = Optional.ofNullable(inputData.particles).orElse(Collections.emptyList());
            if (!particles.isEmpty() && particles.size() != n) {
                throw new IllegalArgumentException("Number of particles does not match n");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Input file not found");
        }
    }

    public double getLength() {
        return length;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public double getRadiusC() {
        return rc;
    }

    public double getMaxRadius() {
        return maxRadius;
    }

    public boolean isPacman() {
        return pacman;
    }

    public String getOutputFilePrefix() {
        return outputFilePrefix;
    }

    public Optional<List<Particle>> getParticles() {
        if (particles.isEmpty()) {
            return Optional.empty();
        }
        Optional<List<Particle>> toReturn = Optional.of(new ArrayList<>());
        int i = 0;
        for (InputParticle p : particles) {
            toReturn.get().add(p.toParticle(i++));
        }
        return toReturn;
    }

    private static class InputFile {
        private double l;
        private int m;
        private int n;
        private double rc;
        private double maxRadius;
        private boolean pacman;
        private String outputFilePrefix;
        private List<InputParticle> particles;
    }

    private static class InputParticle {
        private double x;
        private double y;
        private double r;

        private Particle toParticle(int id) {
            return new Particle(x, y, r);
        }
    }

}
