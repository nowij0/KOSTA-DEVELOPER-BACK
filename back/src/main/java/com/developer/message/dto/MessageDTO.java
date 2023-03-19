package com.developer.message.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
	private Long messageSeq;
	private String title;
	private String content;
	private String senderId;
	private String receiverId;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date sendDate;

	//메세지 작성
	@Data
	@AllArgsConstructor @NoArgsConstructor
	public static class addMessage{
		private String title;
		private String content;
		private String receiverId;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date sendDate;
	}
	
	
	//메세지 상세보기
	@Data
	@AllArgsConstructor @NoArgsConstructor
	public static class messageDetail{
		private Long messageSeq;
		private String title;
		private String content;
		private String senderId;
		private int readCheck;
		@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date sendDate;
	}
	
	
}
