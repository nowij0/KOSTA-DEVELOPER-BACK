package com.developer.host.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.reservation.entity.Reservation;
import com.developer.studycafe.entity.Studycafe;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "host")

@DynamicInsert
@DynamicUpdate
public class Host {

	@Id
	@Column(name = "host_id")
	@Size(min = 6, max = 12, message = "아이디는 6자 이상 12자 이하로 입력해주세요.")
	private String hostId;

	@NotNull
	@Column(name = "pwd")
	@Size(min = 6, max = 12, message = "비밀번호는 6자 이상 12자 이하로 입력해주세요.")
	private String pwd;

	@NotNull
	@Column(name = "business_num")
	private String businessNum;

	@Column(name = "status")
	@ColumnDefault(value = "0") // 0: 승인대기, 1: 승인, 2:탈퇴
	private Integer status;

	@NotNull
	@Column(name = "host_name")
	private String hostName;

	@NotNull
	@Column(name = "host_tel")
	private String hostTel;

	@NotNull
	@Column(name = "host_email")
	private String hostEmail;

	
	
	
	@JsonIgnore
	@OneToOne(mappedBy = "host") // , cascade = CascadeType.ALL)//, cascade = CascadeType.REMOVE)
	private Studycafe studycafe;

	@JsonIgnore
	@OneToMany(mappedBy = "host")
	private List<Reservation> reservation;
}