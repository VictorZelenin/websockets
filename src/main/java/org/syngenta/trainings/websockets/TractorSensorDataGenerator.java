package org.syngenta.trainings.websockets;

import org.springframework.stereotype.Service;
import org.syngenta.trainings.websockets.domain.TractorSensorData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TractorSensorDataGenerator {
    private static final int NUMBER_OF_TRACTORS = 5;

    private List<TractorSimulation> simulations;

    public TractorSensorDataGenerator() {
        this.simulations = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        simulations = IntStream.range(1, NUMBER_OF_TRACTORS)
                .mapToObj(i -> new TractorSimulation())
                .collect(Collectors.toList());
    }

    public List<TractorSensorData> nextSensorData() {
        return simulations.stream()
                .map(TractorSimulation::produceSensorData)
                .collect(Collectors.toList());
    }
}
