package com.developer.userreview.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.appliedlesson.dto.AppliedLessonDTO;
import com.developer.appliedlesson.entity.AppliedLesson;
import com.developer.appliedlesson.repository.AppliedLessonRepository;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.repository.LessonRepository;
import com.developer.userreview.dto.UserReviewDTO;
import com.developer.userreview.entity.UserReview;
import com.developer.userreview.repository.UserReviewRepository;
import com.developer.users.dto.UsersDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserReviewService {

	private final UserReviewRepository rRepository;
	private final LessonRepository lRepository;
	private final AppliedLessonRepository alRepository;

	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 클래스에 신청한 튜티의 리뷰목록 불러오기
	 * 
	 * @author Jin
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<UserReviewDTO.getTuteeReview> getTuteeReview(String userId) throws FindException {
		List<Object[]> Rlist = rRepository.getTuteeReview(userId);
		List<UserReviewDTO.getTuteeReview> dto = new ArrayList<>();
		for (int i = 0; i < Rlist.size(); i++) {
			AppliedLessonDTO.selectAppliedLessonDTO aDTO = new AppliedLessonDTO.selectAppliedLessonDTO();
			UserReviewDTO.getTuteeReview rDTO = new UserReviewDTO.getTuteeReview();
			UsersDTO uDTO = new UsersDTO();
			LessonDTO.selectDetailDTO lDTO = new LessonDTO.selectDetailDTO();

			aDTO.setLessonDTO(lDTO);
			uDTO.setUserId((String) userId);
			aDTO.setTuteeId((String) userId);
			uDTO.setName((String) Rlist.get(i)[0]);
			aDTO.setUsersDTO(uDTO);
			rDTO.setAppliedLessonDTO(aDTO);

			rDTO.setReview((String) Rlist.get(i)[1]);

			BigDecimal change = (BigDecimal) Rlist.get(i)[2];
			Integer star = change.intValue();
			rDTO.setStar(star);

			dto.add(rDTO);
		}
		return dto;
	}

	/**
	 * 튜티가 튜터에게 받은후기작성
	 * 
	 * @author choigeunhyeong
	 * @param addReviewDTO
	 * @throws AddException 회원아이디에 해당하는 수업번호를 알아내서 insert 한다
	 */
	@Transactional
	public void addUserReview(UserReviewDTO.addReviewDTO addReviewDTO, Long applySeqRv) throws AddException {

		Optional<AppliedLesson> optA = alRepository.findById(applySeqRv);
		AppliedLesson a = optA.get();
		UserReview userReview = new UserReview();
		userReview.setReview(addReviewDTO.getReview());
		userReview.setStar(addReviewDTO.getStar());
		userReview.setApplySeqRv(addReviewDTO.getApplySeqRv());
		userReview.setAlLesson(a);
		rRepository.save(userReview);
	}
}
