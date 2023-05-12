package com.developer.recommend.repository;


import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.developer.recommend.entity.Recommend;

@Transactional
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
	
}