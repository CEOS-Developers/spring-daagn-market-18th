package com.carrot.clonecoding.product.domain.model;


import com.carrot.clonecoding.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_image")
public class ProductImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String imageUrl;

    @Column(name="main_image")
    private boolean mainImage;

}
