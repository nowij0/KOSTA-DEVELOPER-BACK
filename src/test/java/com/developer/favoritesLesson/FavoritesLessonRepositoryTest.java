package com.developer.favoritesLesson;

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

import com.developer.favoriteslesson.entity.FavoritesLesson;
import com.developer.favoriteslesson.repository.FavoritesLessonRepository;
import com.developer.lesson.entity.Lesson;
import com.developer.lesson.repository.LessonRepository;

@SpringBootTest
public class FavoritesLessonRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	@Autowired
	private FavoritesLessonRepository flRepository;
	@Autowired
	private LessonRepository lRepository;
	
	
	@Test
	@DisplayName("수업즐겨찾기 SELECT 테스트")
	void testSelectDetail() {
		Optional<FavoritesLesson> optL = flRepository.findById(5L);
		assertTrue(optL.isPresent());
		String expected = "수정중";
		assertEquals(expected, optL.get().getLesson().getLessonName());
		logger.info("값: " + optL.get().getLesson().getLessonName());
	}
	
//	@Test
//	@DisplayName("수업즐겨찾기 SELECT 테스트 : 아이디로 목록")
//	void testSelectList() {
//		List<Object[]> list = flRepository.listFavLesson("devman");
//		list.forEach((arr)->{
//			logger.info("즐겨찾기: " + arr[0]);
//		});
//	}	
	
//	@Test
//	@DisplayName("수업즐겨찾기 INSERT 테스트")
//	void testSave() {
//		FavoritesLesson fl = new FavoritesLesson();		
//		fl.setTuteeId("test1");
//
//		Optional<Lesson> l = lRepository.findById(1L);
//		Lesson lesson = l.get();
//		fl.setLesson(lesson);
//		flRepository.save(fl);
//	}

	@Test
	@DisplayName("수업즐겨찾기 DELETE 테스트")
	void testDelete() {
		Optional<FavoritesLesson> fl = flRepository.findById(2L);
		FavoritesLesson favorites = fl.get();
		flRepository.delete(favorites);	
	}

}
