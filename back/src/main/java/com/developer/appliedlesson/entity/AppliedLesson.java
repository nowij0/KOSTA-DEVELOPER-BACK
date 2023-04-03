package com.developer.appliedlesson.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.lesson.entity.Lesson;
import com.developer.lessonreview.entity.LessonReview;
import com.developer.userreview.entity.UserReview;
import com.developer.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="APPLIED_LESSON")
@DynamicInsert @DynamicUpdate
@Getter @Setter
@NoArgsConstructor 

@SequenceGenerator(
      name ="applySeq", 
      sequenceName ="apply_seq", 
      initialValue = 1, allocationSize = 1 
      )
public class AppliedLesson {
	@Id
	@Column(name="apply_seq")
	@GeneratedValue( 
			strategy = GenerationType.SEQUENCE, 
			generator ="applySeq"  
		)
	private Long applySeq;
	
	@ColumnDefault(value="SYSDATE")
	@Column(name = "apply_date")
	private Date applyDate;
	
	@ColumnDefault(value="0")
	@Column(name="apply_ok")
	private Integer applyOk; //0미승인 1승인 2승인거절
	
	@NotNull
	@Column(name="tutee_id")
	private String tuteeId;
	
	
	
	@ManyToOne
	@JoinColumn(name="al_lesson_seq")
	private Lesson lesson;
	
	@ManyToOne
	@JoinColumn(name = "al_user_id")
	private Users users;
	
	@OneToOne(mappedBy = "appliedLesson",
			cascade = CascadeType.MERGE)	
	private LessonReview lessonReview;
	
	@OneToOne(mappedBy = "appliedLesson",
			cascade = CascadeType.MERGE)
	private UserReview userReview;

}