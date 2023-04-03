package com.developer.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.developer.tutor.entity.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, String> {

	//[JW]
	@Query(value="SELECT \n"
			+ "	l.lesson_seq, l.lesson_name, l.category, l.content, l.people, l.lesson_img, l.start_date, l.end_date,\n"
			+ "	l.price, l.apply_start, l.apply_end, l.location,\n"
			+ "	t.info, t.tutor_img, t.star_avg, \n"
			+ "	u.name, l.pay_lesson \n"
			+ "FROM lesson l\n"
			+ "INNER JOIN tutor t\n"
			+ "ON l.tutor_id = t.tutor_id\n"
			+ "INNER JOIN users u \n"
			+ "ON t.tutor_id = u.user_id\n"
			+ "WHERE l.tutor_id = :tutorId \n"
			+ "AND pay_lesson between 0 and 1",
			nativeQuery = true)
	public List<Object[]> selectTutorDetail(@Param("tutorId") String tutorId);
	
}
