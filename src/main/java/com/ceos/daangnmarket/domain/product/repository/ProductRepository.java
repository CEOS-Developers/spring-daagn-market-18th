package com.ceos.daangnmarket.domain.product.repository;

import com.ceos.daangnmarket.domain.product.entity.Product;
import com.ceos.daangnmarket.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<User, Long> {
}
