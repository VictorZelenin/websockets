package org.syngenta.trainings.websockets;

import org.springframework.stereotype.Component;
import org.syngenta.trainings.websockets.domain.TractorSensorData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class TractorSensorDataGenerator {
    private static final int NUMBER_OF_TRACTORS = 5;

    private List<TractorSimulation> simulations;

    public TractorSensorDataGenerator() {
        this.simulations = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < NUMBER_OF_TRACTORS; i++) {
            simulations.add(new TractorSimulation());
        }
    }

    public List<TractorSensorData> nextSensorData() {
        List<TractorSensorData> sensorData = new ArrayList<>();
        for (TractorSimulation simulation : simulations) {
            sensorData.add(simulation.produceSensorData());
        }

        return sensorData;
    }
}
