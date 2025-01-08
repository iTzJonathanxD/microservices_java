package com.microservice.metodoDePago.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class ClientConnectionInterceptor implements ChannelInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ClientConnectionInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null) {
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                String sessionId = accessor.getSessionId();
                logger.info("Cliente conectado: Session ID = " + sessionId);
            } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                String sessionId = accessor.getSessionId();
                logger.info("Cliente desconectado: Session ID = " + sessionId);
            }
        }

        return message;
    }
}
