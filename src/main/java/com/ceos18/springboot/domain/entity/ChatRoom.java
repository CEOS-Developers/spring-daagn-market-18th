package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private String id; // PK

	@ManyToOne
	@Column(name = "items_id")
	private Item itemId; // FK

	@ManyToOne
	@Column(name = "seller_id")
	private Member seller; // FK

	@ManyToOne
	@Column(name = "temp_buyer_id")
	private Member tempBuyer; // FK

}
