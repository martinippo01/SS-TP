package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.EventOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class OutputData implements Closeable {

    private FileWriter fileWriter;
    private Gson gson;
    private final LocalDateTime timeStamp;
    private final InputData inputData;
    private final boolean pretty;
    private boolean first = true;

    // Constructor to open the file and initialize GSON
    public OutputData(InputData inputData) throws IOException {
        this.pretty = inputData.getPretty();
        this.inputData = inputData;
        this.timeStamp = LocalDateTime.now();
        Files.createDirectory(Path.of(inputData.getOutputDir(), getTimestampDir()));
    }

    private String getTimestampDir() {
        return String.format("%d-%02d-%02d_%02d-%02d-%02d",
                timeStamp.getYear(), timeStamp.getMonthValue(), timeStamp.getDayOfMonth(),
                timeStamp.getHour(), timeStamp.getMinute(), timeStamp.getSecond());
    }

    private String getFileName(String type) {
        return String.format("%s/%s/%s.json", inputData.getOutputDir(), getTimestampDir(), type);
    }

    private Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create();
    }

    public void writeStaticFile() throws IOException {
        FileWriter writer = new FileWriter(getFileName("static"));
        Gson gson = getGson();
        gson.toJson(inputData, writer);
        writer.close();
    }

    public void openDynamicFile() throws IOException {
        if (fileWriter != null) {
            throw new IllegalStateException("Dynamic file already open");
        }
        fileWriter = new FileWriter(getFileName("dynamic"));
        gson = getGson();
        fileWriter.write("{\"events\":[");
    }

    // Method to write an event to the file
    public void writeEvent(EventOutput eventOutput) {
        if (fileWriter == null) {
            throw new IllegalStateException("Dynamic file not open");
        }
        try {
            if (!first) {
                fileWriter.write(",");
            }
            if (!first && pretty) {
                fileWriter.write("\n\t"); // Adds a newline after each event for readability
            }
            first = false;
            String json = gson.toJson(eventOutput); // Convert EventOutput to JSON string
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error writing event to file", e);
        }
    }

    @Override
    public void close() throws IOException {
        if (fileWriter != null) {
            fileWriter.write("]}");
            fileWriter.close();
        }
    }
}
