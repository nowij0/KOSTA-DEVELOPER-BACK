package com.developer.favoriteslesson.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.favoriteslesson.dto.FavoritesLessonDTO;
import com.developer.favoriteslesson.entity.FavoritesLesson;
import com.developer.favoriteslesson.repository.FavoritesLessonRepository;
import com.developer.lesson.entity.Lesson;
import com.developer.lesson.repository.LessonRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final로 선언되지 않은 변수와 @NonNull 어노테이션을 선언한 변수에 대해 생성자 생성
public class FavoritesLessonService {

	private final FavoritesLessonRepository flRepository;
	private final LessonRepository lRepository;
	private final UsersRepository uRepository;

	/**
	 * 나의 수업 즐겨찾기 목록 확인
	 * 
	 * @author moonone
	 * @param userId 사용자아이디
	 * @return 즐겨찾기목록
	 * @throws FindException
	 */
	public List<FavoritesLessonDTO.flListDTO> listFavLesson(String userId) throws FindException {
		List<Object[]> flList = flRepository.listFavLesson(userId);
		List<FavoritesLessonDTO.flListDTO> flDTOList = new ArrayList<>();
		for (int i = 0; i < flList.size(); i++) {
			FavoritesLessonDTO.flListDTO flDTO = new FavoritesLessonDTO.flListDTO();
			flDTO.setTuteeId(userId);

			Long lessonSeq = ((BigDecimal) flList.get(i)[2]).longValue();
			Optional<Lesson> l = lRepository.findById(lessonSeq);
			flDTO.setLessonName(l.get().getLessonName());
			flDTO.setCategory(l.get().getCategory());
			flDTO.setLocation(l.get().getLocation());
			flDTO.setApplyEnd(l.get().getApplyEnd());
			Long favLesSeq = ((BigDecimal) flList.get(i)[0]).longValue();
			flDTO.setFavLesSeq(favLesSeq);
			flDTOList.add(flDTO);
		}
		return flDTOList;
	}

	/**
	 * 수업즐겨찾기 추가
	 * 
	 * @author moonone
	 * @param flDTO     수업즐겨찾기
	 * @param lessonSeq 수업번호
	 * @throws AddException
	 */
	public void addFavLesson(Long lessonSeq, String userId) throws AddException {
		Optional<Lesson> l = lRepository.findById(lessonSeq);
		Optional<Users> u = uRepository.findByUserId(userId);
		FavoritesLesson flEntity = new FavoritesLesson();
		flEntity.setLesson(l.get());
		flEntity.setUsers(u.get());
		flRepository.save(flEntity);
	}

	/**
	 * 수업 즐겨찾기 삭제
	 * 
	 * @author moonone
	 * @param favLesSeq 수업즐겨찾기SEQ
	 * @throws RemoveException
	 */
	public void delFavLesson(Long favLesSeq) throws RemoveException {
		flRepository.deleteById(favLesSeq);
	}

}
