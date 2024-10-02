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

    private final String outputDir;
    private final boolean prettyPrint;
    private final LocalDateTime timeStamp;
    private FileWriter fileWriter;
    private Gson gson;
    private boolean first = true;

    public OutputData(String outputDir, boolean prettyPrint) throws IOException {
        this.outputDir = outputDir;
        this.prettyPrint = prettyPrint;
        this.timeStamp = LocalDateTime.now();
        Files.createDirectory(Path.of(outputDir, getTimestampDir()));
    }

    private String getFileName(String type) {
        return String.format("%s/%s/%s.json", outputDir, getTimestampDir(), type);
    }

    private String getTimestampDir() {
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

    public void openDynamicFile() throws IOException {
        if (fileWriter != null) {
            throw new IllegalStateException("Dynamic file already open");
        }
        fileWriter = new FileWriter(getFileName("dynamic"));
        gson = getGson();
        if(prettyPrint)
            fileWriter.write("{\n\t\"steps\": [");
        else
            fileWriter.write("{\"steps\":[");
    }

    // Method to write an event to the file
    public void writeEvent(TimeEvent timeEvent) throws IOException {
        if (fileWriter == null) {
            throw new IllegalStateException("Dynamic file not open");
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
