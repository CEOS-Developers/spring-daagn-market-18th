package com.ceos18.springboot.repository;

import com.ceos18.springboot.entity.Category;
import com.ceos18.springboot.entity.Member;
import com.ceos18.springboot.entity.Post;
import com.ceos18.springboot.entity.enums.DealType;
import com.ceos18.springboot.entity.enums.PostStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private PostRepository postRepository;

	@Test
	public void testSaveAndFind() {
		// Given

		Category category = Category.builder()
				.name("도서")
				.build();

		Member member = Member.builder()
				.phoneNumber("010-1111-2222")
				.isWithdrawal(false)
				.mannerRating(BigDecimal.valueOf(36.5))
				.region("서울")
				.build();

		em.persist(category);
		em.persist(member);


		Post post1 = Post.builder()
				.category(category)
				.seller(member)
				.title("title")
				.dealType(DealType.SELL)
				.isPriceOffer(true)
				.status(PostStatus.SALE)
				.likedCount(0)
				.viewCount(0)
				.build();

		Post post2 = Post.builder()
				.category(category)
				.seller(member)
				.title("title")
				.dealType(DealType.SELL)
				.isPriceOffer(true)
				.status(PostStatus.SALE)
				.likedCount(0)
				.viewCount(0)
				.build();

		Post post3 = Post.builder()
				.category(category)
				.seller(member)
				.title("title")
				.dealType(DealType.SELL)
				.isPriceOffer(true)
				.status(PostStatus.SALE)
				.likedCount(0)
				.viewCount(0)
				.build();


		// When
		em.persist(post1);
		em.persist(post2);
		em.persist(post3);


		List<Post> posts = postRepository.findAll();

		// Then
		assertThat(posts).hasSize(3);
		assertThat(posts).contains(post1, post2, post3);
	}
}