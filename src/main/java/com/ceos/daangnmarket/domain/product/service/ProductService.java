package com.ceos.daangnmarket.domain.product.service;

import com.ceos.daangnmarket.domain.area.entity.EmdArea;
import com.ceos.daangnmarket.domain.area.repository.EmdAreaRepository;
import com.ceos.daangnmarket.domain.product.dto.PostProductRequest;
import com.ceos.daangnmarket.domain.product.entity.Category;
import com.ceos.daangnmarket.domain.product.entity.Product;
import com.ceos.daangnmarket.domain.product.repository.CategoryRepository;
import com.ceos.daangnmarket.domain.product.repository.ProductRepository;
import com.ceos.daangnmarket.domain.user.entity.User;
import com.ceos.daangnmarket.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final EmdAreaRepository emdAreaRepository;
  private final CategoryRepository categoryRepository;

  public List<Product> getAllProduct(){
    return productRepository.findAll();
  }

  public Product getProductById(Long productId){
    return productRepository.findById(productId).orElseThrow();
  }

  public Product postProduct(PostProductRequest postProductRequest, Long userId){
    User user = userRepository.findById(userId).orElseThrow();
    EmdArea emdArea = emdAreaRepository.findById(postProductRequest.getAreaId()).orElseThrow();
    Category category = categoryRepository.findById(postProductRequest.getCategoryId()).orElseThrow();

    Product createdProduct = productRepository.save(
      Product.builder()
        .user(user)
        .emdArea(emdArea)
        .category(category)
        .title(postProductRequest.getTitle())
        .status(postProductRequest.getStatus())
        .sellPrice(postProductRequest.getSellPrice())
        .viewCount(0)
        .description(postProductRequest.getDescription())
        .build()
    );

    return createdProduct;
  }

  @Transactional
  public String deleteProduct(Long productId){
    Product product = productRepository.findById(productId).orElseThrow();

    productRepository.delete(product);

    return "Product #"+productId+" deleted successfully";
  }
}
