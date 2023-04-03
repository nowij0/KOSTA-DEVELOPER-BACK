package com.developer.host.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.host.dto.HostDTO;
import com.developer.host.service.HostService;
import com.developer.reservation.dto.ReservationDTO;
import com.developer.reservation.service.ReservationService;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roominfo.entity.RoomInfo;
import com.developer.roominfo.service.RoomInfoService;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;
import com.developer.studycafe.service.StudycafeService;
import com.developer.util.Attach;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequiredArgsConstructor
@RequestMapping("host/*")
public class HostController {
	private final HostService hService;
	private final StudycafeService sService;
	private final RoomInfoService riService;
	private final ReservationService rService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * [Studyroom] 스터디카페 + 호스트정보 출력
	 * 
	 * @author SR
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> InfoHostAndCafeController(HttpSession session) throws FindException {

		String hostId = (String) session.getAttribute("hostLogined");

		StudycafeDTO.getHostAndStudycafeDTO dto = sService.getHostAndStudycafe(hostId);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	/**
	 * [HostUser] 호스트유저 1개의 정보를 출력한다.
	 * 
	 * @author SR
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> infoHost(HttpSession session) throws FindException {

		String hostId = (String) session.getAttribute("hostLogined");
		HostDTO hostDTO = hService.selectHost(hostId);
		return new ResponseEntity<>(hostDTO, HttpStatus.OK);

	}

	/**
	 * [HostUser] 호스트 정보를 수정한다.
	 * 
	 * @author SR
	 * @param hostUserDTO
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@PutMapping(value = "info")
	public ResponseEntity<?> editHost(@RequestBody HostDTO hostUserDTO, HttpSession session) throws FindException {

		String hostId = (String) session.getAttribute("hostLogined");
		hService.updateHost(hostId, hostUserDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * 호스트 유저를 탈퇴한다. ready=2
	 * 
	 * @author SR
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@PutMapping(value = "out")
	public ResponseEntity<?> outHost(HttpSession session) throws FindException {

		String hostId = (String) session.getAttribute("hostLogined");
		hService.outHost(hostId);
		logger.info("[호스트탈퇴로 인한 로그아웃 완료] hostsessionid - " + session.getId());
		session.invalidate();

		// TODO 메인페이지로 돌아가기 하기
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * [Studyroom] 스터디카페의 영업을 시작한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @return
	 * @throws FindException
	 */
	@PutMapping(value = "open/{srSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> openCafe(@PathVariable Long srSeq) throws FindException {
		sService.openOc(srSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [Studyroom] 스터디카페의 영업을 마감한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @return
	 * @throws FindException
	 */
	@PutMapping(value = "close/{srSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> closeCafe(@PathVariable Long srSeq) throws FindException {

		sService.closeOc(srSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [Studyroom] Studyroom 객체 1개를 출력한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @return StudyroomDTO
	 * @throws FindException
	 */
	@GetMapping(value = "studyroom/{srSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> infoCafe(@PathVariable Long srSeq) throws FindException {
		StudycafeDTO dto = sService.selectStudyroom(srSeq);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * [Studyroom] 스터디카페 정보를 수정한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @param studyroomDTO
	 * @return
	 * @throws FindException
	 * @throws AddException
	 */
	@PostMapping(value = "studyroom/{srSeq}")
	public ResponseEntity<?> editCafe(@PathVariable Long srSeq, StudycafeDTO studyroomDTO, HttpSession session,
			@RequestPart MultipartFile f) throws FindException, AddException {

		String saveDirectory = "C:\\dev\\studyroom"; // 각자 주소로!
		File saveDirFile = new File(saveDirectory);
		String fileName;
		if (f != null && f.getSize() > 0) { // 첨부파일 f이 전달된 경우만 처리해라!
			long fSize = f.getSize(); // 첨부된 파일크기 확인
			String fOrigin = f.getOriginalFilename(); // 첨부된(업로드된)파일의 이름
			System.out.println("---파일---");
			System.out.println("fSize:" + fSize + ", fOrigin:" + fOrigin);

			// 기존파일 삭제 TODO: 더나은 방법 찾아보기
			Studycafe s = sService.detailStudyroom(srSeq);

			String oldFileName = s.getCafeImg();
			System.out.println("옛파일명: " + oldFileName);
			File oldFile = new File(saveDirFile, oldFileName);

			if (oldFile.exists()) {
				oldFile.delete();
			}

			// imgPath 결합
			UUID uuid = UUID.randomUUID();
			String fName = uuid.toString() + "_" + fOrigin;

			// 파일저장
			fileName = fName;
			File file = new File(saveDirFile, fileName);

			try {
				Attach.upload(f.getBytes(), file);

				// 섬네일파일 만들기 (비율맞춰서된다!)
				int width = 300;
				int height = 300;

				String thumbFileName = "t_" + fileName; // 섬네일파일명
				File thumbFile = new File(saveDirFile, thumbFileName);
				FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);// 출력스트림
				InputStream thumbnailIS = f.getInputStream(); // 첨부파일 입력스트림

				Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height);

				// 기존 섬네일파일 삭제
				String oldthumbFileName = "t_" + oldFileName;
				System.out.println("옛섬네일파일명" + oldthumbFileName);
				File oldthumbFile = new File(saveDirFile, oldthumbFileName);

				if (oldthumbFile.exists()) {
					oldthumbFile.delete();
				}
				studyroomDTO.setCafeImg(fileName);
				sService.updateCafe(srSeq, studyroomDTO);
				logger.info("파일수정 성공");
				return new ResponseEntity<>(HttpStatus.OK);
				// 읽기 쓰기
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("파일업로드에러");
				throw new AddException(e.getMessage());
			}
		}

		return new ResponseEntity<>("오류", HttpStatus.BAD_REQUEST);
	}

	/**
	 * [RoomInfo] 스터디룸을 추가한다.
	 * 
	 * @author SR
	 * @param roomInfoDTO
	 * @param srSeq
	 * @return
	 * @throws AddException
	 */
	@PostMapping(value = "roominfo/{srSeq}")
	public ResponseEntity<?> addRoom(RoomInfoDTO roomInfoDTO, HttpSession session, MultipartFile f,
			@PathVariable long srSeq) throws AddException {

		String saveDirectory = "C:\\dev\\roominfo"; // 각자 주소로!
		File saveDirFile = new File(saveDirectory);
		String fileName;
		if (f != null && f.getSize() > 0) { // 첨부파일 f이 전달된 경우만 처리해라!, f1값이 없는경우
			long fSize = f.getSize(); // 첨부된 파일크기 확인
			String fOrigin = f.getOriginalFilename(); // 첨부된(업로드된)파일의 이름
			System.out.println("---파일---");
			System.out.println("fSize:" + fSize + ", fOrigin:" + fOrigin);

			// imgPath 결합
			UUID uuid = UUID.randomUUID();
			String fName = uuid.toString() + "_" + fOrigin;

			// 파일저장
			fileName = fName;
			File file = new File(saveDirFile, fileName);

			try {
				Attach.upload(f.getBytes(), file);

				// 섬네일파일 만들기 (비율맞춰서된다!)
				int width = 300;
				int height = 300;

				// 원래 첨부파일과 구분짓기 위해
				String thumbFileName = "t_" + fileName; // 섬네일파일명
				File thumbFile = new File(saveDirFile, thumbFileName);
				FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);// 출력스트림
				InputStream thumbnailIS = f.getInputStream(); // 첨부파일 입력스트림

				Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height);

				roomInfoDTO.setRoomImg(fileName);
				riService.insertRoom(roomInfoDTO, srSeq);
				logger.info("파일업로드 성공");
				return new ResponseEntity<>(HttpStatus.OK);
				// 읽기 쓰기
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("파일업로드에러");
				throw new AddException(e.getMessage());
			}
		}
		return new ResponseEntity<>("오류", HttpStatus.BAD_REQUEST);
	}

	/**
	 * [RoomInfo] 룸 1개정보를 출력한다.
	 * 
	 * @author SR
	 * @param roomSeq
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "roominfo/{roomSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> infoRoom(@PathVariable Long roomSeq) throws FindException {
		RoomInfoDTO roomDTO = riService.selectRoom(roomSeq);
		return new ResponseEntity<>(roomDTO, HttpStatus.OK);
	}

	/**
	 * [RoomInfo] 룸정보를 수정한다.
	 * 
	 * @author SR
	 * @param roomSeq
	 * @param roomInfoDTO
	 * @return
	 * @throws FindException
	 * @throws AddException
	 */
	@PostMapping(value = "roominfo/edit/{roomSeq}")
	public ResponseEntity<?> editRoom(@PathVariable Long roomSeq, RoomInfoDTO roomInfoDTO, HttpSession session,
			MultipartFile f) throws FindException, AddException {

		String saveDirectory = "C:\\dev\\roominfo"; // 각자 주소로!
		File saveDirFile = new File(saveDirectory);
		String fileName;
		if (f != null && f.getSize() > 0) { // 첨부파일 f이 전달된 경우만 처리해라!, f1값이 없는경우
			long fSize = f.getSize(); // 첨부된 파일크기 확인
			String fOrigin = f.getOriginalFilename(); // 첨부된(업로드된)파일의 이름
			System.out.println("---파일---");
			System.out.println("fSize:" + fSize + ", fOrigin:" + fOrigin);

			// 기존파일 삭제 TODO: 더나은 방법 찾아보기
			RoomInfo ri = riService.selectRoomEntity(roomSeq);
			String oldFileName = ri.getRoomImg();
			File oldFile = new File(saveDirFile, oldFileName);

			if (oldFile.exists()) {
				oldFile.delete();
			}

			// imgPath 결합
			UUID uuid = UUID.randomUUID();
			String fName = uuid.toString() + "_" + fOrigin;

			// 파일저장
			fileName = fName;
			File file = new File(saveDirFile, fileName);

			try {
				Attach.upload(f.getBytes(), file);

				// 섬네일파일 만들기 (비율맞춰서된다!)
				int width = 300;
				int height = 300;

				// 원래 첨부파일과 구분짓기 위해
				String thumbFileName = "t_" + fileName; // 섬네일파일명
				File thumbFile = new File(saveDirFile, thumbFileName);
				FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);// 출력스트림
				InputStream thumbnailIS = f.getInputStream(); // 첨부파일 입력스트림

				Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height);

				// 기존 섬네일파일 삭제
				String oldthumbFileName = "t_" + oldFileName;
				File oldthumbFile = new File(saveDirFile, oldthumbFileName);

				if (oldthumbFile.exists()) {
					oldthumbFile.delete();
				}

				roomInfoDTO.setRoomImg(fileName);
				riService.updateRoom(roomSeq, roomInfoDTO);
				logger.info("파일업로드 성공");
				return new ResponseEntity<>(HttpStatus.OK);
				// 읽기 쓰기
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("파일업로드에러");
				throw new AddException(e.getMessage());
			}
		}
		return new ResponseEntity<>("오류", HttpStatus.BAD_REQUEST);
	}

	/**
	 * [RoomInfo] 룸 1개를 삭제한다(status = 1)
	 * 
	 * @author SR
	 * @param roomSeq
	 * @return
	 * @throws FindException
	 */
	@PutMapping(value = "roominfo/{roomSeq}")
	public ResponseEntity<?> deleteRoom(@PathVariable Long roomSeq) throws FindException {
		riService.deleteRoom(roomSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [RoomInfo] 방 목록을 출력한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "roominfo/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listRoom(String hostId, HttpSession session) throws FindException {
		hostId = (String) session.getAttribute("hostLogined");
		List<RoomInfoDTO.selectAllRoomDTO> list = riService.selectAllRoom(hostId);
		if (list.isEmpty()) {
			return new ResponseEntity<>("추가한 방이 없습니다.", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	/**
	 * [Reservation] 룸 시퀀스와 예약일을 받아 이미 예약된 예약정보에 대한 리스트를 출력한다
	 * 
	 * @author SR(동수님꺼)
	 * @param roomSeq   스터디룸 시퀀스
	 * @param usingDate 예약일
	 * @return List<Object[]> 특정일자의 해당 스터디룸 예약정보
	 * @throws 전체정보 출력시 FindException, ParseException예외발생한다
	 */
	// 스터디룸 패키지에 들어가야됨
	@GetMapping(value = "reservation/{roomSeq}")
	public ResponseEntity<?> getReservablity(@PathVariable Long roomSeq, String usingDate)
			throws FindException, ParseException {
		List<ReservationDTO.selectAllByUsingDateDTO> list = rService.selectAllByUsingDate(roomSeq, usingDate);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [Reservation] 예약정보를 예약테이블에 넣어 예약내역에 insert
	 * 
	 * @author SR(동수님꺼)
	 * @param rDTO
	 * @param session
	 * @return
	 * @throws AddException
	 */
	@PostMapping(value = "reservation/{roomSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserveHost(@RequestBody ReservationDTO.insertRvDTO rDTO, HttpSession session)
			throws AddException {
		String hostId = (String) session.getAttribute("hostLogined");
		if (hostId != null) {
			rService.insertHostRv(rDTO, hostId);
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<>("로그인하세요", HttpStatus.OK);
		}
	}

	/**
	 * [Reservation] 해당 스터디카페의 모든 예약내역을 출력한다(목록)
	 * 
	 * @author SR
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "reservation/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ListReservation(HttpSession session) throws FindException {

		String hostId = (String) session.getAttribute("hostLogined");
		if(hostId == null) {
			return new ResponseEntity<>("로그인하세요.", HttpStatus.BAD_REQUEST);
		}
		List<ReservationDTO.selectAllReservationDTO> list = rService.selectAllReservation(hostId);
		
		if (list.isEmpty()) {
			return new ResponseEntity<>("예약내역이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
//		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [Reservation] 예약 내역 1건을 출력한다.
	 * 
	 * @author SR
	 * @param resSeq
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "reservation/info/{resSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> InfoReservation(@PathVariable Long resSeq) throws FindException {
		List<ReservationDTO.selectAllReservationDTO> list = rService.infoReservation(resSeq);

		if (list.isEmpty()) {
			return new ResponseEntity<>("예약 내역이 없습니다", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * [Reservation] 예약내역 1건을 삭제한다.
	 * 
	 * @author SR
	 * @param resSeq
	 * @param session
	 * @return
	 * @throws RemoveException
	 */
	@DeleteMapping(value = "reservation/info/{resSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ListReservation(@PathVariable Long resSeq) throws RemoveException {
		rService.deleteReservation(resSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 호스트유저 로그인
	 * 
	 * @author choigeunhyeong
	 * @param hostId
	 * @param pwd
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> hostLogin(@RequestParam String hostId, @RequestParam String pwd, HttpSession session)
			throws FindException {
		HostDTO.HostLoginDTO hostDTO = hService.HostLogin(hostId, pwd);
		session.setAttribute("hostLogined", hostDTO.getHostId());
		session.setAttribute("hostLoginedReady", hostDTO.getStatus());
		logger.info("호스트로그인성공시 hostsessionid : " + session.getId());
		return new ResponseEntity<>(hostDTO, HttpStatus.OK);
	}

	/**
	 * 호스트 로그아웃
	 * 
	 * @author choigeunhyeong
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		logger.info("호스트로그아웃시 hostsessionid : " + session.getId());
		session.invalidate();
		return "";
	}

	/**
	 * 로그인 체크
	 * 
	 * @author choigeunhyeong
	 * @param session
	 * @return
	 */
	@GetMapping(value = "checklogined", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> checklogined(HttpSession session) {
		String logined = (String) session.getAttribute("hostLogined");
		if (logined != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>("호스트로그인이 안된 상태입니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 호스트 아이디 찾기
	 * 
	 * @author choigeunhyeong
	 * @param num
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "findhostid", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findHostId(String num) throws FindException {
		HostDTO hostDTO = hService.findHostId(num);
		return new ResponseEntity<>(hostDTO, HttpStatus.OK);
	}

	/**
	 * [HostUser] 비밀번호 찾기(비밀번호변경) - 메일포함
	 * 
	 * @author SR
	 * @param email
	 * @param hostId
	 * @return 가입된 정보가 있다면 입력받은 id와 email이 서로 일치한지 여부를 리턴
	 * @throws Exception
	 */
	@PostMapping(value = "searchpwd", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean hostUpdatePwd(String email, String hostId) throws Exception {
		boolean check = hService.hostPwdAndEmailCheck(email, hostId);
		return check;
	}
}