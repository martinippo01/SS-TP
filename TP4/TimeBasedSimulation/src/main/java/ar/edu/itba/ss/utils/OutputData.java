package ar.edu.itba.ss.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class OutputData implements Closeable {
    private static String OUTPUT_DIR = null;

    private final String fileName;
    private final boolean prettyPrint;
    private final SimulationParams simulationParams;
    private FileWriter fileWriter;
    private Gson gson;
    private boolean first = true;

    public OutputData(String fileName, boolean prettyPrint, SimulationParams simulationParams) {
        this.fileName = fileName;
        this.prettyPrint = prettyPrint;
        this.simulationParams = simulationParams;
    }

    public static void setAndCreateOutputDir(Path outputDir) throws IOException {
        OUTPUT_DIR = outputDir.toString();
        Files.createDirectory(outputDir);
    }

    private String getFileName() {
        return Path.of(OUTPUT_DIR, fileName + ".json").toString();
    }

    public static String getTimestampDirName(LocalDateTime timeStamp) {
        return String.format("%d-%02d-%02d_%02d-%02d-%02d",
                timeStamp.getYear(), timeStamp.getMonthValue(), timeStamp.getDayOfMonth(),
                timeStamp.getHour(), timeStamp.getMinute(), timeStamp.getSecond());
    }

    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        if (prettyPrint)
            builder.setPrettyPrinting();
        return builder.create();
    }

    private void openFile() throws IOException {
        fileWriter = new FileWriter(getFileName());
        gson = getGson();
        String paramsJson = gson.toJson(simulationParams);
        if(prettyPrint) {
            fileWriter.write("\n{\n\t\"params\": " + paramsJson + ",\n");
            fileWriter.write("\t\"steps\": [");
        }
        else {
            fileWriter.write("{\"params\":" + paramsJson + ",\"steps\":[");
        }
    }

    // Method to write an event to the file
    public void writeEvent(TimeEvent timeEvent) throws IOException {
        if (fileWriter == null) {
            openFile();
        }
        try {
            if (!first) {
                fileWriter.write(",");
            }
            if (!first && prettyPrint) {
                fileWriter.write("\n\t\t"); // Adds a newline after each event for readability
            }
            first = false;
            String json = gson.toJson(timeEvent); // Convert EventOutput to JSON string
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error writing event to file", e);
        }
    }

    @Override
    public void close() throws IOException {
        if (fileWriter != null) {
            if(prettyPrint)
                fileWriter.write("\n\t]\n}");
            else
                fileWriter.write("]}");
            fileWriter.close();
        }
    }
}


/*
*
* {
*   steps: [{
*       time:
*       positions: [
*               {x:, y:},{x:, y:}, {x:, y:}
*           ]
* }
*
*
* */
