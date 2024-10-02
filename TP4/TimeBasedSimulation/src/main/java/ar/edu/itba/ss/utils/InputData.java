package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.SimulationType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class InputData {

    private final InputFile inputData;

    public InputData(String inputFileName) {
        try (JsonReader fileReader = new JsonReader(new FileReader(inputFileName))) {
            Gson gson = new GsonBuilder()
                    .create();
            this.inputData = gson.fromJson(fileReader, InputFile.class);

        } catch (IOException e) {
            throw new IllegalArgumentException("Input file not found");
        }
    }

    public static class InputFile {
        private Integer n;
        private Double mass;
        private Double k;
        private Double gamma;
        private Double tf;
        private Double r0;
        private Double v0;
        private Double dt;
        private Integer dt_jumps;
        private Boolean prettyPrint;
        private String outputDir;
        private String algorithmType;
        private SimulationType simulationType;
        private Double a;
        private Double w;
        private Double l0;

        // List of DynamicFields for the dynamic array
        @SerializedName("dynamic")
        private List<DynamicField> dynamic;

        // Nested class for dynamic objects
        public static class DynamicField {
            private Double k;
            private Double tf;
            private Double r0;
            private Double v0;
            private Double dt;
            private Integer dt_jumps;
            private String algorithmType;
            private Double w;

            public String getAlgorithmType(){
                return algorithmType;
            }

            public Double getK() {
                return k;
            }

            public Double getTf() {
                return tf;
            }

            public Double getR0() {
                return r0;
            }

            public Double getV0() {
                return v0;
            }

            public Double getDt() {
                return dt;
            }

            public Integer getDt_jumps() {
                return dt_jumps;
            }

            public Double getW() {
                return w;
            }
        }
    }

    // Getters
    public String getAlgorithmType(){
        return inputData.algorithmType;
    }

    public SimulationType getSimulationType(){
        return inputData.simulationType;
    }

    public Double getA() {
        return inputData.a;
    }

    public double getMass() { return inputData.mass; }

    public double getGamma() { return inputData.gamma; }

    public double getK() { return inputData.k; }

    public double getTf() { return inputData.tf; }

    public double getR0() { return inputData.r0; }

    public double getV0() { return inputData.v0; }

    public double dt() { return inputData.dt; }

    public Integer getN() { return inputData.n; }

    public int getDtJumps() { return inputData.dt_jumps; }

    public boolean isPrettyPrint() { return inputData.prettyPrint; }

    public String getOutputDir() { return inputData.outputDir; }

    public double getW() { return inputData.w; }

    public double getL0() { return inputData.l0; }

    // Getters for dynamic list
    public List<InputFile.DynamicField> getDynamic() { return inputData.dynamic; }
}
