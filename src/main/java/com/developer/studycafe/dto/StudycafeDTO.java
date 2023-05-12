package com.developer.studycafe.dto;

import java.util.List;

import com.developer.favoritesstudycafe.dto.FavoritesStudycafeDTO;
import com.developer.favoritesstudycafe.entity.FavoritesStudycafe;
import com.developer.host.dto.HostDTO;
import com.developer.host.entity.Host;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roominfo.entity.RoomInfo;
import com.developer.roomreview.dto.RoomReviewDTO;
import com.developer.users.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudycafeDTO {
	private Long cafeSeq;
	private String cafeName;
	private String addr;
	private String info;
	private String openTime;
	private String endTime;
	private String cafeImg;
	private Integer openStatus;
	@JsonIgnore
	private RoomInfo roomInfo; // 원래 List<>
	@JsonIgnore
	private Host host;
	@JsonIgnore
	private FavoritesStudycafe favoritesStudycafe; // 원래 List<>

	// 근형
	@Data
	@NoArgsConstructor
	public static class getAllStudycafeDTO {
		private Long cafeSeq;
		private String openTime;
		private String endTime;
		private String cafeName;
		private String addr;
		private HostDTO.getAllHostDTO host;
	}

	// sr: 카페등록때 필요한 생성자
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class InsertStudycafeDTO {
		private Long cafeSeq;
		private String cafeName;
		private String addr;
		private String info;
		private String openTime;
		private String endTime;
		private String cafeImg;
		private Integer openStatus;
	}

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class getHostAndStudycafeDTO {
		private Long cafeSeq;
		private String cafeName;
		private String addr;
		private String info;
		private String openTime;
		private String endTime;
		private String cafeImg;
		private Integer openStatus;
		private HostDTO.getHostDTO hostDTO;
	}

	// SR: 즐겨찾기목록용
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllFavStudycafeDTO {
		private String cafeName;
		private String addr;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeNameDTO {
		private String cafeName;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeTimeDTO {
		private String openTime;
		private String endTime;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeSelectBySearchDTO {
		private Long cafeSeq;
		private String cafeName;
		private String addr;
		private String cafeImg;
		private Integer person;
		private RoomInfoDTO.RoomInfoPriceAndPersonDTO roomInfoPriceAndPersonDTO;
		private FavoritesStudycafeDTO.favoritesStudycafeUserIdDTO favoritesStudycafeUserIdDTO;

	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeNameAndUserDTO {
		private String name;
		private UsersDTO.UserNickNameDTO userNickNameDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeCafeSeqDTO {
		private Long cafeSeq;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeList5DTO {
		private Long cafeSeq;
		private String cafeName;
		private HostDTO.HostIdDTO hostIdDTO;
	}

	// ds
	@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
	@Data
	@NoArgsConstructor
	public static class StudycafeRoomInfoPageDTO {
		private List<RoomInfoDTO.RoomInfoRoomDetailListDTO> roominfoDTO;
		private List<RoomReviewDTO.RoomReviewSelectAllDTO> roomReviewSelectAllDTO;
		private StudycafeDTO.StudycafeAndFavStudycafeInfoDTO studycafeAndFavStudycafeInfoDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeHostIdDTO {
		private HostDTO.HostIdDTO hostIdDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class StudycafeAndRoomInfoDTO {
		private String openTime;
		private String endTime;
		private RoomInfoDTO.RoomInfoPriceOnlyDTO roomInfoPriceDTO;

	}
	//ds
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StudycafeAndFavStudycafeInfoDTO{
		private Long cafeSeq;
		private String cafeName;
		private String addr;
		private String info;
		private String openTime;
		private String endTime;
		private String cafeImg;
		private Integer openStatus;
		
		private List<FavoritesStudycafeDTO.favStudycafeinfoDTO> favoritesStudycafeDTO; // 원래 List<>

	}
}