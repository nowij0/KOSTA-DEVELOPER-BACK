package com.developer.board.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.board.dto.BoardDTO;
import com.developer.board.dto.BoardDTO.getBoardByBoardTypeDTO;
import com.developer.board.dto.PageBean;
import com.developer.board.entity.Board;
import com.developer.board.repository.BoardRepository;
import com.developer.boardrep.dto.BoardRepDTO;
import com.developer.boardrep.dto.BoardRepDTO.BoardRepSelectDTO;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.ModifyException;
import com.developer.exception.RemoveException;
import com.developer.users.dto.UsersDTO;
import com.developer.users.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository bRepository;

	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 게시글 작성
	 * 
	 * @author choigeunhyeong
	 * @param board
	 * @throws AddException
	 */
	@Transactional
	public void addBoard(BoardDTO.saveBoardDTO saveBoardDTO, String logined) throws AddException {
		Board board = new Board();
		Users writer = new Users();
		writer.setUserId(logined);
		board.setTitle(saveBoardDTO.getTitle());
		board.setBoardContent(saveBoardDTO.getBoardContent());
		board.setBoardImg(saveBoardDTO.getBoardImg());
		board.setUsers(writer);
		bRepository.save(board);

	}

	/**
	 * 게시글 전체 목록(작성일순),페이징처리
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 *
	 */
	public PageBean<BoardDTO> listBoard(int currentPage) throws FindException {
		int startRow = (currentPage - 1) * PageBean.CNT_PER_PAGE + 1;
		int endRow = currentPage * PageBean.CNT_PER_PAGE;
		List<Object[]> list = bRepository.listBoard(startRow, endRow);
		List<BoardDTO> boardList = new ArrayList<>();
		list.forEach(arr -> {
			BoardDTO dto = new BoardDTO();

			String nickName = (String) arr[1];
			BigDecimal post_seq = (BigDecimal) arr[2];
			BigDecimal category = (BigDecimal) arr[3];
			String title = (String) arr[4];
			String content = (String) arr[5];
			String imgPath = (String) arr[6];
			Date cDate = (Date) arr[7];
			BigDecimal recommend = (BigDecimal) arr[8];
			BigDecimal cnt = (BigDecimal) arr[9];

			UsersDTO users = new UsersDTO();
			users.setNickname(nickName);
			dto.setUsersDTO(users);
			dto.setBoardSeq(post_seq.longValue());
			dto.setCategory((int) (category.longValue()));
			dto.setTitle(title);
			dto.setBoardContent(content);
			dto.setBoardImg(imgPath);
			dto.setBoardDate(cDate);
			dto.setLike((int) (recommend.longValue()));
			dto.setHit((int) (cnt.longValue()));

			boardList.add(dto);
		});
		int totalCnt = (int) bRepository.count();// 총상품수
		PageBean<BoardDTO> pb = new PageBean(currentPage, boardList, totalCnt);
		return pb;

	}

	/**
	 * 게시글 전체 목록(추천순)
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 */
	public List<BoardDTO.getBoardByBoardTypeDTO> listBoardByRecommend() throws FindException {
		List<Object[]> Blist = bRepository.getBoardByRecommend();
		List<BoardDTO.getBoardByBoardTypeDTO> dtoList = new ArrayList<>();
		for (int i = 0; i < Blist.size(); i++) {
			BoardDTO.getBoardByBoardTypeDTO bDTO = new BoardDTO.getBoardByBoardTypeDTO();
			BigDecimal post_seq = (BigDecimal) Blist.get(i)[1];
			Long resultPost_seq = post_seq.longValue();
			bDTO.setBoardSeq(resultPost_seq);

			BigDecimal Category = (BigDecimal) Blist.get(i)[2];
			int resultCategory = Category.intValue();
			bDTO.setCategory(resultCategory);

			bDTO.setTitle((String) Blist.get(i)[3]);
			bDTO.setBoardContent((String) Blist.get(i)[4]);
			bDTO.setBoardImg((String) Blist.get(i)[5]);
			bDTO.setBoardDate((Date) Blist.get(i)[6]);

			BigDecimal Recommend = (BigDecimal) Blist.get(i)[7];
			int resultRec = Recommend.intValue();
			bDTO.setLike(resultRec);

			BigDecimal Cnt = (BigDecimal) Blist.get(i)[8];
			int resultCnt = Cnt.intValue();
			bDTO.setHit(resultCnt);

			UsersDTO.UsersNameDTO uDTO = new UsersDTO.UsersNameDTO();
			uDTO.setNickname((String) Blist.get(i)[0]);
			bDTO.setUsersNameDTO(uDTO);
			dtoList.add(bDTO);
		}
		return dtoList;

	}

	
//	public List<BoardDTO.getBoardByBoardTypeDTO> listBoardByCnt() throws FindException {
//		List<Object[]> Blist = bRepository.getBoardByCnt();
//		List<BoardDTO.getBoardByBoardTypeDTO> dtoList = new ArrayList<>();
//		for (int i = 0; i < Blist.size(); i++) {
//			BoardDTO.getBoardByBoardTypeDTO bDTO = new BoardDTO.getBoardByBoardTypeDTO();
//			BigDecimal post_seq = (BigDecimal) Blist.get(i)[1];
//			Long resultPost_seq = post_seq.longValue();
//			bDTO.setPostSeq(resultPost_seq);
//
//			BigDecimal Category = (BigDecimal) Blist.get(i)[2];
//			int resultCategory = Category.intValue();
//			bDTO.setCategory(resultCategory);
//
//			bDTO.setTitle((String) Blist.get(i)[3]);
//			bDTO.setContent((String) Blist.get(i)[4]);
//			bDTO.setImgPath((String) Blist.get(i)[5]);
//			bDTO.setCDate((Date) Blist.get(i)[6]);
//
//			BigDecimal Recommend = (BigDecimal) Blist.get(i)[7];
//			int resultRec = Recommend.intValue();
//			bDTO.setRecommend(resultRec);
//
//			BigDecimal Cnt = (BigDecimal) Blist.get(i)[8];
//			int resultCnt = Cnt.intValue();
//			bDTO.setCnt(resultCnt);
//
//			UsersDTO.UsersNameDTO uDTO = new UsersDTO.UsersNameDTO();
//			uDTO.setNickname((String) Blist.get(i)[0]);
//			bDTO.setUsersNameDTO(uDTO);
//			dtoList.add(bDTO);
//		}
//		return dtoList;
//
//	}
	
	/**
	 * 게시글 전체 목록(조회수순)
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 */
	public PageBean<BoardDTO> listBoardByCnt(int currentPage) throws FindException {
		int startRow = (currentPage - 1) * PageBean.CNT_PER_PAGE + 1;
		int endRow = currentPage * PageBean.CNT_PER_PAGE;
		List<Object[]> list = bRepository.getBoardByCnt(startRow, endRow);
		List<BoardDTO> boardList = new ArrayList<>();
		list.forEach(arr -> {
			BoardDTO dto = new BoardDTO();

			String nickName = (String) arr[1];
			BigDecimal post_seq = (BigDecimal) arr[2];
			BigDecimal category = (BigDecimal) arr[3];
			String title = (String) arr[4];
			String content = (String) arr[5];
			String imgPath = (String) arr[6];
			Date cDate = (Date) arr[7];
			BigDecimal recommend = (BigDecimal) arr[8];
			BigDecimal cnt = (BigDecimal) arr[9];

			UsersDTO users = new UsersDTO();
			users.setNickname(nickName);
			dto.setUsersDTO(users);
			dto.setBoardSeq(post_seq.longValue());
			dto.setCategory((int) (category.longValue()));
			dto.setTitle(title);
			dto.setBoardContent(content);
			dto.setBoardImg(imgPath);
			dto.setBoardDate(cDate);
			dto.setLike((int) (recommend.longValue()));
			dto.setHit((int) (cnt.longValue()));

			boardList.add(dto);
		});
		int totalCnt = (int) bRepository.count();// 총상품수
		PageBean<BoardDTO> pb = new PageBean(currentPage, boardList, totalCnt);
		return pb;


	}

	/**
	 * 글 번호로 게시글 상세 검색(닉네임+글상세+댓글)
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @return
	 * @throws FindException
	 */
	public List<BoardDTO.BoardAllSelectDTO> listBoardDetail(Long postSeq) throws FindException {
		List<Object[]> Blist = bRepository.findPostSeq(postSeq);
		List<BoardDTO.BoardAllSelectDTO> dto = new ArrayList<>();
		for (int i = 0; i < Blist.size(); i++) {
			BoardDTO.BoardAllSelectDTO bDTO = new BoardDTO.BoardAllSelectDTO();
			BigDecimal post_seq = (BigDecimal) Blist.get(i)[2];
			Long resultPost_seq = post_seq.longValue();
			bDTO.setBoardSeq(resultPost_seq);

			BigDecimal Category = (BigDecimal) Blist.get(i)[3];
			int resultCategory = Category.intValue();
			bDTO.setCategory(resultCategory);

			bDTO.setTitle((String) Blist.get(i)[4]);
			bDTO.setBoardContent((String) Blist.get(i)[5]);
			bDTO.setBoardImg((String) Blist.get(i)[6]);
			bDTO.setBoardDate((Date) Blist.get(i)[7]);
			BigDecimal Recommend = (BigDecimal) Blist.get(i)[8];
			int resultRec = Recommend.intValue();
			bDTO.setLike(resultRec);
			BigDecimal Cnt = (BigDecimal) Blist.get(i)[9];
			int resultCnt = Cnt.intValue();
			bDTO.setHit(resultCnt);

			// 11 : 댓글 아이디 ,12 : 댓글내용 , 13:댓글 작성일 14:댓글 닉네임
			UsersDTO.UsersNameDTO uDTO = new UsersDTO.UsersNameDTO();
			uDTO.setNickname((String) Blist.get(i)[1]);
			uDTO.setUserId((String) Blist.get(i)[0]);

			BoardRepDTO.BoardRepSelectDTO brsDTO = new BoardRepSelectDTO();
			brsDTO.setReplyContent((String) Blist.get(i)[12]);
			brsDTO.setBoardRepDate((Date) Blist.get(i)[13]);
			if (Blist.get(i)[15] != null) {
				BigDecimal post_rep_seq = (BigDecimal) Blist.get(i)[15];
				Long resultRep_seq = post_rep_seq.longValue();
				brsDTO.setBoardReqSeq(resultRep_seq);
				}

			UsersDTO.UsersNameDTO repuserDTO = new UsersDTO.UsersNameDTO();
			repuserDTO.setUserId((String) Blist.get(i)[11]);
			repuserDTO.setNickname((String) Blist.get(i)[14]);
			brsDTO.setUsersNameDTO(repuserDTO);

			bDTO.setUsersNameDTO(uDTO);
			bDTO.setBoardRepSelectDTO(brsDTO);

			dto.add(bDTO);
		}
		return dto;
	}

	/**
	 * 글 수정폼 출력
	 * 
	 * @param postSeq
	 * @throws FindException
	 */
	public getBoardByBoardTypeDTO detailBoard(Long postSeq) throws FindException {
		List<Object[]> obj = bRepository.detailBoard(postSeq);
		getBoardByBoardTypeDTO dto = new getBoardByBoardTypeDTO();

		for (int i = 0; i < obj.size(); i++) {
			// 각각의 값을 꺼내서 DTO에 셋팅
			BigDecimal post_seq = (BigDecimal) obj.get(i)[1];
			Long resultPost_seq = post_seq.longValue();
			dto.setBoardSeq(resultPost_seq);
			BigDecimal Category = (BigDecimal) obj.get(i)[2];
			int resultCategory = Category.intValue();
			dto.setCategory(resultCategory);
			dto.setTitle((String) obj.get(i)[3]);
			dto.setBoardContent((String) obj.get(i)[4]);
			dto.setBoardImg((String) obj.get(i)[5]);
			dto.setBoardDate((Date) obj.get(i)[6]);
			BigDecimal Recommend = (BigDecimal) obj.get(i)[7];
			int resultRec = Recommend.intValue();
			dto.setLike(resultRec);
			BigDecimal cnt = (BigDecimal) obj.get(i)[8];
			int resultCnt = cnt.intValue();
			dto.setHit(resultCnt);
			UsersDTO.UsersNameDTO usersNameDTO = new UsersDTO.UsersNameDTO();
			usersNameDTO.setNickname((String) obj.get(i)[0]);
			dto.setUsersNameDTO(usersNameDTO);
		}
		return dto;
	}

	/**
	 * 글 수정
	 * 
	 * @author choigeunhyeong
	 * @param board
	 * @throws FindException
	 */

	@Transactional
	public void editBoard(BoardDTO.editBoardDTO editBoardDTO, Long postSeq) throws ModifyException {
		Optional<Board> optB = bRepository.findById(postSeq);
		if (optB.isPresent()) {
			Board bEntity = optB.get();
			// bEntity.setUsers(optB.get().getUsers());
			// logger.error("값:" + hostuserDTO.toString());
			bEntity.setTitle(editBoardDTO.getTitle());
			bEntity.setBoardContent(editBoardDTO.getBoardContent());
			bEntity.setBoardImg(editBoardDTO.getBoardImg());
			bRepository.save(bEntity);

		} else {
			throw new ModifyException("글 수정오류");
		}
	}

	/**
	 * 게시글 조회수 증가
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @throws FindException
	 */

	@Transactional
	public void updateCnt(Long postSeq) throws ModifyException {
		Optional<Board> optB = bRepository.findById(postSeq);
		if (optB.isPresent()) {
			Integer oldcnt = optB.get().getHit();
			optB.get().setHit(oldcnt + 1);
		} else {
			throw new ModifyException("정상적인 수정이 되지 않았습니다.");
		}
	}

	/**
	 * 게시글 삭제
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @throws RemoveException
	 */
	public void deleteBoard(Long postSeq) throws RemoveException {
		Optional<Board> optB = bRepository.findById(postSeq);
		if (optB.isPresent()) {
			bRepository.deleteById(postSeq);
		} else {
			throw new RemoveException("정상적인 삭제가 되지 않았습니다.");
		}
	}

	/**
	 * 제목으로 검색
	 * 
	 * @author choigeunhyeong
	 * @param title
	 * @return
	 * @throws FindException
	 */

	public PageBean<BoardDTO> findBoardTitle(String title, int currentPage) throws FindException {
		int startRow = (currentPage - 1) * PageBean.CNT_PER_PAGE + 1;
		int endRow = currentPage * PageBean.CNT_PER_PAGE;
		List<Object[]> list = bRepository.findByBoardTitle(startRow, endRow, title);
		List<BoardDTO> boardList = new ArrayList<>();
		list.forEach(arr -> {
			BoardDTO dto = new BoardDTO();
			String nickName = (String) arr[1];
			BigDecimal post_seq = (BigDecimal) arr[2];
			BigDecimal category = (BigDecimal) arr[3];
			String btitle = (String) arr[4];
			String content = (String) arr[5];
			String imgPath = (String) arr[6];
			Date cDate = (Date) arr[7];
			BigDecimal recommend = (BigDecimal) arr[8];
			BigDecimal cnt = (BigDecimal) arr[9];

			UsersDTO users = new UsersDTO();
			users.setNickname(nickName);
			dto.setUsersDTO(users);
			dto.setBoardSeq(post_seq.longValue());
			dto.setCategory((int) (category.longValue()));
			dto.setTitle(btitle);
			dto.setBoardContent(content);
			dto.setBoardImg(imgPath);
			dto.setBoardDate(cDate);
			dto.setLike((int) (recommend.longValue()));
			dto.setHit((int) (cnt.longValue()));

			boardList.add(dto);
		});

		int totalCnt = (int) boardList.size();// 총상품수
		PageBean<BoardDTO> pb = new PageBean(currentPage, boardList, totalCnt);
		return pb;
	}

	/**
	 * [메인페이지] 글작성 최신순으로 list를 출력한다.
	 * 
	 * @author SR
	 * @return List<BoardDTO> 글목록
	 * @throws FindException
	 */
	public List<BoardDTO.selectAllBydateBoardDTO> listByDate() throws FindException {
		List<Object[]> bList = bRepository.selectAllByDate();

		List<BoardDTO.selectAllBydateBoardDTO> bListDto = new ArrayList<>();

		for (int i = 0; i < bList.size(); i++) {

			BoardDTO.selectAllBydateBoardDTO bDto = new BoardDTO.selectAllBydateBoardDTO();
			BigDecimal postSeq = (BigDecimal) bList.get(i)[0];
			long convertSeq = postSeq.longValue();
			bDto.setBoardSeq(convertSeq);
			BigDecimal category = (BigDecimal) bList.get(i)[2];
			int convertCate = category.intValue();
			bDto.setCategory(convertCate);
			bDto.setTitle((String) bList.get(i)[3]);
			bDto.setBoardContent((String)bList.get(i)[4]);
			bDto.setBoardImg((String) bList.get(i)[5]);
			bDto.setBoardDate((Date) bList.get(i)[6]);
			BigDecimal recommend = (BigDecimal) bList.get(i)[7];
			int convertRec = recommend.intValue();
			bDto.setLike(convertRec);
			BigDecimal cnt = (BigDecimal) bList.get(i)[8];
			int convertCnt = cnt.intValue();
			bDto.setHit(convertCnt);

			UsersDTO.selectAllBydateBoardDTO uDto = new UsersDTO.selectAllBydateBoardDTO();
			uDto.setNickname((String) bList.get(i)[1]);

			bDto.setUsersDTO(uDto);
			bListDto.add(bDto);
		}
		return bListDto;

	}

}