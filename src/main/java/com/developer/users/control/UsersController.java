package com.developer.users.control;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.developer.exception.FindException;
import com.developer.users.dto.UsersDTO;
import com.developer.users.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users/*")
@RequiredArgsConstructor
public class UsersController {

	private final UsersService uService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 로그인 체크
	 * 
	 * @author choigeunhyeong
	 * @param session
	 * @return
	 */
	@GetMapping(value = "checklogined", produces = MediaType.APPLICATION_JSON_VALUE)
	public int checklogined(HttpSession session) {
		String logined = (String) session.getAttribute("logined");
		if(logined != null) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 로그인
	 * 
	 * @author choigeunhyeong
	 * @param id
	 * @param pwd
	 * @param session
	 * @return
	 * @throws FindException
	 */
	@PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestParam String userId, String pwd, HttpSession session) throws FindException {
		UsersDTO.uDTO usersDTO = uService.userLogin(userId, pwd);
		session.setAttribute("logined", usersDTO.getUserId());
		session.setAttribute("loginedRole", usersDTO.getRole());
		logger.info("로그인성공시 sessionid : " + session.getId());
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	/**
	 * 로그아웃
	 * 
	 * @author choigeunhyeong
	 * @param session
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		logger.info("로그아웃시 sessionid : " + session.getId());
		session.invalidate();
		return "";
	}

	/**
	 * 아이디 찾기
	 * 
	 * @author choigeunhyeong
	 * @param email
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "findId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findId(String email) throws FindException {
		UsersDTO.uDTO usersDTO = uService.findId(email);
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	/**
	 * [USERS] 비밀번호 찾기(비밀번호변경) - 메일포함
	 * 
	 * @author SR
	 * @param email
	 * @param userId
	 * @return 가입된 정보가 있다면 입력받은 id와 email이 서로 일치한지 여부를 리턴
	 * @throws Exception
	 */
	@PostMapping(value = "searchpwd", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean userUpdatePwd(String email, String userId) throws Exception {
		boolean check = uService.userPwdAndEmailCheck(email, userId);
		return check;

	}
}