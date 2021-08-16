package com.andy.websocket.client;

import com.andy.websocket.dto.Message;
import com.andy.websocket.dto.MessageStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class UserEventSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(UserEventSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/wss-user-events/messages", this);
        logger.info("Subscribed to /wss-user-events/messages");
        session.send("/app/wss-chat/message/send/"+session.getSessionId(), generateMsg());
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        logger.info("Received : " +msg.toString());
    }

    private Message generateMsg() {
        Message msg = Message.builder().message("Hi How are you?").messageStatus(MessageStatus.NEW).build();
        return msg;
    }
}
