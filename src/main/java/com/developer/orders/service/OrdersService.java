package com.developer.orders.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.entity.Lesson;
import com.developer.lesson.repository.LessonRepository;
import com.developer.orders.dto.OrdersDTO;
import com.developer.orders.entity.Orders;
import com.developer.orders.repository.OrdersRepository;
import com.developer.tutor.dto.TutorDTO;
import com.developer.tutor.entity.Tutor;
import com.developer.tutor.repository.TutorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {

	private final OrdersRepository oRepository;
	private final TutorRepository tRepository;
	private final LessonRepository lRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	/**
	 * 결제한 값을 저장
	 * @author Jin
	 * @param oDTO
	 * @param tutorId
	 * @throws AddException
	 */
	public void addOrderDTO(String impUid, String logined, Long lessonSeq) throws AddException{
//		logined = "111";
		Optional<Tutor> optT = tRepository.findById(logined);
		Tutor tutor = optT.get();
		
//		lessonSeq = 2L;
		Optional<Lesson> optL = lRepository.findById(lessonSeq);
		Lesson lesson = optL.get();
		
		Orders orders = new Orders();
		orders.setTutor(tutor);
		orders.setLesson(lesson);
		
//		Map<String, Object> model = new HashMap<>();
//		model.put("imp_uid", oDTO.getImpUid());
//		String impUid = (String)model.get("imp_uid");
		System.out.println("impUid는~~"+impUid);
		
		orders.setImpUid(impUid);
		oRepository.save(orders);
		
	}
	
	/**
	 * 튜터 아이디에 해당하는 결제내역 조회
	 * @author Jin
	 * @param orderId
	 * @return
	 * @throws FindException
	 */
	public List<OrdersDTO.selectOrdersDTO> findByOrderId(String logined) throws FindException{
		List<Object[]> oList = oRepository.getOrdersByOrderId(logined);
//		List<Object[]> lList = lRepository.getLessonByUser1(orderId);
		List<OrdersDTO.selectOrdersDTO> dto = new ArrayList<>();
		for(int i = 0; i < oList.size(); i++) {
			TutorDTO.tutorOrderDTO tDTO = new TutorDTO.tutorOrderDTO();
			OrdersDTO.selectOrdersDTO oDTO = new OrdersDTO.selectOrdersDTO();
			LessonDTO.lessonOrderDTO lDTO = new LessonDTO.lessonOrderDTO();
			tDTO.setTutorId((String)logined);
			lDTO.setLessonName((String) oList.get(i)[0]);
			oDTO.setOdate((Date) oList.get(i)[1]);
			oDTO.setLessonDTO(lDTO);
			oDTO.setTutor(tDTO);
			dto.add(oDTO);
		}
		return dto;
	}
	
	
	/**
	 * 결제 전체 내역 불러오기
	 * @author Jin
	 * @return
	 * @throws FindException
	 */
	public List<Orders> selectAll() throws FindException{
		List<Orders> list = oRepository.selectAll();
		return list;
	}

	
}
