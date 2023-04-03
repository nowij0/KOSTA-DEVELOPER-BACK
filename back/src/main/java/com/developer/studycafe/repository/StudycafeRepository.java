package com.developer.studycafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.studycafe.entity.Studycafe;

public interface StudycafeRepository extends CrudRepository<Studycafe, Long> {

	//GH - 스터디카페 전체출력
	@Query(value="select * from studycafe order by cafe_seq desc", nativeQuery = true)
	public List<Object[]> getAllStudyroom();
	
	//[SR]호스트메인페이지 - 스터디룸+호스트정보 출력
		@Query(value="SELECT h.host_id, h.pwd, h.business_num, h.status, h.host_name AS hname, h.host_tel, h.host_email,"
				+ "s.cafe_seq, s.cafe_name, s.addr, s.info, s.open_time, s.end_time, s.cafe_img, s.oc "
				+ "FROM host h "
				+ "INNER JOIN studycafe s "
				+ "ON h.host_id = s.host_id "
				+ "WHERE h.host_id = :hostId", nativeQuery = true)
		public Optional<Studycafe> getHostAndStudyroom(@Param("hostId") String hostId);
		
		//ds
		@Query(value = "SELECT * \r\n"
				+ "FROM (SELECT cafe_seq, cafe_name, host_id FROM studycafe ORDER BY cafe_seq DESC)\r\n"
				+ "WHERE rownum BETWEEN 1 AND 5",nativeQuery = true)
		public List<Object[]> getList5();
		
		//ds
		@Query(value = "select * from studycafe where cafe_seq= :srSeq", nativeQuery = true)
		public Studycafe getBySRSEQ(@Param("srSeq") Long srSeq);
			
		//ds
		@Query(value= "SELECT\r\n"
				+ "        S.cafe_name,\r\n"
				+ "        S.ADDR,\r\n"
				+ "        S.cafe_img,\r\n"
				+ "        MAX(R.PERSON) AS PERSON,\r\n"
				+ "        MIN(R.PRICE) AS PRICE,\r\n"
				+ "        COUNT(distinct(F.USER_ID)) AS FAV_CNT,\r\n"
				+ "        S.cafe_seq  \r\n"
				+ "    FROM\r\n"
				+ "        Studycafe S\r\n"
				+ "    join\r\n"
				+ "        ROOM_INFO R  \r\n"
				+ "            ON s.cafe_seq = r.cafe_seq  \r\n"
				+ "    left outer join\r\n"
				+ "        FAVORITES_STUDYcafe F  \r\n"
				+ "            ON F.cafe_seq = S.cafe_seq \r\n"
				+ "    where S.open_status=0\r\n"
				+ "    GROUP BY\r\n"
				+ "        S.cafe_name ,\r\n"
				+ "        S.ADDR ,\r\n"
				+ "        S.cafe_img,\r\n"
				+ "        S.cafe_seq   \r\n"
				+ "    ORDER BY\r\n"
				+ "        PRICE ASC", nativeQuery = true)
		public List<Object[]> getListAll();
		
		
		//ds
		@Query(value="select ro.price, sr.open_time, sr.end_time  \r\n"
				+ "from room_info ro, studycafe sr \r\n"
				+ "where  sr.cafe_seq = ro.cafe_seq\r\n"
				+ "AND ro.room_seq= :roomSeq ",nativeQuery=true)
		public List<Object[]> getInfoOne(@Param("roomSeq") Long roomSeq);
}