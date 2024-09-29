package ar.edu.itba.ss;

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

    private static class InputFile {
        private double m;
        private double k;
        private double gamma;
        private double tf;
        private double r0;
        private double v0;
        private double dt;
        private int dt_jumps;

        // List of DynamicFields for the dynamic array
        @SerializedName("dynamic")
        private List<DynamicField> dynamic;

        // Nested class for dynamic objects
        private static class DynamicField {
            private double k;
            private double tf;
            private double r0;
            private double v0;
            private double dt;
            private int dt_jumps;
        }
    }

    // Getters
    public double getM() { return inputData.m; }

    public double getGamma() { return inputData.gamma; }

    public double getK() { return inputData.k; }

    public double getTf() { return inputData.tf; }

    public double getR0() { return inputData.r0; }

    public double getV0() { return inputData.v0; }

    public double dt() { return inputData.dt; }

    // Getters for dynamic list
    public List<InputFile.DynamicField> getDynamic() { return inputData.dynamic; }
}
