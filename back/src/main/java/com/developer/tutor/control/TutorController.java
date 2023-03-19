package com.developer.tutor.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.tutor.dto.TutorDTO;
import com.developer.tutor.service.TutorService;
import com.developer.util.Attach;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("tutor/*")
@RequiredArgsConstructor
public class TutorController {

	private final TutorService tservice;

	/**
	 * 튜터 등록 및 수정
	 * 
	 * @author moonone
	 * @param tDTO
	 * @return 상태값
	 * @throws AddException
	 * @throws FindException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(TutorDTO.saveTutorDTO tDTO, HttpSession session, MultipartFile f)
			throws AddException, FindException {
		String logined = (String) session.getAttribute("logined");
		String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/tutor"; // 각자 주소로!
		File saveDirFile = new File(saveDirectory);
		String fileName;
		if (f != null && f.getSize() > 0) {
			long fSize = f.getSize();
			String fOrigin = f.getOriginalFilename();
			System.out.println("---파일---");
			System.out.println("fSize:" + fSize + ", fOrigin:" + fOrigin);

			// 저장될 파일명에 tutorId값 더하기
			String fName = "tutor_" + logined + "_" + fOrigin;

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

				tDTO.setImgPath(fileName);
				System.out.println("컨트롤단: " + logined);
				tservice.saveTutor(tDTO, logined);
				return new ResponseEntity<>("등록 성공", HttpStatus.OK);
			} catch (IOException e) {
				throw new AddException(e.getMessage());
			}
		}
		return new ResponseEntity<>("오류", HttpStatus.BAD_REQUEST);
	}

	/**
	 * 튜터가 생성한 클래스 목록 + 튜터 정보
	 * 
	 * @author moonone
	 * @param tutorId
	 * @return 튜터가 생성한 클래스 목록 + 튜터 정보
	 * @throws AddException
	 * @throws FindException
	 */
	@GetMapping(value = "{tutorId}")
	public ResponseEntity<?> selectTutorDetail(@PathVariable String tutorId) throws AddException, FindException {
		List<TutorDTO.selectTutorDetailDTO> list = tservice.selectTutorDetail(tutorId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
