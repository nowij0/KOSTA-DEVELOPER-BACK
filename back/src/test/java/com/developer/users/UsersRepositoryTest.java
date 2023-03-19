package com.developer.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.tutor.entity.Tutor;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

@SpringBootTest
public class UsersRepositoryTest {
	@Autowired
	private UsersRepository uRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	void test() {
		
	}
	
	@Test
	@DisplayName("유저 INSERT 테스트")
	void testUsersAdd() {
		Users u = new Users();
		u.setUserId("test1");
		u.setRole(1);
		u.setUserId("tutor1");
		u.setRole(2);
		u.setPwd("1234");
		u.setNickname("테스트닉네임");
		u.setName("테스트이름");
		u.setEmail("테스트이메일");
		u.setTel("테스트전화번호");
		u.setAddr("테스트주소");
		uRepository.save(u);
	}
	
	@Test
	@DisplayName("유저 SELECT 테스트")
	void testFind() {
		Optional<Users> optU = uRepository.findByUserId("test1");
		assertTrue(optU.isPresent());
		String expected = "test";
		assertEquals(expected, optU.get().getUserId());
	}
	
	@Test
	@DisplayName("유저 UPDATE 테스트")
	void testUpdate() {
		Optional<Users> optU = uRepository.findByUserId("test1");
		assertTrue(optU.isPresent());
		Users u = optU.get();
		u.setRole(2);		
		uRepository.save(u);
	}
	
	@Test
	@DisplayName("유저 DELETE 테스트")
	void testDelete() {
		Optional<Users> optU = uRepository.findByUserId("test");
		Users u = optU.get();
//		u.setTutor(null);
		uRepository.delete(u);
	}
	
	@Test
	@DisplayName("유저 로그인 테스트")
	void userLogin() {
		Optional<Users> u = uRepository.findById("test1");
		Users user = u.get();
	//	Users user = uRepository.usersLogin("test1", 1);
		logger.info("닉네임: "+ user.getNickname());
		logger.info("비밀번호: " + user.getPwd());
		logger.info("타입: "+ user.getRole());
		logger.info("주소: "+ user.getAddr());
		logger.info("email: "+ user.getEmail());
		logger.info("전화: "+ user.getTel());
		logger.info("아이디: "+ user.getUserId());
		
	}

}
