package org.syngenta.trainings.websockets.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TractorSensorData {
    private long tractorId;
    private LocalDateTime dateTime;
    private GeoLocation location;
    private double speed;
    private double fuelConsumption;
    private Status tractorStatus;
    private long countOfProcessedSeeds;
}
