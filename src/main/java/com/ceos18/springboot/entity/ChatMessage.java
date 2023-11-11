package com.ceos18.springboot.entity;

import com.ceos18.springboot.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@ManyToOne
	@JoinColumn(name = "chat_room_id", nullable = false)
	private ChatRoom chatRoom;

	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private Member sender;

	@Column(name = "content")
	private String content;

	@Column(name = "is_read")
	private String isRead;
}
