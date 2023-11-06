package com.ceos18.market.domain.product.dto;

import com.ceos18.market.database.Product;
import com.ceos18.market.database.User;
import com.ceos18.market.database.enums.ClothesSize;
import com.ceos18.market.database.enums.StatusCode;
import com.ceos18.market.database.enums.TradingCode;
import com.ceos18.market.database.enums.TradingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long productNo;

    private String userNo;

    private String title;

    private String keyword;

    private String content;

    private TradingCode tradingCode;

    private StatusCode tradingOptionCode;

    private int price;

    private String brand;

    private ClothesSize size;

    private TradingStatus tradingStatus;

    @Builder
    public ProductResponse(Long productNo, String userNo, String title, String keyword, String content,
                          TradingCode tradingCode, StatusCode tradingOptionCode, int price,
                          String brand, ClothesSize size, TradingStatus tradingStatus) {
        this.productNo = productNo;
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

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .productNo(product.getProductNo())
                .userNo(product.getUserNo().getUserNo())
                .title(product.getTitle())
                .keyword(product.getKeyword())
                .content(product.getContent())
                .tradingCode(product.getTradingCode())
                .tradingOptionCode(product.getTradingOptionCode())
                .price(product.getPrice())
                .brand(product.getBrand())
                .size(product.getSize())
                .tradingStatus(product.getTradingStatus())
                .build();
    }
}
