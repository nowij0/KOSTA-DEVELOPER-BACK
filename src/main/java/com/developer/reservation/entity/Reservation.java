package com.developer.reservation.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.developer.host.entity.Host;
import com.developer.roominfo.entity.RoomInfo;
import com.developer.roomreview.entity.RoomReview;
import com.developer.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor

@Entity
@Table(name = "RESERVATION")

@DynamicInsert @DynamicUpdate
@SequenceGenerator(name = "RES_SEQ_GENERATOR", // 사용할 sequence 이름
		sequenceName = "res_seq", // 실제 데이터베이스 sequence 이름
		initialValue = 1, allocationSize = 1)
public class Reservation {
	@Id
	@Column(name = "res_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RES_SEQ_GENERATOR")
	private Long resSeq;

	@NotNull
	@Column(name = "start_time")
	private String startTime;

	@NotNull
	@Column(name = "end_time")
	private String endTime;

	@ColumnDefault(value = "SYSDATE")
	@Temporal(TemporalType.DATE)
	@Column(name = "res_date")
	private Date resDate;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "using_date")
	private Date usingDate;

	
	
	@OneToOne(mappedBy = "reservation", fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	private RoomReview roomReview;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;

	@ManyToOne // (cascade= {CascadeType.MERGE})
	@JoinColumn(name = "host_id")
	private Host host;

	@ManyToOne // (cascade= {CascadeType.MERGE})
	@JoinColumn(name = "room_seq")
	private RoomInfo roominfo;

}
