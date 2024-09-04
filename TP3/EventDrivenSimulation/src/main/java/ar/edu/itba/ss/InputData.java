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
                    .registerTypeAdapter(Obstacle.class, new ObstacleDeserializer())
                    .create();

            this.inputData = gson.fromJson(fileReader, InputFile.class);

        } catch (IOException e) {
            throw new IllegalArgumentException("Input file not found");
        }
    }

    private static class InputFile {
        private double mass;
        private double radious;
        private double speed;
        private long n;
        private String plane;
        private List<Obstacle> obstacles;
    }

    public double getMass() {
        return inputData.mass;
    }

    public double getRadious() {
        return inputData.radious;
    }

    public double getSpeed() {
        return inputData.speed;
    }

    public long getN() {
        return inputData.n;
    }

    public String getPlane() {
        return inputData.plane;
    }

    public List<Obstacle> getObstacles() { return inputData.obstacles;}



    private static class ObstacleDeserializer implements JsonDeserializer<Obstacle> {
        @Override
        public Obstacle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            JsonObject positionObject = jsonObject.getAsJsonObject("p");
            double x = positionObject.get("x").getAsDouble();
            double y = positionObject.get("y").getAsDouble();
            Position position = new Position(x, y);

            double radius = jsonObject.get("radius").getAsDouble();
            double mass = jsonObject.get("mass").getAsDouble();

            return new Obstacle(position, radius, mass);
        }
    }


}
