package com.developer.lessonreview.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.appliedlesson.entity.AppliedLesson;
import com.developer.appliedlesson.repository.AppliedLessonRepository;
import com.developer.exception.FindException;
import com.developer.lessonreview.dto.LessonReviewDTO;
import com.developer.lessonreview.entity.LessonReview;
import com.developer.lessonreview.repository.LessonReviewRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonReviewService {

	private final LessonReviewRepository lrRepository;
	private final AppliedLessonRepository alRepository;
	private final UsersRepository uRepository;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 튜터의 수업에 대한 후기 추가 및 수정
	 * 
	 * @author moonone
	 * @param lrDTO 작성한 후기
	 * @throws FindException
	 */
	public void addReview(LessonReviewDTO.lrDTO lrDTO, Long applySeq) throws FindException {
		Optional<LessonReview> lr = lrRepository.findById(applySeq);
		LessonReview lessonReview = new LessonReview();
		if (!lr.isPresent()) {
			lessonReview.setApplySeq(applySeq);
			lessonReview.setCDate(lrDTO.getCDate());
			lessonReview.setReview(lrDTO.getReview());
			lessonReview.setStar(lrDTO.getStar());

			Optional<AppliedLesson> al = alRepository.findById(applySeq);
			lessonReview.setAppliedLesson(al.get());
		} else {
			lessonReview.setReview(lrDTO.getReview());
			lessonReview.setStar(lrDTO.getStar());
		}
		lrRepository.save(lessonReview);
	}

	/**
	 * 해당 튜터의 후기 개수
	 * 
	 * @param tutorId 튜티아이디
	 * @return 개수
	 * @throws FindException
	 */
	public Integer cntReview(String tutorId) throws FindException {
		Integer cnt = lrRepository.cntLReview(tutorId);
		logger.info("값1: " + tutorId);
		logger.info("값2: " + cnt);
		if (cnt == null) {
			cnt = 0;
		}
		return cnt;
	}

	/**
	 * 작성한 후기 목록 확인
	 * 
	 * @author moonone
	 * @param userId 사용자아이디
	 * @return 후기목록
	 * @throws FindException
	 */
	public List<LessonReviewDTO.listLRListDTO> lReviewList(String userId) throws FindException {
		List<Object[]> list = lrRepository.listLRList(userId);
		List<LessonReviewDTO.listLRListDTO> result = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			LessonReviewDTO.listLRListDTO dto = new LessonReviewDTO.listLRListDTO();

			dto.setReview((String) (list.get(i)[0]));
			dto.setStar(((BigDecimal) (list.get(i)[1])).intValue());
			dto.setLessonName((String) (list.get(i)[3]));
			Optional<Users> u = uRepository.findById(userId);
			dto.setName(u.get().getName());
			result.add(dto);
		}
		return result;
	}

	/**
	 * 후기를 작성하지 않은 수업 목록 확인
	 * 
	 * @author moonone
	 * @param userId 사용자아이디
	 * @return 수업목록
	 * @throws FindException
	 */
	public List<LessonReviewDTO.noWriteLReviewDTO> noWriteLReview(String userId) throws FindException {
		List<Object[]> list = lrRepository.noWriteLReview(userId);
		List<LessonReviewDTO.noWriteLReviewDTO> result = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			LessonReviewDTO.noWriteLReviewDTO dto = new LessonReviewDTO.noWriteLReviewDTO();
			dto.setApplySeq(((BigDecimal) list.get(i)[0]).longValue());
			dto.setLessonName((String) list.get(i)[1]);
			result.add(dto);
		}
		return result;
	}
}
