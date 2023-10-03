package com.ceos18.dangn.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotNull
    private String title;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("\"SALE\"")
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

    //카테고리는 필수
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;
}
