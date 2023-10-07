package com.ceos.daangnmarket.domain.product.controller;

import com.ceos.daangnmarket.common.dto.ResponseDto;
import com.ceos.daangnmarket.domain.product.dto.PostProductRequest;
import com.ceos.daangnmarket.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping(value = "")
  public ResponseEntity<?> getAllProduct(){

    return ResponseDto.ok(productService.getAllProduct());
  }

  @GetMapping(value = "/{productId}")
  public ResponseEntity<?> getProductById(
    @PathVariable Long productId
  ){

    return ResponseDto.ok(productService.getProductById(productId));
  }

  @PostMapping(value = "")
  public ResponseEntity<?> postProduct(
    @RequestBody PostProductRequest postProductRequest
  ){

    // TODO: 로그인 구현 후 로그인되어있는 사용자의 Id를 가져오는 것으로 수정할 부분
    Long userId = Long.valueOf(1);

    return ResponseDto.ok(productService.postProduct(postProductRequest, userId));
  }

  @DeleteMapping(value = "/{productId}")
  public ResponseEntity<?> deleteProduct(
    @PathVariable Long productId
  ){

    return ResponseDto.ok(productService.deleteProduct(productId));
  }
}
