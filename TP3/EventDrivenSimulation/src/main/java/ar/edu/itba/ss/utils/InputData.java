package ar.edu.itba.ss.utils;

import java.io.FileReader;
import java.io.IOException;

import ar.edu.itba.ss.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.List;

public class InputData {

    private final InputFile inputData;


    public InputData(String inputFileName) {

        try(JsonReader fileReader = new JsonReader(new FileReader(inputFileName))){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Obstacle.class, new ObstacleDeserializer())
                    .registerTypeAdapter(Plane.class, new PlaneDeserializer())
                    .registerTypeAdapter(Particle.class, new ParticleDeserializer())
                    .create();

            this.inputData = gson.fromJson(fileReader, InputFile.class);

        } catch (IOException e) {
            throw new IllegalArgumentException("Input file not found");
        }
    }

    private static class InputFile {
        private double m;
        private double r;
        private double v0;
        private long n;
        private double L;
        private PlaneType planeType;
        private SimulationType simulationType;
        private List<Obstacle> obstacles;
        private List<Particle> particles;
        private Plane plane;
        private double maxTime;
        private String outputDir;
        private boolean prettyOutput;
    }

    public double getM() {
        return inputData.m;
    }

    public double getR() {
        return inputData.r;
    }

    public double getV0() {
        return inputData.v0;
    }

    public long getN() {
        return inputData.n;
    }

    public Plane getPlane() {
        return inputData.plane;
    }

    public List<Obstacle> getObstacles() { return inputData.obstacles;}

    public double getMaxTime() {
        return inputData.maxTime;
    }

    public String getOutputDir(){
        return inputData.outputDir;
    }

     public boolean getPretty(){
        return inputData.prettyOutput;
     }


    private static class PlaneDeserializer implements JsonDeserializer<Plane> {
        @Override
        public Plane deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String type = jsonObject.get("planeType").getAsString();
            PlaneType planeType = PlaneType.valueOf(type.toUpperCase());
            double l = jsonObject.get("L").getAsDouble();
            return planeType.createPlane(l);
        }
    }

    private static class ParticleDeserializer implements JsonDeserializer<Particle> {
        @Override
        public Particle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            JsonObject positionObject = jsonObject.getAsJsonObject("p");
            double x = positionObject.get("x").getAsDouble();
            double y = positionObject.get("y").getAsDouble();
            Position position = new Position(x, y);

            JsonObject velocityObject = jsonObject.getAsJsonObject("v");
            double vx = velocityObject.get("vx").getAsDouble();
            double vy = velocityObject.get("vy").getAsDouble();
            Velocity velocity = new Velocity(vx, vy);

            double radius = jsonObject.get("r").getAsDouble();
            double mass = jsonObject.get("m").getAsDouble();

            return new Particle(position, radius, velocity, mass);
        }
    }

    private static class ObstacleDeserializer implements JsonDeserializer<Obstacle> {
        @Override
        public Obstacle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            JsonObject positionObject = jsonObject.getAsJsonObject("p");
            double x = positionObject.get("x").getAsDouble();
            double y = positionObject.get("y").getAsDouble();
            Position position = new Position(x, y);

            double radius = jsonObject.get("r").getAsDouble();
            double mass = jsonObject.get("m").getAsDouble();

            return new Obstacle(position, radius, mass);
        }
    }


}
