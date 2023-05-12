
package com.developer.favoritesstudycafe.entity;

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

import com.developer.studycafe.entity.Studycafe;
import com.developer.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor

@Entity
@Table(name = "FAVORITES_STUDYCAFE")

@SequenceGenerator(name = "FAV_CAFE_SEQ_GENERATOR", 
		sequenceName = "fav_cafe_seq", 
		initialValue = 1, allocationSize = 1)
public class FavoritesStudycafe {
	@Id
	@Column(name = "fav_cafe_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
									generator = "FAV_CAFE_SEQ_GENERATOR")
	private Long favCafeSeq;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "cafe_seq")
	private Studycafe studycafe;
}