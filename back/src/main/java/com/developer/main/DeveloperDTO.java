package com.developer.main;

import java.util.List;

import com.developer.board.dto.BoardDTO;
import com.developer.lesson.dto.LessonDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeveloperDTO {
	
	// SR: [메인페이지] 커뮤니티 + 수업 출력
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MainPageDTO {
		private List<BoardDTO.selectAllBydateBoardDTO> listBoard;
		private List<LessonDTO.selectAllBydateLessonDTO> listLesson;
	}
}
