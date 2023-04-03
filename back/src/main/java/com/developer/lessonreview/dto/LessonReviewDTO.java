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
	public static class lrAlDTO {
		@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
		private Date lessonReviewDate;
		private String lessonReview;
		private Integer lessonStar;
	}

	// [JW] 작성한 후기 목록 확인
	@Data
	@NoArgsConstructor
	public static class listLRListDTO {
		private String lessonName;
		private String lessonReview;
		private Integer lessonStar;
		private String tuteeName;
	}

	// [JW] 후기를 작성하지 않은 수업 목록 확인
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
