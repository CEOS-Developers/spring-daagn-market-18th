package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private String id; // PK

	@ManyToOne
	@Column(name = "chat_room_id")
	private ChatRoom chatRoom; // FK

	@ManyToOne
	@Column(name = "sender_id")
	private Member sender; // FK

	private String content;

	private Boolean isRead;

}
