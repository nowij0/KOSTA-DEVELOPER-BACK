package com.developer.admin;

import java.util.List;

import com.developer.lesson.dto.LessonDTO;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.studyroom.entity.Studyroom;

import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminDTO {

	// DS
	@Data
	@NoArgsConstructor
	public static class getList5DTO {
		private List<StudyroomDTO.StudyroomList5DTO> studyroomList5DTO;
		private List<LessonDTO.LessonList5DTO> lessonList5DTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class adminStudyroomDetailDTO {
		private Studyroom s;
		private List<RoomInfoDTO.getReservationDTO> ReservationDTO;
	}
}
