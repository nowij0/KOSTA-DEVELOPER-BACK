package com.developer.board.dto;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Page;

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
	private Long postSeq;
	private Integer category;
	private String title;
	private String content;
	private String imgPath;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date cDate;
	private Integer recommend;
	private Integer cnt;

	private BoardRepDTO boardRepDTO;
	private UsersDTO usersDTO;
	private RecommendDTO recommendDTO;

	@Data
	@NoArgsConstructor
	public static class BoardAllSelectDTO {
		private Long postSeq;
		private Integer category;
		private String title;
		private String content;
		private String imgPath;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date cDate;
		private Integer recommend;
		private Integer cnt;

		// private BoardRepDTO boardRepDTO;
		private BoardRepDTO.BoardRepSelectDTO boardRepSelectDTO;
		private UsersDTO.UsersNameDTO usersNameDTO;
	}

	// SR
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class selectAllBydateBoardDTO {
		private Long postSeq;
		private Integer category;
		private String title;
		private String content;
		private String imgPath;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date cDate;
		private Integer recommend;
		private Integer cnt;
		private UsersDTO.selectAllBydateBoardDTO usersDTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class getBoardByBoardTypeDTO {
		private Long postSeq;
		private Integer category;
		private String title;
		private String content;
		private String imgPath;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date cDate;
		private Integer recommend;
		private Integer cnt;
		private UsersDTO.UsersNameDTO usersNameDTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class saveBoardDTO {
		private Long postSeq;
		private Integer category;
		private String title;
		private String content;
		private String imgPath;
		private UsersDTO.UsersNameDTO usersNameDTO;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date cDate;
		private Integer recommend;
		private Integer cnt;
		private UsersDTO.selectAllBydateBoardDTO usersDTO;
	}

	// 근형
	@Data
	@NoArgsConstructor
	public static class editBoardDTO {
		private String title;
		private String content;
		private String imgPath;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//			@ColumnDefault(value = "SYSDATE")
		private Date cDate;
	}

//	@Data
//	@NoArgsConstructor
//	public static class PagebeanDTO{
//		private int startPage;
//		private int endPage;
//		private Page<BoardDTO.getBoardByBoardTypeDTO> list;
//	}

}