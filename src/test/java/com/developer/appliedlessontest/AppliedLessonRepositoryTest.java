
package com.developer.appliedlessontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.appliedlesson.entity.AppliedLesson;
import com.developer.appliedlesson.repository.AppliedLessonRepository;
import com.developer.lesson.entity.Lesson;
import com.developer.lesson.repository.LessonRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;
@SpringBootTest
class AppliedLessonRepositoryTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AppliedLessonRepository alRepository;
	
	@Autowired
	private LessonRepository lRepository;

	@Autowired
	private UsersRepository uRepository;
	
	@Test
	@DisplayName("수업신청 INSERT 테스트")
	void testAppliedLessonAdd() {
		Optional<Lesson>optL = lRepository.findById(1L);
		assertTrue(optL.isPresent());
		Lesson l = optL.get();
		
		Optional<Users>optU = uRepository.findById("tutee2");
		assertTrue(optU.isPresent());
		Users u = optU.get();
		
		AppliedLesson al = new AppliedLesson();
		al.setApplySeq(1L);
		al.setTuteeId("tutee2");
		al.setLesson(l);
		al.setUsers(u);

		alRepository.save(al);
	}
	
	@Test
	@DisplayName("수업신청 SELECT 테스트")
	void testFind() {
		Optional<AppliedLesson> optAl = alRepository.findById(1L);
		assertTrue(optAl.isPresent());
		Long expected = 1L;
		assertEquals(expected, optAl.get().getApplySeq());
	}
	
	@Test
	@DisplayName("수업신청 UPDATE 테스트")
	void testUpdate() {
		Optional<AppliedLesson> optAl = alRepository.findById(1L);
		assertTrue(optAl.isPresent());
		AppliedLesson al = optAl.get();
		al.setApplyOk(1);
		alRepository.save(al);
	}
	
	@Test
	@DisplayName("수업신청 DELETE 테스트")
	void testDelete() {
		Optional<AppliedLesson> optAl = alRepository.findById(1L);
		AppliedLesson al = optAl.get();
//		u.setTutor(null);
		alRepository.delete(al);
	}
	
//	@Test
//	@DisplayName("미승인 튜티 리스트 확인")
//	void findLessonApplyUsers0() {
////		Optional<Lesson> optL = lRepository.findById(1L);
////		Lesson l = optL.get();
//		List<Object[]> list = alRepository.findLessonApplyUsers0(1L);
//
//		logger.error("첫번째글번호: " + list.get(0)[0]);
//		logger.error("첫번째내용: " + list.get(0)[1]);
//		
//		logger.error("두번째글번호: " + list.get(1)[0]);
//		logger.error("두번쨰내용: " + list.get(1)[1]);
//		
//	}

}
