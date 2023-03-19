package com.developer.message.entity;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import com.developer.hostuser.entity.HostUser;
import com.developer.users.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "MESSAGE_SEQ", // 사용할 sequence 이름
		sequenceName = "message_seq", // 실제 데이터베이스 sequence 이름
		initialValue = 1, allocationSize = 1)
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
									generator = "MESSAGE_SEQ" // 위의 sequence 이름
	)
    private Long messageSeq;
	
	@NonNull
    private String title;

	@NonNull
	private String content;
	
	@Column(name="send_date")
	@ColumnDefault(value = "SYSDATE")
	private Date sendDate;

	@NotNull
	private String sender;
	
	@NotNull
	private String receiver;

}
