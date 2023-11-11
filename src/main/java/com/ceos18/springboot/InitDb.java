package com.ceos18.springboot;

import com.ceos18.springboot.domain.entity.Category;
import com.ceos18.springboot.domain.entity.Member;
import com.ceos18.springboot.repository.CategoryRepository;
import com.ceos18.springboot.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.dbInit1();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {

		private final MemberRepository memberRepository;
		private final CategoryRepository categoryRepository;

		public void dbInit1() {
			System.out.println("Init1" + this.getClass());

			Member member = new Member();
			member.setAccount("abc123");
			member.setPassword("1234");
			member.setPhoneNumber("010-1111-2222");
			member.setEmail("abc@gmail.com");

			memberRepository.save(member);

			Category category = new Category();
			category.setName("전자기기");

			categoryRepository.save(category);
		}

	}
}