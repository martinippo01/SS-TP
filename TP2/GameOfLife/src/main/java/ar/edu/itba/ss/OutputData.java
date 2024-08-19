package ar.edu.itba.ss;

/*
{
    "border": {
    "x": {
      "min": 0,
      "max": 100
    },
    "y": {
      "min": 0,
      "max": 100
    },
    "z": {
      "min": 0,
      "max": 100
    }
  },
  "is3D": false,
  "initialDomainProportion": 0.1,
  "initialLiveCellsProportion": 0.1,
  "inputProportion": 0.25,
  "steps": [[{ x: , y: }, ...], []]
}
 */

import com.google.gson.*;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OutputData {

    private final InputData inputData;
    private final List<Set<Position>> liveCellsForStep;

    public OutputData(InputData inputData) {
        this.inputData = inputData;
        this.liveCellsForStep = new ArrayList<>();
    }

    public String getFileName() {
        String outputFilePrefix = inputData.getOutputFilePrefix();
        LocalDateTime now = LocalDateTime.now();
        String timestamp = String.format("%d-%02d-%02d_%02d-%02d-%02d",
                now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
                now.getHour(), now.getMinute(), now.getSecond());
        return String.format("%s_%s.json", outputFilePrefix, timestamp);
    }

    public void addStep(Set<Position> liveCells) {
        this.liveCellsForStep.add(
                Set.copyOf(liveCells)
        );
    }

    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Border.class, new BorderAdapter())
                .create();
    }

    public OutputContent getContent() {
        return new OutputContent(
                inputData.getBorder(),
                inputData.isIs3D(),
                inputData.getInitialDomainProportion(),
                inputData.getInitialLiveCellsProportion(),
                inputData.getInputProportion(),
                liveCellsForStep
        );
    }

    public static class OutputContent {
        private final Border border;
        private final boolean is3D;
        private final double initialDomainProportion;
        private final double initialLiveCellsProportion;
        private final double inputProportion;
        private final List<Set<Position>> liveCellsForStep;

        public OutputContent(
                Border border,
                boolean is3D,
                double initialDomainProportion,
                double initialLiveCellsProportion,
                double inputProportion,
                List<Set<Position>> liveCellsForStep
        ) {
            this.border = border;
            this.is3D = is3D;
            this.initialDomainProportion = initialDomainProportion;
            this.initialLiveCellsProportion = initialLiveCellsProportion;
            this.inputProportion = inputProportion;
            this.liveCellsForStep = liveCellsForStep;
        }

        public Border getBorder() {
            return border;
        }

        public double getInitialDomainProportion() {
            return initialDomainProportion;
        }

        public boolean isIs3D() {
            return is3D;
        }

        public double getInitialLiveCellsProportion() {
            return initialLiveCellsProportion;
        }

        public double getInputProportion() {
            return inputProportion;
        }

        public List<Set<Position>> getLiveCellsForStep() {
            return liveCellsForStep;
        }
    }

    private static class BorderAdapter implements JsonSerializer<Border> {
        @Override
        public JsonElement serialize(Border border, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject obj = new JsonObject();
            obj.addProperty("x.min", border.getX_min());
            obj.addProperty("x.max", border.getX_max());
            obj.addProperty("y.min", border.getY_min());
            obj.addProperty("y.max", border.getY_max());
            obj.addProperty("z.min", border.getZ_min());
            obj.addProperty("z.max", border.getZ_max());
            return obj;
        }
    }


}
