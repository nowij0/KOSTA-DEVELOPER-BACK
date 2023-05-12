package com.developer.chat;

import java.util.*;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ChatRepository {

    private Map<String, ChatRoomDTO> chatRoomMap;

    //의존성 주입이 이루어진 후 초기화 작업이 필요한 메서드에 사용. 
    //해당 어노테이션이 적용된 초기화 메서드는 WAS가 띄워질 때 혹은 Bean이 생성된 후 실행
    @PostConstruct
    public void init(){
        chatRoomMap = new HashMap<>();
    }

    // 전체 채팅방 조회
    public List<ChatRoomDTO> findAllRoom(){
        //채팅방 생성 순서를 최근순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    // roomId 기준으로 채팅방 찾기
    public ChatRoomDTO findByRoomId(String roomId){

        return chatRoomMap.get(roomId);
    }

    // roomName 으로 채팅방 만들기
    public ChatRoomDTO createChatRoom(String roomName){
        //채팅방 이름으로 채팅 방 생성후
        ChatRoomDTO chatRoom = new ChatRoomDTO().create(roomName);
        //map에 채팅방 아이디와 만들어진 채팅룸을 저장
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    // 채팅방 인원 +1
    public void increaseUser(String roomId){

        ChatRoomDTO chatRoom = chatRoomMap.get(roomId);
        chatRoom.setUserCount(chatRoom.getUserCount()+1);
    }

    // 채팅방 인원 -1
    public void decreaseUser(String roomId){

        ChatRoomDTO chatRoom = chatRoomMap.get(roomId);
        chatRoom.setUserCount(chatRoom.getUserCount()-1);
    }

    //채팅방 유저 리스트에 유저추가
    public  String addUser(String roomId, String userName){

        ChatRoomDTO chatRoom = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();
        //아이디 중복 확인 후 userList에 추가
        chatRoom.getUserList().put(userUUID,userName);

        return userUUID;
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId,String username){

        ChatRoomDTO chatRoom = chatRoomMap.get(roomId);
        String temp = username;

        // 만약 username이 중복이라면 랜덤한 숫자를 붙여준다.
        // 이 때 랜덤한 숫자를 붙였을때 getUserList 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기
        while(chatRoom.getUserList().containsValue(temp)){
            int ranNum = (int) (Math.random() * 100) + 1;
            temp = username+ranNum;
        }

        return temp;
    }

    // 채팅방 유저 리스트 삭제
    public void deleteUser(String roomId,String userUUID){
        ChatRoomDTO chatRoom = chatRoomMap.get(roomId);
        chatRoom.getUserList().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId,String userUUID){
        ChatRoomDTO chatRoom = chatRoomMap.get(roomId);

        return chatRoom.getUserList().get(userUUID);
    }

    //채팅방 전체 userList 조회
    public List<String> getUserList(String roomId){
        List<String> list = new ArrayList<>();

        ChatRoomDTO chatRoom = chatRoomMap.get(roomId);

        chatRoom.getUserList().forEach((key,value) -> list.add(value));
        
        return list;
    }

}
