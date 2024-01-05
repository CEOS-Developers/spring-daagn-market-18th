package com.ceos18.market.database;

import com.ceos18.market.database.base.BaseTimeEntity;
import com.ceos18.market.database.enums.ClothesSize;
import com.ceos18.market.database.enums.TradingCode;
import com.ceos18.market.database.enums.StatusCode;
import com.ceos18.market.database.enums.TradingStatus;
import com.ceos18.market.domain.product.dto.ProductRequest;
import com.ceos18.market.domain.product.dto.ProductResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "PROD_LIST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROD_NO")
    private Long productNo;

    @ManyToOne
    @JoinColumn(name = "USR_NO")
    private User userNo;

    @Column(name = "PROD_TIT")
    @NotNull
    private String title;

    @Column(name = "PROD_KEYW", length = 10)
    @NotNull
    @Size(max = 10)
    private String keyword;

    @Column(name = "PROD_CONT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRD_CD", columnDefinition = "CHAR(5)")
    private TradingCode tradingCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRD_OPT_CD", columnDefinition = "CHAR(1)")
    private StatusCode tradingOptionCode;

    @Column(name = "PROD_PRC", length = 10)
    @NotNull
    private int price;

    @Column(name = "PROD_BRN", length = 20)
    @Size(max = 20)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROD_SIZ", columnDefinition = "CHAR(4)")
    private ClothesSize size;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRD_STAT", columnDefinition = "CHAR(11)")
    @ColumnDefault("'PROCEEDING'")
    private TradingStatus tradingStatus;

    @Builder
    public Product(User userNo, String title, String keyword,
                   String content, TradingCode tradingCode, StatusCode tradingOptionCode,
                   int price, String brand, ClothesSize size, TradingStatus tradingStatus) {
        this.userNo = userNo;
        this.title = title;
        this.keyword = keyword;
        this.content = content;
        this.tradingCode = tradingCode;
        this.tradingOptionCode = tradingOptionCode;
        this.price = price;
        this.brand = brand;
        this.size = size;
        this.tradingStatus = tradingStatus;
    }

    public static Product of(ProductRequest productRequest, User user) {
        return Product.builder()
                .userNo(user)
                .title(productRequest.getTitle())
                .keyword(productRequest.getKeyword())
                .content(productRequest.getContent())
                .tradingCode(productRequest.getTradingCode())
                .tradingOptionCode(productRequest.getTradingOptionCode())
                .price(productRequest.getPrice())
                .brand(productRequest.getBrand())
                .size(productRequest.getSize())
                .tradingStatus(productRequest.getTradingStatus())
                .build();
    }
}
