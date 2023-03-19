package com.developer.roomreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.roomreview.entity.RoomReview;

public interface RoomReviewRepository extends JpaRepository<RoomReview, Long> {

	/**
	 * [스터디카페 상세페이지 하단 후기리스트 페이지] 스터디룸 시퀀스를 받아 특정 스터디룸 후기에 대한 리스트를 출력한다
	 * @author ds
	 * @param srSeq 스터디카페 시퀀스(장소번호) 
	 * @return List<Object[]> 특정스터디카페 후기 리스트
	 */
	@Query(value = "	SELECT u.nickname, s.name AS sName,	r.name AS rifName, rev.cdate, rev.star, rev.content\r\n"
			+ "		FROM STUDYROOM s, ROOM_INFO r,\r\n"
			+ "		RESERVATION res, ROOM_REVIEW rev, USERS u\r\n"
			+ "		WHERE res.res_seq =\r\n"
			+ "		rev.res_seq\r\n"
			+ "		and u.user_id = res.user_id\r\n"
			+ "		and r.room_seq = res.room_seq\r\n"
			+ "		and s.sr_seq = r.sr_seq\r\n"
			+ "		and s.sr_seq= :srSeq\r\n"
			+ "		order by rev.cdate DESC", nativeQuery = true)
	public List<Object[]> findBySrSeq1(@Param("srSeq") String srSeq);
	
	/**
	 * [마이페이지 스터디카페 후기페이지] 유저 아이디로 작성한 이용후기 목록을 출력한다
	 * @author ds
	 * @param userId 유저아이디 
	 * @return List<Object[]> 유저의 작성한 이용후기 전체목록
	 */
	@Query(value="SELECT s.name AS srName, rif.name AS riName, rr.cdate, \r\n"
			+ "		rr.star, rr.content\r\n"
			+ "		FROM studyroom s, room_info rif, reservation r, room_review rr\r\n"
			+ "		WHERE s.sr_seq = rif.sr_seq\r\n"
			+ "		AND rif.room_seq = r.room_seq\r\n"
			+ "		AND rr.res_seq = r.res_seq\r\n"
			+ "		AND r.user_id = :userId\r\n"
			+ "		ORDER BY RR.CDATE DESC", nativeQuery = true)
	public List<Object[]> findByUserId(@Param("userId") String userId);
	

	/**
	 * [마이페이지 스터디카페 후기페이지] 예약시퀀스를 받아 해당 예약의 후기를 전체출력한다
	 * @author ds
	 * @param resSeq 예약 시퀀스
	 * @return List<Object[]> 유저의 작성한 이용후기 상세정보
	 */
	@Query(name = "	SELECT s.name AS srName, rif.name AS riName, r.using_date, rr.cdate, rr.star, rr.content\r\n"
			+ "		FROM studyroom s, room_info rif, reservation r,room_review rr\r\n"
			+ "		WHERE rr.res_seq = r.res_seq\r\n"
			+ "		AND rif.room_seq = r.room_seq\r\n"
			+ "		AND s.sr_seq = rif.sr_seq\r\n"
			+ "		AND r.res_seq = :resSeq", nativeQuery = true)
	public List<Object[]> getByResSeq(@Param("resSeq") Long resSeq);
	
	
 }