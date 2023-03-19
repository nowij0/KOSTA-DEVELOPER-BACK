package com.developer.lesson.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.appliedlesson.dto.AppliedLessonDTO;
import com.developer.exception.FindException;
import com.developer.favoriteslesson.dto.FavoritesLessonDTO;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.dto.LessonDTO.selectAllBydateLessonDTO;
import com.developer.lesson.entity.Lesson;
import com.developer.lesson.repository.LessonRepository;
import com.developer.lessonreview.dto.LessonReviewDTO;
import com.developer.tutor.dto.TutorDTO;
import com.developer.tutor.entity.Tutor;
import com.developer.tutor.repository.TutorRepository;
import com.developer.users.dto.UsersDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonService {

	private final LessonRepository lRepository;
	private final TutorRepository tRepository;

	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 튜터아이디에 해당하는 진행예정 클래스 리스트 출력
	 * 
	 * @author Jin
	 * @param tutorId
	 * @return LessonDTO List형태로 반환
	 * @throws FindException
	 */
	public List<LessonDTO.GetLessonByUser1> getLessonByUser1(String logined) throws FindException {
		List<Object[]> Llist = lRepository.getLessonByUser1(logined);
		List<LessonDTO.GetLessonByUser1> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			TutorDTO.tutorDTO tDTO = new TutorDTO.tutorDTO();
			LessonDTO.GetLessonByUser1 lDTO = new LessonDTO.GetLessonByUser1();
			UsersDTO uDTO = new UsersDTO();
			lDTO.setLessonName((String) Llist.get(i)[0]);
			BigDecimal lessonSeq = (BigDecimal) Llist.get(i)[1];
			Long resultLessonSeq = lessonSeq.longValue();
			lDTO.setLessonSeq(resultLessonSeq);
			tDTO.setTutorId((String) logined);
			uDTO.setUserId((String) logined);
			tDTO.setUdto(uDTO);
			lDTO.setTDTO(tDTO);
			dto.add(lDTO);

		}
		return dto;
	}

	/**
	 * 튜터아이디에 해당하는 진행중인 클래스 리스트 출력
	 * 
	 * @author Jin
	 * @param tutorId
	 * @return LessonDTO List형태로 반환
	 * @throws FindException
	 */
	public List<LessonDTO.GetLessonByUser2> getLessonByUser2(String logined) throws FindException {
		List<Object[]> Llist = lRepository.getLessonByUser2(logined);
		List<LessonDTO.GetLessonByUser2> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			TutorDTO.tutorDTO tDTO = new TutorDTO.tutorDTO();
			LessonDTO.GetLessonByUser2 lDTO = new LessonDTO.GetLessonByUser2();
			UsersDTO uDTO = new UsersDTO();
			lDTO.setLessonName((String) Llist.get(i)[0]);
			BigDecimal lessonSeq = (BigDecimal) Llist.get(i)[1];
			Long resultLessonSeq = lessonSeq.longValue();
			lDTO.setLessonSeq(resultLessonSeq);
			tDTO.setTutorId((String) logined);
			uDTO.setUserId((String) logined);
			tDTO.setUdto(uDTO);
			lDTO.setTDTO(tDTO);
			dto.add(lDTO);

		}
		return dto;
	}

	/**
	 * 튜터아이디에 해당하는 진행완료 클래스 리스트 출력
	 * 
	 * @author Jin
	 * @param tutorId
	 * @return LessonDTO List형태로 반환
	 * @throws FindException
	 */
	public List<LessonDTO.GetLessonByUser3> getLessonByUser3(String logined) throws FindException {
		List<Object[]> Llist = lRepository.getLessonByUser3(logined);
		List<LessonDTO.GetLessonByUser3> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			TutorDTO.tutorDTO tDTO = new TutorDTO.tutorDTO();
			LessonDTO.GetLessonByUser3 lDTO = new LessonDTO.GetLessonByUser3();
			UsersDTO uDTO = new UsersDTO();
			lDTO.setLessonName((String) Llist.get(i)[0]);
			BigDecimal lessonSeq = (BigDecimal) Llist.get(i)[1];
			Long resultLessonSeq = lessonSeq.longValue();
			lDTO.setLessonSeq(resultLessonSeq);
			tDTO.setTutorId((String) logined);
			uDTO.setUserId((String) logined);
			tDTO.setUdto(uDTO);
			lDTO.setTDTO(tDTO);
			dto.add(lDTO);

		}
		return dto;
	}

	/**
	 * 결제가 필요한 수업 리스트
	 * 
	 * @author Jin
	 * @param tutorId
	 * @return
	 * @throws FindException
	 */
	public List<LessonDTO.UnpaidLessonByUser> unpaidLessonByUser(String logined) throws FindException {
		List<Object[]> Llist = lRepository.unpaidLessonByUser(logined);
		List<LessonDTO.UnpaidLessonByUser> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			TutorDTO.tutorDTO tDTO = new TutorDTO.tutorDTO();
			LessonDTO.UnpaidLessonByUser lDTO = new LessonDTO.UnpaidLessonByUser();
			UsersDTO uDTO = new UsersDTO();
			lDTO.setLessonName((String) Llist.get(i)[0]);
			BigDecimal lessonSeq = (BigDecimal) Llist.get(i)[1];
			Long resultLessonSeq = lessonSeq.longValue();
			lDTO.setLessonSeq(resultLessonSeq);

			tDTO.setTutorId((String) logined);
			uDTO.setUserId((String) logined);
			tDTO.setUdto(uDTO);
			lDTO.setTDTO(tDTO);
			dto.add(lDTO);

		}
		return dto;
	}

	/**
	 * 승인 대기중인 클래스(튜티기준)
	 * 
	 * @author Jin
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<LessonDTO.notYetLessonBytutee> getApplyLesson(String userId) throws FindException {
		List<Object[]> Llist = lRepository.getApplyLesson(userId);
		List<LessonDTO.notYetLessonBytutee> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			LessonDTO.notYetLessonBytutee lDTO = new LessonDTO.notYetLessonBytutee();
			AppliedLessonDTO.selectAppliedLessonDTO aDTO = new AppliedLessonDTO.selectAppliedLessonDTO();
			uDTO.setUserId((String) userId);
			lDTO.setLessonName((String) Llist.get(i)[0]);
			BigDecimal lessonSeq = (BigDecimal) Llist.get(i)[1];
			Long resultLessonSeq = lessonSeq.longValue();
			lDTO.setLessonSeq(resultLessonSeq);
			aDTO.setUsersDTO(uDTO);
			lDTO.setAlDTO(aDTO);
			dto.add(lDTO);
		}
		return dto;
	}

	/**
	 * 진행예정 클래스(튜티기준)
	 * 
	 * @author Jin
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<LessonDTO.applyLessonBytutee> upComingLesson(String userId) throws FindException {
		List<Object[]> Llist = lRepository.upComingLesson(userId);
		List<LessonDTO.applyLessonBytutee> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			LessonDTO.applyLessonBytutee lDTO = new LessonDTO.applyLessonBytutee();
			AppliedLessonDTO.selectAppliedLessonDTO aDTO = new AppliedLessonDTO.selectAppliedLessonDTO();
			uDTO.setUserId((String) userId);
			lDTO.setLessonName((String) Llist.get(i)[0]);
			BigDecimal lessonSeq = (BigDecimal) Llist.get(i)[1];
			Long resultLessonSeq = lessonSeq.longValue();
			lDTO.setLessonSeq(resultLessonSeq);
			aDTO.setUsersDTO(uDTO);
			lDTO.setAlDTO(aDTO);
			dto.add(lDTO);
		}
		return dto;
	}

	/**
	 * 진행중인 클래스(튜티기준)
	 * 
	 * @author Jin
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<LessonDTO.applyLessonBytutee> onGoingLesson(String userId) throws FindException {
		List<Object[]> Llist = lRepository.onGoingLesson(userId);
		List<LessonDTO.applyLessonBytutee> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			LessonDTO.applyLessonBytutee lDTO = new LessonDTO.applyLessonBytutee();
			AppliedLessonDTO.selectAppliedLessonDTO aDTO = new AppliedLessonDTO.selectAppliedLessonDTO();
			uDTO.setUserId((String) userId);
			lDTO.setLessonName((String) Llist.get(i)[0]);
			BigDecimal lessonSeq = (BigDecimal) Llist.get(i)[1];
			Long resultLessonSeq = lessonSeq.longValue();
			lDTO.setLessonSeq(resultLessonSeq);
			aDTO.setUsersDTO(uDTO);
			lDTO.setAlDTO(aDTO);
			dto.add(lDTO);
		}
		return dto;
	}

	/**
	 * 진행 완료된 수업 (튜티기준)
	 * 
	 * @author moonone
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<LessonDTO.applyLessonBytutee> lastApplyLesson(String userId) throws FindException {
		List<Object[]> Llist = lRepository.lastApplyLesson(userId);
		List<LessonDTO.applyLessonBytutee> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			LessonDTO.applyLessonBytutee lDTO = new LessonDTO.applyLessonBytutee();
			AppliedLessonDTO.selectAppliedLessonDTO aDTO = new AppliedLessonDTO.selectAppliedLessonDTO();
			uDTO.setUserId((String) userId);
			lDTO.setLessonName((String) Llist.get(i)[0]);
			aDTO.setUsersDTO(uDTO);
			lDTO.setAlDTO(aDTO);
			dto.add(lDTO);
		}
		return dto;
	}

	/**
	 * 레슨번호에 해당하는 클래스 상세정보 출력
	 * 
	 * @author Jin
	 * @param lessonSeq
	 * @return
	 * @throws FindException
	 */
	public List<LessonDTO.selectLessonDTO> getLessonDetail(Long lessonSeq) throws FindException {
		List<Object[]> Llist = lRepository.getLessonDetail(lessonSeq);
		List<LessonDTO.selectLessonDTO> dto = new ArrayList<>();
		TutorDTO.tutorDTO tDTO = new TutorDTO.tutorDTO();
		LessonDTO.selectLessonDTO lDTO = new LessonDTO.selectLessonDTO();
		UsersDTO uDTO = new UsersDTO();

		lDTO.setLessonName((String) Llist.get(0)[0]);
		lDTO.setLocation((String) Llist.get(0)[2]);
		lDTO.setImgPath((String) Llist.get(0)[1]);
		lDTO.setStartCdate((Date) Llist.get(0)[3]);
		lDTO.setEndCdate((Date) Llist.get(0)[4]);
		BigDecimal category = (BigDecimal) Llist.get(0)[5];
		BigDecimal people = (BigDecimal) Llist.get(0)[6];
		Integer categoryToInt = category.intValue();
		Integer peopleToInt = people.intValue();

		lDTO.setCategory(categoryToInt);
		lDTO.setPeople(peopleToInt);
		lDTO.setLessonSeq(lessonSeq);
		uDTO.setName((String) Llist.get(0)[7]);
		tDTO.setUdto(uDTO);
		lDTO.setTDTO(tDTO);
		dto.add(lDTO);

		return dto;
	}

	/**
	 * 클래스 삭제할 때, 찾아오기 위한 단순 검색용
	 * 
	 * @author Jin
	 * @param lessonSeq
	 * @return
	 * @throws FindException
	 */
	public LessonDTO.selectLessonDTO selectLesson(Long lessonSeq) throws FindException {
		Optional<Lesson> optLesson = lRepository.findById(lessonSeq);
		if (optLesson.isPresent()) {
			Lesson lesson = optLesson.get();
			ModelMapper modelMapper = new ModelMapper();
			LessonDTO.selectLessonDTO lessonDTO = modelMapper.map(lesson, LessonDTO.selectLessonDTO.class);

			return lessonDTO;
		} else {
			throw new FindException("수업 번호가 없습니다.");
		}
	}

	/**
	 * 수업 추가, 수정(지원님과 병합 충돌 예정->지원님껄로)
	 * 
	 * @author Jin
	 * @param lDTO
	 * @throws FindException
	 */
	public void updates(LessonDTO.selectLessonDTO lDTO) throws FindException {
		Lesson lEntity = new Lesson();
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(lDTO, lEntity);
		lRepository.save(lEntity);
	}

	/**
	 * 수업 삭제(payLesson을 3로 변경)
	 * 
	 * @author Jin
	 * @param lessonSeq
	 * @throws FindException
	 */
	public void deleteLesson(Long lessonSeq) throws FindException {

		Optional<Lesson> optLesson = lRepository.findById(lessonSeq);
		if (optLesson.isPresent()) {
			Lesson lessonEntity = optLesson.get();
			lessonEntity.setPayLesson(3);
			lRepository.save(lessonEntity);
		}
	}

	/**
	 * 결제한 수업의 payLesson을 1로 세팅한다.
	 * 
	 * @author Jin
	 * @param lessonSeq
	 * @throws FindException
	 */
	public void updatePayLesson(Long lessonSeq) throws FindException {
		Optional<Lesson> optLesson = lRepository.findById(lessonSeq);
		if (optLesson.isPresent()) {
			Lesson lessonEntity = optLesson.get();
			lessonEntity.setPayLesson(1);
			lRepository.save(lessonEntity);
		}
	}

	/**
	 * 선택한 수업에 대한 상세 정보
	 * 
	 * @author moonone
	 * @param lessonSeq 수업번호
	 * @return 수업 상세정보 + 튜터 정
	 * @throws FindException
	 */
	public LessonDTO.selectDetailDTO selectDetail(Long lessonSeq) throws FindException {
		Optional<Lesson> optL = lRepository.findById(lessonSeq);

		LessonDTO.selectDetailDTO lDTO = modelMapper.map(optL.get(), LessonDTO.selectDetailDTO.class);
		TutorDTO.selectDetailDTO tDTO = modelMapper.map(optL.get().getTutor(), TutorDTO.selectDetailDTO.class);
		UsersDTO.UsersDetailDTO uDTO = modelMapper.map(optL.get().getTutor().getUsers(), UsersDTO.UsersDetailDTO.class);

		List<FavoritesLessonDTO.selectDetailDTO> flList = new ArrayList<>();
		for (int i = 0; i < optL.get().getFlList().size(); i++) {
			FavoritesLessonDTO.selectDetailDTO flDTO = new FavoritesLessonDTO.selectDetailDTO();
			flDTO.setFavLesSeq(optL.get().getFlList().get(i).getFavLesSeq());
			flDTO.setTuteeId(optL.get().getFlList().get(i).getUsers().getUserId());
			flList.add(flDTO);
		}
		List<AppliedLessonDTO.alAddRequestDTO> alList = new ArrayList<>();
		for (int i = 0; i < optL.get().getAlList().size(); i++) {
			AppliedLessonDTO.alAddRequestDTO alDTO = new AppliedLessonDTO.alAddRequestDTO();
			alDTO.setApplySeq(optL.get().getAlList().get(i).getApplySeq());
			alDTO.setApplyOk(optL.get().getAlList().get(i).getApplyOk());
			alDTO.setCdate(optL.get().getAlList().get(i).getCdate());
			alDTO.setTuteeId(optL.get().getAlList().get(i).getTuteeId());
			alList.add(alDTO);
		}

		lDTO.setFlDTO(flList);
		lDTO.setAlDTO(alList);
		lDTO.setTDTO(tDTO);
		lDTO.setUDTO(uDTO);
		return lDTO;
	};

	/**
	 * 수업 등록 및 수정
	 * 
	 * @author moonone
	 * @param dto    사용자가 입력한 등록 및 수정 정보값
	 * @param userId 사용자 아이디
	 * @throws FindException
	 */
	public void addLessonDTO(LessonDTO.selectDetailDTO dto, String userId) throws FindException {
		Optional<Tutor> optT = tRepository.findById(userId);
		Tutor tutor = optT.get();
		Lesson lesson = new Lesson();
		if (dto.getPayLesson() == 0) {
			dto.setPrice(0);
		}
		lesson.setTutor(tutor);

		modelMapper.map(dto, lesson);
		lRepository.save(lesson);
	}

	/**
	 * 튜터가 받은 후기 목록
	 * 
	 * @author moonone
	 * @param lessonSeq 튜터의 수업번호
	 * @return 후기 목록
	 * @throws FindException
	 */
	public List<LessonDTO.selectAllReviewDTO> selectAllReview(Long lessonSeq) throws FindException {

		List<Object[]> lList = lRepository.selectAllReview(lessonSeq);
		List<LessonDTO.selectAllReviewDTO> dto = new ArrayList<>();

		for (int i = 0; i < lList.size(); i++) {

			LessonDTO.selectAllReviewDTO lDto = new LessonDTO.selectAllReviewDTO();
			lDto.setLessonName((String) lList.get(i)[4]);

			TutorDTO.tDTO tDto = new TutorDTO.tDTO();
			UsersDTO.uNameDTO uDto = new UsersDTO.uNameDTO();
			uDto.setName((String) lList.get(i)[5]);
			tDto.setUDTO(uDto);

			List<AppliedLessonDTO.alLessonDTO> alList = new ArrayList<>();
			AppliedLessonDTO.alLessonDTO alDto = new AppliedLessonDTO.alLessonDTO();
			alDto.setTuteeId((String) lList.get(i)[3]);

			LessonReviewDTO.lrALDTO rDto = new LessonReviewDTO.lrALDTO();
			BigDecimal star = (BigDecimal) lList.get(i)[2];
			Integer result = star.intValue();
			rDto.setStar(result);
			rDto.setReview((String) lList.get(i)[1]);
			rDto.setCDate((Date) lList.get(i)[0]);

			alDto.setLrdto(rDto);
			alList.add(alDto);

			lDto.setName(tDto.getUDTO().getName());
			lDto.setAlDTO(alList);

			dto.add(lDto);
		}
		return dto;
	}

	/**
	 * [관리자] 모든 수업 목록
	 * 
	 * @author moonone
	 * @return 수업 목록
	 */
	public List<LessonDTO.allLessonListDTO> allLessonList() {
		List<Object[]> list = lRepository.selectAllLesson();
		List<LessonDTO.allLessonListDTO> lesson = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			LessonDTO.allLessonListDTO dto = new LessonDTO.allLessonListDTO();
			dto.setLessonSeq(((BigDecimal) list.get(i)[0]).longValue());
			dto.setLessonName((String) list.get(i)[2]);
			dto.setCategory(((BigDecimal) list.get(i)[3]).intValue());
			dto.setPayLesson(((BigDecimal) list.get(i)[12]).intValue());
			dto.setPrice(((BigDecimal) list.get(i)[9]).intValue());
			dto.setTutorId((String) list.get(i)[1]);
			lesson.add(dto);
		}
		return lesson;
	}

	/**
	 * 신청날짜가 지나지 않은 수업 전체 목록 (가나다순)
	 * 
	 * @author moonone
	 * @return 수업목록
	 */
	public List<LessonDTO.searchLessonDTO> userLessonList() {
		List<Object[]> list = lRepository.userSelectAllLesson();
		List<LessonDTO.searchLessonDTO> lesson = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			LessonDTO.searchLessonDTO dto = new LessonDTO.searchLessonDTO();
			dto.setLessonSeq(((BigDecimal) list.get(i)[0]).longValue());
			dto.setLessonName((String) list.get(i)[2]);
			dto.setCategory(((BigDecimal) list.get(i)[3]).intValue());
			dto.setApplyStartDate((Date) list.get(i)[10]);
			dto.setApplyEndDate((Date) list.get(i)[11]);
			dto.setStartDate((Date) list.get(i)[7]);
			dto.setEndDate((Date) list.get(i)[8]);
			dto.setImgPath((String) list.get(i)[6]);
			dto.setPrice(((BigDecimal) list.get(i)[9]).intValue());
			lesson.add(dto);
		}
		return lesson;
	}

	/**
	 * 수업 이름 검색
	 * 
	 * @author moonone
	 * @param searchKeyword 검색할 단어
	 * @return 해당하는 값
	 */
	public List<LessonDTO.searchLessonDTO> findByLessonNameContaining(String searchKeyword) {
		List<Object> list = lRepository.findByLessonNameContaining(searchKeyword);
		List<LessonDTO.searchLessonDTO> lesson = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			LessonDTO.searchLessonDTO dto = modelMapper.map(list.get(i), LessonDTO.searchLessonDTO.class);
			lesson.add(dto);
		}
		return lesson;
	}

	/**
	 * 승인 거절된 수업(튜티기준)
	 * 
	 * @author moonone
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<LessonDTO.notYetLessonBytutee> rejectApply(String userId) throws FindException {
		List<Object[]> Llist = lRepository.rejectLesson(userId);
		List<LessonDTO.notYetLessonBytutee> dto = new ArrayList<>();
		for (int i = 0; i < Llist.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			LessonDTO.notYetLessonBytutee lDTO = new LessonDTO.notYetLessonBytutee();
			AppliedLessonDTO.selectAppliedLessonDTO aDTO = new AppliedLessonDTO.selectAppliedLessonDTO();
			uDTO.setUserId((String) userId);
			lDTO.setLessonName((String) Llist.get(i)[0]);
			aDTO.setUsersDTO(uDTO);
			lDTO.setAlDTO(aDTO);
			dto.add(lDTO);
		}
		return dto;
	}

	/**
	 * [메인페이지] 신청종료날짜 임박순으로 list를 출력한다.
	 * 
	 * @author SR
	 * @return List<Object[]>
	 * @throws FindException
	 */
	public List<selectAllBydateLessonDTO> selectAllByDateLesson() throws FindException {
		List<Object[]> lList = lRepository.selectAllBydateLesson();

		List<LessonDTO.selectAllBydateLessonDTO> lListDto = new ArrayList<>();
		for (int i = 0; i < lList.size(); i++) {
			LessonDTO.selectAllBydateLessonDTO lDto = new LessonDTO.selectAllBydateLessonDTO();
			BigDecimal lessonSeq = (BigDecimal) lList.get(i)[0];
			long convertSeq = lessonSeq.longValue();
			lDto.setLessonSeq(convertSeq);
			lDto.setLessonName((String) lList.get(i)[1]);
			lDto.setImgPath((String) lList.get(i)[2]);
			BigDecimal price = (BigDecimal) lList.get(i)[3];
			int convertPrice = price.intValue();
			lDto.setPrice(convertPrice);

			lListDto.add(lDto);
		}
		return lListDto;
	}

	/**
	 * [DS] 관리자 대시보드 최신 생성된 클래스 TOP5
	 * 
	 * @author ds
	 * @return List<LessonDTO.LessonList5DTO>
	 * @throws FindException
	 */
	public List<LessonDTO.LessonList5DTO> selectList5() throws FindException {
		List<Object[]> list = lRepository.selectClassList5();
		List<LessonDTO.LessonList5DTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {

			LessonDTO.LessonList5DTO llDTO = new LessonDTO.LessonList5DTO();
			llDTO.setLessonName((String) list.get(i)[0]);
			llDTO.setCategory(Integer.parseInt(String.valueOf(list.get(i)[1])));
			llDTO.setPeople(Integer.parseInt(String.valueOf(list.get(i)[2])));

			dto.add(llDTO);

		}
		return dto;
	}

}
