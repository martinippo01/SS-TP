package ar.edu.itba.ss;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import com.google.gson.*;
import java.lang.reflect.Type;

public class InputData {

    private final InputFile inputData;

    public InputData(String inputFileName) {
        try(JsonReader fileReader = new JsonReader(new FileReader(inputFileName))){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Border.class, new BorderDeserializer())
                    .registerTypeAdapter(NeighbourhoodCondition.class, new ConditionDeserializer())
                    .create();
            this.inputData = gson.fromJson(fileReader, InputFile.class);

        } catch (IOException e) {
            throw new IllegalArgumentException("Input file not found");
        }
    }

    private static class InputFile {
        private Border border;
        private boolean is3D;
        private NeighbourhoodCondition condition;
        private int r;
        private Set<Integer> shouldKeepAlive;
        private Set<Integer> shouldRevive;
        private String outputFilePrefix;
        private double initialDomainProportion;
        private double initialLiveCellsProportion;
        private double inputProportion;
        private long maxIter;
    }

    public long getMaxIter() {
        return inputData.maxIter;
    }

    public Border getBorder() {
        return inputData.border;
    }

    public boolean isIs3D() {
        return inputData.is3D;
    }

    public NeighbourhoodCondition getCondition() {
        return inputData.condition;
    }

    public int getR() {
        return inputData.r;
    }

    public Set<Integer> getShouldKeepAlive() {
        return inputData.shouldKeepAlive;
    }

    public Set<Integer> getShouldRevive() {
        return inputData.shouldRevive;
    }

    public String getOutputFilePrefix() {
        return inputData.outputFilePrefix;
    }

    public double getInitialDomainProportion() {
        return inputData.initialDomainProportion;
    }

    public double getInitialLiveCellsProportion() {
        return inputData.initialLiveCellsProportion;
    }

    public double getInputProportion() {
        return inputData.inputProportion;
    }


    private static class BorderDeserializer implements JsonDeserializer<Border> {
        @Override
        public Border deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            JsonObject xObject = jsonObject.getAsJsonObject("x");
            int x_min = xObject.get("min").getAsInt();
            int x_max = xObject.get("max").getAsInt();

            JsonObject yObject = jsonObject.getAsJsonObject("y");
            int y_min = yObject.get("min").getAsInt();
            int y_max = yObject.get("max").getAsInt();

            JsonObject zObject = jsonObject.getAsJsonObject("z");
            int z_min = zObject.get("min").getAsInt();
            int z_max = zObject.get("max").getAsInt();

            return new Border(x_min, x_max, y_min, y_max, z_min, z_max);
        }
    }

    private static class ConditionDeserializer implements JsonDeserializer<NeighbourhoodCondition> {
        @Override
        public NeighbourhoodCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String condition = json.getAsString();
            return NeighbourhoodCondition.valueOf(condition.toUpperCase());
        }
    }


}
