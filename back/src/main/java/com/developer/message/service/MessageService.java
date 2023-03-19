package com.developer.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.developer.hostuser.entity.HostUser;
import com.developer.hostuser.repository.HostUserRepository;
import com.developer.message.dto.MessageDTO;
import com.developer.message.entity.Message;
import com.developer.message.repository.MessageRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository mRepository;
	private final UsersRepository uRepository;
	private final HostUserRepository hRepository;

	
	/**
	 * 쪽지 작성 
	 * @param mDTO 입력한 쪽지내용 
	 * @param logined 쪽지 작성자(발신자) 
	 */
	public void addMessage(MessageDTO.addMessage mDTO, String logined) {
		Message message = new Message();
		message.setTitle(mDTO.getTitle());
		message.setContent(mDTO.getContent());
		message.setSender(logined);
		message.setReceiver(mDTO.getReceiverId());
		message.setSendDate(mDTO.getSendDate());
		
		mRepository.save(message);
	}
	

//	/**
//	 * [User] 받은 쪽지함 목록
//	 * 
//	 * @author moonone
//	 * @param user
//	 * @return
//	 */
//	public List<MessageDTO> receivedMessage(Users user) {
//		// 해당 사용자에게 메세지를 보냈던 사용자들 목록 (중복X)
//		List<String> senderList = mRepository.findBySender();
//		List<Message> list = new ArrayList<>();
//		List<MessageDTO> dtoList = new ArrayList<>();
//
//		for (int i = 0; i < senderList.size(); i++) {
//			Optional<Users> u = uRepository.findById(senderList.get(i));
//			list = mRepository.findAllByuSender(u.get());
//
//			MessageDTO dto = new MessageDTO();
//			if (list.size() == 1) {
//				if (list.get(0).getUSender().getUserId() != user.getUserId()) {
//					dto.setTitle(list.get(0).getTitle());
//					dto.setContent(list.get(0).getContent());
//					dto.setSenderId(list.get(0).getUSender().getUserId());
//					dto.setReceiverId(list.get(0).getUReceiver().getUserId());
//					dto.setSendDate(list.get(0).getSendDate());
//				} else {
//					continue;
//				}
//			} else {
//				if (list.get(list.size() - 1).getUSender().getUserId() != user.getUserId()) {
//					dto.setTitle(list.get(list.size() - 1).getTitle());
//					dto.setContent(list.get(list.size() - 1).getContent());
//					dto.setSenderId(list.get(list.size() - 1).getUSender().getUserId());
//					dto.setReceiverId(list.get(list.size() - 1).getUReceiver().getUserId());
//					dto.setSendDate(list.get(list.size() - 1).getSendDate());
//				} else {
//					continue;
//				}
//			}
//			dtoList.add(dto);
//		}
//		return dtoList;
//	}
//
//	/**
//	 * [User] 해당 사용자와 나눈 쪽지 목록
//	 * 
//	 * @param user
//	 * @param senderId
//	 * @return
//	 */
//	public List<MessageDTO.messageDetail> receivedMessageList(Users user, String senderId) {
//
//		List<Message> list = new ArrayList<>();
//		List<MessageDTO.messageDetail> dtoList = new ArrayList<>();
//		System.out.println("값: " + senderId);
//
//		Optional<Users> u = uRepository.findById(senderId);
//		list = mRepository.findAllByuSender(u.get());
//
//		for (int j = 0; j < list.size(); j++) {
//			MessageDTO.messageDetail dto = new MessageDTO.messageDetail();
//
//			dto.setMessageSeq(list.get(j).getMessageSeq());
//			dto.setTitle(list.get(j).getTitle());
//			dto.setContent(list.get(j).getContent());
//			dto.setSenderId(list.get(j).getUSender().getUserId());
//			dto.setSendDate(list.get(j).getSendDate());
//			dto.setReadCheck(list.get(j).getReadChek());
//
//			dtoList.add(dto);
//		}
//
//		return dtoList;
//	}
//
//	/**
//	 * [User] 보낸 쪽지함 목록
//	 * 
//	 * @author moonone
//	 * @param user
//	 * @return
//	 */
//	public List<MessageDTO> sentMessage(Users user) {
//		List<Message> messages = mRepository.findAllByuSender(user);
//		List<MessageDTO> mDTO = new ArrayList<>();
//
//		for (Message message : messages) {
//			if (message.getDeletedBySender() == 0) {
//				mDTO.add(MessageDTO.uDTO(message));
//			}
//		}
//		return mDTO;
//	}
//
//	/**
//	 * [Host] 보낸 쪽지함 목록
//	 * 
//	 * @author moonone
//	 * @param host
//	 * @return
//	 */
//	public List<MessageDTO> sentMessage(HostUser host) {
//		return null;
//	}
//
//	/**
//	 * [host] 받은 쪽지함 목록
//	 * 
//	 * @author moonone
//	 * @param host
//	 * @return
//	 */
//	public List<MessageDTO> receivedMessage(HostUser host) {
//		// 해당 사용자에게 메세지를 보냈던 사용자들 목록 (중복X)
//		List<String> senderList = mRepository.findByHSender();
//		List<Message> list = new ArrayList<>();
//		List<MessageDTO> dtoList = new ArrayList<>();
//
//		for (int i = 0; i < senderList.size(); i++) {
//			Optional<HostUser> h = hRepository.findById(senderList.get(i));
//			list = mRepository.findAllByhSender(h.get());
//
//			MessageDTO dto = new MessageDTO();
//			if (list.size() == 1) {
//				dto.setTitle(list.get(0).getTitle());
//				dto.setContent(list.get(0).getContent());
//				dto.setSenderId(list.get(0).getHSender().getHostId());
//				dto.setReceiverId(list.get(0).getUReceiver().getUserId());
//				dto.setSendDate(list.get(0).getSendDate());
//			} else {
//				dto.setTitle(list.get(list.size() - 1).getTitle());
//				dto.setContent(list.get(list.size() - 1).getContent());
//				dto.setSenderId(list.get(list.size() - 1).getHSender().getHostId());
//				dto.setReceiverId(list.get(list.size() - 1).getUReceiver().getUserId());
//				dto.setSendDate(list.get(list.size() - 1).getSendDate());
//			}
//			dtoList.add(dto);
//		}
//		return dtoList;
//	}



}
