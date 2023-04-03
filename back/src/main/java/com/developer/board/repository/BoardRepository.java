package com.developer.board.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	/**
	 * 게시글 하나 상세페이지
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @return
	 */
	@Query(value = "select users.nickname, board.board_seq, board.category, board.title, board.board_content,"
			+ "		board.board_img, board.board_date, board.recommend_cnt, board.hit"
			+ "		from users inner join board on users.user_id = board.user_id"
			+ "		where board_seq=:postSeq", nativeQuery = true)
	public List<Object[]> detailBoard(@Param("postSeq") Long postSeq);

	/**
	 * 제목으로 검색
	 * 
	 * @author choigeunhyeong
	 * @param title
	 * @return
	 */
	@Query(value = "SELECT * " + "FROM ( SELECT rownum rn, a. * " + "		FROM ( "
			+ "            SELECT  users.nickname, board.board_seq, board.category, board.title, board.board_content, "
			+ "    board.board_img, board.board_date, board.recommend_cnt, board.hit "
			+ "            FROM users inner join board on users.user_id = board.user_id "
			+ " 		   where board.title like %:title% " + "            ORDER BY c_date  desc" + "      ) a) "
			+ "	WHERE rn BETWEEN :startRow AND :endRow", nativeQuery = true)
	public List<Object[]> findByBoardTitle(@Param("startRow") int startRow, @Param("endRow") int endRow,
			@Param("title") String title);

	public List<Board> findByTitleLike(String title);

	/**
	 * 게시글 글번호로 상세검색 (게시글 클릭시 들어갈 상세페이지) 닉네임+글 상세정보+댓글 상세정보
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @return
	 */
	@Query(value = "SELECT b.*, r.* " + "FROM (" + "  SELECT b.user_id AS b_user_id, u.nickname AS u_nickname,"
			+ "       b.board_seq AS board_post_seq, b.category, b.title, b.content AS bContent, b.board_img, b.c_date, b.recommend_cnt,  b.hit "
			+ "  FROM board b JOIN users u on b.user_id = u.user_id" + "  WHERE b.board_seq = :postSeq) b "
			+ "LEFT JOIN (" + "  SELECT "
			+ "    r.board_rep_seq AS board_rep_post_seq, r.user_id AS r_user_id, r.reply_content AS rContent, r.board_rep_date, "
			+ "    ru.nickname AS ru_nickname, r.post_rep_seq"
			+ "  FROM board_rep r JOIN users ru ON r.user_id = ru.user_id " + "  WHERE r.board_rep_date = :postSeq"
			+ ") r ON b.board_post_seq = r.board_rep_post_seq", nativeQuery = true)
	public List<Object[]> findPostSeq(@Param("postSeq") Long postSeq);

	/**
	 * 게시글목록 작성일 순으로 정렬해서 출력
	 * 
	 * @author choigeunhyeong
	 * @param pageable
	 * @return
	 */

	@Query(value = "SELECT * " + "FROM ( SELECT rownum rn, a. * " + "		FROM ("
			+ "            SELECT  users.nickname, board.board_seq, board.category, board.title, board.board_content,"
			+ "    board.board_img, board.board_date, board.recommend_cnt, board.hit "
			+ "            FROM users inner join board on users.user_id = board.user_id "
			+ "            ORDER BY board_date desc " + "      ) a) "
			+ "	WHERE rn BETWEEN :startRow AND :endRow", nativeQuery = true)
	public List<Object[]> listBoard(@Param("startRow") int startRow,
			@Param("endRow") int endRow);

	/**
	 * 게시글목록 조회수높은 순으로 정렬해서 출력
	 * 
	 * @author choigeunhyeong
	 * @return
	 */
	@Query(value = "SELECT * FROM ( SELECT rownum rn, a. * 	FROM (\n"
			+ "			           SELECT  users.nickname, board.board_seq, board.category, board.title, board.board_content,\n"
			+ "			    board.board_img, board.board_date, board.recommend_cnt, board.hit \n"
			+ "			            FROM users inner join board on users.user_id = board.user_id \n"
			+ "			            ORDER BY cnt desc ) a) \n"
			+ "				WHERE rn BETWEEN :startRow AND :endRow", nativeQuery = true)
	public List<Object[]> getBoardByCnt( @Param("startRow") int startRow,
			@Param("endRow") int endRow);

//	@Query(value = "select users.nickname, board.post_seq, board.category, board.title, board.content,"
//			+ "		board.img_path, board.c_date, board.recommend, board.cnt" + "		from users"
//			+ "		inner join board" + "		on users.user_id = board.user_id"
//			+ "		order by cnt desc", nativeQuery = true)
//	public List<Object[]> getBoardByCnt( @Param("startRow") int startRow,
//			@Param("endRow") int endRow);
	

	/**
	 * 게시글목록 추천많은순 순으로 정렬해서 출력
	 * 
	 * @author choigeunhyeong
	 * @return
	 */
	@Query(value = "select users.nickname, board.board_seq, board.category, board.title, board.board_content,"
			+ "		board.board_img, board.board_date, board.recommend_cnt, board.hit" + "from users"
			+ "		inner join board" + "		on users.user_id = board.user_id"
			+ "		order by recommend_cnt desc", nativeQuery = true)
	public List<Object[]> getBoardByRecommend();

	/**
	 * 게시글 조회수 증가
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 */
	@Query(value = "update board set hit =" + "		hit+1 where board_seq= :postSeq", nativeQuery = true)
	public Board updateCnt(@Param("postSeq") Long postSeq);

	// [SR]메인페이지 - 글작성 최신순으로 list 출력
	@Query(value = "SELECT *"
			+ "FROM (SELECT b.board_seq, u.nickname, b.category, b.title, b.board_content, b.board_img, b.board_date, b.recommend_cnt, b.hit "
			+ "      FROM users u INNER JOIN board b ON u.user_id = b.user_id " + " ORDER BY board_date DESC)"
			+ "WHERE rownum BETWEEN 1 AND 6", nativeQuery = true)
	public List<Object[]> selectAllByDate();

}