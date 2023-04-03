package com.developer.lessonreview.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.appliedlesson.entity.AppliedLesson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="LESSON_REVIEW")
@DynamicInsert @DynamicUpdate

@Setter@Getter
@NoArgsConstructor
public class LessonReview {
	@Id
	@Column(name="apply_seq")
	private Long applySeq;
	
	@ColumnDefault(value="SYSDATE")
	@Column(name="lesson_review_date")
	private Date lessonReviewDate;
	
	@NotNull
	@Column(name="lesson_review")
	private String lessonReview;
	
	@NotNull
	@Column(name="lesson_star")
	private Integer lessonStar;	
	
	
	@MapsId("applySeq")
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="apply_seq", nullable = true)
	private AppliedLesson appliedLesson;
}
