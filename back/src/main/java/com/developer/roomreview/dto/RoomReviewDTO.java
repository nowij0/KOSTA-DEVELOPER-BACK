package com.developer.roomreview.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.developer.reservation.dto.ReservationDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
@Data
public class RoomReviewDTO {

	private Long resSeq;
	private String content;
	private Integer star;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date cDate;
	private ReservationDTO reservationDTO;

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomReviewInsertDTO {
		private Long resSeq;
		private String content;
		private Integer star;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomReviewSelectAllDTO {
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date cdate;
		private Integer star;
		private String content;

		private ReservationDTO.RoomReviewSelectAllDTO rrsaDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class selectMyRmRvDTO {
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date cdate;
		private Integer star;
		private String content;
		private ReservationDTO.RoomReviewSelectMyRmRvDTO rrsaDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class selectMyRmRvDetailDTO {
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date cdate;
		private Integer star;
		private String content;
		private ReservationDTO.RoomReviewSelectMyRmRvDetailDTO rrsaDTO;
	}
}