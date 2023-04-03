package com.developer.userreview.entity;

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

@DynamicInsert
@DynamicUpdate
@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "user_review")
public class UserReview {	
	@Id
	@Column(name = "apply_seq")
	private Long applySeq;
	
	@NotNull
	@Column(name = "user_star")
	private Integer userStar;
	
	@NotNull
	@Column(name = "user_review")
	private String userReview;
	
	@ColumnDefault(value = "SYSDATE")
	@Column(name = "user_review_date")
	private Date userReviewDate;
	
	
	@MapsId(value="applySeq")
	@OneToOne(cascade = {CascadeType.REMOVE,CascadeType.MERGE})
	@JoinColumn(name = "apply_seq")
	private AppliedLesson appliedLesson;
}
