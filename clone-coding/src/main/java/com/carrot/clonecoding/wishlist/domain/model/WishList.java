package com.carrot.clonecoding.wishlist.domain.model;


import com.carrot.clonecoding.product.domain.model.Product;
import com.carrot.clonecoding.user.domain.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
