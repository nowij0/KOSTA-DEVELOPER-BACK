package com.developer.userreview.entity;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@Column(name = "apply_seq_rv")
	private Long applySeqRv;
	
	@Column(name = "star")
	@NotNull
	private Integer star;
	
	@Column(name = "review")
	@NotNull
	private String review;
	
	
	
	@MapsId(value="applySeqRv")
	@OneToOne(cascade = {CascadeType.REMOVE,CascadeType.MERGE})
	@JoinColumn(name = "apply_seq_rv")
	private AppliedLesson alLesson;
}
