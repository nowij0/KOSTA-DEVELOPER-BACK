package com.developer.boardrep.control;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.boardrep.dto.BoardRepDTO;
import com.developer.boardrep.service.BoardRepSerivce;
import com.developer.exception.AddException;
import com.developer.exception.ModifyException;
import com.developer.exception.RemoveException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("boardRep/*")
@RequiredArgsConstructor
public class BoardRepContorller {

	private final BoardRepSerivce BoardRepservice;

	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 댓글 작성
	 * 
	 * @author choigeunhyeong
	 * @param boardRepDTO
	 * @param session
	 * @return
	 * @throws AddException
	 */
	@PostMapping(value = "add/{postSeq}")
	public ResponseEntity<?> addBoardRep(@PathVariable Long postSeq, @RequestBody BoardRepDTO.saveBoardRepDTO boardRepDTO,
			HttpSession session) throws AddException {
		System.out.println("postSeq:" + postSeq);
		String logined = (String) session.getAttribute("logined");
		if (logined == null) { // 로그인 안한 경우
			throw new AddException("로그인하세요");
		}
		BoardRepservice.addBoardRep(boardRepDTO, postSeq, logined);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 댓글 수정
	 * 
	 * @author choigeunhyeong
	 * @param boardRepDTO
	 * @param postRepSeq
	 * @return
	 * @throws ModifyException
	 */
	@PutMapping(value = "{postRepSeq}")
	public ResponseEntity<?> editBoardRep(@RequestBody BoardRepDTO.saveBoardRepDTO boardRepDTO, @PathVariable Long postRepSeq)
			throws ModifyException {

		BoardRepservice.editBoardRep(boardRepDTO, postRepSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 댓글 삭제
	 * 
	 * @author choigeunhyeong
	 * @param postRepSeq
	 * @return
	 * @throws RemoveException
	 */
	@DeleteMapping(value = "{postRepSeq}")
	public ResponseEntity<?> deleteBoardRep(@PathVariable Long postRepSeq) throws RemoveException {
		BoardRepservice.deleteBoardRep(postRepSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}