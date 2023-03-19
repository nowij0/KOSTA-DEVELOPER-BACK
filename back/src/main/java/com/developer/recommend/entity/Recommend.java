package com.developer.recommend.entity;

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

import com.developer.board.entity.Board;
import com.developer.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "REC_SEQ_GENERATOR", // 사용할 sequence 이름
		sequenceName = "rec_seq", // 실제 데이터베이스 sequence 이름
		initialValue = 1, allocationSize = 1)

@Entity
@Table(name = "RECOMMEND")
public class Recommend {
	@Id
	@Column(name = "rec_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REC_SEQ_GENERATOR" // 위의 sequence 이름
	)
	private Long recSeq;

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_seq", nullable = false)
	private Board board;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private Users users;
}
