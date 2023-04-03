package com.developer.roominfo.dto;

import java.util.List;

import com.developer.reservation.dto.ReservationDTO;
import com.developer.reservation.entity.Reservation;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;
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
	private String roomName;
	private String info;
	private String roomImg;
	private Integer person;
	private Integer price;
	private Integer roomStatus;

	@JsonIgnore
	private Studycafe studycafe;
	@JsonIgnore
	private List<Reservation> reservation; // 원래 List<>타입

	// 근형
	@Data
	@NoArgsConstructor
	public static class getReservationDTO {
		private String roomName;
		private ReservationDTO.getReservationDTO reservation;
		private UsersDTO.UsersNameDTO users;
	}

	// SR: 방목록 출력용
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllRoomDTO {
		private Long roomSeq;
		private String roomName;
		private String info;
		private String roomImg;
		private Integer person;
		private Integer price;
		private StudycafeDTO.StudycafeTimeDTO studycafeTimeDTO;
	}

	// SR: 예약목록/상세 출력용
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllReservationDTO {
		private String roomName;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoNameDTO {
		private String roomName;
		private StudycafeDTO.StudycafeNameDTO studycafeNameDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomInfoPriceDTO {
		private Integer price;
		private StudycafeDTO.StudycafeTimeDTO studycafeTimeDTO;
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
		private String roomName;
		private String info;
		private String roomImg;
		private Integer person;
		private Integer price;
		private Integer roomStatus;
		private StudycafeDTO.StudycafeHostIdDTO studycafeDTO;
	}

}