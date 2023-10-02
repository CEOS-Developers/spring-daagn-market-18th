# 당근마켓 클론

---

# 🌱 2주차 미션

## 1️⃣ 당근 마켓의 DB를 모델링해요

- 물건 올리기
- 물건 찾기
- 1:1 채팅
- 구매 확정
- 리뷰(온도)

[당근마켓 모델링](https://www.erdcloud.com/d/za7jEwYXR2iz545rX)

![Untitled](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/70761da6-0ecf-4e9c-9cd4-818511cd2586)

DB 모델링

| 엔티티명(논리) | 테이블명(물리) | 설명 |
| --- | --- | --- |
| 회원 | members | 회원 정보를 저장 |
| 게시글 | posts | 회원은 여러 개의 게시글(중고물품)을 올릴 수 있음 |
| 게시글 이미지 | item_images | 게시글의 이미지 링크를 저장 |
| 카테고리 | categories | 게시글의 카테고리 범주 |
| 거래 완료 | completed_deals | 완료된 거래 |
| 거래 후기 | deal_reviews | 완료된 거래의 거래 후기 |
| 채팅방 | chat_rooms | 중고물건 게시글에 생성되는 채팅방 |
| 회원-채팅방 (연결테이블) | members_chatrooms | 회원 과 채팅방 을 매핑하는 테이블 |
| 채팅 메시지 | chat_messages | 채팅 발신자와 내용을 저장 |

### 모델링 과정에서 한 고민

**1. FK 를 꼭 다 써야할까?**  

   FK 쓸 때 장점 : 데이터 무결성, join 성능

   FK 안 쓸 때 장점 : 개발 편의성 향상, 확장에 유리, 수동으로 DB 작업이 편리, 진짜 혹시도 모를 대참사 방지(?)

   ( + FK 를 안 쓴다면 join 성능을 위해 인덱스 사용이 거의 필수적 )

   과제 모델링에서는 FK 를 적용했음


**2. 식별 vs 비식별**

   유연한 설계를 위해 비식별 관계를 선호함


**3. 당근마켓의 로그인 방식에 따른 회원 엔티티 설계**

  ![Untitled 1](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/a7aa3e79-37fd-41ce-afa0-00029569322b)

   회원가입을 할 때 아이디, 패스워드 방식이 아닌 휴대폰 번호로 전송된 인증 코드를 인증하는 방식으로 회원가입이 완료된다. 아이디가 휴대폰 번호가 되는셈이고 패스워드는 따로 없다.



**4. 엔티티 네이밍 : 중고물건(items,product..) vs 게시글(post..)**

   일반적인 이커머스와 다르게 중고거래는 **물품**이 **게시글**의 역할도 겸한다.

   네이밍을 어떻게 할까 고민을 했는데, **게시글(posts)** 로 결정함

   근거

    - 당근 서비스 내 에서 글 쓰기 버튼을 클릭한 뒤 중고물품을 올리는 폼이 뜸
    - 물품에 대한 채팅방 보다는 게시글에 대한 채팅방이 더 자연스럽다고 생각

<br>

**5. 거래 완료 와 거래 후기**

   ![Untitled 2](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/a7a69063-95f5-4d16-87e5-20c4ac754776)

   거래 완료 처리를 위해 items 테이블에 판매자와 구매자 컬럼을 둘 다 놓을 수 도 있겠지만, 거래 완료 테이블을 별도로 분리했음 <br><br>

   근거

    - nullable 한 FK 를 최대한 줄이고 싶었음 (구매자 ID)
    - 거래 라는 비즈니스 로직에서 거래 완료는 핵심 비즈니스 이므로 별도로 분리하는 것이 괜찮겠다고 생각함

<br>
   회원의 게시글 조회, 회원의 거래 완료 조회, 회원의 거래 후기 조회 의 성능을 위해 각 테이블에 member 를 참조한 FK 를 둠

<br>


**6. 회원의 채팅방을 조회할 때의 성능 고민**

- case 1) chat_room 테이블에 게시글(posts) 와 회원(members) 을 참조하는 FK가 존재

  ![Untitled 3](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/eef35ca7-9d2a-49e4-8799-65bd14bf49f9)


- 내가 판매자인 채팅방 조회

  members → posts → chat_rooms : 삼중조인 or `where…in` 사용

- 내가 구매(희망)자인 채팅방 조회

  members → chat_rooms : 조인


- case 2) chat_room 테이블에 판매자(seller_id) 컬럼을 FK 로 추가

  ![Untitled 4](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/ea59e70b-4b96-4d56-8cc7-b9eb97bc903f)


chat_room 테이블에 판매자 id (member 참조 FK) 를 두는 역정규화

- 내가 판매자인 채팅방 조회

  members → chat_rooms : 조인

- 내가 구매자인 채팅방 조회

  members → chat_rooms : 조인


⇒ posts 테이블과 무관해지므로 case 1 보다 더 개선됨

- case 3) many to many 사이에 join table 추가

  ![Untitled 5](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/ba099f93-b8ff-4dbe-aac4-0d154e38a901)


- 내가 판매자,구매자인 채팅방 조회

  members → members_chatrooms → chat_rooms : 삼중조인


장점

- 판매자, 구매자에 대한 로직 분기가 필요 없어짐
- 채팅방 에 대한 확장성도 증가해서 이 방식을 최종적으로 선택

  ⇒ 1:1 채팅이 아닌 단체 채팅도 쉽게 구현 가능


### 테이블 상세 설명

모든 테이블에 createdDate 와 updatedDate 컬럼이 존재합니다 (ERD 상에는 생략됨)
    


- 회원 (members)

  ![Untitled 6](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/785e117c-4465-4234-87a1-02c3f91397a7)


- 회원탈퇴여부 : 탈퇴 시, DELETE 가 아닌 UPDATE 로 데이터 유지 및 사기 유저 방지
- 지역 : 깊게 들어가면 고려할 사항이 너무 많아서 우선은 단순 string 으로 처리
  

- 게시글 (posts)

  ![Untitled 7](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/f80565c5-5100-4787-ab05-eb8f6055a4a4)
  

- 게시글 이미지 (post_images)

  ![Untitled 8](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/ca95419f-0c42-463a-82cb-596e1df2c7ec)
  

- 카테고리 (categories)

  ![Untitled 9](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/31e71156-24e0-4638-b99c-9e35f1d1ee6b)
  

- 거래 완료 (completed_deals)

  ![Untitled 10](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/b422bd45-0e30-4546-8412-1ff94a4ffe9c)
  

- 거래 후기 (deal_reviews)

  <img width="318" alt="Untitled 11" src="https://github.com/itsme-shawn/itsme-shawn/assets/48885608/31cf03d8-8ccd-4613-ab6a-68f554f2cc6d">
  

- 채팅방 (chat_rooms)

  <img width="227" alt="Untitled 12" src="https://github.com/itsme-shawn/itsme-shawn/assets/48885608/78d0ce8d-bb05-4ace-92ce-6d4289b32f7e">
  

- 회원-채팅방 (members_chatrooms)

  <img width="267" alt="Untitled 13" src="https://github.com/itsme-shawn/itsme-shawn/assets/48885608/e95b1fd6-4ad0-4496-bba8-99e386203f36">
  

- 채팅 메시지 (chat_messages)

  <img width="302" alt="Untitled 14" src="https://github.com/itsme-shawn/itsme-shawn/assets/48885608/a63073e1-d12d-4f36-87df-4d2c200a3290">


### 전체 연관 관계

![Untitled 15](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/ff9c4d87-c258-479e-87ed-2a1e0dbb4126)

---

## 2️⃣ Repository 단위 테스트를 진행해요

Post Entity 에대한 Repository 를 만들고 @DataJpaTest 를 통해 단위테스트를 해보자

```java
package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Category;
import com.ceos18.springboot.domain.entity.Member;
import com.ceos18.springboot.domain.entity.Post;
import com.ceos18.springboot.domain.entity.enums.DealType;
import com.ceos18.springboot.domain.entity.enums.PostStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
```

- Post Entity 는 Category 와 Member 를 참조하고 있어서 Category 와 Member 인스턴스를 먼저 생성한 뒤 persist 해줘야 한다.
  <br><br>
- 테스트 통과

    ![Untitled 16](https://github.com/itsme-shawn/itsme-shawn/assets/48885608/122b2edd-f297-4e2c-b0b9-b2a18fb91891)

## TroubleShooting

- java springboot 에서는 패키지명으로 enum 이 안 된다
- 정확한 실수 계산이 필요할 때에는 float, double 대신 BigDecimal 을 사용하자
- [https://codingdog.tistory.com/entry/mysql-decimal-vs-double-고정-소수점과-부동-소수점](https://codingdog.tistory.com/entry/mysql-decimal-vs-double-%EA%B3%A0%EC%A0%95-%EC%86%8C%EC%88%98%EC%A0%90%EA%B3%BC-%EB%B6%80%EB%8F%99-%EC%86%8C%EC%88%98%EC%A0%90)