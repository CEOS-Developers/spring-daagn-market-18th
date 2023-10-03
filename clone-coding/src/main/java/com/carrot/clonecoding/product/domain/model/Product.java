package com.carrot.clonecoding.product.domain.model;

import com.carrot.clonecoding.region.domain.model.Region;
import com.carrot.clonecoding.user.domain.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    public enum Status {
        SOLD_OUT, ON_SALE, RESERVED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

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
