package com.developer.recommend.dto;

import com.developer.board.dto.BoardDTO;
import com.developer.users.dto.UsersDTO;

import lombok.Data;

@Data
public class RecommendDTO {
	private Long recSeq;
	private BoardDTO boardDTO;
	private UsersDTO usersDTO;
}
