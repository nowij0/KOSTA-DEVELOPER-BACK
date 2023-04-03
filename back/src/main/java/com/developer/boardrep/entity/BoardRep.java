package com.developer.boardrep.entity;

import java.time.LocalDateTime;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor

@Entity
@Table(name = "board_rep")
@DynamicInsert @DynamicUpdate

@SequenceGenerator(name = "BOARD_REP_SEQ_GENERATOR",
		sequenceName = "board_rep_seq",
		initialValue = 1, allocationSize = 1)
public class BoardRep {

	@Id
	@Column(name = "board_rep_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
									generator = "BOARD_REP_SEQ_GENERATOR" 
								)
	private Long boardReqSeq;

	@NotNull
	@Column(name = "reply_content")
	private String replyContent;

	@Column(name = "board_rep_date")
	@ColumnDefault(value = "SYSDATE")
	private LocalDateTime boardRepDate;

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_seq")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users users;

}