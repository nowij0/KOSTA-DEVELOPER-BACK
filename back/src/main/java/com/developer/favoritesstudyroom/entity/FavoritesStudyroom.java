
package com.developer.favoritesstudyroom.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.developer.studyroom.entity.Studyroom;
import com.developer.users.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FAVORITES_STUDYROOM")

@SequenceGenerator(name = "FAV_SEQ_GENERATOR", // 사용할 sequence 이름
		sequenceName = "fav_seq", // 실제 데이터베이스 sequence 이름
		initialValue = 1, allocationSize = 1)
public class FavoritesStudyroom {
	@Id
	@Column(name = "fav_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAV_SEQ_GENERATOR")
	private Long favSeq;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "sr_seq")
	private Studyroom studyroom;
}