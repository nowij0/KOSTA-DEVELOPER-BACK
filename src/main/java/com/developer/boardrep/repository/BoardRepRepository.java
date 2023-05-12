package com.developer.boardrep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.boardrep.entity.BoardRep;

public interface BoardRepRepository extends JpaRepository<BoardRep, Long> {

}
