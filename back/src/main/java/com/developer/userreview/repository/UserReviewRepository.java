package com.developer.userreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.userreview.entity.UserReview;

public interface UserReviewRepository extends CrudRepository<UserReview, Long> {

	//[JH] 사용자의 지난 후기 목록을 불러온다.
	@Query(value = "SELECT u.name, r.user_review, r.user_star"
		+ " FROM APPLIED_LESSON a, USER_REVIEW r, USERS u, lesson l"
		+ " WHERE u.user_id = a.al_user_id"
		+ " and l.lesson_seq = a.al_lesson_seq"
		+ " and r.apply_seq_rv = a.apply_seq"
		+ " and u.user_id=:userId", nativeQuery = true)
	public List<Object[]> getTuteeReview(@Param("userId") String userId);

}
