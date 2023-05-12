package com.developer.board.dto;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.boardrep.dto.BoardRepDTO;
import com.developer.recommend.dto.RecommendDTO;
import com.developer.users.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicInsert
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
public class BoardDTO {
	// 근형
	private Long boardSeq;
	private Integer category;
	private String title;
	private String boardContent;
	private String boardImg;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date boardDate;
	private Integer like;
	private Integer hit;

	private BoardRepDTO boardRepDTO;
	private UsersDTO usersDTO;
	private RecommendDTO recommendDTO;

	@Data
	@NoArgsConstructor
	public static class BoardAllSelectDTO {
		private Long boardSeq;
		private Integer category;
		private String title;
		private String boardContent;
		private String boardImg;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date boardDate;
		private Integer like;
		private Integer hit;

		// private BoardRepDTO boardRepDTO;
		private BoardRepDTO.BoardRepSelectDTO boardRepSelectDTO;
		private UsersDTO.UsersNameDTO usersNameDTO;
	}

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllBydateBoardDTO {
		private Long boardSeq;
		private Integer category;
		private String title;
		private String boardContent;
		private String boardImg;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date boardDate;
		private Integer like;
		private Integer hit;
		private UsersDTO.selectAllBydateBoardDTO usersDTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class getBoardByBoardTypeDTO {
		private Long boardSeq;
		private Integer category;
		private String title;
		private String boardContent;
		private String boardImg;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date boardDate;
		private Integer like;
		private Integer hit;
		private UsersDTO.UsersNameDTO usersNameDTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class saveBoardDTO {
		private Long boardSeq;
		private Integer category;
		private String title;
		private String boardContent;
		private String boardImg;
		private UsersDTO.UsersNameDTO usersNameDTO;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date boardDate;
		private Integer like;
		private Integer hit;
		private UsersDTO.selectAllBydateBoardDTO usersDTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class editBoardDTO {
		private String title;
		private String boardContent;
		private String boardImg;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//			@ColumnDefault(value = "SYSDATE")
		private Date boardDate;
	}

//	@Data
//	@NoArgsConstructor
//	public static class PagebeanDTO{
//		private int startPage;
//		private int endPage;
//		private Page<BoardDTO.getBoardByBoardTypeDTO> list;
//	}

}