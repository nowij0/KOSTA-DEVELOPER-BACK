package com.developer.studyroom.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.favoritesstudyroom.entity.FavoritesStudyroom;
import com.developer.hostuser.entity.HostUser;
import com.developer.roominfo.entity.RoomInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "studyroom")
@SequenceGenerator(name = "SR_SEQ_GENERATOR", // 사용할 sequence 이름
		sequenceName = "SR_SEQ", // 실제 데이터베이스 sequence 이름
		initialValue = 1, allocationSize = 1)

@DynamicInsert
@DynamicUpdate
public class Studyroom {

	@Id
	@Column(name = "sr_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SR_SEQ_GENERATOR" // 위의 sequence 이름
	)
	private Long srSeq;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "addr")
	private String addr;

	@Column(name = "info")
	private String info;

	@NotNull
	@Column(name = "open_time")
	private String openTime;

	@NotNull
	@Column(name = "end_time")
	private String endTime;

	@NotNull
	@Column(name = "img_path")
	private String imgPath;

	@Column(name = "oc")
	@ColumnDefault(value = "0") // 0: 오픈, 1: 마감
	private Integer oc;
	
	
	

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "studyroom")
	private List<RoomInfo> roomInfo;
	@JsonIgnore
	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "host_id", nullable = false)
	private HostUser hostUser;

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "studyroom")
	private List<FavoritesStudyroom> favoritesStudyroom;

}