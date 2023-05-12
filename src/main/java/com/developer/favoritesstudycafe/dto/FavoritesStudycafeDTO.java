package com.developer.favoritesstudycafe.dto;

import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;
import com.developer.users.dto.UsersDTO;
import com.developer.users.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavoritesStudycafeDTO {
	private Long favSeq;
	private Users userId;
	private Studycafe studycafe;

	private Integer cnt;

	// ds
	@Data
	@NoArgsConstructor
	public static class favoritesStudycafeUserIdDTO {
		private UsersDTO.UserIdDTO userIdDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class fvInsertDTO {
		private Long cafeSeq;
	}
	
	@Data
	@NoArgsConstructor
	public static class favStudycafeListDTO {
		private Long cafeSeq;
		private StudycafeDTO.selectAllFavStudycafeDTO studycafe;
	}
	//ds
	@Data
	@NoArgsConstructor
	public static class favStudycafeCafeseqDTO{
		
		private Long favCafeSeq;
		private StudycafeDTO.StudycafeCafeSeqDTO cafeseqDTO;
	}
	
	//ds
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class favStudycafeinfoDTO{
		
		private Long favCafeSeq;
		private String userId;

	}
	
	
}