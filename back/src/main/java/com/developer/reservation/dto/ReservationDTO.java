package com.developer.reservation.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.developer.hostuser.dto.HostUserDTO;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roomreview.dto.RoomReviewDTO;
import com.developer.users.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReservationDTO {
	private Long resSeq;
	private String startTime;
	private String endTime;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date usingDate;

	private RoomInfoDTO roomInfoDTO;
	private HostUserDTO hostUserDTO;
	private UsersDTO usersDTO;
	private RoomReviewDTO roomReviewDTO;

	// SR: 예약상세 & 목록 출력용
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllReservationDTO {
		private Long resSeq;
		private String startTime;
		private String endTime;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date usingDate;
		private String hostId;
		private String userId;
		private RoomInfoDTO.selectAllReservationDTO roomInfoDTO;
		private UsersDTO.selectAllReservationDTO usersDTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class getReservationDTO {
		private Long resSeq;
		private Date usingDate;
		private String startTime;
		private String endTime;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class selectRmRvDTO {
		private Long resSeq;
		private String startTime;
		private String endTime;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date usingDate;
		private RoomInfoDTO.RoomInfoNameDTO roomInfoNameDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class selectAllByUsingDateDTO {
		private Long roomSeq;
		private String startTime;
		private String endTime;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date usingDate;
		private RoomInfoDTO.RoomInfoPriceDTO roomInfoPriceDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class selectMyResHistoryDTO {
		private Long resSeq;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date usingDate;
		private String startTime;
		private String endTime;
		private RoomInfoDTO.RoomInfoNameDTO roomInfoNameDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class insertRvDTO {
		private String startTime;
		private String endTime;
		private Date usingDate;
		private String userId;
		// 아이디도 session으로 넘어옴
		private String hostId;
		// 스터디룸
		private Long roomSeq;
		// 방번호는 이전페이지에서 넘어옴
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomReviewSelectAllDTO {
		private RoomInfoDTO.RoomInfoNameDTO roomInfoNameDTO;
		private UsersDTO.UserNickNameDTO userNickNameDTO;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomReviewSelectMyRmRvDTO {
		private RoomInfoDTO.RoomInfoNameDTO roomInfoNameDTO;

	}

	// ds
	@Data
	@NoArgsConstructor
	public static class RoomReviewSelectMyRmRvDetailDTO {
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date usingDate;
		private RoomInfoDTO.RoomInfoNameDTO roomInfoNameDTO;

	}

}