package com.developer.orders.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.developer.lesson.dto.LessonDTO;
import com.developer.lesson.entity.Lesson;
import com.developer.tutor.dto.TutorDTO;
import com.developer.tutor.entity.Tutor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrdersDTO {

	@Data
	@NoArgsConstructor @Setter @Getter
	@JsonAutoDetect
	public static class selectOrdersDTO{
		
	private Long orderSeq;
	private String impUid;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date odate;
	private LessonDTO.lessonOrderDTO lessonDTO;
	private TutorDTO.tutorOrderDTO tutor;
	
	}
}
