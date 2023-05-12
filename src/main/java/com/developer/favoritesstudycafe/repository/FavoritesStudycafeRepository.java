package com.developer.favoritesstudycafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.favoritesstudycafe.entity.FavoritesStudycafe;

public interface FavoritesStudycafeRepository extends CrudRepository<FavoritesStudycafe, Long> {

	// [SR] 마이페이지 - 즐겨찾기 스터디카페 목록
	@Query(value = "SELECT s.cafe_seq, s.cafeName, s.addr " 
				+ "FROM studyroom s, favorites_studyroom fav_s, host_user h "
				+ "WHERE s.sr_seq = fav_s.sr_seq " 
				+ "AND s.host_id = h.host_id " 
				+ "AND h.status != 2 "
				+ "AND fav_s.user_id = :userId", nativeQuery = true)
	public List<Object[]> selectAllFavStudyroom(@Param("userId") String userId);
	
	//DS
	@Query(value="select cafe_seq, fav_cafe_seq from favorites_studyroom where user_id= :userId", nativeQuery = true)
	public List<Object[]> getfvInfo(@Param("userId") String userId);
	
	//DS
	@Query(value="delete from favorites_studyroom where cafe_seq= :srSeq And user_id = :userId", nativeQuery = true)
	public void deleteFvstudyroom(@Param("srSeq") Long srSeq, @Param("userId") String userId);
		
}