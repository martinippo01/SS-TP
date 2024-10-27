package ar.edu.itba.ss.tp5.IO;

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
        // Listado de parametros de entrada

        // List of DynamicFields for the dynamic array
        @SerializedName("dynamic")
        private List<DynamicField> dynamic;

        // Nested class for dynamic objects
        public static class DynamicField {
            // Listado de parametros sujetos a modifcarse en multiples corridas

            // Getters
        }
    }

    // Getters


    // Getters for dynamic list
    public List<InputFile.DynamicField> getDynamic() { return inputData.dynamic; }
}
