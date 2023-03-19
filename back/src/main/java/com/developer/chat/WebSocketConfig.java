package com.developer.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //WebSocket 서버 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/*
	 * 클라이언트가 웹 소켓 서버에 연결하는데 사용할 웹 소켓 엔드포인트 등록 withSockJS를 통해 웹 소켓을 지원하지 않는 브라우저에 대해
	 * 웹 소켓을 대체한다. +)메소드명에 STOMP가 들어가는 경우 통신 프로토콜인 STOMP구현에서 작동된다.
	 * 
	 * Client에서 websocket연결할 때 사용할 API 경로를 설정해주는 메서드.
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println("registerStompEndpoints");
		
		registry.addEndpoint("/ws/chat") // 연결될 엔드포인트
					.setAllowedOriginPatterns("*")  
					.withSockJS(); // SocketJS 를 연결한다는 설정
	}

	/* 한 클라이언트에서 다른 클라이언트로 메시지를 라우팅하는데 사용될 메시지 브로커 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		System.out.println("configureMessageBroker");
		
		registry.enableSimpleBroker("/topic"); // 메시지를 발행하는 요청 url => 즉 메시지 보낼 때
		registry.setApplicationDestinationPrefixes("/app"); // 메시지를 구독하는 요청 url => 즉 메시지 받을 때
	}
}
