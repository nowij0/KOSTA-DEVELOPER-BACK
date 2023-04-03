package com.developer.favoriteslesson.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.developer.lesson.entity.Lesson;
import com.developer.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FAVORITES_LESSON")
@DynamicInsert @DynamicUpdate

@Setter @Getter
@NoArgsConstructor
@SequenceGenerator(name = "favLesSeq", sequenceName = "fav_les_seq", initialValue = 1, allocationSize = 1)
public class FavoritesLesson {
	@Id
	@Column(name = "fav_les_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favLesSeq")
	private Long favLesSeq;

	
	
	@ManyToOne
	@JoinColumn(name = "tutee_id")
	private Users users;

	@ManyToOne
	@JoinColumn(name = "lesson_seq")
	private Lesson lesson;
}
