//package com.developer.BoardTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.developer.board.dto.BoardDTO;
//import com.developer.board.entity.Board;
//import com.developer.board.repository.BoardRepository;
//import com.developer.users.entity.Users;
//import com.developer.users.repository.UsersRepository;
//
//@SpringBootTest
//class BoardRepositoryTest {
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private BoardRepository br;
//	@Autowired
//	private UsersRepository ur;
//
//	@Test
//	@DisplayName("Board Save 테스트")
//	void testBoardSave() {
//		for(int i=1; i<=5; i++) {
//			Board b = new Board();
//			Optional<Users> optB = ur.findById("test1");
//			Users u = optB.get();
//			b.setUsers(u);
//		//	b.setPostSeq(null);
//			b.setCategory(i);
//			b.setTitle("JPA테스트제목"+i);
//			b.setContent("JPA테스트내용"+i);
//			b.setImgPath("이미지"+i);
//		//	b.setCDate(null);
//			b.setRecommend(i);
//			b.setCnt(i);
//			br.save(b);
//		}
//	}
//	
//	@Test
//	@DisplayName("Board FindById 테스트")
//	void testBoardFindById() {
//		Board b = new Board();
//		Optional<Board> optB = br.findById(1L);
//		assertTrue(optB.isPresent());
//		String expectedId = "아이디1";
//		String testId = optB.get().getUsers().getUserId();
//		assertEquals(expectedId, testId);
//	}
//	
//	@Test
//	@DisplayName("Board update 테스트")
//	void testBoardUpdate() {
//		
//			Board b = new Board();
//			b.setPostSeq(13L);
//	//		b.setUserId("아이디1");
//			Optional<Users> optB = ur.findById("아이디3");
//			Users u = optB.get();
//			b.setUsers(u);
//			b.setTitle("JPA테스트제목수정");
//			b.setContent("JPA테스트내용수정");
//			b.setRecommend(6);
//			b.setCnt(3);
//			br.save(b);	
//	}
//	
//	@Test
//	@DisplayName("Board delete 테스트")
//	void testBoardDelete() {
//		Long postSeq = 3L;
//		br.deleteById(postSeq);
//	}
//	
////	@Test
////	void testFindTest1() {
////		List<Board> list = br.findTest1();
////		for(int i=0; i<list.size(); i++) {
////			logger.error("게시글: " + list);
////		};
////	}
////	
////	@Test
////	@DisplayName("Board list 테스트")
////	void findFindTest2() {
////	List<Object[]> list = br.findTest2();
////	logger.error("첫번째글번호: " + list.get(0)[0]);
////	logger.error("첫번째내용: " + list.get(0)[1]);
////	
////	logger.error("두번째글번호: " + list.get(1)[0]);
////	logger.error("두번쨰내용: " + list.get(1)[1]);
////	}
//	
//	@Test
//	@DisplayName("Board 제목검색 테스트")
//	void findByTitle() {
//		List<Board> list = br.findByTitleLike("%수정%");
//		for(int i=0; i<list.size(); i++) {
//			logger.info("검색내용: "+list.get(i).toString());
//			
//		};
//	}
//	
//	@Test
//	@DisplayName("닉네임+글상세+댓글 PostSeq로 검색 테스트")
//	void findPostSeq() {
//		List<Object[]> list = br.findPostSeq(1L);
//		logger.info("-----------------------");
//		logger.info("글번호 : "+list.get(0).toString());
////		logger.info("닉네임 : "+map.get("nickname"));
////		logger.info("카테고리: " + map.get("category"));
////		logger.info("제목: " + map.get("title"));
////		logger.info("내용: " + map.get("content"));
////		logger.info("이미지: " + map.get("imt_path"));
////		logger.info("작성일: " + map.get("c_date"));
////		logger.info("추천수: " + map.get("recommend"));
////		logger.info("조회수: " + map.get("cnt"));
//		logger.info("-----------------------");
//	//	logger.info("댓글내용: " + list.get(0).getBoardRepDTO().getContent());
////		logger.info("댓글작성일: " + map.get("cdate"));
//		logger.info("-----------------------");
//	}
//	
////	@Test
////	@DisplayName("전체 리스트(작성일순) 테스트")
////	void findAllBoardc_dateTest() {
////		List<Map<String,Object>> list = br.findAllBoardc_date();
////		for(int i=0; i<list.size(); i++) {
////			logger.info("-----------------------");
////			logger.info("글번호: " + list.get(i).get("post_seq"));
////			logger.info("닉네임: " + list.get(i).get("nickname"));
////			logger.info("카테고리: " + list.get(i).get("category"));
////			logger.info("제목: " + list.get(i).get("title"));
////			logger.info("내용: " + list.get(i).get("content"));
////			logger.info("이미지: " + list.get(i).get("imt_path"));
////			logger.info("작성일: " + list.get(i).get("c_date"));
////			logger.info("추천수: " + list.get(i).get("recommend"));
////			logger.info("조회수: " + list.get(i).get("cnt"));
////			logger.info("-----------------------");
////		};
////	}
////	
////	@Test
////	@DisplayName("전체 리스트(좋아요순) 테스트")
////	void findAllBoardCntTest() {
////		List<Map<String,Object>> list = br.findAllBoardCnt();
////		for(int i=0; i<list.size(); i++) {
////			logger.info("-----------------------");
////			logger.info("글번호: " + list.get(i).get("post_seq"));
////			logger.info("닉네임: " + list.get(i).get("nickname"));
////			logger.info("카테고리: " + list.get(i).get("category"));
////			logger.info("제목: " + list.get(i).get("title"));
////			logger.info("내용: " + list.get(i).get("content"));
////			logger.info("이미지: " + list.get(i).get("imt_path"));
////			logger.info("작성일: " + list.get(i).get("c_date"));
////			logger.info("추천수: " + list.get(i).get("recommend"));
////			logger.info("조회수: " + list.get(i).get("cnt"));
////			logger.info("-----------------------");
////		};
////	}
////	
////	@Test
////	@DisplayName("전체 리스트(추천많은순) 테스트")
////	void findAllBoardRecommendTest() {
////		
////		List<Map<String,Object>> list = br.findAllBoardRecommend();
////		for(int i=0; i<list.size(); i++) {
////			logger.info("-----------------------");
////			logger.info("글번호: " + list.get(i).get("post_seq"));
////			logger.info("닉네임: " + list.get(i).get("nickname"));
////			logger.info("카테고리: " + list.get(i).get("category"));
////			logger.info("제목: " + list.get(i).get("title"));
////			logger.info("내용: " + list.get(i).get("content"));
////			logger.info("이미지: " + list.get(i).get("imt_path"));
////			logger.info("작성일: " + list.get(i).get("c_date"));
////			logger.info("추천수: " + list.get(i).get("recommend"));
////			logger.info("조회수: " + list.get(i).get("cnt"));
////			logger.info("-----------------------");
////		};
////	}
//	
//	//이거 안되뮤.
//	@Test
//	@DisplayName("게시글 조회수 증가 테스트")
//	void updateCnt() {
//		Optional<Board> b = br.findById(1L);
//		br.updateCnt(1L);
//	}
//}
