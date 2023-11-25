package com.ceos18.market.domain.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super("Product not found");
    }
}
