package com.developer.tutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.lesson.entity.Lesson;
import com.developer.tutor.entity.Tutor;
import com.developer.tutor.repository.TutorRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

@SpringBootTest
public class TutorRepositoryTest {
	@Autowired
	private TutorRepository tRepository;
	@Autowired
	private UsersRepository uRepository;
	
	@Test
	@DisplayName("튜터 INSERT 테스트")
	void testTutorAdd() {
		Tutor t = new Tutor();
		t.setTutorId("test4");
		t.setInfo("테스트내용");
		t.setImgPath("테스트이미지");
		//t.setStarAvg(4.4);
		t.setApplyOk(1);
		
		Optional<Users> u = uRepository.findById("test4");
		Users user = u.get();
		t.setUsers(user);
		tRepository.save(t);
	}
	
	@Test
	@DisplayName("튜터 SELECT 테스트")
	void testFind() {
		Optional<Tutor> optT = tRepository.findById("test");
		assertTrue(optT.isPresent());
		String expected = "test";
		assertEquals(expected, optT.get().getTutorId());
	}

}
