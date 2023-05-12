package com.developer.users.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.appliedlesson.entity.AppliedLesson;
import com.developer.board.entity.Board;
import com.developer.boardrep.entity.BoardRep;
import com.developer.favoriteslesson.entity.FavoritesLesson;
import com.developer.favoritesstudycafe.entity.FavoritesStudycafe;
import com.developer.recommend.entity.Recommend;
import com.developer.reservation.entity.Reservation;
import com.developer.tutor.entity.Tutor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor

@Entity
@Table(name = "users")
@DynamicInsert @DynamicUpdate
public class Users {
	@Id
	@Column(name = "user_id")
	@Size(min = 6, max = 12, message = "아이디는 6자 이상 12자 이하로 입력해주세요.")
	private String userId;

	@Column(name = "role")
	@ColumnDefault(value = "2")
	private Integer role; // 1튜터 2튜티 3탈퇴 9관리

	@Size(min = 6, max = 12, message = "비밀번호는 6자 이상 12자 이하로 입력해주세요.")
	@NotNull
	@Column(name = "pwd")
	private String pwd;

	@NotNull
	@Column(name = "nickname")
	private String nickname;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Email(message = "올바른 형식의 이메일 주소를 입력해주세요.")
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "tel")
	@Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
	private String tel;

	@NotNull
	@Column(name = "addr")
	private String addr;

	@OneToOne(mappedBy = "users", cascade = CascadeType.REMOVE)
	private Tutor tutor;
	

	@OneToMany(mappedBy = "users")
	private List<AppliedLesson> appliedLesson;

	@OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
	private List<Board> board;

	@OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
	private List<BoardRep> boardRep;

	@OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
	private List<Recommend> recommend;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "users")
	private List<Reservation> reservation;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "users")
	private List<FavoritesStudycafe> favoritesStudyroom;

	@OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
	private List<FavoritesLesson> favoritesLesson;
}