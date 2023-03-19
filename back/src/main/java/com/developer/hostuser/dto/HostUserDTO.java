package com.developer.hostuser.dto;

import com.developer.reservation.dto.ReservationDTO;
import com.developer.studyroom.dto.StudyroomDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostUserDTO {

	private String hostId;
	private String pwd;
	private String num;
	private Integer ready;
	private String name;
	private String tel;
	private String email;
	@JsonIgnore
	private StudyroomDTO studyroom;
	private ReservationDTO reservation; // 원래는 List<>타입

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class getHostDTO {
		private String hostId;
		private String pwd;
		private String num;
		private Integer ready;
		private String name;
		private String tel;
		private String email;
	}

	// SR: 미승인 호스트 목록
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class unApproveHostDTO {
		private String hostId;
		private String num;
		private String name;
		private String tel;
		private String email;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class getAllHostUserDTO {
		private String hostId;
		private String pwd;
		private String num;
		private Integer ready;
		private String name;
		private String email;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class HostLoginDTO {
		private String hostId;
		private String pwd;
		private Integer ready;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class HostIdDTO {
		private String hostId;
	}

}
