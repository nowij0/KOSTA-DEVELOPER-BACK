package com.developer.host.dto;

import com.developer.reservation.dto.ReservationDTO;
import com.developer.studycafe.dto.StudycafeDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostDTO {

	private String hostId;
	private String pwd;
	private String businessNum;
	private Integer ready;
	private String hostName;
	private String hostTel;
	private String hostEmail;
	@JsonIgnore
	private StudycafeDTO studycafe;
	private ReservationDTO reservation; // 원래는 List<>타입

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class getHostDTO {
		private String hostId;
		private String pwd;
		private String businessNum;
		private Integer status;
		private String hostName;
		private String hostTel;
		private String hostEmail;
	}

	// SR: 미승인 호스트 목록
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class unApproveHostDTO {
		private String hostId;
		private String businessNum;
		private String hostName;
		private String hostTel;
		private String hostEmail;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class getAllHostDTO {
		private String hostId;
		private String pwd;
		private String businessNum;
		private Integer status;
		private String hostName;
		private String hostEmail;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class HostLoginDTO {
		private String hostId;
		private String pwd;
		private Integer status;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class HostIdDTO {
		private String hostId;
	}

}
