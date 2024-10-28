package ar.edu.itba.ss.tp5;

import ar.edu.itba.ss.tp5.IO.InputData;
import ar.edu.itba.ss.tp5.IO.OutputData;
import ar.edu.itba.ss.tp5.IO.SimulationParams;
import ar.edu.itba.ss.tp5.events.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Runner {

    private final InputData inputData;

    public Runner(InputData inputData) {
        this.inputData = inputData;
    }

    private void runSimulation(SimulationParams params) {
        try (OutputData outputData = new OutputData(params.getId(), inputData.getPretty(), params);) {
            Consumer<Event> callback = (event) -> {
                try {
                    outputData.writeEvent(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
            Simulation simulation = new Simulation(params.getId(), params, callback);
            simulation.prepare();
            simulation.runSimulation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkIds() {
        Set<String> simulationIds = inputData.getDynamic().stream().map(InputData.InputFile.DynamicField::getId).collect(Collectors.toSet());
        if (simulationIds.size() != inputData.getDynamic().size()) {
            throw new IllegalArgumentException("Simulation ids must be unique");
        }
        if (simulationIds.stream().anyMatch((id) -> id == null || id.isEmpty())) {
            throw new IllegalArgumentException("Simulation ids must not be empty");
        }
    }

    public void run() {
        checkIds();
        List<SimulationParams> simulations = new ArrayList<>();
        for (InputData.InputFile.DynamicField dynamic : inputData.getDynamic()) {
            String id = dynamic.getId();
            int repetitions = Optional.ofNullable(dynamic.getRepetitions()).orElse(inputData.getRepetitions());
            for (int i = 0; i < repetitions; i++) {
                String simulationId = id + "-" + i;
                simulations.add(new SimulationParams(simulationId, inputData, dynamic));
            }
        }
        simulations.parallelStream().forEach(this::runSimulation);
    }
}
