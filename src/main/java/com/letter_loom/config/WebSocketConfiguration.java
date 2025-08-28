package com.letter_loom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    // Initialize handshake between client and server
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/game-socket").withSockJS();
//        registry.addEndpoint("/game-socket")
//                .setAllowedOriginPatterns("*");
    }

    // Configures message routing between client and server
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/game-room"); // server -> client responses (SUBSCRIBE endpoints)
        registry.setApplicationDestinationPrefixes("/game"); // client -> server requests (SEND endpoints)
    }

}
