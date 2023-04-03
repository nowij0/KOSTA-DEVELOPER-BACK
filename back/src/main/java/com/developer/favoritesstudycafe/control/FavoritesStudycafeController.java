package com.developer.favoritesstudycafe.control;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.favoritesstudycafe.dto.FavoritesStudycafeDTO;
import com.developer.favoritesstudycafe.service.FavoritesStudycafeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("favoritesstudyroom/*")
@RequiredArgsConstructor
public class FavoritesStudycafeController {

	private final FavoritesStudycafeService fsService;

	/**
	 * [스터디카페 상세페이지]즐겨찾기 추가기능
	 * 
	 * @author ds
	 * @param
	 * @throws AddException
	 */
	@PostMapping(value = "add/{srSeq}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addFavStudyroom(@PathVariable Long srSeq, HttpSession session) throws AddException {
		String logined = (String) session.getAttribute("logined");
		if (logined != null) {
			fsService.insertFVstudyroom(srSeq, logined);

		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [스터디카페 상세페이지]즐겨찾기 삭제기능
	 * 
	 * @author ds
	 * @param
	 * @throws RemoveException
	 */
	@DeleteMapping(value = "{srSeq}")
	public ResponseEntity<?> deleteFavStudyroom(@PathVariable Long srSeq, HttpSession session) throws RemoveException {
		String logined = (String) session.getAttribute("logined");
		if (logined != null) {
			fsService.deleteFvstudyroom(srSeq, logined);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * [스터디카페 상세페이지]즐겨찾기 상태여부 확인기능
	 * 
	 * @author ds
	 * @param session
	 * @return
	 * @throws FindException 객체 1개 받는 컨트롤러/ 에러때문에 리스트로 변환해서 받음.
	 */
	@GetMapping(value = "check")
	public ResponseEntity<?> checkFavStudyroom(HttpSession session) throws FindException {
		String logined = (String) session.getAttribute("logined");
		if (logined != null) {
			List<FavoritesStudycafeDTO.favStudycafeCafeseqDTO> list = fsService.getSrSeqAndFavSeq(logined);
			if (list.size() > 0) {

				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.LENGTH_REQUIRED);
			}

		} else {
			return new ResponseEntity<>("로그인이 안된 상태입니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}