package com.carrot.clonecoding.chat.domain.model;

import com.carrot.clonecoding.common.base.BaseTimeEntity;
import com.carrot.clonecoding.product.domain.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sellerId;
    private int buyerId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;
}
