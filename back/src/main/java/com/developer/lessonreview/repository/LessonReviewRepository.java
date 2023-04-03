package com.developer.lessonreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.lessonreview.entity.LessonReview;

public interface LessonReviewRepository extends CrudRepository<LessonReview, Long> {
	
	//[JW]
	@Query(nativeQuery = true,
				 value = "select * from applied_lesson al\n"
				 		+ "INNER JOIN lesson_review lr\n"
				 		+ "ON al.apply_seq = lr.apply_seq\n"
				 		+ "WHERE lr.apply_seq = :applySeq")
	public List<LessonReview> findAllById(@Param("applySeq") Long applySeq);
	
	//[JW]
	@Query(value = "	SELECT COUNT(*)\n"
			+ "FROM lesson_review lr\n"
			+ "LEFT OUTER JOIN applied_lesson al\n"
			+ "ON lr.apply_seq = al.apply_seq\n"
			+ "Inner JOIN lesson l\n"
			+ "ON al.al_lesson_seq = l.lesson_seq\n"
			+ "WHERE l.tutor_id = :tutorId", nativeQuery = true)
	public Integer cntLReview(@Param("tutorId") String tutorId);
	
	//[JW]
	@Query(value = "SELECT lr.lesson_review, lr.lesson_star, u.name, l.lesson_name\n"
			+ "			FROM lesson_review lr \n"
			+ "			LEFT OUTER JOIN applied_lesson al \n"
			+ "			ON lr.apply_seq = al.apply_seq \n"
			+ "            INNER JOIN lesson l\n"
			+ "            ON al.al_lesson_seq = l.lesson_seq\n"
			+ "			FULL OUTER JOIN users u \n"
			+ "			ON al.al_user_id = u.user_id \n"
			+ "			WHERE al.al_user_id = :tuteeId ",
					nativeQuery = true)
	public List<Object[]> listLRList(@Param("tuteeId") String tuteeId);
	
	//[JW]
	@Query(value = "SELECT al.apply_seq, l.lesson_name, al.apply_ok, l.end_date\n"
			+ "FROM applied_lesson al\n"
			+ "INNER JOIN lesson l\n"
			+ "ON al.al_lesson_seq = l.lesson_seq\n"
			+ "WHERE al.al_user_id = :userId\n"
			+ " AND l.end_date <= SYSDATE\n"
			+ "AND al.apply_ok = 1"
			+ "MINUS\n"
			+ "SELECT al.apply_seq, l.lesson_name, al.apply_ok, l.end_date\n"
			+ "FROM lesson_review lr\n"
			+ "INNER JOIN applied_lesson al\n"
			+ "ON lr.apply_seq = al.apply_seq\n"
			+ "INNER JOIN lesson l\n"
			+ "ON al.al_lesson_seq = l.lesson_seq\n"
			+ "where al.al_user_id = :userId"
			+ " AND l.end_date <= SYSDATE\n"
			+ "AND al.apply_ok = 1",
					nativeQuery = true)
	public List<Object[]> noWriteLReview(@Param("userId") String userId);

}
