package com.developer.BoardTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.board.entity.Board;
import com.developer.board.repository.BoardRepository;
import com.developer.boardrep.entity.BoardRep;
import com.developer.boardrep.repository.BoardRepRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

@SpringBootTest
class BoardRepRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private BoardRepRepository brr;
	@Autowired
	private BoardRepository br;
	@Autowired
	private UsersRepository ur;
	
	@Test
	@DisplayName("testCreateBoardRep 테스트")
	void testCreateBoardRep() {
		Optional<Board> optB = br.findById(1L);
		Board board = optB.get();
		
		Optional<Users> optU = ur.findById("test1");
		Users users = optU.get();

		BoardRep boardRep = new BoardRep();
		boardRep.setUsers(users);
		boardRep.setContent("테스트 댓글");
		//boardRep.setUserId("아이디");
		boardRep.setBoard(board);
		
		brr.save(boardRep);
	}	
	
	@Test
	@DisplayName("BoardRep FindById 테스트")
	void testBoardRepFindById() {
		BoardRep br = new BoardRep();
		Optional<BoardRep> optB = brr.findById(1L);
		assertTrue(optB.isPresent());
		String expectedId = "아이디1";
	//	assertEquals(expectedId, optB.get().getUserId());
	}
	
//	
//	@Test
//	@DisplayName("BoardRep update 테스트")
//	void testBoardRepUpdate() {
//			BoardRep brp = new BoardRep();
//			brp.setPostRepSeq(2L);
//			
//			Optional<Board> optB = br.findById(5L);
//			Board board = optB.get();
//			
//			Optional<Users> optU = ur.findById("아이디3");
//			Users users = optU.get();
//			
//			brp.setBoard(board);
//			brp.setContent("boardrep수정테스트");
//			brp.setUsers(users);
//			//br.setCDate(null);
//			brr.save(brp);
//		
//	}
//	
	@Test
	@DisplayName("BoardRep delete 테스트")
	void testBoardRepDelete() {
		Long a = 2L;
		brr.deleteById(a);
	}


}
