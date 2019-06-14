package org.syngenta.trainings.websockets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.syngenta.trainings.websockets.domain.TractorSensorData;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TractorSensorDataGenerator {
    @Value("${application.number-of-tractors}")
    private int numberOfTractors;
    private List<TractorSimulation> simulations;

    @PostConstruct
    public void init() {
        simulations = IntStream.range(1, numberOfTractors)
                .mapToObj(i -> new TractorSimulation())
                .collect(Collectors.toList());
    }

    public List<TractorSensorData> nextSensorData() {
        return simulations.stream()
                .map(TractorSimulation::produceSensorData)
                .collect(Collectors.toList());
    }
}
