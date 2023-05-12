package com.developer.studycafe.entity;

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

import com.developer.favoritesstudycafe.entity.FavoritesStudycafe;
import com.developer.host.entity.Host;
import com.developer.roominfo.entity.RoomInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@DynamicInsert @DynamicUpdate

@Entity
@Table(name = "studycafe")
@SequenceGenerator(name = "CAFE_SEQ_GENERATOR", // 사용할 sequence 이름
		sequenceName = "CAFE_SEQ", // 실제 데이터베이스 sequence 이름
		initialValue = 1, allocationSize = 1)
public class Studycafe {

	@Id
	@Column(name = "cafe_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
									generator = "CAFE_SEQ_GENERATOR" // 위의 sequence 이름
	)
	private Long cafeSeq;

	@NotNull
	@Column(name = "cafe_name")
	private String cafeName;

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
	@Column(name = "cafe_img")
	private String cafeImg;

	@ColumnDefault(value = "0") // 0: 오픈, 1: 마감
	@Column(name = "open_status")
	private Integer openStatus;
	
	@ColumnDefault(value = "0")
	@Column(name = "star_avg")
	private Integer starAvg;	
	
	

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "studycafe")
	private List<RoomInfo> roomInfo;
	
	@JsonIgnore
	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "host_id", nullable = false)
	private Host host;

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "studycafe")
	private List<FavoritesStudycafe> favoritesStudycafe;

}