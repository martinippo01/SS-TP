package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.EventOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class OutputData {

    private final FileWriter fileWriter;
    private final Gson gson;
    private final boolean pretty;
    private boolean first = true;

    // Constructor to open the file and initialize GSON
    public OutputData(String filePath, InputData inputData, boolean pretty) throws IOException {
        this.pretty = pretty;
        String timeStamp = Long.toString(System.currentTimeMillis());
        try (FileWriter writer = new FileWriter(inputData.getOutputDir() + timeStamp + "_static.json")) {
            (new GsonBuilder().create()).toJson(inputData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileWriter = new FileWriter(filePath + timeStamp + "_dynamic.json");
        if(pretty) {
            this.gson = new GsonBuilder().setPrettyPrinting().create(); // For readable JSON
            fileWriter.write("{\n\"events\": [ \n");
        }
        else {
            this.gson = new GsonBuilder().create(); // For NOT readable JSON
            fileWriter.write("{\"events\":[");
        }
    }

    // Method to write an event to the file
    public void writeEvent(EventOutput eventOutput) throws IOException {
        if(!first) {
            fileWriter.write(",");
        }
        if(!first && pretty) {
            fileWriter.write("\n"); // Adds a newline after each event for readability
        }
        first = false;
        String json = gson.toJson(eventOutput); // Convert EventOutput to JSON string
        fileWriter.write(json);
    }

    // Method to close the file writer
    public void closeFile() throws IOException {
        if (fileWriter != null) {
            fileWriter.write("]}");
            fileWriter.close();
        }
    }
}
