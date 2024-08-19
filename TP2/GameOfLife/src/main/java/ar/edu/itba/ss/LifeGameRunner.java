package ar.edu.itba.ss;

import com.google.gson.*;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LifeGameRunner implements Closeable {

    private final LifeGame lifeGame;
    private final InputData inputData;
    private final List<Set<Position>> liveCellsForStep;

    public LifeGameRunner(final Set<Position> initialLiveCells, final InputData inputData) {
        this.inputData = inputData;
        this.lifeGame = new LifeGame(initialLiveCells, inputData);
        this.liveCellsForStep = new ArrayList<>();
        this.liveCellsForStep.add(initialLiveCells);
    }

    public void run() {
        boolean finished;
        long iter = 0;
        long maxIter = inputData.getMaxIter();
        do {
            finished = lifeGame.evolve();
            Set<Position> liveCellsByPosition = lifeGame.getLiveCellsByPosition();
            liveCellsForStep.add(liveCellsByPosition);
            iter++;
        } while (!finished && iter < maxIter);
    }

    @Override
    public void close() throws IOException {
        FinishCondition finishCondition = lifeGame.getFinishCondition().orElse(FinishCondition.MAX_ITER);
        OutputData outputData = new OutputData(inputData, liveCellsForStep, finishCondition);
        Output output = new Output(inputData.getOutputFilePrefix(), outputData);
        output.write();
    }

    private static class Output {
        private final static Gson GSON = new GsonBuilder()
                .registerTypeAdapter(Border.class, new BorderAdapter())
                .create();

        private final OutputData outputData;
        private final String outputFilePrefix;

        public Output(String outputFilePrefix, OutputData outputData) {
            this.outputData = outputData;
            this.outputFilePrefix = outputFilePrefix;
        }

        private String getFileName() {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = String.format("%d-%02d-%02d_%02d-%02d-%02d",
                    now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
                    now.getHour(), now.getMinute(), now.getSecond());
            return String.format("%s_%s.json", outputFilePrefix, timestamp);
        }

        public void write() throws IOException {
            try (FileWriter outputFile = new FileWriter(getFileName())) {
                GSON.toJson(outputData, outputFile);
            }
        }
    }

    private static class OutputData {
        private final Border border;
        private final boolean is3D;
        private final double initialDomainProportion;
        private final double initialLiveCellsProportion;
        private final double inputProportion;
        private final List<Set<Position>> liveCellsForStep;
        private final FinishCondition finishCondition;

        public OutputData(
                InputData inputData,
                List<Set<Position>> liveCellsForStep,
                FinishCondition finishCondition
        ) {
            this.border = inputData.getBorder();
            this.is3D = inputData.isIs3D();
            this.initialDomainProportion = inputData.getInitialDomainProportion();
            this.initialLiveCellsProportion = inputData.getInitialLiveCellsProportion();
            this.inputProportion = inputData.getInputProportion();
            this.liveCellsForStep = liveCellsForStep;
            this.finishCondition = finishCondition;
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

        public FinishCondition getFinishCondition() {
            return finishCondition;
        }
    }

    private static class BorderAdapter implements JsonSerializer<Border> {
        @Override
        public JsonElement serialize(Border border, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject obj = new JsonObject();
            obj.addProperty("x.min", border.getXMin());
            obj.addProperty("x.max", border.getXMax());
            obj.addProperty("y.min", border.getYMin());
            obj.addProperty("y.max", border.getYMax());
            obj.addProperty("z.min", border.getZMin());
            obj.addProperty("z.max", border.getZMax());
            return obj;
        }
    }
}
