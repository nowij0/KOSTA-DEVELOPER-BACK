package com.developer.appliedlesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.appliedlesson.entity.AppliedLesson;

public interface AppliedLessonRepository extends JpaRepository<AppliedLesson, Long> {

	/**
	 * 미승인 튜티 리스트
	 * @author Jin
	 * @param userId
	 * @return
	 */
   @Query(value = " SELECT u.name, a.apply_seq"
   +" FROM USERS u, APPLIED_LESSON a, LESSON l"
   +" WHERE a.tutee_id = u.user_id"
   +" and a.apply_ok = 0"
   +" and l.lesson_seq = a.al_lesson_seq"
   +" and l.lesson_seq = :lessonSeq"
   +" order by u.name desc", nativeQuery = true)
   public List<Object[]> getLessonNotApplyUser(@Param("lessonSeq") long lessonSeq);
   
   /**
    * 승인 튜티 리스트
    * @author Jin
    * @param userId
    * @return
    */
   @Query(value = " SELECT u.name, a.apply_seq"
   +" FROM USERS u, APPLIED_LESSON a, LESSON l"
   +" WHERE a.tutee_id = u.user_id"
   +" and a.apply_ok = 1"
   +" and l.lesson_seq = a.al_lesson_seq"
   +" and l.lesson_seq = :lessonSeq"
   +" order by u.name desc", nativeQuery = true)
   public List<Object[]> getLessonApplyUser(@Param("lessonSeq") long lessonSeq);
   
	

 //근형 진행완료된 클래스 페이지 클래스명, 수강했던 튜티목록(후가기없는사람)
 	@Query(value="SELECT u.name, l.lesson_name, al.apply_seq "
 			+ "FROM applied_lesson al "
 			+ "INNER JOIN lesson l ON al.al_lesson_seq = l.lesson_seq "
 			+ "INNER JOIN users u ON al.al_user_id = u.user_id "
 			+ "LEFT JOIN user_review ur ON al.apply_seq = ur.apply_seq_rv "
 			+ "WHERE ur.apply_seq_rv IS NULL and l.lesson_seq = :lesson_seq", nativeQuery=true)
 	public List<Object[]> noReviewTutee(@Param("lesson_seq") Long lessonSeq);

 	
 	//근형 진행완료된클래스 후기목록
 	@Query(value="SELECT r.lesson_review, r.lesson_star, u.nickname "
 			+ "FROM applied_Lesson a "
 			+ "INNER JOIN lesson_Review r "
 			+ "ON a.apply_seq = r.apply_seq "
 			+ "INNER JOIN users u "
 			+ "ON a.al_user_id = u.user_id "
 			+ "WHERE a.al_lesson_seq = :lesson_seq ", nativeQuery= true)
 	public List<Object[]> selectCompletedClassList(@Param("lesson_seq") Long lessonSeq);
 	
	//[JW]
	@Query(nativeQuery =  true,
				value = "select * from applied_lesson al\n"
						+ "INNER JOIN lesson l\n"
						+ "ON al.al_lesson_seq = l.lesson_seq\n"
						+ "WHERE l.lesson_seq = :lessonSeq")
	public List<AppliedLesson> findByAlLessonSeq(@Param("lessonSeq") Long lessonSeq);

	//[JW]	
	@Query(nativeQuery =  true,
			value = "select al.apply_seq from applied_lesson al\n"
					+ "WHERE al.al_lesson_seq = :lessonSeq "
					+ "AND al.al_user_id = :tuteeId")
	public Long delAppliedTutee(@Param("tuteeId") String tuteeId, @Param("lessonSeq") Long lessonSeq);

}

