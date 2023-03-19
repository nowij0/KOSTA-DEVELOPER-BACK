package com.developer.lesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.lesson.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

	//[JW]
    @Query("select DISTINCT l from Lesson l join fetch l.alList ")
    public List<Lesson> findAll();
    
	//[JW]
    @Query(nativeQuery = true,
    			 	value = "SELECT lr.cdate AS cDate, lr.review AS review, lr.star AS star, al.tutee_id AS tuteeId, l.lesson_name AS lessonName, u.name AS name "
    			 	+ "FROM users u "
    			 	+ "INNER JOIN applied_lesson al "
    			 	+ "ON al.tutee_id = u.user_id "
    			 	+ "INNER JOIN lesson l "
    			 	+ "ON al.al_lesson_seq = l.lesson_seq "
    			 	+ "INNER JOIN lesson_review lr "
    			 	+ "ON lr.apply_seq = al.apply_seq "
    			 	+ "WHERE l.lesson_seq = :lessonSeq")
	public List<Object[]> selectAllReview(@Param("lessonSeq") Long lessonSeq);
	
	//[JW]
	@Query(nativeQuery = true,
					value ="SELECT * FROM LESSON "
							+ "ORDER BY lesson_seq DESC ")
	public List<Object[]> selectAllLesson();
	
	//[JW]
	@Query(nativeQuery = true,
			value ="SELECT * FROM LESSON "
					+ "    WHERE end_date >= SYSDATE "
					+ "    AND pay_lesson between 0 and 1 "
					+ "ORDER BY lesson_name ASC")
	public List<Object[]> userSelectAllLesson();
	
	
	@Query(value="SELECT \n"
			+ "	l.lesson_seq, l.lesson_name, l.category, l.content, l.people, l.img_path, l.start_cdate, l.end_cdate,\n"
			+ "	l.price, l.start_date, l.end_date, l.location,\n"
			+ "	t.info, t.img_path AS tutorImg, t.star_avg,\n"
			+ "	u.name\n"
			+ "FROM lesson l\n"
			+ "INNER JOIN tutor t\n"
			+ "ON l.tutor_id = t.user_id\n"
			+ "INNER JOIN users u \n"
			+ "ON t.user_id = u.user_id\n"
			+ "WHERE l.tutor_id = :userId \n"
			+ "AND pay_lesson != 2",
			nativeQuery = true)
	public List<Object[]> selectTutorDetail(@Param("userId") String userId);

	//[JH]
		@Query(value="SELECT l.lesson_name, l.lesson_seq"
				+ " from LESSON l, TUTOR t, USERS u"
				+ " where l.tutor_id = t.tutor_id"
				+ " and t.tutor_id = u.user_id"
				+ " and l.pay_lesson = 2"
				+ " and TO_CHAR(SYSDATE,'yyyymmdd')<=l.end_date"
				+ " and t.tutor_id = :logined"
				+ " order by l.lesson_seq desc", nativeQuery = true)
		public List<Object[]> unpaidLessonByUser(@Param("logined") String logined);
		
		//[JH]
		@Query(value="	SELECT l.lesson_name, l.lesson_seq"
				+ "	from LESSON l, TUTOR t, USERS u"
				+ "	where l.tutor_id = t.tutor_id"
				+ "	and t.tutor_id = u.user_id"
				+ "	and u.user_id = :logined"
				+ " and l.pay_lesson between 0 and 1"
				+ "	and TO_CHAR(SYSDATE,'yyyymmdd')<l.start_cdate"
				+ "	order by l.lesson_seq desc",
				nativeQuery = true)
		public List<Object[]> getLessonByUser1(@Param("logined") String logined);
		
		//[JH]
		@Query(value="	SELECT l.lesson_name, l.lesson_seq"
				+ "	from LESSON l, TUTOR t, USERS u"
				+ "	where l.tutor_id = t.tutor_id"
				+ "	and t.tutor_id = u.user_id"
				+ "	and u.user_id = :logined"
				+ " and l.pay_lesson between 0 and 1"
				+ "	and l.start_cdate<=TO_CHAR(SYSDATE,'yyyymmdd')"
				+ " and TO_CHAR(SYSDATE,'yyyymmdd')<=l.end_cdate"
				+ "	order by l.lesson_seq desc",
				nativeQuery = true)
		public List<Object[]> getLessonByUser2(@Param("logined") String logined);
		
		//[GH]
		@Query(value="	SELECT l.lesson_name, l.lesson_seq"
				+ "	from LESSON l, TUTOR t, USERS u"
				+ "	where l.tutor_id = t.tutor_id"
				+ "	and t.tutor_id = u.user_id"
				+ "	and u.user_id = :logined"
				+ " and l.pay_lesson between 0 and 1"
				+ "	and TO_CHAR(SYSDATE,'yyyymmdd')>l.end_cdate"
				+ "	order by l.lesson_seq desc",
				nativeQuery = true)
		public List<Object[]> getLessonByUser3(@Param("logined") String logined);
	
	
		//[JH]
		@Query(value= "	SELECT l.lesson_name, l.img_path, l.location, l.start_cdate, l.end_cdate,"
				+ "	        l.category, l.people, u.name, l.lesson_seq"
				+ "	FROM TUTOR t, LESSON l, USERS u"
				+ "	WHERE u.user_id = t.tutor_id"
				+ "	and t.tutor_id = l.tutor_id"
				+ "	and l.lesson_seq = :lessonSeq", nativeQuery = true)
		public List<Object[]> getLessonDetail(@Param("lessonSeq") Long lessonSeq);
	
		//[JH]
		@Query(value=" SELECT l.lesson_name, l.lesson_seq, a.apply_seq"
				+ " from LESSON l, USERS u, APPLIED_LESSON a"
				+ " where u.user_id = a.tutee_id"
				+ " and l.lesson_seq = a.al_lesson_seq"
				+ " and TO_CHAR(SYSDATE,'yyyy-mm-dd')<l.start_cdate"
				+ " and a.apply_ok=0"
				+ " and u.user_id = :userId"
				+ " order by l.lesson_seq desc", nativeQuery= true)
		public List<Object[]> getApplyLesson(@Param("userId") String userId);
		
		//[JH]
		@Query(value=" SELECT l.lesson_name, l.lesson_seq, a.apply_seq"
				+ " from LESSON l, USERS u, APPLIED_LESSON a"
				+ " where u.user_id = a.tutee_id"
				+ " and l.lesson_seq = a.al_lesson_seq"
				+ " and TO_CHAR(SYSDATE,'yyyy-mm-dd')<l.start_cdate"
				+ " and a.apply_ok=1"
				+ " and u.user_id = :userId"
				+ " order by l.lesson_seq desc", nativeQuery= true)
		public List<Object[]> upComingLesson(@Param("userId") String userId);
		
		//[JH]
		@Query(value=" SELECT l.lesson_name, l.lesson_seq"
				+ " from LESSON l, USERS u, APPLIED_LESSON a"
				+ " where u.user_id = a.tutee_id"
				+ " and l.lesson_seq = a.al_lesson_seq"
				+ " and l.start_cdate <= TO_CHAR(SYSDATE,'yyyy-mm-dd')"
				+ " and TO_CHAR(SYSDATE, 'yyyy-mm-dd') <=l.end_cdate"
				+ " and a.apply_ok=1"
				+ " and u.user_id = :userId"
				+ " order by l.lesson_seq desc", nativeQuery= true)
		public List<Object[]> onGoingLesson(@Param("userId") String userId);
	
		//[JW]
		@Query(value=" SELECT l.lesson_name"
				+ " from LESSON l, USERS u, APPLIED_LESSON a"
				+ " where u.user_id = a.tutee_id"
				+ " and l.lesson_seq = a.al_lesson_seq"
				+ " and  l.end_cdate < TO_CHAR(SYSDATE, 'yyyy-mm-dd')"
				+ " and a.apply_ok=1"
				+ " and u.user_id = :userId"
				+ " order by l.lesson_seq desc", nativeQuery= true)
		public List<Object[]> lastApplyLesson(@Param("userId") String userId);
		
		//[JW]
		@Query(value=" SELECT l.lesson_name"
				+ " from LESSON l, USERS u, APPLIED_LESSON a"
				+ " where u.user_id = a.tutee_id"
				+ " and l.lesson_seq = a.al_lesson_seq"
				+ " and a.apply_ok=2"
				+ " and u.user_id = :userId"
				+ " order by l.lesson_seq desc", nativeQuery= true)
		public List<Object[]> rejectLesson(@Param("userId") String userId);
		
		//[JW]
		public List<Object> findByLessonNameContaining(String searchKeyword);

		
		//[SR]메인페이지 - 신청종료일 임박순으로 list 출력
		@Query(value = "SELECT *"
				+ "FROM (SELECT lesson_seq, lesson_name, img_path, price "
				+ "                FROM lesson "
				+ "                WHERE pay_lesson between 0 and 1 "
				+ "                AND TO_DATE(end_date, 'YY-MM-DD') >= TO_DATE(sysdate, 'YY-MM-DD') "
				+ "                ORDER BY end_date ASC) "
				+ "WHERE rownum BETWEEN 1 AND 6 ", nativeQuery = true)
		public List<Object[]> selectAllBydateLesson();
		
		//[DS]
			@Query(value="SELECT * \r\n"
					+ "FROM (SELECT lesson_name, category, people FROM lesson ORDER BY lesson_seq DESC)\r\n"
					+ "WHERE rownum BETWEEN 1 AND 5",nativeQuery = true)
			public List<Object[]> selectClassList5();
}
