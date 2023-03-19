package com.developer.favoriteslesson.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.favoriteslesson.entity.FavoritesLesson;

public interface FavoritesLessonRepository extends CrudRepository<FavoritesLesson, Long> {

	//[JW]
	@Query(value = "SELECT * FROM FAVORITES_LESSON WHERE tutee_id = :tuteeId", nativeQuery = true)
	public List<Object[]> listFavLesson(@Param ("tuteeId") String tuteeId);
	
}
