package com.developer.studyroom.control;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.reservation.dto.ReservationDTO;
import com.developer.reservation.service.ReservationService;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roominfo.service.RoomInfoService;
import com.developer.roomreview.dto.RoomReviewDTO;
import com.developer.roomreview.service.RoomReviewService;
import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.studyroom.service.StudyroomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("studyroom/*")
@RequiredArgsConstructor
public class StudyroomController {

	private final StudyroomService sService;
	private final ReservationService rService;
	private final RoomInfoService riService;
	private final RoomReviewService rrservice;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * [스터디카페 메인] 주소(1) 또는 스터디카페명(2) 및 인원 수 로 스터디카페리스트를 검색한다
	 * 
	 * @author ds
	 * @param srNameAddrName, searchBy, person, orderBy
	 * @throws 전체정보 출력시 FindException예외발생한다
	 */
	@PostMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListBySearch(@RequestParam String srNameAddrName, @RequestParam Integer searchBy,
			@RequestParam Integer person, @RequestParam Integer orderBy) throws FindException {

		List<StudyroomDTO.StudyroomSelectBySearchDTO> list = sService.selectBySearch(srNameAddrName, searchBy, person,
				orderBy);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [스터디카페 메인] 스터디카페 리스트를 출력한다
	 * 
	 * @author ds
	 * @param List<StudyroomDTO.StudyroomSelectBySearchDTO>
	 * @throws 전체정보 출력시 FindException예외발생한다
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListALL() throws FindException {
		List<StudyroomDTO.StudyroomSelectBySearchDTO> list = sService.selectListALL();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [Reservation] 예약정보를 예약테이블에 넣어 예약내역에 insert
	 * 
	 * @author ds
	 * @throws 전체정보 출력시 AddException예외발생한다
	 */
	@PostMapping(value = "roominfo/reservation")
	public ResponseEntity<?> reserve(@RequestBody ReservationDTO.insertRvDTO rDTO, HttpSession session)
			throws AddException {

		String logined = (String) session.getAttribute("logined");

		System.out.println("세션아이디는:" + logined);
		if (logined != null) {
			rService.insertRv(rDTO, logined);
			return new ResponseEntity<>(rDTO, HttpStatus.OK);

		} else {
			return new ResponseEntity<>("로그인하세요", HttpStatus.OK);

		}
	}

	/**
	 * [Reservation] 룸 시퀀스와 예약일을 받아 이미 예약된 예약정보에 대한 리스트를 출력한다 [Studyroom] 스터디카페 예약일
	 * 조회시 예약 내역이 없을 경우 예약을 위한 룸정보(오픈시간, 마감시간, 가격) 출력
	 * 
	 * @author ds
	 * @param roomSeq   스터디룸 시퀀스
	 * @param usingDate 예약일
	 * @return List<Object[]> 특정일자의 해당 스터디룸 예약정보
	 * @throws 전체정보 출력시 FindException, ParseException예외발생한다
	 */
	// 스터디룸 패키지에 들어가야됨
	@GetMapping(value = "roominfo/reservation/{roomSeq}")
	public ResponseEntity<?> getReservablity(@PathVariable Long roomSeq, String usingDate)
			throws FindException, ParseException {

		List<ReservationDTO.selectAllByUsingDateDTO> list = rService.selectAllByUsingDate(roomSeq, usingDate);

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	/**
	 * Controller 3개 합친거 [RoomInfo] 스터디룸 시퀀스를 받아 스터디카페의 스터디룸 리스트를 출력한다
	 * [RoomReview]특정 스터디카페 후기 리스트 전체출력 [Studyroom] Studyroom 객체 1개의 상세정보 출력.
	 * 
	 * @author ds
	 * @param srSeq 스터디카페 시퀀스(장소번호)
	 * @return 스터디룸 리스트, 스터디카페 후기리스트, 스터디카페 정보
	 * @throws 전체정보 출력시 FindException예외발생한다
	 */
	@GetMapping(value = "roominfo/{srSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAll(@PathVariable Long srSeq) throws FindException {
		StudyroomDTO.StudyroomRoomInfoPageDTO dto = new StudyroomDTO.StudyroomRoomInfoPageDTO();

		StudyroomDTO.StudyroomAndFavStuyroomInfoDTO object = sService.getStudyroomDetail(srSeq);
		List<RoomInfoDTO.RoomInfoRoomDetailListDTO> list1 = riService.selectAll(srSeq);
		List<RoomReviewDTO.RoomReviewSelectAllDTO> list2 = rrservice.selectAll(srSeq);
		dto.setStudyroomAndFavStuyroomInfoDTO(object);
		dto.setRoominfoDTO(list1);
		dto.setRoomReviewSelectAllDTO(list2);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

}