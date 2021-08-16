package com.andy.websocket.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class ConnectToWebSocket {
    @Value("${wss.base.url}")
    private   String URL;

    @Bean
    public void connectToServer() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new UserEventSessionHandler();
        StompHeaders connectHeaders = new StompHeaders();
        connectHeaders.add("login", "andy.wang@yahoo.com");
        connectHeaders.add("passcode", "Andy@1234");
        stompClient.connect(URL, new WebSocketHttpHeaders(), connectHeaders, sessionHandler);
       //  stompClient.connect(URL, sessionHandler);
        //new Scanner(System.in).nextLine();
    }
}
