package com.developer.tutor.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.lesson.entity.Lesson;
import com.developer.orders.entity.Orders;
import com.developer.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TUTOR")
@DynamicInsert
@DynamicUpdate

@Getter
@Setter
@NoArgsConstructor
public class Tutor {
	@Id
	@Column(name = "tutor_id")
	private String tutorId;
	@Column(name = "info")
	private String info;
	@Column(name = "img_path")
	private String imgPath;
	@Column(name = "star_avg")
	private Double starAvg;
	@Column(name = "apply_ok")
	private Integer applyOk;

	
	
	@MapsId("tutorId")
	@OneToOne(optional = true, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "tutor_id", nullable = true)
	private Users users;

	@OneToMany(mappedBy = "tutor")
	private List<Lesson> lesson;
	
	@OneToMany(mappedBy = "tutor")
	private List<Orders> orders;
}