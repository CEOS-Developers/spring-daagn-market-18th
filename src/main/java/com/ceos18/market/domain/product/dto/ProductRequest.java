package com.ceos18.market.domain.product.dto;

import com.ceos18.market.database.Product;
import com.ceos18.market.database.User;
import com.ceos18.market.database.enums.ClothesSize;
import com.ceos18.market.database.enums.StatusCode;
import com.ceos18.market.database.enums.TradingCode;
import com.ceos18.market.database.enums.TradingStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequest {
    @NotEmpty(message = "유저 정보를 작성해주세요")
    @Valid
    private String userNo;

    @NotEmpty(message = "제목을 작성해주세요")
    @Valid
    private String title;

    @NotEmpty(message = "키워드를 골라주세요")
    @Valid
    private String keyword;

    @NotEmpty(message = "상세 설명을 작성해주세요")
    @Valid
    private String content;

    private TradingCode tradingCode;

    private StatusCode tradingOptionCode;

    @NotNull(message = "가격을 입력해주세요")
    @Valid
    private int price;

    private String brand;

    private ClothesSize size;

    private TradingStatus tradingStatus;

    @Builder
    public ProductRequest(String userNo, String title, String keyword, String content,
                          TradingCode tradingCode, StatusCode tradingOptionCode, int price,
                          String brand, ClothesSize size, TradingStatus tradingStatus) {
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

}
