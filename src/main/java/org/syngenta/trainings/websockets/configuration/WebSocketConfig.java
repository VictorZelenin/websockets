package org.syngenta.trainings.websockets.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.syngenta.trainings.websockets.TractorSensorDataGenerator;
import org.syngenta.trainings.websockets.websocket.SensorDataSocketHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final ObjectMapper objectMapper;
    private final TractorSensorDataGenerator dataGenerator;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sensorDataSocketHandler(), "/sensors");
    }

    @Bean
    public SensorDataSocketHandler sensorDataSocketHandler() {
        return new SensorDataSocketHandler(objectMapper, dataGenerator);
    }
}
