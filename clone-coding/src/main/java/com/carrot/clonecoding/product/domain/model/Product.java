package com.carrot.clonecoding.product.domain.model;

import com.carrot.clonecoding.common.base.BaseTimeEntity;
import com.carrot.clonecoding.common.enums.Status;
import com.carrot.clonecoding.region.domain.model.Region;
import com.carrot.clonecoding.user.domain.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private long price;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int category;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
}
