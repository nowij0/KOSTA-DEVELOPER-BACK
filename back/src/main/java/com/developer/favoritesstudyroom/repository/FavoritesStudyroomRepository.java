package com.developer.favoritesstudyroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.favoritesstudyroom.entity.FavoritesStudyroom;

public interface FavoritesStudyroomRepository extends CrudRepository<FavoritesStudyroom, Long> {

	// [SR] 마이페이지 - 즐겨찾기 스터디카페 목록
	@Query(value = "SELECT s.sr_seq, s.name, s.addr " 
				+ "FROM studyroom s, favorites_studyroom fav_s, host_user h "
				+ "WHERE s.sr_seq = fav_s.sr_seq " 
				+ "AND s.host_id = h.host_id " 
				+ "AND h.ready != 2 "
				+ "AND fav_s.user_id = :userId", nativeQuery = true)
	public List<Object[]> selectAllFavStudyroom(@Param("userId") String userId);
	
	//DS
	@Query(value="select sr_seq, fav_seq from favorites_studyroom where user_id= :userId", nativeQuery = true)
	public List<Object[]> getfvInfo(@Param("userId") String userId);
	
	//DS
	@Query(value="delete from favorites_studyroom where sr_seq= :srSeq And user_id = :userId", nativeQuery = true)
	public void deleteFvstudyroom(@Param("srSeq") Long srSeq, @Param("userId") String userId);
		
}