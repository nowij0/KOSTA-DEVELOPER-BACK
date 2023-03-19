package com.developer.roomreview.entity;

import java.sql.Date;
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
import com.developer.reservation.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ROOM_REVIEW")
@DynamicInsert
@DynamicUpdate

public class RoomReview {
	@Id
	@Column(name = "res_seq")
	private Long resSeq;

	@NotNull
	@Column(name = "content")
	private String content;

	@NotNull
	@Column(name = "star")
	private Integer star;

	@JsonFormat(pattern = "yyyy-mm-dd", timezone = "Asia/Seoul")
	@ColumnDefault(value = "sysdate")
	@Column(name = "cdate")
	private Date cDate;

	@MapsId("resSeq")
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "res_seq") // inner
	private Reservation reservation;
}
