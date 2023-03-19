package com.developer.userstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.tutor.entity.Tutor;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

@SpringBootTest
public class UsersRepositoryTest {
	@Autowired
	private UsersRepository uRepository;
	
	@Test
	void test() {
		
	}
	
	@Test
	@DisplayName("유저 INSERT 테스트")
	void testUsersAdd() {
		Users u = new Users();
		u.setUserId("테스트");
		u.setRole(0);
		u.setPwd("테스트비밀번호");
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
		Optional<Users> optU = uRepository.findByUserId("테스트");
		assertTrue(optU.isPresent());
		String expected = "테스트";
		assertEquals(expected, optU.get().getUserId());
	}
	
	@Test
	@DisplayName("유저 UPDATE 테스트")
	void testUpdate() {
		Optional<Users> optU = uRepository.findByUserId("테스트");
		assertTrue(optU.isPresent());
		Users u = optU.get();
		u.setName(new String("수정테스트중"));
		uRepository.save(u);
	}
	
	@Test
	@DisplayName("유저 DELETE 테스트")
	void testDelete() {
		Optional<Users> optU = uRepository.findByUserId("테스트");
		Users u = optU.get();
//		u.setTutor(null);
		uRepository.delete(u);
	}


}
