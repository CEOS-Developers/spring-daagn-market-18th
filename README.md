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

---

# 🌱3주차 미션

# 프로젝트 패키지 구조

![Untitled 17](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/6dc9be1f-9b94-4e58-953f-0ec5d8de0224)

Post (게시글) 도메인에 대해 CRUD 를 만들어봅시다.

현재 제 Post 엔티티는 아래와 같습니다.

```java
package com.ceos18.springboot.domain.entity;

import com.ceos18.springboot.domain.entity.base.BaseTimeEntity;
import com.ceos18.springboot.domain.entity.enums.DealType;
import com.ceos18.springboot.domain.entity.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "posts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Member seller;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "deal_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private DealType dealType;

	@Column(name = "description")
	private String description;

	@Column(name = "deal_place")
	private String dealPlace;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "is_price_offer", nullable = false)
	private Boolean isPriceOffer;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private PostStatus status;

	@Column(name = "liked_count", nullable = false)
	private int likedCount;

	@Column(name = "view_count", nullable = false)
	private int viewCount;

	@Column(name = "pullup_at")
	private LocalDateTime pullupAt;

}
```

Post 는 Category 와 Member(seller) 를 참조하고 있기 때문에 사전에 Category 와 Member 데이터가 필요합니다.

따라서 임시로 InitDb 라는 클래스에서 스프링이 구동될 때 Category 와 Member DB 를 만들어주도록 했습니다.

`InitDb`

```java
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
			member.setPhoneNumber("010-1111-2222");
			member.setEmail("abc@gmail.com");

			memberRepository.save(member);

			Category category = new Category();
			category.setName("전자기기");

			categoryRepository.save(category);
		}

	}
}
```

Category 와 Member 하나씩을 save 해줍니다.

![Untitled 18](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/7fd61359-0a75-4713-be2b-19d424d6dbde)

![Untitled 19](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/17955e5a-aac5-4f2e-b0f0-91a249b45af4)



# API 설계

![Untitled 20](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/c3309bb8-b973-46ae-b252-916161fb0ea3)

## post 생성

**POST : /api/v1/post**

- parameter : 없음
- request body

```java
{
  "title": "string",
  "categoryId": 0,
  "dealType": "SELL",
  "description": "string",
  "dealPlace": "string",
  "price": 0,
  "isPriceOffer": true
}
```

- 성공 response : post 의 id 를 반환

```java
1
```

## post 전체 조회

**GET : /api/v1/post**

- parameter : 없음
- request body : 없음

- 성공 response

```java
[
  {
    "id": 0,
    "category": {
      "id": 0,
      "name": "string"
    },
    "seller": {
      "id": 0,
      "nickName": "string"
    },
    "title": "string",
    "dealType": "SELL",
    "description": "string",
    "dealPlace": "string",
    "price": 0,
    "isPriceOffer": true,
    "status": "SALE",
    "likedCount": 0,
    "viewCount": 0,
    "pullupAt": "2023-10-07T14:29:51.516Z"
  }
]
```

## post 단건 조회

**GET : /api/v1/post/{postId}**

- parameter : postId
- request body : 없음

- 성공 response

```java
{
  "id": 0,
  "category": {
    "id": 0,
    "name": "string"
  },
  "seller": {
    "id": 0,
    "nickName": "string"
  },
  "title": "string",
  "dealType": "SELL",
  "description": "string",
  "dealPlace": "string",
  "price": 0,
  "isPriceOffer": true,
  "status": "SALE",
  "likedCount": 0,
  "viewCount": 0,
  "pullupAt": "2023-10-07T14:32:25.278Z"
}
```

## post 수정

**PATCH : /api/v1/post/{postId}**

- parameter : `postId`
- request body

    ```java
    {
      "title": "string",
      "categoryId": 0,
      "dealType": "SELL",
      "description": "string",
      "dealPlace": "string",
      "price": 0,
      "isPriceOffer": true
    }
    
    ```


- 성공 response : post 의 id 를 반환

```java
1
```

## post 삭제

**DELETE : /api/v1/post/{postId}**

- parameter : `postId`
- request body : 없음

- 성공 response : post 의 id 를 반환

```java
1
```

# Dto 생성

## request dto

`PostCreateRequestDto`

```java
@Getter
@NoArgsConstructor
public class PostCreateRequestDto {

	private String title;
	private Long categoryId;
	private DealType dealType;
	private String description;
	private String dealPlace;
	private BigDecimal price;
	private Boolean isPriceOffer;

}
```

- Post 를 생성할 때, 클라이언트 단에서 넘어올 값을 정의합니다.

`PostUpdateRequestDto`

```java
@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
	private String title;
	private Long categoryId;
	private DealType dealType;
	private String description;
	private String dealPlace;
	private BigDecimal price;
	private Boolean isPriceOffer;
}
```

- Post 를 수정할 때, 클라이언트 단에서 넘어올 값을 정의합니다.

## response dto

```java
@Getter
@Setter
public class PostReadResponseDto {
	private Long id;
	private CategoryVo category; // 연관관계
	private MemberVo seller; // // 연관관계
	private String title;
	private DealType dealType;
	private String description;
	private String dealPlace;
	private BigDecimal price;
	private Boolean isPriceOffer;
	private PostStatus status;
	private int likedCount;
	private int viewCount;
	private LocalDateTime pullupAt;

	public static PostReadResponseDto fromEntity(Post post) {
		PostReadResponseDto dto = new PostReadResponseDto();
		dto.setId(post.getId());
		dto.setCategory(CategoryVo.fromEntity(post.getCategory()));
		dto.setSeller(MemberVo.fromEntity(post.getSeller()));
		dto.setTitle(post.getTitle());
		dto.setDealType(post.getDealType());
		dto.setDescription(post.getDescription());
		dto.setDealPlace(post.getDealPlace());
		dto.setPrice(post.getPrice());
		dto.setIsPriceOffer(post.getIsPriceOffer());
		dto.setStatus(post.getStatus());
		dto.setLikedCount(post.getLikedCount());
		dto.setViewCount(post.getViewCount());
		dto.setPullupAt(post.getPullupAt());
		return dto;
	}

}
```

dto 에서 response 값을 아래처럼 주기 위해 내부적으로 CategoryVo 와 MemberVo 를 사용했습니다.

```java
{
  "id": 0,
  "category": {
    "id": 0,
    "name": "string"
  },
  "seller": {
    "id": 0,
    "nickName": "string"
  },
  "title": "string",
  "dealType": "SELL",
  "description": "string",
  "dealPlace": "string",
  "price": 0,
  "isPriceOffer": true,
  "status": "SALE",
  "likedCount": 0,
  "viewCount": 0,
  "pullupAt": "2023-10-07T14:32:25.278Z"
}
```

`CategoryVo`

```java
@Getter
@Setter
public class CategoryVo {
	private Long id;
	private String name;

	public static CategoryVo fromEntity(Category category) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setId(category.getId());
		categoryVo.setName(category.getName());
		return categoryVo;
	}

	public Category toEntity() {
		Category category = new Category();
		category.setId(this.id);
		category.setName(this.name);
		return category;
	}
}
```

`MemberVo`

```java
@Getter
@Setter
public class MemberVo {
	private Long id;
	private String nickName;

	public static MemberVo fromEntity(Member member) {
		MemberVo memberVo = new MemberVo();
		memberVo.setId(member.getId());
		memberVo.setNickName(member.getNickName());
		return memberVo;
	}

	public Member toEntity() {
		Member member = new Member();
		member.setId(this.id);
		member.setNickName(this.nickName);
		return member;
	}
}
```

# Controller

`PostApiController`

```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostApiController {
	private final PostService postService;

	// 등록
	@PostMapping("/v1/post")
	public Long createPost(@RequestBody PostCreateRequestDto requestDto) {

		// TODO : 나중에 헤더의 토큰에서 member id 뽑아오는 로직 필요
		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return postService.createPost(requestDto, memberId);
	}

	// 전체 조회
	@GetMapping("/v1/post/")
	public List<PostReadResponseDto> findAllPosts() {
		return postService.findAllPosts();
	}

	// 단건 조회
	@GetMapping("/v1/post/{postId}")
	public PostReadResponseDto findPost(@PathVariable("postId") Long postId) {
		return postService.findPost(postId);
	}

	// 수정
	@PatchMapping("/v1/post/{postId}")
	public Long updatePost(@PathVariable("postId") Long postId, @RequestBody PostUpdateRequestDto requestDto) {

		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return postService.updatePost(requestDto, postId, memberId);
	}

	//삭제
	@DeleteMapping("/v1/post/{postId}")
	public Long deletePost(@PathVariable("postId") Long postId) {

		Long memberId = 1L; // 지금은 임시로 member id를 1 로 설정

		return postService.deletePost(postId, memberId);
	}

}
```

- 권한 체크를 위해 memberId 를 임시로 설정하고 Service 단에 넘겨주어서 체크하도록 했습니다.

# Service

`PostService`

```java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final CategoryRepository categoryRepository;
	private final MemberRepository memberRepository;
//	private final ModelMapper modelMapper;

	/**
	 * 게시글 등록
	 */
	@Transactional
	public Long createPost(PostCreateRequestDto requestDto, Long memberId) {

		Category category = categoryRepository.findById(requestDto.getCategoryId())
				.orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. id=" + requestDto.getCategoryId()));

		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id=" + memberId));

		Post post = Post.builder()
				.category(category)
				.seller(member)
				.title(requestDto.getTitle())
				.dealType(requestDto.getDealType())
				.description(requestDto.getDescription())
				.dealPlace(requestDto.getDealPlace())
				.price(requestDto.getPrice())
				.isPriceOffer(requestDto.getIsPriceOffer())
				.status(PostStatus.SALE)
				.likedCount(0)
				.viewCount(0)
				.build();

		return postRepository.save(post).getId();
	}

	/**
	 * 게시글 전체 조회
	 */
	public List<PostReadResponseDto> findAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream()
				.map(PostReadResponseDto::fromEntity)
				.collect(Collectors.toList());
	}

	/**
	 * 게시글 단건 조회
	 */
	public PostReadResponseDto findPost(Long postId) {
		Post post =  postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

		// return modelMapper.map(post, PostReadResponseDto.class);
		return PostReadResponseDto.fromEntity(post);
	}

	/**
	 * 게시글 수정
	 */
	@Transactional
	public Long updatePost(PostUpdateRequestDto requestDto, Long postId, Long memberId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

		// 게시글 작성자와 수정 요청자가 같은지 확인
		if (post.getSeller().getId() != memberId) {
			throw new IllegalArgumentException("게시글 작성자와 수정 요청자가 같지 않습니다.");
		}

		// requestDto.getCategoryId 가 있을 때
		Category category = null;
		if (requestDto.getCategoryId() != null) {
			category = categoryRepository.findById(requestDto.getCategoryId())
					.orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. id=" + requestDto.getCategoryId()));
		}

		post.setTitle(requestDto.getTitle());

		if (category != null) {
			post.setCategory(category);
		}

		post.setDealType(requestDto.getDealType());
		post.setDescription(requestDto.getDescription());
		post.setDealPlace(requestDto.getDealPlace());
		post.setPrice(requestDto.getPrice());
		post.setIsPriceOffer(requestDto.getIsPriceOffer());

		return postId;
	}

	/**
	 * 게시글 삭제
	 */
	@Transactional
	public Long deletePost(Long postId, Long memberId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

		// 게시글 작성자와 삭제 요청자가 같은지 확인
		if (post.getSeller().getId() != memberId) {
			throw new IllegalArgumentException("게시글 작성자와 삭제 요청자가 같지 않습니다.");
		}

		postRepository.delete(post);
		return postId;
	}

}
```

# Repository

repository 는 별도의 커스텀메스더를 사용하지 않고 JpaRepository 에서 지원하는 메서드를 그대로 사용했습니다.

```java
package com.ceos18.springboot.repository;

import com.ceos18.springboot.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
```

# api 테스트

통합테스트 코드를 따로 짜진 않았고 swagger 상에서 직접 테스트를 진행했습니다.

## post 생성

![Untitled 21](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/b401da9d-0056-4af1-a915-2af86374ff5b)

![Untitled 22](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/609e384e-ae02-4362-bcfa-4cc636e25235)

![Untitled 23](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/dc4fa73f-539f-42c1-aabb-f8e9274caa97)

## post 전체 조회


![Untitled 24](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/3ed57c5e-0155-4e67-af40-e760ae665105)

![Untitled 25](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/89914ad4-da64-483c-8136-928116c6ae1c)

```java
[
  {
    "id": 1,
    "category": {
      "id": 1,
      "name": "전자기기"
    },
    "seller": {
      "id": 1,
      "nickName": null
    },
    "title": "t1",
    "dealType": "SELL",
    "description": "string",
    "dealPlace": "string",
    "price": 0,
    "isPriceOffer": true,
    "status": "SALE",
    "likedCount": 0,
    "viewCount": 0,
    "pullupAt": null
  },
  {
    "id": 2,
    "category": {
      "id": 1,
      "name": "전자기기"
    },
    "seller": {
      "id": 1,
      "nickName": null
    },
    "title": "t2",
    "dealType": "SELL",
    "description": "string",
    "dealPlace": "string",
    "price": 0,
    "isPriceOffer": true,
    "status": "SALE",
    "likedCount": 0,
    "viewCount": 0,
    "pullupAt": null
  }
]
```

## post 단건 조회

![Untitled 26](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/dbb1132d-0a1a-4856-8e27-b132e3110182)

![Untitled 27](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/57804ff0-8c12-4f63-b158-fc4d93069102)

## post 수정

![Untitled 28](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/d2f735c8-dc8d-4e48-a57c-dfd6fdbbc809)

title 을 수정해보겠습니다

![Untitled 29](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/81abe3ec-736d-4b34-a1b7-ff8afdbe72e6)

## post 삭제

![Untitled 30](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/ebe110fc-19aa-4fad-b689-965d6ae4c4a8)

![Untitled 31](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/48885608/776af15d-7ea7-408f-b47d-a2a951e14b0f)

---

# 느낀 점 및 고민

- 지연로딩 세팅 및 N+1 문제 해결해야 합니다
- controller 에서 service 단에 데이터를 넘겨줄 때 어떻게 해야 더 깔끔하게 넘겨줄 수 있을 지 고민입니다.
- service 단에서 인터페이스와 impl 패턴 사용 시 장단점과 프로젝트에서 적용 여부에 대한 고민
- vo 를 이렇게 쓰는게 맞는건지에 대한 의문
- 커맨드, 쿼리를 분리하는 패턴 또한 우리 수준 프로젝트에서 유의미한 장점이 있을까 하는 고민