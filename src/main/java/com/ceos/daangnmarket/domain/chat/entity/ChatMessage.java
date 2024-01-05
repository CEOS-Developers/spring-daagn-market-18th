package com.ceos.daangnmarket.domain.chat.entity;

import com.ceos.daangnmarket.common.BaseEntity;
import com.ceos.daangnmarket.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sender_id")
  private Account account;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  private String message;

  private Boolean readOrNot;

}
