package com.developer.lesson.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.developer.appliedlesson.dto.AppliedLessonDTO;
import com.developer.favoriteslesson.dto.FavoritesLessonDTO;
import com.developer.tutor.dto.TutorDTO;
import com.developer.users.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicUpdate
public class LessonDTO {
	
	@Data
	@NoArgsConstructor
	public static class lessonOrderDTO{
		private String lessonName;
	}
	
	// [JH]
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Data
	@NoArgsConstructor
	public static class selectLessonDTO {
		private Long lessonSeq;
		private String lessonName;
		private Integer category;
		private String content;
		private Integer people;
		private String imgPath;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date startCdate;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date endCdate;
		private Integer price;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date startDate;
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date endDate;
		private Integer payLesson;
		private String location;

		private TutorDTO.tutorDTO tDTO;
		private FavoritesLessonDTO flDTO;
		private AppliedLessonDTO alDTO;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectDetailDTO {
		private Long lessonSeq;
		private String lessonName;
		private Integer category;
		private String content;
		private Integer people;
		private String imgPath;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date startCdate;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date endCdate;
		private Integer price;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date startDate;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date endDate;
		private Integer payLesson;
		private String location;

		private UsersDTO.UsersDetailDTO uDTO;
		private TutorDTO.selectDetailDTO tDTO;
		private List<FavoritesLessonDTO.selectDetailDTO> flDTO;
		private List<AppliedLessonDTO.alAddRequestDTO> alDTO;
	}

	// [JH]
	@Data
	@NoArgsConstructor
	public static class GetLessonByUser1 {
		private String lessonName;
		private Long lessonSeq;
		private TutorDTO.tutorDTO tDTO;
	}

	// [JH]
	@Data
	@NoArgsConstructor
	public static class GetLessonByUser2 {
		private String lessonName;
		private Long lessonSeq;
		private TutorDTO.tutorDTO tDTO;
	}

	// [JH]
	@Data
	@NoArgsConstructor
	public static class GetLessonByUser3 {
		private String lessonName;
		private Long lessonSeq;
		private TutorDTO.tutorDTO tDTO;
	}
	
	//[JH]
	@Data
	@NoArgsConstructor
	public static class UnpaidLessonByUser {
		private String lessonName;
		private Long lessonSeq;
		private TutorDTO.tutorDTO tDTO;
		
	}

	// [JH}
	@Data
	@NoArgsConstructor
	public static class applyLessonBytutee {
		private Long lessonSeq;
		private String lessonName;
		private AppliedLessonDTO.selectAppliedLessonDTO alDTO;
	}

	// [JH}
	@Data
	@NoArgsConstructor
	public static class notYetLessonBytutee {
		private Long lessonSeq;
		private String lessonName;
		private AppliedLessonDTO.selectAppliedLessonDTO alDTO;
	}


	// [JW]
	@Data
	@NoArgsConstructor
	public static class onlyLessonDTO {
		private Long lessonSeq;
		private String lessonName;
		private Integer category;
		private String content;
		private Integer people;
		private String imgPath;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date startCdate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date endCdate;
		private Integer price;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date startDate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date endDate;
		private Integer payLesson;
		private String location;
	}


	// [JW]
	@Data
	@NoArgsConstructor
	public static class addLessonDTO {
		private Long lessonSeq;
		private String lessonName;
		private Integer category;
		private String content;
		private Integer people;
		private String imgPath;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date startCdate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date endCdate;
		private Integer price;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date startDate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date endDate;
		private Integer payLesson;
		private String location;

		private TutorDTO tDTO;
	}


	// [JW]
	@Data
	@NoArgsConstructor
	public static class selectAllReviewDTO {
		private String lessonName;
		private String name;

		private List<AppliedLessonDTO.alLessonDTO> alDTO;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class lessonDetailDTO {
		private selectDetailDTO lessonDto;
		private Integer cnt;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class flListDTO {
		private String lessonName;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class allLessonListDTO {
		private Long lessonSeq;
		private String lessonName;
		private Integer category;
		private Integer payLesson;
		private Integer price;
		private String tutorId;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class searchLessonDTO {
		private Long lessonSeq;
		private String lessonName;
		private Integer category;
		private String imgPath;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date startDate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date endDate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date applyStartDate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date applyEndDate;
		private Integer price;
	}
	
	// [JW]
	@Data
	@NoArgsConstructor
	public static class searchName {
		private String lessonName;
	}

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllBydateLessonDTO {
		private Long lessonSeq;
		private String lessonName;
		private String imgPath;
		private Integer price;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class getLessonNameDTO {
		private String lessonName;
	}

	// DS
	@Data
	@NoArgsConstructor
	public static class LessonList5DTO {
		private String lessonName;
		private Integer category;
		private Integer people;
	}

}
