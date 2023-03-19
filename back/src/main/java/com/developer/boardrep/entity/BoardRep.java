package com.developer.boardrep.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.board.entity.Board;
import com.developer.users.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "board_rep")

@DynamicInsert
@DynamicUpdate
@SequenceGenerator(name = "POST_REP_SEQ_GENERATOR", // 사용할 sequence 이름
		sequenceName = "post_rep_seq", // 실제 데이터s베이스 sequence 이름
		initialValue = 1, allocationSize = 1)

public class BoardRep {

	@Id
	@Column(name = "post_rep_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_REP_SEQ_GENERATOR" // 위의 sequence 이름
	)
	private Long postRepSeq;

	@NotNull
	@Column(name = "content")
	private String content;

	@Column(name = "cdate")
	@ColumnDefault(value = "SYSDATE")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date cDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_seq")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users users;

}