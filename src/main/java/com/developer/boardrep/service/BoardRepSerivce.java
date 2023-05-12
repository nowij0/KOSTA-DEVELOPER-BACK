package com.developer.boardrep.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.board.entity.Board;
import com.developer.board.repository.BoardRepository;
import com.developer.boardrep.dto.BoardRepDTO;
import com.developer.boardrep.entity.BoardRep;
import com.developer.boardrep.repository.BoardRepRepository;
import com.developer.exception.AddException;
import com.developer.exception.ModifyException;
import com.developer.exception.RemoveException;
import com.developer.users.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardRepSerivce {

	private final BoardRepRepository BoardRepRepository;
	private final BoardRepository BoardRepository;

	/**
	 * 게시판 댓글 작성
	 * 
	 * @author choigeunhyeong
	 * @param boardRepDTO
	 * @param postSeq
	 * @param logined
	 * @throws AddException
	 */
	@Transactional
	public void addBoardRep(BoardRepDTO.saveBoardRepDTO boardRepDTO, Long postSeq, String logined) throws AddException {
		BoardRep boardRep = new BoardRep();
		Users writer = new Users();
		Optional<Board> optB = BoardRepository.findById(postSeq);
		boardRep.setBoard(optB.get());
		writer.setUserId(logined);
		boardRep.setReplyContent(boardRepDTO.getContent());
		boardRep.setUsers(writer);
		BoardRepRepository.save(boardRep);
	}

	/**
	 * 게시판 댓글 수정
	 * 
	 * @author choigeunhyeong
	 * @param boardRepDTO
	 * @param postRepSeq
	 * @throws ModifyException
	 */
	@Transactional
	public void editBoardRep(BoardRepDTO.saveBoardRepDTO boardRepDTO, Long postRepSeq) throws ModifyException {

		Optional<BoardRep> optBR = BoardRepRepository.findById(postRepSeq);
		if (optBR.isPresent()) {
			BoardRep br = optBR.get();
			br.setReplyContent(boardRepDTO.getContent());
			BoardRepRepository.save(br);
		} else {
			throw new ModifyException("정상적인 수정이 되지 않았습니다.");
		}
	}

	/**
	 * 게시판 댓글 삭제
	 * 
	 * @author choigeunhyeong
	 * @param postRepSeq
	 * @throws RemoveException
	 */
	public void deleteBoardRep(Long postRepSeq) throws RemoveException {
		Optional<BoardRep> optBR = BoardRepRepository.findById(postRepSeq);
		if (optBR.isPresent()) {
			BoardRepRepository.deleteById(postRepSeq);
		} else {
			throw new RemoveException("정상적인 삭제가 되지 않았습니다.");
		}
	}
}
