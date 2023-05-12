package com.developer.chat;

import java.util.HashMap;
import java.util.UUID;

import lombok.Data;

@Data
public class ChatRoomDTO {

    private String roomId;  // 채팅방 아이디
    private String roomName;// 채팅방 이름
    private long userCount; // 채팅방 인원수

    private HashMap<String,String> userList = new HashMap<>();

    public ChatRoomDTO create(String roomName){
        ChatRoomDTO chatRoom = new ChatRoomDTO();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        
        return chatRoom;
    }
}
