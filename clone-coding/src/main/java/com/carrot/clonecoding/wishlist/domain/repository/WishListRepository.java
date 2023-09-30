package com.carrot.clonecoding.wishlist.domain.repository;

import com.carrot.clonecoding.wishlist.domain.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Integer> {
}
