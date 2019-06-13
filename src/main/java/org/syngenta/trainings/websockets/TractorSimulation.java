package org.syngenta.trainings.websockets;

import io.github.benas.randombeans.api.EnhancedRandom;
import org.syngenta.trainings.websockets.domain.GeoLocation;
import org.syngenta.trainings.websockets.domain.Status;
import org.syngenta.trainings.websockets.domain.TractorSensorData;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class TractorSimulation {
    private static AtomicInteger COUNTER = new AtomicInteger(0);

    private static final GeoLocation LEFT_NORTHERN_POINT = new GeoLocation(46.791748, 32.599024);
    private static final GeoLocation RIGHT_NORTHERN_POINT = new GeoLocation(46.794590, 32.625934);
    private static final GeoLocation LEFT_SOUTHERN_POINT = new GeoLocation(46.785167, 32.609457);
    private static final GeoLocation RIGHT_SOUTHERN_POINT = new GeoLocation(46.786594, 32.628620);

    private final Random random = new Random();

    private TractorSensorData prevSensorData;

    TractorSensorData produceSensorData() {
        if (prevSensorData == null) {
            TractorSensorData sensorData = new TractorSensorData(COUNTER.incrementAndGet(), LocalDateTime.now(),
                    LEFT_SOUTHERN_POINT, 0.0, 0.0, Status.STOPPED, 0);
            this.prevSensorData = sensorData;
            return sensorData;
        }

        double tractorSpeed = randomizeSpeed();
        double fuelConsumption = calculateFuelConsumption(tractorSpeed);
        GeoLocation randomNextLocation = nextLocation(prevSensorData.getLocation());
        long processedSeeds = prevSensorData.getCountOfProcessedSeeds() + getProcessedSeeds();

        TractorSensorData sensorData = new TractorSensorData(prevSensorData.getTractorId(), LocalDateTime.now(),
                randomNextLocation, tractorSpeed, fuelConsumption, Status.WORKING, processedSeeds);

        this.prevSensorData = sensorData;

        return sensorData;
    }

    private double randomizeSpeed() {
        final double high = 5.5;
        final double low = 2.0;
        return low + (high - low) * random.nextDouble();
    }

    private double calculateFuelConsumption(double speed) {
        if (speed == 0) {
            return 0;
        }

        return speed + random.nextDouble() * 2.5;
    }

    private int getProcessedSeeds() {
        final int high = 1000;
        final int low = 100;

        return random.nextInt(high - low) + low;
    }

    private GeoLocation nextLocation(GeoLocation currLocation) {
        final double coordDelta = 0.01;
        Direction direction = getDirection(currLocation, coordDelta);

        if (direction == Direction.TOP) {
            return new GeoLocation(currLocation.getLatitude(), currLocation.getLongitude() + coordDelta);
        }

        if (direction == Direction.RIGHT) {
            return new GeoLocation(currLocation.getLatitude() + coordDelta, currLocation.getLongitude());
        }

        if (direction == Direction.LEFT) {
            return new GeoLocation(currLocation.getLatitude() - coordDelta, currLocation.getLongitude());
        }

        if (direction == Direction.BOTTOM) {
            return new GeoLocation(currLocation.getLatitude(), currLocation.getLongitude() - coordDelta);
        }

        return currLocation;
    }

    // Here we have a rectangle like:
    // A-----B
    // |     |
    // |     |
    // C-----D
    private Direction getDirection(GeoLocation currLocation, double coordDelta) {
        Direction direction = EnhancedRandom.random(Direction.class);

        if (direction == Direction.TOP && currLocation.getLongitude() + coordDelta >= LEFT_NORTHERN_POINT.getLongitude()) {
            if (currLocation.getLatitude() + coordDelta >= RIGHT_NORTHERN_POINT.getLatitude()) {
                direction = Direction.BOTTOM;
            } else {
                direction = Direction.RIGHT;
            }
        }

        if (direction == Direction.BOTTOM && currLocation.getLongitude() + coordDelta <= LEFT_SOUTHERN_POINT.getLongitude()) {
            if (currLocation.getLatitude() + coordDelta >= RIGHT_SOUTHERN_POINT.getLatitude()) {
                direction = Direction.TOP;
            } else {
                direction = Direction.RIGHT;
            }
        }

        return direction;
    }

    private enum Direction {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
}
