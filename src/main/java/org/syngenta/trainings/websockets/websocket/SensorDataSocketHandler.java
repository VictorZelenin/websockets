package org.syngenta.trainings.websockets.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.syngenta.trainings.websockets.TractorSensorDataGenerator;
import org.syngenta.trainings.websockets.domain.TractorSensorData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class SensorDataSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final TractorSensorDataGenerator dataGenerator;

    @Value("${application.data-generation.time-freq}")
    private int timeFrequency;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                List<TractorSensorData> sensorData = dataGenerator.nextSensorData();
                TextMessage response = new TextMessage(objectMapper.writeValueAsString(sensorData));

                session.sendMessage(response);
            } catch (IOException e) {
                log.error("Couldn't send message: {}", e.getMessage());
            }
        }, 0, timeFrequency, TimeUnit.SECONDS);
    }
}
