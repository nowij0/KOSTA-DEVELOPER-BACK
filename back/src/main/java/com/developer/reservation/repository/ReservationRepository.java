package com.developer.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	//[SR] 스터디카페 예약내역 목록 출력
	@Query(value="SELECT r.res_seq, ri.name as riName, r.user_id, u.name As uName, r.host_id,"
			+ " r.using_date, r.start_time, r.end_time "
			+ "FROM reservation r JOIN users u ON r.user_id = u.user_id "
			+ "JOIN room_info ri ON   ri.room_seq = r.room_seq "
			+ "JOIN studyroom sr ON   sr.sr_seq = ri.sr_seq "
			+ "WHERE sr.host_id = :hostId "
			+ "AND ri.status = 0 "
			+ "UNION "
			+ "SELECT r.res_seq, ri.name, '', '',r.host_id,r.using_date, r.start_time, r.end_time "
			+ "FROM reservation r "
			+ "JOIN room_info ri ON ri.room_seq = r.room_seq "
			+ "WHERE host_id = :hostId "
			+ "AND ri.status = 0 "
			+ "ORDER BY using_date DESC", nativeQuery = true)
	public List<Object[]> selectAllReservation(@Param("hostId")String hostId);
	
	
		//[SR] 스터디카페 예약상세내역
		@Query(value="SELECT r.res_seq, r.user_id, u.name As uname, u.tel, rif.name, r.using_date, r.start_time, r.end_time, r.host_id "
				+ "FROM room_info rif "
				+ "inner JOIN reservation r ON rif.room_seq = r.room_seq "
				+ "left JOIN users u ON r.user_id = u.user_id "
				+ "WHERE r.res_seq = :resSeq", nativeQuery = true)
		public List<Object[]> selectReservation(@Param("resSeq")long resSeq);
		
		//[DS}룸시퀀스와 예약날짜로 찾기
		@Query(value = "select r.room_seq, r.start_time, r.end_time, r.using_date, ro.price, sr.open_time,sr.end_time AS srEndTime, r.user_id\r\n"
				+ "from reservation r, room_info ro, studyroom sr\r\n"
				+ "where r.room_seq = ro.room_seq \r\n"
				+ "AND r.room_seq= :roomSeq \r\n"
				+ "AND TO_DATE(TO_CHAR(r.using_date, 'YY/MM/DD')) BETWEEN TO_DATE(:usingDate, 'YY/MM/DD') AND TO_DATE(:usingDate, 'YY/MM/DD')\r\n"
				+ "AND sr.sr_seq = ro.sr_seq ", nativeQuery = true)
		public List<Object[]> findAllByUsingDate(@Param("roomSeq") Long roomSeq, @Param("usingDate") String usingDate);
		
		//[DS]유저 아이디로 예약내역 찾기O
		@Query(value="SELECT r. res_seq, s.name AS sName, rif.name AS rifName, r.using_date, r.start_time, r.end_time\r\n"
				+ "FROM studyroom s, room_info rif, reservation r\r\n"
				+ "WHERE s.sr_seq = rif.sr_seq\r\n"
				+ "AND rif.room_seq = r.room_seq\r\n"
				+ "AND r.user_id = :userId\r\n"
				+ "ORDER BY r.using_date DESC", nativeQuery = true)
		public List<Object[]> findByUserId(@Param("userId")String userId);
		
		
		//[DS] 아이디값으로 후기를 작성하지 않은 예약리스트를 출력한다
		@Query(value= "SELECT r.res_seq , s.name AS sName, rif.name AS rifName, r.using_date, r.start_time, r.end_time\r\n"
				+ "FROM studyroom s, room_info rif, reservation r\r\n"
				+ "WHERE s.sr_seq = rif.sr_seq\r\n"
				+ "AND rif.room_seq = r.room_seq\r\n"
				+ "AND r.user_id = :userId\r\n"
				+ "AND TO_DATE(SYSDATE,'yyyy-mm-dd') > TO_DATE(r.using_date, 'yyyy-mm-dd')\r\n"
				+ "MINUS\r\n"
				+ "SELECT r.res_seq, s.name AS sName, rif.name AS rifName, r.using_date, r.start_time, r.end_time\r\n"
				+ "FROM studyroom s, room_info rif, reservation r, room_review rr\r\n"
				+ "WHERE s.sr_seq = rif.sr_seq\r\n"
				+ "AND rif.room_seq = r.room_seq\r\n"
				+ "AND r.res_seq = rr.res_seq\r\n"
				+ "AND r.user_id = :userId", nativeQuery = true)
		public List<Object[]> selectReqRmRv(@Param("userId") String userId);
	
}

