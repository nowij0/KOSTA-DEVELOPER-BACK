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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor

@Entity
@Table(name = "ROOM_REVIEW")
@DynamicInsert @DynamicUpdate
public class RoomReview {
	@Id
	@Column(name = "res_seq")
	private Long resSeq;

	@NotNull
	@Column(name = "room_content")
	private String roomContent;

	@NotNull
	@Column(name = "room_star")
	private Integer roomStar;

	@ColumnDefault(value = "SYSDATE")
	@Column(name = "write_date")
	private Date writeDate;

	
	@MapsId("resSeq")
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "res_seq") // inner
	private Reservation reservation;
}
