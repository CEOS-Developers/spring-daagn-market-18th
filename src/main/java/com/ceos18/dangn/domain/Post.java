package com.ceos18.dangn.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotNull
    private String title;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("\"SALE\"")
    @Setter
    private TradeMethod tradeMethod;

    //엥 가격이 없어도 된다
    private int price;

    @Column(columnDefinition="TINYINT(0) DEFAULT 0")
    private boolean isPriceOffer;

    @NotNull //1글자 이상인 듯
    private String description;

    //자주 쓰는 문구는 계정이랑 연결된 것 같은데

    private String brand;
    private String wishPlace;
    private int townRange;
    private String thumbnail;

    @Column(columnDefinition = "TINYINT(0) DEFAULT 0")
    private boolean isTempSave;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("\"SALE\"")
    private PostStatus postStatus;

    @ColumnDefault("0")
    private int view;

    @Column(columnDefinition = "TINYINT(0) DEFAULT 0")
    private boolean isDel;

    //카테고리는 필수
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Setter
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @Builder
    public Post(User seller, String thumbnail, String title, int price, boolean isPriceOffer, String description, String wishPlace, int townRange) {
        this.seller = seller;
        this.thumbnail = thumbnail;
        this.title = title;
        this.price = price;
        this.isPriceOffer = isPriceOffer;
        this.description = description;
        this.wishPlace = wishPlace;
        this.townRange = townRange;
    }
}
