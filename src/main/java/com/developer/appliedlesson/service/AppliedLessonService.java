package com.developer.appliedlesson.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.developer.appliedlesson.dto.AppliedLessonDTO;
import com.developer.appliedlesson.entity.AppliedLesson;
import com.developer.appliedlesson.repository.AppliedLessonRepository;
import com.developer.exception.FindException;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.entity.Lesson;
import com.developer.lesson.repository.LessonRepository;
import com.developer.lessonreview.dto.LessonReviewDTO;
import com.developer.users.dto.UsersDTO;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppliedLessonService {

	private final AppliedLessonRepository alRepository;
	private final UsersRepository uRepository;
	private final LessonRepository lRepository;

	/**
	 * 클래스 신청내역 정보 불러오기
	 * 
	 * @author Jin
	 * @param applySeq
	 * @return
	 * @throws FindException
	 */
	public AppliedLessonDTO.selectAppliedLessonDTO selectAppliedLesson(Long applySeq) throws FindException {
		Optional<AppliedLesson> optAppliedLesson = alRepository.findById(applySeq);
		if (optAppliedLesson.isPresent()) {
			AppliedLesson appliedLesson = optAppliedLesson.get();
			ModelMapper modelMapper = new ModelMapper();
			AppliedLessonDTO.selectAppliedLessonDTO appliedLessonDTO = modelMapper.map(appliedLesson,
					AppliedLessonDTO.selectAppliedLessonDTO.class);

			return appliedLessonDTO;
		} else {
			throw new FindException("수업 신청 내역을 찾을 수 없습니다.");
		}

	}

	/**
	 * 내 클래스에 신청한 튜티 승인하기
	 * 
	 * @author Jin
	 * @param applySeq
	 * @throws FindException
	 */
	public void updateApplyLesson(Long applySeq) throws FindException {
		Optional<AppliedLesson> optAl = alRepository.findById(applySeq);
		if (optAl.isPresent()) {
			AppliedLesson appliedLessonEntity = optAl.get();
			appliedLessonEntity.setApplyOk(1);
			alRepository.save(appliedLessonEntity);
		}
	}

	/**
	 * 내 클래스에 신청한 튜티 거절하기
	 * 
	 * @author Jin
	 * @param applySeq
	 * @throws FindException
	 */
	public void updateNotApplyLesson(Long applySeq) throws FindException {
		Optional<AppliedLesson> optAl = alRepository.findById(applySeq);
		if (optAl.isPresent()) {
			AppliedLesson appliedLessonEntity = optAl.get();
			appliedLessonEntity.setApplyOk(2);
			alRepository.save(appliedLessonEntity);
		}
	}

	/**
	 * 수업 신청
	 * 
	 * @author moonone
	 * @param dto       신청정보
	 * @param lessonSeq 신청하는 수업번호
	 * @param logined   사용자아이디
	 */
	public void applyLesson(Long lessonSeq, String logined) {
		AppliedLesson alEntity = new AppliedLesson();
		alEntity.setApplyOk(0);
		alEntity.setTuteeId(logined);
		Optional<Lesson> optL = lRepository.findById(lessonSeq);
		alEntity.setLesson(optL.get());
		Optional<Users> optU = uRepository.findById(logined);
		alEntity.setUsers(optU.get());

		alRepository.save(alEntity);
	}

	/**
	 * 승인대기중인 튜티리스트 출력
	 * 
	 * @author Jin
	 * @param lessonSeq
	 * @return
	 * @throws FindException
	 */
	public List<AppliedLessonDTO.NotYetUserByAppliedLessonDTO> getLessonNotApplyUser(Long lessonSeq)
			throws FindException {
		List<Object[]> Alist = alRepository.getLessonNotApplyUser(lessonSeq);
		List<AppliedLessonDTO.NotYetUserByAppliedLessonDTO> dto = new ArrayList<>();
		for (int i = 0; i < Alist.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			AppliedLessonDTO.NotYetUserByAppliedLessonDTO aDTO = new AppliedLessonDTO.NotYetUserByAppliedLessonDTO();
			LessonDTO.selectDetailDTO lDTO = new LessonDTO.selectDetailDTO();
			uDTO.setName((String) Alist.get(i)[0]);
			BigDecimal applySeq = (BigDecimal) Alist.get(i)[1];
			Long resultApplySeq = applySeq.longValue();
			aDTO.setApplySeq(resultApplySeq);
			aDTO.setApplyOk(0);
			aDTO.setTuteeId((String) uDTO.getUserId());
			lDTO.setLessonSeq(lessonSeq);
			aDTO.setUsersDTO(uDTO);
			aDTO.setLessonDTO(lDTO);
			dto.add(aDTO);
		}
		return dto;
	}

	/**
	 * 승인된 튜티리스트 출력
	 * 
	 * @author Jin
	 * @param lessonSeq
	 * @return
	 * @throws FindException
	 */
	public List<AppliedLessonDTO.ApproveUserByAppliedLessonDTO> getLessonApplyUser(Long lessonSeq)
			throws FindException {
		List<Object[]> Alist = alRepository.getLessonApplyUser(lessonSeq);
		List<AppliedLessonDTO.ApproveUserByAppliedLessonDTO> dto = new ArrayList<>();
		for (int i = 0; i < Alist.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			AppliedLessonDTO.ApproveUserByAppliedLessonDTO aDTO = new AppliedLessonDTO.ApproveUserByAppliedLessonDTO();
			LessonDTO.selectDetailDTO lDTO = new LessonDTO.selectDetailDTO();
			uDTO.setName((String) Alist.get(i)[0]);
			BigDecimal applySeq = (BigDecimal) Alist.get(i)[1];
			Long resultApplySeq = applySeq.longValue();
			aDTO.setApplySeq(resultApplySeq);
			aDTO.setTuteeId((String) uDTO.getUserId());
			lDTO.setLessonSeq(lessonSeq);
			aDTO.setUsersDTO(uDTO);
			aDTO.setLessonDTO(lDTO);
			dto.add(aDTO);
		}
		return dto;
	}

	/**
	 * 진행완료된 수업 수업명, 튜티목록(후기가없는사람)
	 * 
	 * @author choigeunhyeong
	 * @param lessonSeq
	 * @return
	 * @throws FindException
	 */
	public List<UsersDTO.getNameDTO> noReviewTutee(Long lessonSeq) throws FindException {
		List<Object[]> list = alRepository.noReviewTutee(lessonSeq);
		List<UsersDTO.getNameDTO> dtoList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			UsersDTO.getNameDTO uDTO = new UsersDTO.getNameDTO();
			uDTO.setUsername((String) list.get(i)[0]);
			
			LessonDTO.getLessonNameDTO lDTO = new LessonDTO.getLessonNameDTO();
			lDTO.setLessonName((String) list.get(i)[1]);
			uDTO.setLessonName(lDTO);
			
			AppliedLessonDTO.UserByAppliedLessonDTO aDTO = new AppliedLessonDTO.UserByAppliedLessonDTO();
			BigDecimal seq = (BigDecimal) list.get(i)[2];
			Long applySeq = seq.longValue();
			aDTO.setApplySeq(applySeq);
			uDTO.setApplySeq(aDTO);
			dtoList.add(uDTO);
		}
		return dtoList;
	}

	/**
	 * 진행완료된 클래스 페이지 후기 전체목록
	 * 
	 * @author choigeunhyeong
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<UsersDTO.getCompletedClassDTO> selectCompletedClassList(Long lessonSeq) throws FindException {
		List<Object[]> list = alRepository.selectCompletedClassList(lessonSeq);
		List<UsersDTO.getCompletedClassDTO> dtoList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			LessonReviewDTO.getReviewList lDTO = new LessonReviewDTO.getReviewList();
			lDTO.setReview((String) list.get(i)[0]);
			BigDecimal star = (BigDecimal) list.get(i)[1];
			int resultstar = star.intValue();
			lDTO.setStar(resultstar);
			UsersDTO.getCompletedClassDTO gDTO = new UsersDTO.getCompletedClassDTO();
			gDTO.setUsername((String) list.get(i)[2]);
			gDTO.setReview(lDTO);
			dtoList.add(gDTO);
		}
		return dtoList;
	}

}
