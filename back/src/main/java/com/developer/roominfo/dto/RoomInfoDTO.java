package com.developer.roominfo.dto;

import java.util.List;

import com.developer.reservation.dto.ReservationDTO;
import com.developer.reservation.entity.Reservation;
import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.studyroom.entity.Studyroom;
import com.developer.users.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfoDTO {
	private Long roomSeq;
	private String name;
	private String info;
	private String imgPath;
	private Integer person;
	private Integer price;
	private Integer status;

	@JsonIgnore
	private Studyroom studyroom;
	@JsonIgnore
	private List<Reservation> reservation; // 원래 List<>타입

	// 근형
	@Data
	@NoArgsConstructor
	public static class getReservationDTO {
		private String name;
		private ReservationDTO.getReservationDTO reservation;
		private UsersDTO.UsersNameDTO users;
	}

	// SR: 방목록 출력용
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllRoomDTO {
		private Long roomSeq;
		private String name;
		private String info;
		private String imgPath;
		private Integer person;
		private Integer price;
		private StudyroomDTO.StudyroomTimeDTO studyroomTimeDTO;
	}

	// SR: 예약목록/상세 출력용
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllReservationDTO {
		private String name;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoNameDTO {
		private String name;
		private StudyroomDTO.StudyroomNameDTO studyroomNameDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoPriceDTO {
		private Integer price;
		private StudyroomDTO.StudyroomTimeDTO studyroomTimeDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoPriceOnlyDTO {
		private Integer price;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoPriceAndPersonDTO {
		private Integer price;
		private Integer person;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoRoomSeqDTO {
		private Long roomSeq;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoRoomDetailListDTO {
		private long roomSeq;
		private String name;
		private String info;
		private String imgPath;
		private Integer person;
		private Integer price;
		private Integer status;
		private StudyroomDTO.StudyroomHostIdDTO studyroomDTO;
	}

}