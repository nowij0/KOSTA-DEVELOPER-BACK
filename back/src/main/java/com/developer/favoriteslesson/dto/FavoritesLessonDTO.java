package com.developer.favoriteslesson.dto;

import java.util.Date;

import com.developer.lesson.dto.LessonDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FavoritesLessonDTO {

	// [JW] 나의 수업 즐겨찾기 목록 확인
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class flListDTO {
		private Long favLesSeq;
		private String tuteeId;
		private String lessonName;
		private int category;
		private String location;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date applyEnd;
	}

	// [JW]
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class favoritesLessonDTO {
		private Long favLesSeq;
		private String tuteeId;

		private LessonDTO.onlyLessonDTO ldto;
	}

	// [JW]
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class selectDetailDTO {
		private Long favLesSeq;
		private String tuteeId;
	}

}
