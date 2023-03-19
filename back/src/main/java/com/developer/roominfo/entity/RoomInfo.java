package com.developer.roominfo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.reservation.entity.Reservation;
import com.developer.studyroom.entity.Studyroom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "room_info")
@DynamicInsert
@DynamicUpdate

@SequenceGenerator(name = "ROOM_SEQ_GENERATOR", // 사용할 sequence 이름
		sequenceName = "ROOM_SEQ", // 실제 데이터베이스 sequence 이름
		initialValue = 1, allocationSize = 1)
public class RoomInfo {

	@Id
	@Column(name = "room_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOM_SEQ_GENERATOR" // 위의 sequence 이름
	)
	private Long roomSeq;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "info")
	private String info;

	@NotNull
	@Column(name = "img_path")
	private String imgPath;

	@NotNull
	@Column(name = "person")
	private Integer person;

	@NotNull
	@Column(name = "price")
	private Integer price;

	@Column(name = "status")
	@ColumnDefault(value = "0") // 0: 활성화, 1: 비활성화(삭제)
	private Integer status;
	
	
	

	@ManyToOne // (cascade= {CascadeType.MERGE})
	@JoinColumn(name = "sr_seq", nullable = false)
	private Studyroom studyroom;

	@OneToMany(mappedBy = "roominfo")
	private List<Reservation> reservation;
}