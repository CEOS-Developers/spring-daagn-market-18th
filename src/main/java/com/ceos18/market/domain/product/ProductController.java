package com.ceos18.market.domain.product;

import com.ceos18.market.domain.product.dto.ProductRequest;
import com.ceos18.market.domain.product.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        log.info("Product 생성하기");
        Long productNo = productService.createProduct(productRequest);
        return ResponseEntity.ok(productNo);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        log.info("Product 모두 가져오기");
        List<ProductResponse> productResponseList = productService.getAllProduct();
        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        log.info("Product 1개만 가져오기");
        ProductResponse productResponse = productService.getProduct(id);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        log.info("Product 삭제하기");
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
