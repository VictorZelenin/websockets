package org.syngenta.trainings.websockets.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.syngenta.trainings.websockets.websocket.SensorDataSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sensorDataSocketHandler(), "/sensors");
    }

    @Bean
    public SensorDataSocketHandler sensorDataSocketHandler() {
        return new SensorDataSocketHandler();
    }
}
