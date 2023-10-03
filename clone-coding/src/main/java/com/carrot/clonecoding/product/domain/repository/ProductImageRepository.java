package com.carrot.clonecoding.product.domain.repository;

import com.carrot.clonecoding.product.domain.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
