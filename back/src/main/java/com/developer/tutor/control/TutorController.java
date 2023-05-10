package com.developer.tutor.control;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.tutor.dto.TutorDTO;
import com.developer.tutor.service.TutorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tutor/*")
@RequiredArgsConstructor
public class TutorController {

	private final TutorService tservice;
	private final AmazonS3Client amazonS3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 * 	튜터 등록 및 수정
	 * 
	 * @param tDTO
	 * @param session
	 * @param f
	 * @return 성공/실패 여부
	 * @throws AddException
	 * @throws FindException
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(TutorDTO.saveTutorDTO tDTO, HttpSession session, MultipartFile f)
			throws AddException, FindException {
		String logined = (String) session.getAttribute("logined");

		UUID uuid = UUID.randomUUID();
		String fileName = uuid.toString() + "_" + f.getOriginalFilename();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(f.getContentType());
		metadata.setContentLength(f.getSize());
		try {
			amazonS3Client.putObject(bucket + "/file-upload", fileName, f.getInputStream(), metadata);
			tservice.saveTutor(tDTO, logined, fileName);
			return new ResponseEntity<>(200, HttpStatus.OK);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
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
