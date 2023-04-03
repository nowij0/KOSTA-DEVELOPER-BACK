package com.developer.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.host.dto.HostDTO;
import com.developer.host.entity.Host;
import com.developer.host.service.HostService;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.service.LessonService;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roominfo.service.RoomInfoService;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;
import com.developer.studycafe.service.StudycafeService;
import com.developer.tutor.service.TutorService;
import com.developer.users.dto.UsersDTO;
import com.developer.users.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/*")
@RequiredArgsConstructor
public class AdminController {

	private final HostService hService;
	private final LessonService lservice;
	private final UsersService uService;
	private final TutorService tService;
	private final RoomInfoService riService;
	private final StudycafeService sService;


	/**
	 * 호스트 회원 전체출력
	 * @author Jin
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "host")
	public ResponseEntity<?> selectAllHostUser() throws FindException {
		List<Host> list = hService.selectAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [관리자 대쉬보드] 스터디카페 최신 순 5, 수업 5개 리스트
	 * 
	 * @author ds
	 * @return 스터디카페 최신 순 5, 수업 5개 리스트
	 * @throws FindException
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStudyroomList5() throws FindException {
		AdminDTO.getList5DTO dto = new AdminDTO.getList5DTO();

		List<StudycafeDTO.StudycafeList5DTO> list1 = sService.selectList5();
		List<LessonDTO.LessonList5DTO> list2 = lservice.selectList5();
		dto.setStudycafeList5DTO(list1);
		dto.setLessonList5DTO(list2);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	/**
	 * 관리자페이지에서 회원리스트 전체출력
	 * 
	 * @author DS
	 * @return 회원리스트
	 * @throws FindException
	 */
	@GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUsers() throws FindException {
		List<UsersDTO> list = uService.getALLUsers();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 관리자페이지 회원리스트에서 검색하기
	 * 
	 * @author DS
	 * @param userId
	 * @return 검색된 회원정보
	 * @throws FindException
	 */
	@GetMapping(value = "users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getuserById(@PathVariable String userId) throws FindException {
		List<UsersDTO> list = uService.getUserById(userId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 관리자페이지 아이디 1개의 상세정보 출력
	 * 
	 * @author DS
	 * @param userId
	 * @return 검색된 회원정보
	 * @throws FindException
	 */
	@GetMapping(value = "users/detail/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getuserDetail(@PathVariable String userId) throws FindException {
		UsersDTO dto = uService.selectUserDetail(userId);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * [관리자] 모든 수업 목록
	 * 
	 * @author moonone
	 * @return 수업 목록
	 */
	@GetMapping(value = "lesson", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> allLessonList(HttpSession session) throws FindException {
			List<LessonDTO.allLessonListDTO> list = lservice.allLessonList();
			return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [관리자] 수업을 예약한 튜티 목록
	 * 
	 * @author moonone
	 * @param lessonSeq 수업번호
	 * @return 튜티목록
	 */
	@GetMapping(value = "lesson/detail/{lessonSeq}")
	public ResponseEntity<?> applyTuteeList(@PathVariable Long lessonSeq) {
		List<UsersDTO.uNameDTO> list = uService.applyTuteeList(lessonSeq);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [관리자] 수업을 예약한 튜티 삭제
	 * 
	 * @author moonone
	 * @param lessonSeq 수업번호
	 * @return 튜티목록
	 * @throws RemoveException
	 */
	@DeleteMapping(value = "lesson/detail/{lessonSeq}/{tuteeId}")
	public ResponseEntity<?> deleteTutee(@PathVariable Long lessonSeq, @PathVariable String tuteeId)
			throws RemoveException {
		uService.deleteTutee(tuteeId, lessonSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [Users] 튜터 미승인 목록을 출력한다.
	 * 
	 * @author SR
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "users/tutor", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> tutorUnapproveList() throws FindException {
		List<UsersDTO.unapproveTutorDTO> list = uService.selectAllUnapproveTutor();
		if (list.isEmpty()) {
			return new ResponseEntity<>("미승인한 튜터가 없습니다", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	/**
	 * [Tutor]튜터로 승인한다.(승인메일 포함)
	 * 
	 * @author SR
	 * @param userId
	 * @return
	 * @throws FindException, Exception 
	 */
	@PutMapping(value = "users/tutor/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> tutorApply(@PathVariable String userId) throws FindException, Exception {
		tService.tutorApply(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [Tutor] 튜터 승인거절(삭제 및 거절메일 포함)
	 * 
	 * @author SR
	 * @param userId
	 * @return
	 * @throws Exception 
	 */

	@PostMapping(value = "users/tutor/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> tutorReject(@PathVariable String userId) throws FindException, Exception {
			tService.deleteTutor(userId);
			return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [HostUser] 미승인 호스트 유저 목록 출력한다.
	 * 
	 * @author SR
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "host/unapprove")
	public ResponseEntity<?> hostUnapproveList() throws FindException {

		List<HostDTO.unApproveHostDTO> list = hService.hostUnapproveList();
		if (list.isEmpty()) {
			return new ResponseEntity<>("미승인 호스트 유저가 없습니다.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	/**
	 * [HostUser] 미승인 호스트를 승인한다. (메일 포함)
	 * 
	 * @author SR
	 * @param hostId
	 * @return
	 * @throws FindException, Exception 
	 */
	@PutMapping(value = "host/unapprove/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> hostOk(@PathVariable String hostId) throws FindException, Exception {
		hService.readyOk(hostId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [HostUser] 호스트 승인을 거절한다(삭제 및 거절메일 포함)
	 * 
	 * @author SR
	 * @param hostId
	 * @return
	 * @throws Exception 
	 * @throws FindException 
	 */
	@PostMapping(value = "host/unapprove/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> hostReject(@PathVariable String hostId) throws FindException, Exception {
		hService.deleteHost(hostId);
		return new ResponseEntity<>(HttpStatus.OK);

	}


	/**
	 * 관리자 스터디카페 전체목록 출력
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "studyroom")
	public ResponseEntity<?> getAllStudyroom() throws FindException {
		List<StudycafeDTO.getAllStudycafeDTO> list = sService.getAllStudyroom();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 관리자페이지 방상세정보+ 방에 해당하는 예약내역
	 * 
	 * @author choigeunhyeong
	 * @param srSeq
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "studyroom/detail/{srSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> MypageStudyroomDetail(@PathVariable Long srSeq) throws FindException {

		AdminDTO.adminStudyroomDetailDTO dto = new AdminDTO.adminStudyroomDetailDTO();

		Studycafe s = sService.detailStudyroom(srSeq);
		List<RoomInfoDTO.getReservationDTO> lList = riService.getReservation(srSeq);

		dto.setS(s);
		dto.setReservationDTO(lList);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * 호스트유저 1개의 전체정보 출력
	 * 
	 * @author choigeunhyeong
	 * @param hostId
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "host/detail/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> infoHost(@PathVariable String hostId, HttpSession session) throws FindException {
		HostDTO hostDTO = hService.selectHost(hostId);
		return new ResponseEntity<>(hostDTO, HttpStatus.OK);
	}
}
