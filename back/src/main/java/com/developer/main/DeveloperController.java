package com.developer.main;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.board.dto.BoardDTO;
import com.developer.board.service.BoardService;
import com.developer.exception.FindException;
import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.service.LessonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("main/*")
public class DeveloperController {

	private final BoardService bService;
	private final LessonService lservice;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * [메인페이지] 커뮤니티 + 수업 출력
	 * 
	 * @author SR
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> mainPageList() throws FindException {

		DeveloperDTO.MainPageDTO dto = new DeveloperDTO.MainPageDTO();

		List<BoardDTO.selectAllBydateBoardDTO> bList = bService.listByDate();
		List<LessonDTO.selectAllBydateLessonDTO> lList = lservice.selectAllByDateLesson();

		dto.setListBoard(bList);
		dto.setListLesson(lList);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * 첨부파일 다운로드 한다
	 * 
	 * @param boardNum 글번호
	 * @param type     첨부파일인 경우 1, 섬네일파일인 경우 2
	 * @param opt      다운로드 방식: 무조건 다운로드인 경우 "attachment", 실행경우 "inline"
	 * @return 파일
	 * @throws FindException
	 */
	@GetMapping("download")
	public ResponseEntity<?> download(String boardNum, int type) throws FindException {

//		String saveDirectory = "C:\\dev\\lesson";
		String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/lesson";

		String fileName = "";
		if (type == 2) {
			fileName = "t_";
		}
		fileName += boardNum; // 글번호
		File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
		File file = null;

		for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들

			String fn = f.getName();
			int lastIndex = fn.lastIndexOf(".");

			if (fn.substring(0, lastIndex).equals(fileName)) {
				file = f;
				fileName = f.getName();
				break;
			}
		}
		if (file == null) {
			throw new FindException(fileName + "으로 된 파일이 없습니다");
		}

		try {
			HttpHeaders headers = new HttpHeaders();
			String contentType = Files.probeContentType(file.toPath()); // 파일의 형식
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);// 응답형식
			headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length()); // 응답크기

			headers.add(HttpHeaders.CONTENT_DISPOSITION,
					"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답

			byte[] bArr = FileCopyUtils.copyToByteArray(file);
			ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		}
	}

}