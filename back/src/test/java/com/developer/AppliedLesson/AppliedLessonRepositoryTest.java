//package com.developer.AppliedLesson;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.sql.Date;
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.developer.appliedlesson.entity.AppliedLesson;
//import com.developer.appliedlesson.repository.AppliedLessonRepository;
//import com.developer.lesson.entity.Lesson;
//import com.developer.lesson.repository.LessonRepository;
//import com.developer.users.entity.Users;
//import com.developer.users.repository.UsersRepository;
//
//@SpringBootTest
//public class AppliedLessonRepositoryTest {
//	@Autowired
//	private AppliedLessonRepository alRepository;
//	@Autowired
//	private LessonRepository lRepository;
//	@Autowired
//	private UsersRepository uRepository;
//	
//	@Test
//	@DisplayName("수업신청 INSERT 테스트")
//	void testSave() {
//		AppliedLesson al = new AppliedLesson();
//		Date date = new Date(2023, 02, 19);
//		al.setCdate(date);
//		al.setApplyOk(0);
//		al.setTuteeId("test2");
//		
//		Optional<Lesson> l = lRepository.findById(2L);
//		Lesson lesson = l.get();
//		al.setLesson(lesson);
//		
//		String tutorId = lesson.getTutor().getTutorId();
//		Optional<Users> u = uRepository.findById(tutorId);
//		Users users = u.get();
//		al.setUsers(users);
//		
//		alRepository.save(al);
//	}
//	
//	@Test
//	@DisplayName("수업신청 SELECT 테스트")
//	void testSelectDetail() {
//		Optional<AppliedLesson> optA = alRepository.findById(1L);
//		assertTrue(optA.isPresent());
//		Long expected = 1L;
//		assertEquals(expected, optA.get().getApplySeq());
//	}
//	
//
//
//}
