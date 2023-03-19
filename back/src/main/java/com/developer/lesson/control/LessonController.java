package com.developer.lesson.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.appliedlesson.service.AppliedLessonService;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.favoriteslesson.service.FavoritesLessonService;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.dto.LessonDTO.lessonDetailDTO;
import com.developer.lesson.dto.LessonDTO.selectDetailDTO;
import com.developer.lesson.service.LessonService;
import com.developer.lessonreview.service.LessonReviewService;
import com.developer.util.Attach;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("lesson/*")
@RequiredArgsConstructor
public class LessonController {

	private final LessonService lservice;
	private final AppliedLessonService alService;
	private final FavoritesLessonService flService;
	private final LessonReviewService lrservice;

	/**
	 * 수업 등록 및 수정
	 * 
	 * @author moonone
	 * @param dto    사용자가 입력한 등록 및 수정 정보값
	 * @param userId 사용자 아이디
	 * @throws FindException
	 */
	@PostMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
	public ResponseEntity<?> add(LessonDTO.selectDetailDTO dto, HttpSession session, MultipartFile f)
			throws AddException, FindException {

		String userId = (String) session.getAttribute("logined");
		String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/lesson"; // 각자 주소로!
		File saveDirFile = new File(saveDirectory);
		String fileName;
		if (f != null && f.getSize() > 0) {
			long fSize = f.getSize();
			String fOrigin = f.getOriginalFilename();
			System.out.println("---파일---");
			System.out.println("fSize:" + fSize + ", fOrigin:" + fOrigin);

			// 저장될 파일명에 tutorId값 더하기
			String fName = "lesson_" + userId + "_" + fOrigin;

			// 파일저장
			fileName = fName;
			File file = new File(saveDirFile, fileName);

			try {
				Attach.upload(f.getBytes(), file);

				int width = 300;
				int height = 300;

				// 원래 첨부파일과 구분짓기 위해
				String thumbFileName = "t_" + fileName;
				File thumbFile = new File(saveDirFile, thumbFileName);
				FileOutputStream thumbnailOs = new FileOutputStream(thumbFile);
				InputStream thumbnailsS = f.getInputStream();

				Thumbnailator.createThumbnail(thumbnailsS, thumbnailOs, width, height);

				dto.setImgPath(fileName);
				lservice.addLessonDTO(dto, userId);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (IOException e) {
				throw new AddException(e.getMessage());
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	

	/**
	 * 튜터가 받은 후기 목록
	 * 
	 * @author moonone
	 * @param lessonSeq 튜터의 수업번호
	 * @return 후기 목록
	 * @throws FindException
	 */
	@GetMapping(value = "review/{lessonSeq}")
	public ResponseEntity<?> review(@PathVariable Long lessonSeq) throws FindException {
		List<LessonDTO.selectAllReviewDTO> list = lservice.selectAllReview(lessonSeq);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 선택한 클래스에 대한 상세 정보
	 * 
	 * @author moonone
	 * @param lessonSeq 수업번호
	 * @return 상세정보
	 * @throws FindException
	 */
	@GetMapping(value = { "detail/{lessonSeq}" }, produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
	public ResponseEntity<LessonDTO.lessonDetailDTO> detail(@PathVariable Long lessonSeq) throws FindException {
		LessonDTO.lessonDetailDTO result = new lessonDetailDTO();

		selectDetailDTO lessonDto = lservice.selectDetail(lessonSeq);

		Integer cnt = lrservice.cntReview(lessonDto.getTDTO().getTutorId());
		result.setCnt(cnt);

		result.setLessonDto(lessonDto);
		return new ResponseEntity<LessonDTO.lessonDetailDTO>(result, HttpStatus.OK);
	}

	/**
	 * 수업 이름 검색
	 * 
	 * @author moonone
	 * @param searchKeyword 검색할 단어
	 * @return 해당하는 값
	 */
	@GetMapping("/search")
	public ResponseEntity<?> searchLesson(@RequestParam Map<String, String> param) throws FindException {
		String searchWord = param.get("searchWord");
		List<LessonDTO.searchLessonDTO> list = lservice.findByLessonNameContaining(searchWord);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 신청날짜가 지나지 않은 수업 전체 목록 (가나다순)
	 * 
	 * @author moonone
	 * @return 수업 전체 목록
	 * @throws FindException
	 */
	@GetMapping
	public ResponseEntity<?> userAllLesson() throws FindException {
		List<LessonDTO.searchLessonDTO> list = lservice.userLessonList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 수업 신청
	 * 
	 * @author moonone
	 * @param session
	 * @return
	 * @throws AddException
	 * @throws FindException
	 */
	@GetMapping(value = "apply/{lessonSeq}")
	public ResponseEntity<?> applyLesson(HttpSession session, @PathVariable Long lessonSeq)
			throws AddException, FindException {
		String logined = (String) session.getAttribute("logined");
		alService.applyLesson(lessonSeq, logined);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 수업즐겨찾기 추가
	 * 
	 * @author moonone
	 * @param flDTO     수업즐겨찾기
	 * @param lessonSeq 수업번호
	 * @throws AddException
	 */
	@PostMapping(value = "favoriteslesson/{lessonSeq}", produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
	public ResponseEntity<?> add(HttpSession session, @PathVariable Long lessonSeq)
			throws AddException, FindException, JsonMappingException, JsonProcessingException {
		String userId = (String) session.getAttribute("logined");

		flService.addFavLesson(lessonSeq, userId);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	/**
	 * 수업 즐겨찾기 삭제
	 * 
	 * @author moonone
	 * @param favLesSeq 수업즐겨찾기SEQ
	 * @throws RemoveException
	 */
	@DeleteMapping(value = "favoriteslesson/{favLesSeq}")
	public ResponseEntity<?> del(@PathVariable Long favLesSeq) throws RemoveException, FindException {
		flService.delFavLesson(favLesSeq);
		return new ResponseEntity<>("즐겨찾기 삭제됨", HttpStatus.OK);
	}
}
