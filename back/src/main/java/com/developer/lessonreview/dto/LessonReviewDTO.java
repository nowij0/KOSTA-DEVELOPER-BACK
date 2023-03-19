package com.developer.lessonreview.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

public class LessonReviewDTO {

	// [JW]
	@Data
	@NoArgsConstructor
	public static class lrDTO {
		private Long applySeq;
		@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
		private Date cDate;
		private String review;
		private Integer star;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class lrALDTO {
		@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
		private Date cDate;
		private String review;
		private Integer star;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class listLRListDTO {
		private String lessonName;
		private String review;
		private Integer star;
		private String name;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class noWriteLReviewDTO {
		private Long applySeq;
		private String lessonName;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class getReviewList {
		private String review;
		private Integer star;
	}
}
