package com.developer.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	
	/*
	 	/app으로 시작하는 대상이 있는 클라이언트에서 보낸 모든 메시지는 
		@MessageMapping 어노테이션이 달린 메서드로 라우팅
		
			"/app/chat.sendMessage" 메세지는 sendMessage()로 라우팅
			"/app/chat.addUser" 메시지는 addUser()로 라우팅 된다. 
	*/
	
	
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}