package com.developer.users.dto;

import java.util.List;

import com.developer.appliedlesson.dto.AppliedLessonDTO;
import com.developer.appliedlesson.entity.AppliedLesson;
import com.developer.board.dto.BoardDTO;
import com.developer.boardrep.dto.BoardRepDTO;
import com.developer.favoriteslesson.entity.FavoritesLesson;
import com.developer.favoritesstudycafe.entity.FavoritesStudycafe;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lessonreview.dto.LessonReviewDTO;
import com.developer.recommend.dto.RecommendDTO;
import com.developer.recommend.entity.Recommend;
import com.developer.reservation.entity.Reservation;
import com.developer.tutor.entity.Tutor;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
public class UsersDTO {
	private String userId;
	private String pwd;
	private Integer role;
	private String nickname;
	private String name;
	private String email;
	private String tel;
	private String addr;

	private Tutor tutor;
	private List<AppliedLesson> appliedLesson;
	private List<FavoritesLesson> favoritesLesson;
	private BoardDTO boardDTO;
	private BoardRepDTO boardRepDTO;
	private RecommendDTO recommendDTO;
	private List<Recommend> recommend;
	private List<Reservation> reservation;
	private List<FavoritesStudycafe> favStudycafe;

	// [JH]
	@Data
	@NoArgsConstructor
	public static class UsersDetailDTO {
		private String userId;
		private String pwd;
		private Integer role;
		private String nickname;
		private String name;
		private String email;
		private String tel;
		private String addr;
	}

	// [JW] [관리자] 수업을 예약한 튜티 목록
	@Data
	@NoArgsConstructor
	public static class uNameDTO {
		private String tuteeName;
		private String tuteeId;
	}

	//[JW]
	@Data
	@NoArgsConstructor
	public static class applyLessonDTO {
		private String userId;
	}

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllBydateBoardDTO {
		private String nickname;
	}

	// SR: 예약상세 & 목록용
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllReservationDTO {
		private String name;
		private String tel;
	}

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class tutorApplyDTO {
		private String name;
	}

	// SR 튜터미승인목록
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class unapproveTutorDTO {
		private String userId;
		private String nickname;
		private String name;
		private String email;
		private String tel;

	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class uDTO {
		private String userId;
		private String pwd;
		private Integer role;
		private String nickname;
		private String name;
		private String email;
		private String tel;
		private String addr;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class UsersNameDTO {
		private String userId;
		private Integer role;
		private String nickname;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class getNameDTO {
		private String username;
		private LessonDTO.getLessonNameDTO lessonName;
		private AppliedLessonDTO.UserByAppliedLessonDTO applySeq;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class getCompletedClassDTO {
		private String username;
		private LessonReviewDTO.getReviewList review;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class UserIdDTO {
		private Integer fvCNT; // count함수
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class UserNickNameDTO {
		private String nickname;
	}
	
	//ds
	@Data
	@NoArgsConstructor
	public static class UsersUserIdDTO {
		private String userId;
	}
}
