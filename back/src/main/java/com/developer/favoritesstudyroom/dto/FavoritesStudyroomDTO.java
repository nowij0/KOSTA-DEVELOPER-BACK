package com.developer.favoritesstudyroom.dto;

import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.studyroom.entity.Studyroom;
import com.developer.users.dto.UsersDTO;
import com.developer.users.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavoritesStudyroomDTO {
	private Long favSeq;
	private Users userId;
	private Studyroom studyroom;

	private Integer cnt;

	// ds
	@Data
	@NoArgsConstructor
	public static class favoritesStudyroomUserIdDTO {
		private UsersDTO.UserIdDTO userIdDTO;

	}

	// ds
	@Data
	@NoArgsConstructor
	public static class fvInsertDTO {
		private Long srSeq;
	}
	
	@Data
	@NoArgsConstructor
	public static class favStudyroomListDTO {
		private Long srSeq;
		private StudyroomDTO.selectAllFavStudyroomDTO studyroom;
	}
	//ds
	@Data
	@NoArgsConstructor
	public static class favStudyroomSrSeqDTO{
		
		private Long favSeq;
		private StudyroomDTO.StudyroomSrSeqDTO srseqDTO;
	}
	
	//ds
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class favStudyroominfoDTO{
		
		private Long favSeq;
		private String userId;

	}
	
	
}