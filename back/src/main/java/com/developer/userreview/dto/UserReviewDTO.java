package com.developer.userreview.dto;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.appliedlesson.dto.AppliedLessonDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

public class UserReviewDTO {

	@Data
	@NoArgsConstructor
	public static class selectUserReview {
		private Long applySeqRv;
		private Integer star;
		private String review;
		private AppliedLessonDTO appliedLessonDTO;
	}

	// [JH]
	@Data
	@NoArgsConstructor
	public static class getTuteeReview {
		private Long applySeqRv;
		private Integer star;
		private String review;
		private AppliedLessonDTO.selectAppliedLessonDTO appliedLessonDTO;
	}

	// 근형
	@Data
	@DynamicInsert
	@DynamicUpdate
	@NoArgsConstructor
	public static class addReviewDTO {
		private Long applySeqRv;
		private Integer star;
		private String review;
	}
}
