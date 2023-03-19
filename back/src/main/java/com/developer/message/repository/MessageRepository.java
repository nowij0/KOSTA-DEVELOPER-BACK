package com.developer.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.developer.hostuser.entity.HostUser;
import com.developer.message.entity.Message;
import com.developer.users.entity.Users;

public interface MessageRepository extends JpaRepository<Message, Long>{
//	List<Message> findAllByuReceiver(Users user);
//    List<Message> findAllByuSender(Users user);
//    List<Message> findAllByhReceiver(HostUser host);
//    List<Message> findAllByhSender(HostUser host);
//    
//    //쪽지 보낸 사용자 목록
//    @Query(nativeQuery = true,
//    		value =  "SELECT\n"
//    		+ "distinct u_sender_id\n"
//    		+ "FROM MESSAGE\n"
//    		+ "WHERE u_sender_id IS NOT NULL")
//    List<String> findBySender();
//    
//    //쪽지 보낸 호스트 목록
//    @Query(nativeQuery = true,
//    		value =  "SELECT\n"
//    		+ "distinct h_sender_id\n"
//    		+ "FROM MESSAGE\n"
//    		+ "WHERE h_sender_id IS NOT NULL")
//    List<String> findByHSender();
    
}
