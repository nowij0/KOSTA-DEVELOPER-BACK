package com.developer.admin;

import java.util.List;

import com.developer.lesson.dto.LessonDTO;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;

import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminDTO {

	// DS
	@Data
	@NoArgsConstructor
	public static class getList5DTO {
		private List<StudycafeDTO.StudycafeList5DTO> studycafeList5DTO;
		private List<LessonDTO.LessonList5DTO> lessonList5DTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class adminStudyroomDetailDTO {
		private Studycafe s;
		private List<RoomInfoDTO.getReservationDTO> ReservationDTO;
	}
}
