package com.developer.mypage;

import java.util.List;

import com.developer.appliedlesson.dto.AppliedLessonDTO;
import com.developer.lesson.dto.LessonDTO;
import com.developer.reservation.dto.ReservationDTO;
import com.developer.roomreview.dto.RoomReviewDTO;
import com.developer.users.dto.UsersDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MyPageDTO {

	// [JH] 튜터 메인
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TutorMainDTO {
		private List<LessonDTO.GetLessonByUser1> list;
		private List<LessonDTO.GetLessonByUser2> list2;
		private List<LessonDTO.GetLessonByUser3> list3;
		private List<LessonDTO.UnpaidLessonByUser> list4;
	}

	// [JH] 튜터의 진행예정 수업 상세
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TutorUpcomingDTO {
		private List<AppliedLessonDTO.NotYetUserByAppliedLessonDTO> notYetList;
		private List<AppliedLessonDTO.ApproveUserByAppliedLessonDTO> approveList;
		private List<LessonDTO.selectLessonDTO> lList;
	}

	// [JH] 튜터가 진행중인 수업 상세
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TutorOngoingDTO {
		private List<LessonDTO.selectLessonDTO> lList;
		private List<AppliedLessonDTO.ApproveUserByAppliedLessonDTO> alList;
	}

	// [JH] 튜티의 아직 승인되지 않은 수업&승인되어 진행 예정인 수업 리스트
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TuteeUpcomingDTO {
		private List<LessonDTO.applyLessonBytutee> applyList;
		private List<LessonDTO.notYetLessonBytutee> notYetlist;
	}

	// ds
	@Data
	@NoArgsConstructor
	public static class getRoomReviewList {
		private List<ReservationDTO.selectRmRvDTO> selectRmRvDTO;
		private List<RoomReviewDTO.selectMyRmRvDTO> selectMyRmRvDTO;
	}

	// 근형 mypage-tutor-completed-detail
	@Data
	@NoArgsConstructor
	public static class tutorCompletedDetailDTO {
		List<AppliedLessonDTO.ApproveUserByAppliedLessonDTO> userAppliedLessonDTO;
		List<LessonDTO.selectLessonDTO> selectLessonDTO;
		List<UsersDTO.getCompletedClassDTO> CompletedlessonReviewDTO;
	}

	// [JW]
	@Data
	@NoArgsConstructor
	public static class tuteeDashboardDTO {
		List<LessonDTO.notYetLessonBytutee> applyWaitList;
		List<LessonDTO.notYetLessonBytutee> rejectList;
		List<LessonDTO.applyLessonBytutee> notYetList;
		List<LessonDTO.applyLessonBytutee> proceedingList;
		List<LessonDTO.applyLessonBytutee> lastList;
	}

}
