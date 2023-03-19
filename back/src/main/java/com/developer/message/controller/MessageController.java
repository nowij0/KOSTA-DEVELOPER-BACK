package com.developer.message.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.hostuser.entity.HostUser;
import com.developer.hostuser.repository.HostUserRepository;
import com.developer.message.dto.MessageDTO;
import com.developer.message.service.MessageService;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("message/*")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService mService;
	private final UsersRepository uRepository;
	private final HostUserRepository hRepository;

	/**
	 * 쪽지 작성
	 * 
	 * @author moonone
	 * @param mDTO
	 * @param session
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> sendUMessage(MessageDTO.addMessage mDTO, HttpSession session) {
		String logined = (String) session.getAttribute("logined"); 
		mService.addMessage(mDTO, logined);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	/**
//	 * 쪽지 상세내용 
//	 * @author moonone
//	 * @param messageSeq
//	 * @param session
//	 * @return
//	 */
//	@GetMapping("{messageSeq}")
//	public ResponseEntity<?> messageDetail(@PathVariable("messageSeq") Long messageSeq,
//																		HttpSession session) {
//		 MessageDTO.messageDetail result = mService.detail(messageSeq);
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}

//	/**
//	 * [User] 받은 쪽지함 목록
//	 * @author moonone
//	 * @param session
//	 * @return
//	 */
//	@GetMapping("received/user")
//	public ResponseEntity<?> getReceivedUMessage(HttpSession session) {
//		String userId = (String) session.getAttribute("logined");
//		Optional<Users> user = uRepository.findById(userId);
//		List<MessageDTO> list = mService.receivedMessage(user.get());
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
//	
//	/**
//	 * [User] 해당 사용자와 나눈 쪽지 목록
//	 * @author moonone
//	 * @param session
//	 * @return
//	 */
//	@GetMapping("received/user/{senderId}")
//	public ResponseEntity<?> getReceivedUMessage(@PathVariable("senderId") String senderId, HttpSession session) {
//		String userId = (String) session.getAttribute("logined");
//		Optional<Users> user = uRepository.findById(userId);
//		List<MessageDTO.messageDetail> list = mService.receivedMessageList(user.get(), senderId);
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
//	
//	
//	/**
//	 *  [host] 받은 쪽지함 목록 
//	 * @param session
//	 * @return
//	 */
//	@GetMapping("received/host")
//	public ResponseEntity<?> getReceivedHMessage(HttpSession session) {
//		String userId = (String) session.getAttribute("hostLogined");
//		System.out.println("값: " + userId);
//		Optional<HostUser> host = hRepository.findById(userId);
//		List<MessageDTO> list = mService.receivedMessage(host.get());
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
//
//
//	/**
//	 * [User] 보낸 쪽지함 목록
//	 * 
//	 * @author moonone
//	 * @param session
//	 * @return
//	 */
//	@GetMapping("sent/user")
//	public ResponseEntity<?> getSentUMessage(HttpSession session) {
//		String userId = (String) session.getAttribute("logined");
//		Optional<Users> user = uRepository.findById(userId);
//		List<MessageDTO> list = mService.sentMessage(user.get());
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
//	
//	
//	/**
//	 * [Host] 보낸 쪽지함 목록
//	 * @param session
//	 * @return
//	 */
//	@GetMapping("sent/host")
//	public ResponseEntity<?> getSentHMessage(HttpSession session) {
//		String userId = (String) session.getAttribute("hostLogined");
//		Optional<HostUser> host = hRepository.findById(userId);
//		List<MessageDTO> list = mService.sentMessage(host.get());
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}



}
