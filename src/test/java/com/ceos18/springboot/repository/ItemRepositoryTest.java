package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ItemRepository itemRepository;

	@BeforeEach
	public void setUp() {
		// 테스트 데이터를 미리 준비
		Item item1 = new Item();
		item1.setTitle("Item 1");
		itemRepository.save(item1);

		Item item2 = new Item();
		item2.setTitle("Item 2");
		itemRepository.save(item2);
	}

	@Test
	public void testFindByTitle() {
		// When
		Item foundItem = itemRepository.findByTitle("Item 1");

		// Then
		assertThat(foundItem).isNotNull();
		assertThat(foundItem.getTitle()).isEqualTo("Item 1");
	}

	@Test
	public void testFindByTitle_NotFound() {
		// When
		Item foundItem = itemRepository.findByTitle("없는 아이템");

		// Then
		assertThat(foundItem).isNull();
	}

}