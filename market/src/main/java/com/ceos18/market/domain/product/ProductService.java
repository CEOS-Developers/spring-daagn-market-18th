package com.ceos18.market.domain.product;

import com.ceos18.market.database.Product;
import com.ceos18.market.database.User;
import com.ceos18.market.domain.auth.UserRepository;
import com.ceos18.market.domain.product.dto.ProductRequest;
import com.ceos18.market.domain.product.dto.ProductResponse;
import com.ceos18.market.domain.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createProduct(ProductRequest productRequest) {
        User user = userRepository.findByUserNo(productRequest.getUserNo());
        Product product = Product.of(productRequest, user);
        productRepository.save(product);
        return product.getProductNo();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> productResponseList = productList.stream()
                .map(product -> ProductResponse.from(product))
                .collect(Collectors.toList());
        return productResponseList;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return ProductResponse.from(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException()));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
        productRepository.delete(product);
    }
}
