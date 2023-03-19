package com.developer.roominfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.roominfo.entity.RoomInfo;

public interface RoomInfoRepository extends CrudRepository<RoomInfo, Long> {

	/**
	 * 방 번호에 대한 예약내역출력
	 * 
	 * @author choigeunhyeong
	 * @param srSeq
	 * @return
	 */
	@Query(value = "SELECT r.res_seq, rif.name, u.user_id, u.name AS uName, r.using_date, r.start_time, r.end_time "
			+ "FROM room_info rif, reservation r, users u " + "WHERE rif.room_seq = r.room_seq "
			+ "AND r.user_id = u.user_id " + "AND rif.sr_seq = :srSeq "
			+ "ORDER BY r.using_date DESC", nativeQuery = true)
	public List<Object[]> getReservation(@Param("srSeq") Long srSeq);

	// [SR]호스트마이페이지 - 가지고 있는 방 목록 출력
	@Query(value = "SELECT rif.room_seq, rif.name, rif.info, rif.img_path, rif.person, rif.price, s.open_time, s.end_time "
			+ "FROM studyroom s, room_info rif "
			+ "where s.sr_seq = rif.sr_seq "
			+ "AND s.host_id = :hostId "
			+ "AND rif.status = 0 "
			+ "ORDER BY rif.room_seq ASC", nativeQuery = true)
	public List<Object[]> selectAllRoom(@Param("hostId") String hostId);


	
	/**
	 * [스터디카페 정보 출력페이지] 스터디룸 시퀀스를 받아 스터디룸의 전체정보를 출력한다
	 * 
	 * @author ds
	 * @param srSeq 스터디카페 시퀀스(장소번호)
	 * @return 특정스터디카페 전체정보들(방여러개)
	 * 
	 */
	@Query(value =  "select rif.room_seq, rif.img_path, rif.info, rif.name, rif.person, rif.price, rif.status, rif.sr_seq, h.host_id\r\n"
			+ "    from room_info rif, studyroom s, host_user h\r\n"
			+ "    where rif.sr_seq = s.sr_seq\r\n"
			+ "    And s.host_Id= h.host_Id\r\n"
			+ "    and rif.sr_seq = :srSeq", nativeQuery = true)
	public List<Object[]> selectAll(@Param("srSeq") Long srSeq);
}