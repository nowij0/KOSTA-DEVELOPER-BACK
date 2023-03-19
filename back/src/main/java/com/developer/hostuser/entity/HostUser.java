package com.developer.hostuser.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.reservation.entity.Reservation;
import com.developer.studyroom.entity.Studyroom;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "host_user")

@DynamicInsert
@DynamicUpdate
public class HostUser {

	@Id
	@Column(name = "host_id")
	@Size(min = 6, max = 12, message = "아이디는 6자 이상 12자 이하로 입력해주세요.")
	private String hostId;

	@NotNull
	@Column(name = "pwd")
	@Size(min = 6, max = 12, message = "비밀번호는 6자 이상 12자 이하로 입력해주세요.")
	private String pwd;

	@NotNull
	@Column(name = "num")
	private String num;

	@Column(name = "ready")
	@ColumnDefault(value = "0") // 0: 승인대기, 1: 승인, 2:탈퇴
	private Integer ready;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "tel")
	private String tel;

	@NotNull
	@Column(name = "email")
	private String email;

	
	
	
	@JsonIgnore
	@OneToOne(mappedBy = "hostUser") // , cascade = CascadeType.ALL)//, cascade = CascadeType.REMOVE)
	private Studyroom studyroom;

	@JsonIgnore
	@OneToMany(mappedBy = "hostUser")
	private List<Reservation> reservation;
}