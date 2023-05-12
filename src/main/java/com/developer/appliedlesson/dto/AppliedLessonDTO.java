package com.developer.appliedlesson.dto;

import java.util.Date;

import com.developer.lesson.dto.LessonDTO;
import com.developer.lessonreview.dto.LessonReviewDTO;
import com.developer.userreview.dto.UserReviewDTO;
import com.developer.users.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

public class AppliedLessonDTO {
	private Long applySeq;
	
	// [JH]
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")   
	@Data
	@NoArgsConstructor
	public static class selectAppliedLessonDTO{ 
		private Long applySeq;
		private Date cdate;
		private Integer applyOk;
		private String tuteeId;
	   
	    private LessonDTO.selectDetailDTO lessonDTO;
	    private LessonReviewDTO lessonReviewDTO;
	    private UsersDTO usersDTO;
	    private UserReviewDTO userReviewDTO;
	}

	// [JH]
	@Data @NoArgsConstructor
	public static class NotYetUserByAppliedLessonDTO{
		private Long applySeq;
		private Integer applyOk;
		private String tuteeId;
		private LessonDTO.selectDetailDTO lessonDTO;
		private UsersDTO usersDTO;
	}
	
	// [JH]
	@Data @NoArgsConstructor
	public static class ApproveUserByAppliedLessonDTO{
		private Long applySeq;
		private Integer applyOk;
		private String tuteeId;
		private LessonDTO.selectDetailDTO lessonDTO;
		private UsersDTO usersDTO;
	}
	
	// [JH]
	@Data @NoArgsConstructor
	public static class getTutteAppliedLessonDTO{
		private Long applySeq;
		private Date cdate;
		private Integer applyOk;
		private String tuteeId;
	   
	    private LessonDTO.selectDetailDTO lessonDTO;
	    private UsersDTO usersDTO;
	}
	
	// [GH]
	@Data @NoArgsConstructor
	public static class UserByAppliedLessonDTO{
		private Long applySeq;
		private Integer applyOk;
		private String tuteeId;
		private LessonDTO.selectDetailDTO lessonDTO;
		private UsersDTO usersDTO;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class alDTO {
		private Long applySeq;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date cdate;
		private Integer applyOk;
		private String tuteeId;
	   
	    private LessonDTO.selectDetailDTO lessonDTO;
	    private LessonReviewDTO lessonReviewDTO;
	    private UsersDTO usersDTO;
	    private UserReviewDTO userReviewDTO;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class lessonAlDTO {
		private String tuteeId;
		private LessonReviewDTO.lrAlDTO lrdto;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class alAddRequestDTO {
		private Long applySeq;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date applyDate;
		private Integer applyOk;
		private String tuteeId;
	}
	
}
