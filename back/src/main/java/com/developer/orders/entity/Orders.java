package com.developer.orders.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.lesson.entity.Lesson;
import com.developer.tutor.entity.Tutor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")

@DynamicInsert @DynamicUpdate
@Getter @Setter
@NoArgsConstructor

@JsonAutoDetect
@SequenceGenerator(
		name ="orderSeq", 
		sequenceName ="order_seq", 
		initialValue = 1, allocationSize = 1 
		)
public class Orders {

	@Id
	@Column(name = "order_seq")
	@GeneratedValue( 
			strategy = GenerationType.SEQUENCE, 
			generator ="orderSeq"  
		)
	private Long orderSeq;
	
	@NotNull
	@Column(name = "imp_uid")
	private String impUid;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@ColumnDefault(value="SYSDATE")
	@Column(name = "odate")
	private Date odate;
	
	@JsonIgnore
	@OneToOne(optional = true, cascade = {CascadeType.MERGE})
	@JoinColumn(name="or_lesson_seq", nullable = true)
	private Lesson lesson;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Tutor tutor;
	
}
