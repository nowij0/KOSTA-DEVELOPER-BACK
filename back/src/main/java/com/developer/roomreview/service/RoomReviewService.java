package com.developer.roomreview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.reservation.dto.ReservationDTO;
import com.developer.reservation.entity.Reservation;
import com.developer.reservation.repository.ReservationRepository;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roomreview.dto.RoomReviewDTO;
import com.developer.roomreview.dto.RoomReviewDTO.RoomReviewInsertDTO;
import com.developer.roomreview.entity.RoomReview;
import com.developer.roomreview.repository.RoomReviewRepository;
import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.users.dto.UsersDTO;

@Service
public class RoomReviewService {
	@Autowired
	private RoomReviewRepository rrRepository;
	@Autowired
	private ReservationRepository rRepository;

	/**
	 * [마이페이지 스터디카페 후기페이지] 후기작성한다
	 * 
	 * @author ds
	 * @param resSeq, content, star
	 */
	public void insertReview(RoomReviewInsertDTO rrDTO) throws AddException {
		RoomReview rr = new RoomReview();
		rr.setResSeq(rrDTO.getResSeq());
		Optional<Reservation> optR = rRepository.findById(rrDTO.getResSeq());
		Reservation r = optR.get();
		rr.setContent(rrDTO.getContent());
		rr.setStar(rrDTO.getStar());
		rr.setReservation(r);
		rrRepository.save(rr);
	}

	/**
	 * [마이페이지 스터디카페 후기페이지]특정 스터디룸 후기 리스트 전체출력
	 * 
	 * @author ds
	 * @param srSeq 스터디카페 시퀀스
	 * @return List<RoomReviewDTO.RoomReviewSelectAllDTO>
	 */

	public List<RoomReviewDTO.RoomReviewSelectAllDTO> selectAll(Long srSeq) throws FindException {
		List<Object[]> list = rrRepository.findBySrSeq1(String.valueOf(srSeq));

		List<RoomReviewDTO.RoomReviewSelectAllDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			RoomReviewDTO.RoomReviewSelectAllDTO rrDTO = new RoomReviewDTO.RoomReviewSelectAllDTO();

			rrDTO.setContent((String) list.get(i)[5]);
			rrDTO.setCdate((Date) list.get(i)[3]);
			rrDTO.setStar(Integer.parseInt(String.valueOf(list.get(i)[4])));
			StudyroomDTO.StudyroomNameDTO studyDTO = new StudyroomDTO.StudyroomNameDTO();
			studyDTO.setName((String) list.get(i)[1]);
			ReservationDTO.RoomReviewSelectAllDTO reservationDTO = new ReservationDTO.RoomReviewSelectAllDTO();
			RoomInfoDTO.RoomInfoNameDTO roomInfoDTO = new RoomInfoDTO.RoomInfoNameDTO();
			roomInfoDTO.setName((String) list.get(i)[2]);
			roomInfoDTO.setStudyroomNameDTO(studyDTO);
			UsersDTO.UserNickNameDTO userDTO = new UsersDTO.UserNickNameDTO();

			userDTO.setNickname((String) list.get(i)[0]);
			reservationDTO.setRoomInfoNameDTO(roomInfoDTO);
			reservationDTO.setUserNickNameDTO(userDTO);
			rrDTO.setRrsaDTO(reservationDTO);
			dto.add(rrDTO);
		}
		return dto;
	}

	/**
	 * [마이페이지 스터디카페 후기페이지] 유저 아이디로 작성한 이용후기 목록을 출력한다
	 * 
	 * @author ds
	 * @param userId 유저아이디
	 * @return List<RoomReviewDTO.selectMyRmRvDTO> 유저의 작성한 이용후기 전체목록
	 */
	public List<RoomReviewDTO.selectMyRmRvDTO> selectMyRmRv(String userId) throws FindException {
		List<Object[]> list = rrRepository.findByUserId(userId);
		List<RoomReviewDTO.selectMyRmRvDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			RoomReviewDTO.selectMyRmRvDTO smDTO = new RoomReviewDTO.selectMyRmRvDTO();
			smDTO.setContent((String) list.get(i)[4]);
			smDTO.setCdate((Date) list.get(i)[2]);
			smDTO.setStar(Integer.parseInt(String.valueOf(list.get(i)[3])));
			ReservationDTO.RoomReviewSelectMyRmRvDTO resDTO = new ReservationDTO.RoomReviewSelectMyRmRvDTO();
			RoomInfoDTO.RoomInfoNameDTO rinDTO = new RoomInfoDTO.RoomInfoNameDTO();
			StudyroomDTO.StudyroomNameDTO studyDTO = new StudyroomDTO.StudyroomNameDTO();
			studyDTO.setName((String) list.get(i)[0]);
			rinDTO.setStudyroomNameDTO(studyDTO);
			rinDTO.setName((String) list.get(i)[1]);
			resDTO.setRoomInfoNameDTO(rinDTO);
			smDTO.setRrsaDTO(resDTO);
			dto.add(smDTO);
		}
		return dto;
	}

	/**
	 * [마이페이지 스터디카페 후기페이지] 예약시퀀스를 받아 해당 후기상세출력한다
	 * 
	 * @author ds
	 * @param resSeq 예약 시퀀스
	 * @return RoomReviewDTO.selectMyRmRvDetailDTO 유저의 작성한 이용후기 상세정보
	 */
	public List<RoomReviewDTO.selectMyRmRvDetailDTO> selectRmRvDetail(Long resSeq) throws FindException {
		List<Object[]> list = rrRepository.getByResSeq(resSeq);
		System.out.println("리스트사이즈는" + list.size());

		List<RoomReviewDTO.selectMyRmRvDetailDTO> dto = new ArrayList<>();

		RoomReviewDTO.selectMyRmRvDetailDTO rrDTO = new RoomReviewDTO.selectMyRmRvDetailDTO();
		ReservationDTO.RoomReviewSelectMyRmRvDetailDTO resDTO = new ReservationDTO.RoomReviewSelectMyRmRvDetailDTO();
		RoomInfoDTO.RoomInfoNameDTO rinDTO = new RoomInfoDTO.RoomInfoNameDTO();
		StudyroomDTO.StudyroomNameDTO snDTO = new StudyroomDTO.StudyroomNameDTO();

		for (int i = 0; i < list.size(); i++) {

			rrDTO.setCdate((Date) list.get(i)[3]);
			rrDTO.setStar(Integer.parseInt(String.valueOf(list.get(i)[4])));
			rrDTO.setContent((String) list.get(i)[5]);
			resDTO.setUsingDate((Date) list.get(i)[2]);
			rinDTO.setName((String) list.get(i)[1]);
			snDTO.setName(String.valueOf(list.get(i)[0]));
			rinDTO.setStudyroomNameDTO(snDTO);
			resDTO.setRoomInfoNameDTO(rinDTO);
			rrDTO.setRrsaDTO(resDTO);
			dto.add(rrDTO);

		}

		return dto;

	}

}