package com.developer.board.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.board.dto.BoardDTO;
import com.developer.board.dto.PageBean;
import com.developer.board.entity.Board;
import com.developer.board.repository.BoardRepository;
import com.developer.board.service.BoardService;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.ModifyException;
import com.developer.exception.RemoveException;
import com.developer.recommend.entity.Recommend;
import com.developer.recommend.service.RecommendService;
import com.developer.util.Attach;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("board/*")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService bService;
	private final RecommendService rService;
	private final BoardRepository bRepository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 게시판 글 작성
	 * 
	 * @author choigeunhyeong
	 * @param board
	 * @throws AddException
	 */

	@PostMapping(value = "add")
	public ResponseEntity<?> addBoard(BoardDTO.saveBoardDTO saveBoardDTO, HttpSession session,
			@RequestPart(value = "f", required = false) MultipartFile f) throws AddException, FindException {
		String logined = (String) session.getAttribute("logined");
		if (logined == null) { // 로그인 안한 경우
			throw new FindException("로그인하세요");
		}
//		String savdDirectory = "/Users/choigeunhyeong/Documents/attach";
		String savdDirectory = "/Users/moonone/Desktop/KOSTA/img/board";
		
		File saveDirFile = new File(savdDirectory);
		String fileName = null;

		if (f != null && f.getSize() > 0) {
			String fOrigin = f.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String fName = uuid.toString() + "_" + fOrigin;

			fileName = fName;
			File file = new File(saveDirFile, fileName);
			try {
				Attach.upload(f.getBytes(), file);

				// 섬네일파일 만들기 (비율맞춰서된다!)
				int width = 300;
				int height = 300;

				String thumbFileName = "t_" + fileName; // 섬네일파일명
				File thumbFile = new File(saveDirFile, thumbFileName);
				FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);
				InputStream thumbnailIS = f.getInputStream();

				Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height);
				logger.info("파일업로드 성공");
				saveBoardDTO.setImgPath(fileName);

			} catch (IOException e) {
				e.printStackTrace();
				logger.error("파일업로드 에러");
				throw new AddException(e.getMessage());
			}
		}
		bService.addBoard(saveBoardDTO, logined);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 글 수정폼
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "edit" // , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> detailBoard(@RequestParam Long postSeq) throws FindException {
		BoardDTO.getBoardByBoardTypeDTO detail = bService.detailBoard(postSeq);
		return new ResponseEntity<>(detail, HttpStatus.OK);

	}

	/**
	 * 커뮤니티 메인 글목록 (작성일순)
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 */
//	@GetMapping("/list")
//	public ResponseEntity<PageBean> list(int currentPage) throws FindException{
//		 return new ResponseEntity<>(bService.listBoard(currentPage),HttpStatus.OK);
//	}
	@GetMapping("/list")
	public ResponseEntity<PageBean> list(int currentPage) throws FindException {

		return new ResponseEntity<>(bService.listBoard(currentPage), HttpStatus.OK);
	}
	
	/**
	 * 커뮤니티 메인 글목록 (조회수)
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 */
	@GetMapping("/listcnt")
	public ResponseEntity<PageBean> listcnt(int currentPage) throws FindException {

		return new ResponseEntity<>(bService.listBoardByCnt(currentPage), HttpStatus.OK);
	}

	/**
	 * 조회수 증가
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @return
	 * @throws FindException
	 */
	@PutMapping(value = "updatecnt/{postSeq}" // , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> searchBoard(@PathVariable Long postSeq) throws ModifyException {
		bService.updateCnt(postSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 게시글 수정 !
	 * 
	 * @author choigeunhyeong
	 * @param editBoardDTO
	 * @param postSeq
	 * @param f
	 * @return
	 * @throws AddException
	 * @throws FindException
	 * @throws ModifyException
	 */
	@PostMapping(value = "edit/{postSeq}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> editBoard(
			BoardDTO.editBoardDTO editBoardDTO, 
			@PathVariable Long postSeq,
			@RequestPart(value = "f", required = false) 
			MultipartFile f)
			throws AddException, FindException, ModifyException {

		logger.error("파일확인: " + f.toString());

//		String savdDirectory = "/Users/choigeunhyeong/Documents/attach";
		String savdDirectory = "/Users/moonone/Desktop/KOSTA/img/board";
		File saveDirFile = new File(savdDirectory);
		String fileName = null;
		
		if (f != null && f.getSize() > 0) {//첨부된 파일이 존재하고 
			
			String fOrigin = f.getOriginalFilename();

			Optional<Board> b = bRepository.findById(postSeq);

			String oldFileName = b.get().getImgPath();
			if(!fOrigin.equals(oldFileName)) { //첨부된 파일이 기존파일이다
				
				//기존파일이 있는경우에만 실행
				if(oldFileName!= null) {
					File oldFile = new File(saveDirFile, oldFileName);
					if (oldFile.exists()) {
						oldFile.delete();
						System.out.println("delete");
					}
				}

				UUID uuid = UUID.randomUUID();
				String fName = uuid.toString() + "_" + fOrigin;
	
				fileName = fName;
				File file = new File(saveDirFile, fileName);
				
				try {
					
					Attach.upload(f.getBytes(), file);
					System.out.println("파일: " +file.length()+ ", 파일크기:"+f.getBytes());
					
					// 섬네일파일 만들기 (비율맞춰서된다!)
					int width = 300;
					int height = 300;
	
					String thumbFileName = "t_" + fileName; // 섬네일파일명
					File thumbFile = new File(saveDirFile, thumbFileName);
					FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);
					InputStream thumbnailIS = f.getInputStream();
	
					Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height);
					String oldthumbFileName = "t_" + b.get().getImgPath();
					;
					File oldthumbFile = new File(saveDirFile, oldthumbFileName);
	
					if (oldthumbFile.exists()) {
						oldthumbFile.delete();
					}
					editBoardDTO.setImgPath(fName);
					
	
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("파일업로드 에러");
					throw new ModifyException(e.getMessage());
				}
			}
		}
		bService.editBoard(editBoardDTO, postSeq);

		


		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 게시판 글 삭제
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @return
	 * @throws RemoveException
	 */
	@DeleteMapping(value = "{postSeq}" // , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> deleteBoard(@PathVariable Long postSeq) throws RemoveException {
		bService.deleteBoard(postSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 글 번호로 게시글 상세 검색(닉네임+글상세+댓글)
	 * 
	 * @author choigeunhyeong
	 * @param postSeq
	 * @return
	 * @throws FindException
	 * @throws ModifyException
	 */
	@GetMapping(value = "detail" // , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> listBoardDetail(@RequestParam Long postSeq) throws FindException, ModifyException {
		bService.updateCnt(postSeq);
		List<BoardDTO.BoardAllSelectDTO> list = bService.listBoardDetail(postSeq);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 게시글 제목으로 검색
	 * 
	 * @author choigeunhyeong
	 * @param title
	 * @return
	 * @throws FindException
	 */
//	@GetMapping(value = "search" // , produces = MediaType.APPLICATION_JSON_VALUE
//	)
//	public ResponseEntity<?> searchBoard(@RequestParam String title) throws FindException {
//		List<Board> list = bService.findByTitle("%" + title + "%");
//		return new ResponseEntity<>(list, HttpStatus.OK);
//
//	}
	@GetMapping(value = "search" // , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<PageBean> searchBoard(@RequestParam String title, int currentPage) throws FindException {

		return new ResponseEntity<>(bService.findBoardTitle("%" + title + "%", currentPage), HttpStatus.OK);

	}

	/**
	 * 추천수 증가
	 * 
	 * @author choigeunhyeong
	 * @param recommend
	 * @param postSeq
	 * @param session
	 * @return
	 * @throws AddException
	 */
	@PostMapping(value = "detail/{postSeq}")
	public ResponseEntity<?> addRecommend(Recommend recommend, @PathVariable Long postSeq, HttpSession session)
			throws AddException {
		String logined = (String) session.getAttribute("logined");
		if (logined == null) {
			throw new AddException("로그인하세요");
		}
		rService.addRecommend(recommend, postSeq, logined);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 추천수 감소
	 * 
	 * @author choigeunhyeong
	 * @param recSeq
	 * @return
	 * @throws RemoveException
	 */
	@DeleteMapping(value = "detail/{recSeq}")
	public ResponseEntity<?> delRecommend(@PathVariable Long recSeq) throws RemoveException {
		rService.delRecommend(recSeq);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}