package com.developer.board.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.boardrep.entity.BoardRep;
import com.developer.recommend.entity.Recommend;
import com.developer.users.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor

@Entity
@Table(name = "Board")
@DynamicInsert @DynamicUpdate

@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", 
		sequenceName = "board_seq",
		initialValue = 1, allocationSize = 1)
public class Board {
	@Id
	@Column(name="board_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
									generator = "BOARD_SEQ_GENERATOR" 
	)
	private Long boardSeq;

	@Column(name = "category")
	@ColumnDefault(value = "0")
	private Integer category; // 0:Q/A, 1:스터디모집, 2:자유 게시판

	@NotNull
	@Column(name = "title")
	private String title;

	@NotNull
	@Column(name = "board_content")
	private String boardContent;

	@Column(name = "board_img")
	private String boardImg;

	@Column(name = "board_date")
	@ColumnDefault(value = "SYSDATE")
	private Date boardDate;

	@Column(name = "recommend_cnt")
	@ColumnDefault(value = "0")
	private Integer recommendCnt;

	@Column(name = "hit")
	@ColumnDefault(value = "0")
	private Integer hit;

	
	
	@JsonIgnore
	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.MERGE }, mappedBy = "board")
	private List<BoardRep> boardRep;

	@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.MERGE }, mappedBy = "board")
	private List<Recommend> Recommend;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users users;

	
	public Board(Long boardSeq, Integer category, String title, String boardContent, String boardImg,
			Date boardDate, Integer recommendCnt, Integer hit) {
		super();
		this.boardSeq = boardSeq;
		this.category = category;
		this.title = title;
		this.boardContent = boardContent;
		this.boardImg = boardImg;
		this.boardDate = boardDate;
		this.recommendCnt = recommendCnt;
		this.hit = hit;
	}

	@JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
	public void update(String title, String boardContent, String boardImg, Date boardDate) {
		this.title = title;
		this.boardContent = boardContent;
		this.boardImg = boardImg;
		this.boardDate = boardDate;
	}

	public void updateHit(Integer hit) {
		this.hit = hit;
	}

}
