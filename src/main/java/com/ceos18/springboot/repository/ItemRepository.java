package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {

	Item findByTitle(String title);

}