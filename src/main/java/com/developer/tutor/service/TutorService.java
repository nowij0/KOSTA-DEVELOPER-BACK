package com.developer.tutor.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.email.EmailService;
import com.developer.exception.FindException;
import com.developer.lesson.dto.LessonDTO;
import com.developer.tutor.dto.TutorDTO;
import com.developer.tutor.entity.Tutor;
import com.developer.tutor.repository.TutorRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TutorService {

	private final TutorRepository tRepository;
	private final UsersRepository uRepository;
	private final EmailService emailService;	
	private Logger logger = LoggerFactory.getLogger(getClass());
	ModelMapper modelMapper = new ModelMapper();


	/**
	 * 튜터 등록 및 수정
	 * 
	 * @author moonone
	 * @param tDTO
	 * @throws FindException
	 */
	public void saveTutor(TutorDTO.saveTutorDTO tDTO, String logined, String fileName) throws FindException {
		Optional<Tutor> t = tRepository.findById(logined);
		Optional<Users> u = uRepository.findById(logined);
		Tutor tEntity = new Tutor();

		tEntity.setTutorId(logined);
		tEntity.setTutorImg(fileName);
		tEntity.setInfo(tDTO.getInfo());
		tEntity.setUsers(u.get());
		if (!t.isPresent()) {
			tEntity.setApplyOk(0);
			tEntity.setStarAvg(0.0);
		} else {
			tEntity.setApplyOk(tDTO.getApplyOk());
			tEntity.setStarAvg(tDTO.getStarAvg());
		}
		tRepository.save(tEntity);
	}

	/**
	 * 튜터가 생성한 클래스 목록 + 튜터 정보
	 * 
	 * @author moonone
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<TutorDTO.selectTutorDetailDTO> selectTutorDetail(String tutorId) throws FindException {
		List<TutorDTO.selectTutorDetailDTO> tResult = new ArrayList<>();
		TutorDTO.selectTutorDetailDTO tDTO = new TutorDTO.selectTutorDetailDTO();

		List<Object[]> list = tRepository.selectTutorDetail(tutorId);

		Optional<Tutor> t = tRepository.findById(tutorId);
		tDTO.setInfo(t.get().getInfo());
		tDTO.setTutorImg(t.get().getTutorImg());
		tDTO.setStarAvg(t.get().getStarAvg());
		tDTO.setTutorName(t.get().getUsers().getName());
		
		if (list.size() == 0) {
			tDTO.setLesson(null);
		} else {
			List<LessonDTO.onlyLessonDTO> lResult = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				LessonDTO.onlyLessonDTO lDTO = new LessonDTO.onlyLessonDTO();
				lDTO.setLessonSeq(((BigDecimal) list.get(i)[0]).longValue());
				lDTO.setLessonName((String) list.get(i)[1]);
				lDTO.setCategory(((BigDecimal) list.get(i)[2]).intValue());
				lDTO.setContent((String) list.get(i)[3]);
				lDTO.setPeople(((BigDecimal) list.get(i)[4]).intValue());
				lDTO.setLessonImg((String) list.get(i)[5]);
				lDTO.setStart((Date) (list.get(i)[6]));
				lDTO.setEnd(((Date) list.get(i)[7]));
				lDTO.setPrice(((BigDecimal) list.get(i)[8]).intValue());
				lDTO.setApplyStart(((Date) list.get(i)[9]));	
				lDTO.setApplyEnd((Date) list.get(i)[10]);
				lDTO.setLocation((String) list.get(i)[11]);
				lDTO.setPayLesson(((BigDecimal) list.get(i)[16]).intValue());

				lResult.add(lDTO);
			}
			tDTO.setLesson(lResult);
		}
		tResult.add(tDTO);
		return tResult;
	}

	/**
	 * 튜터로 승인한다.(승인메일 포함)
	 * 
	 * @author SR
	 * @param userId
	 * @throws FindException, Exception 
	 */
	public void tutorApply(String tutorId) throws FindException, Exception {
		Optional<Tutor> optT = tRepository.findById(tutorId);
		Optional<Users> optU = uRepository.findById(tutorId);
		
		if (optT.isPresent()) {
			Tutor entityT = optT.get();
			Users entityU = optU.get();
			entityT.setApplyOk(1);
			entityU.setRole(1);
			tRepository.save(entityT);
			uRepository.save(entityU);
			emailService.tutorOk(entityT.getUsers().getEmail());
		} else {
			throw new FindException("해당 ID가 존재하지 않습니다.");
		}
	}

	/**
	 * 튜터승인거절(삭제 및 거절메일 포함)
	 * 
	 * @author SR
	 * @param userId
	 * @throws Exception
	 */
	public void deleteTutor(String userId) throws FindException, Exception {
		Optional<Tutor> optT = tRepository.findById(userId);
		if (optT.isPresent()) {
			Tutor entityT = optT.get();
			emailService.tutorReject(entityT.getUsers().getEmail());
			tRepository.delete(entityT);
		} else {
			throw new FindException("해당 유저가 존재하지 않습니다.");
		}
	}
	
//	/**
//	 * 튜터 조회
//	 * @author Jin
//	 * @param tutorId
//	 * @return
//	 * @throws FindException
//	 */
//	public TutorDTO.tutorDTO getTutor(String tutorId) throws FindException{
//		Optional<Tutor> optT = tRepository.findById(tutorId);
//		Tutor tutor = optT.get();
//		TutorDTO.tutorDTO tutorDTO = modelMapper.map(tutor, TutorDTO.tutorDTO.class);
//		return tutorDTO;
//	}
}
