package com.developer.userreviewtest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.appliedlesson.entity.AppliedLesson;
import com.developer.appliedlesson.repository.AppliedLessonRepository;
import com.developer.userreview.entity.UserReview;
import com.developer.userreview.repository.UserReviewRepository;
@SpringBootTest
class UserReviewRepositoryTest {

	@Autowired
	private AppliedLessonRepository alRepository;
	
	@Autowired
	private UserReviewRepository urRepository;
	
	
	@Test
	@DisplayName("튜티리뷰 INSERT 테스트")
	void testUserReviewAdd() {
		Optional<AppliedLesson>optAl = alRepository.findById(1L);
		assertTrue(optAl.isPresent());
		AppliedLesson al = optAl.get();
		
		UserReview ur = new UserReview();
		
//		ur.setApplySeqRv(al.getApplySeq());
//		ur.setAlLesson(al);
		ur.setReview("좋아요");
		ur.setStar(5);
		
//		al.setUserReview(ur);
		urRepository.save(ur);
		
	}
	
	@Test
	@DisplayName("튜티리뷰 SELECT 테스트")
	void testFind() {
		Optional<UserReview> optUr = urRepository.findById(1L);
		assertTrue(optUr.isPresent());
		Long expected = 1L;
//		assertEquals(expected, optUr.get().getApplySeqRv());
	}
	
	@Test
	@DisplayName("튜티리뷰 UPDATE 테스트")
	void testUpdate() {
		Optional<UserReview> optUr = urRepository.findById(1L);
		assertTrue(optUr.isPresent());
		UserReview ur = optUr.get();
		ur.setReview("생각해보니 별로네요");
		urRepository.save(ur);
	}
	
	@Test
	@DisplayName("튜티리뷰 DELETE 테스트")
	void testDelete() {
		Optional<UserReview> optUr = urRepository.findById(1L);
		UserReview ur = optUr.get();
		urRepository.delete(ur);
	}

}
