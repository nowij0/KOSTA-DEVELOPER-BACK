package com.developer.join;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.email.EmailService;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.host.dto.HostDTO;
import com.developer.host.service.HostService;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.service.StudycafeService;
import com.developer.users.dto.UsersDTO;
import com.developer.users.service.UsersService;
import com.developer.util.Attach;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("join/*")
@RequiredArgsConstructor
public class JoinController {

	private final UsersService uService;
	private final HostService hService;
	private final StudycafeService sService;
	private final EmailService emailService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 호스트 회원가입
	 * 
	 * @author Jin
	 * @param hostDTO
	 * @return
	 * @throws AddException
	 */
	@PostMapping(value = "hostuser")
	public ResponseEntity<?> addHost(@RequestBody HostDTO hostDTO) throws AddException {
		hService.addHost(hostDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 사용자 회원가입
	 * 
	 * @author Jin
	 * @param usersDTO
	 * @return
	 * @throws AddException
	 */
	@PostMapping(value = "users")
	public ResponseEntity<?> addUsers(@RequestBody UsersDTO usersDTO) throws AddException {
		uService.addUsers(usersDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 통합아이디 중복체크 (true중복, false사용가능) 
	 * 사용자 아이디 중복체크(수정예정)
	 * 
	 * @author Jin
	 * @param userId
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "users/check/{userId}")
	public boolean checkUserId(@PathVariable String userId, HttpSession session) throws FindException {
		boolean check = false;
		boolean check1 = false;
		check1 = uService.existsByUserId(userId);
		System.out.println("check1결과는" + check1);
		boolean check2 = false;
		String hostId = userId;
		check2 = hService.existsByHostId(hostId);
		System.out.println("check2결과는" + check2);
		if (check1 == check2) {
			check = false;
		} else {
			check = true;
		}
		return check;

	}

	/**
	 * 사용자 이메일 중복체크 (true중복, false사용가능)
	 * 
	 * @author Jin
	 * @param email
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "users/checkemail/{email}")
	public ResponseEntity<?> checkEmail(@PathVariable String email, HttpSession session) throws FindException {
		return ResponseEntity.ok(uService.existsByEmail(email));
	}

	/**
	 * 호스트 이메일 중복체크 (true중복, false사용가능)
	 * 
	 * @author Jin
	 * @param email
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "hostuser/checkemail/{email}")
	public ResponseEntity<?> checkHostEmail(@PathVariable String email, HttpSession session) throws FindException {
		return ResponseEntity.ok(hService.existsByHostEmail(email));
	}

	/**
	 * 호스트 사업자 번호 중복체크(true중복, false 사용가능)
	 * 
	 * @author Jin
	 * @param num
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "hostuser/checknum/{num}")
	public ResponseEntity<?> checkHostNum(@PathVariable String num, HttpSession session) throws FindException {
		return ResponseEntity.ok(hService.existsByNum(num));
	}

	/**
	 * 스터디카페를 추가한다.
	 * 
	 * @author SR
	 * @param studyroomDTO
	 * @param session
	 * @param f
	 * @return
	 * @throws AddException
	 */
	@PostMapping(value = "studyroom")
	public ResponseEntity<?> addCafe(StudycafeDTO studyroomDTO, // 파일이랑 리퀘스트바디랑 같이 못씀
			HttpSession session, @RequestPart MultipartFile f) throws AddException {

		// TODO 시간 정규표현식 설정해보기..프론트단이든...뭐든..
		String hostId = (String) session.getAttribute("hostLogined");
		logger.error("로그인값:" + hostId);
		String saveDirectory = "C:\\dev\\studyroom"; // 각자 주소로!
		File saveDirFile = new File(saveDirectory);
		String fileName;
		if (f != null && f.getSize() > 0) { // 첨부파일 f이 전달된 경우만 처리해라!, f1값이 없는경우
			long fSize = f.getSize(); // 첨부된 파일크기 확인
			String fOrigin = f.getOriginalFilename(); // 첨부된(업로드된)파일의 이름
			System.out.println("---파일---");
			System.out.println("fSize:" + fSize + ", fOrigin:" + fOrigin);

			logger.error("값:" + hostId);

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

				studyroomDTO.setCafeImg(fileName);
				sService.insertCafe(studyroomDTO, hostId);
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
	 * [Email&Users] 본인인증 메일 : front에 반환한 인증 코드와 서버 터미널에 찍힌 인증 코드가 같은지 확인 필요함
	 * 
	 * @author SR
	 * @param email
	 * @return 난수값
	 * @throws Exception
	 */
	@PostMapping(value = "users/emailcheck") // get방식으로되는지 체크
	@ResponseBody
	public Map<String, Object> userEmailConfirm(@RequestParam String email) throws Exception {
		Map<String, Object> map = new HashMap<>();
		boolean check = uService.userEmailCheck(email);
		System.out.println("이메일존재여부: " + check);
		if (check == true) { // 기존의 가입된 정보가 없음(가입가능)
			String confirm = emailService.sendSimpleMessage(email);
			// System.out.println("컨트롤러 키:" + confirm);
			map.put("key", confirm);
			return map;
		} else {
			Object msg = "이미 가입된 이메일입니다.";
			map.put("error", msg);
			return map;
		}
	}

	@PostMapping(value = "host/emailcheck")
	@ResponseBody
	public Map<String, Object> emailConfirm(@RequestParam String email) throws Exception {
		Map<String, Object> map = new HashMap<>();
		boolean check = hService.hostEmailCheck(email);
		System.out.println("이메일존재여부: " + check);
		if (check == true) { // 기존의 가입된 정보가 없음(가입가능)
			String confirm = emailService.sendSimpleMessage(email);
			// System.out.println("컨트롤러 키:" + confirm);
			map.put("key", confirm);
			return map;
		} else {
			Object msg = "이미 가입된 이메일입니다.";
			map.put("error", msg);
			return map;
		}
	}

}