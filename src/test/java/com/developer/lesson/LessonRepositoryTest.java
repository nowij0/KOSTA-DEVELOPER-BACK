//<<<<<<< HEAD
////package com.developer.lesson;
////
////<<<<<<< HEAD
////import java.time.LocalDate;
////=======
////import java.util.Date;
////>>>>>>> a17bbc4b15abb5fd1a82dbbe90805ae698acaa4f
////import java.util.List;
////import java.util.Optional;
////
////import org.junit.jupiter.api.DisplayName;
////import org.junit.jupiter.api.Test;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.context.SpringBootTest;
////
////import com.developer.lesson.entity.Lesson;
////import com.developer.lesson.repository.LessonRepository;
////import com.developer.tutor.entity.Tutor;
////import com.developer.tutor.repository.TutorRepository;
////
////@SpringBootTest
////public class LessonRepositoryTest {
////	private Logger logger = LoggerFactory.getLogger(getClass()); 
////	@Autowired
////	private LessonRepository lRepository;
////	@Autowired
////	private TutorRepository tRepository;
////	
////	@Test
////	@DisplayName("수업 SELECT 테스트 : 전체출력")
////	void testSelectAll() {		
////		Iterable<Lesson> all = lRepository.findAll();
//////		all.forEach((l)->{
//////			logger.info("즐겨찾기값: " + l.getFlList().get(0).getFavLesSeq()
//////					+ ", 즐겨찾기한 수업번호: " + l.getFlList().get(0).getLesson().getLessonSeq()
//////					+ "튜터아이디: " + l.getTutor().getUserId()
//////					+ "수업등록목록: " + l.getAlList().get(0).getUserId());
//////		});	
////	}
////	
////	@Test
////	@DisplayName("수업 SELECT 테스트 : 번호별 출력")
////	void testSelectSeq() {		
////		Optional<Lesson> lesson = lRepository.findById(1L);
////		logger.info("수업정보: " + lesson.get().getLessonName());
////	}
////	
//////	@Test
//////	@DisplayName("수업 SELECT 테스트 : 클래스를 개설한 튜터의 후기 목록")
//////	void testSelectAllReview() {		
//////		for(int i=0; i<list.size(); i++) {
//////			Object review = list.get(i);
//////			logger.info(review.toString());
//////		}
//////	}
////	
////
////	@Test
////	@DisplayName("수업 SELECT 테스트 : 튜터가 생성한 클래스목록 + 튜터 정보")
////	void testSelectTutorDetail() {		
////		List<Object[]> list = lRepository.selectTutorDetail("test3");
////		for(int i=0; i<list.size(); i++) {
////			Object classList = list.get(i);
////			logger.info(classList.toString());
////		}
////	}
////	
////	@Test
////	@DisplayName("가보자고")
////	void testselectDetail() {
////		List<Object[]> list = lRepository.getLessonDetail(1L);
////	}
//////	@Test
//////	@DisplayName("수업 SELECT 테스트 : 튜터가 생성한 클래스목록 + 튜터 정보")
//////	void testSelectTutorDetail() {		
//////		List<Object[]> list = lRepository.selectTutorDetail("test3");
//////		for(int i=0; i<list.size(); i++) {
//////			Object classList = list.get(i);
//////			logger.info(classList.toString());
//////		}
//////	}
////
//////	
//////	@Test
//////	@DisplayName("수업 SELECT 테스트 : 번호별 출력")
//////	void testSelectSeq() {		
//////		Optional<Lesson> lesson = lRepository.findById(1L);
//////		logger.info("수업정보: " + lesson.get().getLessonName());
//////	}
//////	
////////	@Test
////////	@DisplayName("수업 SELECT 테스트 : 클래스를 개설한 튜터의 후기 목록")
////////	void testSelectAllReview() {		
////////		List<Object[]> list = lRepository.selectAllReview(2L);
////////		for(int i=0; i<list.size(); i++) {
////////			Object review = list.get(i);
////////			logger.info(review.toString());
////////		}
////////	}
////////	
////////	@Test
////////	@DisplayName("수업 SELECT 테스트 : 튜터가 생성한 클래스목록 + 튜터 정보")
////////	void testSelectTutorDetail() {		
////////		List<Object[]> list = lRepository.selectTutorDetail("test3");
////////		for(int i=0; i<list.size(); i++) {
////////			Object classList = list.get(i);
////////			logger.info(classList.toString());
////////		}
////////	}
//////
//////
//////	@Test
//////	@DisplayName("수업 INSERT 테스트")
//////	void testAdd() {
//////		Lesson lesson = new Lesson();
////<<<<<<< HEAD
//////		lesson.setCategory(1);
////=======
//////		lesson.setCategory(2);
////>>>>>>> a17bbc4b15abb5fd1a82dbbe90805ae698acaa4f
//////		lesson.setContent("sdfsdsdf");
//////		Date date = new Date(2023, 3, 5);
//////		lesson.setEndCdate(date);
//////		lesson.setEndDate(date);
//////		lesson.setImgPath("...");
////<<<<<<< HEAD
//////		lesson.setLessonName("2번수업.");
//////		lesson.setLessonSeq(124L);
////=======
//////		lesson.setLessonName("3번수업.");
//////		lesson.setLessonSeq(125L);
////>>>>>>> a17bbc4b15abb5fd1a82dbbe90805ae698acaa4f
//////		lesson.setLocation("..."); 
//////		lesson.setPayLesson(0);
//////		lesson.setPeople(10);
//////		lesson.setPrice(1000);
//////		lesson.setStartCdate(date);
//////		lesson.setStartDate(date);
//////		
//////		Optional<Tutor> t = tRepository.findById("test4");
//////		Tutor tutor = t.get();
//////		lesson.setTutor(tutor);
//////		lRepository.save(lesson);
//////	}
//////	
//////	@Test
//////	@DisplayName("수업 UPDATE 테스트")
//////	void testUpdate() {
//////		Optional<Lesson> l = lRepository.findById(4L);
//////		Lesson lesson = l.get();
//////		lesson.setLessonName("수정중");
//////		lRepository.save(lesson);		
//////	}
//////	
//////	@Test
//////	@DisplayName("수업 DELETE 테스트")
//////	void testDelete() {
//////		Optional<Lesson> l = lRepository.findById(3L);
//////		Lesson lesson = l.get();
//////		lRepository.delete(lesson);	
//////	}
//////
//////}
////
////
////
//////	@Test
//////	@DisplayName("수업 INSERT 테스트")
//////	void testAdd() {
//////		Lesson lesson = new Lesson();
//////		lesson.setCategory(1);
//////		lesson.setContent("sdfsdsdf");
//////		LocalDate date = LocalDate.of(2023, 02, 27);
//////		lesson.setEndCdate(date);
//////		lesson.setEndDate(date);
//////		lesson.setImgPath("...");
//////		lesson.setLessonName("2번수업.");
//////		lesson.setLessonSeq(1L);
//////		lesson.setLessonName("1번수업.");
//////		lesson.setLessonSeq(124L);
//////		lesson.setLocation("..."); 
//////		lesson.setPayLesson(0);
//////		lesson.setPeople(10);
//////		lesson.setPrice(1000);
//////		lesson.setStartCdate(date);
//////		lesson.setStartDate(date);
//////		
//////		Optional<Tutor> t = tRepository.findById("tutor1");
//////		Tutor tutor = t.get();
//////		lesson.setTutor(tutor);
//////		lRepository.save(lesson);
//////	}
////	
////	@Test
////	@DisplayName("수업 UPDATE 테스트")
////	void testUpdate() {
////		Optional<Lesson> l = lRepository.findById(4L);
////		Lesson lesson = l.get();
////		lesson.setLessonName("수정중");
////		lRepository.save(lesson);		
////	}
////	
////	@Test
////	@DisplayName("수업 DELETE 테스트")
////	void testDelete() {
////		Optional<Lesson> l = lRepository.findById(3L);
////		Lesson lesson = l.get();
////		lRepository.delete(lesson);	
////	}
////
////}
//=======
//package com.developer.lesson;
//
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.developer.lesson.entity.Lesson;
//import com.developer.lesson.repository.LessonRepository;
//import com.developer.tutor.entity.Tutor;
//import com.developer.tutor.repository.TutorRepository;
//
//@SpringBootTest
//public class LessonRepositoryTest {
//	private Logger logger = LoggerFactory.getLogger(getClass()); 
//	@Autowired
//	private LessonRepository lRepository;
//	@Autowired
//	private TutorRepository tRepository;
//	
//	
//	@Test
//	@DisplayName("수업 UPDATE 테스트")
//	void testUpdate() {
//		Optional<Lesson> l = lRepository.findById(4L);
//		Lesson lesson = l.get();
//		lesson.setLessonName("수정중");
//		lRepository.save(lesson);		
//	}
//	
//	@Test
//	@DisplayName("수업 DELETE 테스트")
//	void testDelete() {
//		Optional<Lesson> l = lRepository.findById(3L);
//		Lesson lesson = l.get();
//		lRepository.delete(lesson);	
//	}
//
//}
//>>>>>>> 5099a307826135b0c822d9a20d496426f8ad754e
