<div align="center">

# Spring Practice 🌱

<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/><br/>
<b>CEOS 18th Backend Study</b><br><br>




### 📒 목차
[2주차 | DB 모델링과 JPA](#2주차-미션-주제) <br><br>

### 2주차 미션 주제
### 당근 마켓의 DB를 모델링한다 🥕
<br>

</div>

#### ✦ 클론 코딩할 당근 마켓 서비스<br>
1. <b>물건을 올려서</b> 팔거나 찾을 수 있다.
2. 물건을 올린 유저와 <b>1:1 채팅</b>을 할 수 있다.
3. 물건을 구매 후 <b>구매 확정</b>할 수 있다.
4. 판매자나 구매자로서 <b>리뷰</b>를 달아 유저의 <b>매너온도</b>를 결정할 수 있다.

#### ✦ DataBase 설계

- [ERDCloud Link](https://www.erdcloud.com/d/yfayiX7p9ZFCZWuhZ)
  ![CEOS_STUDY](https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/a22745b2-96d8-467c-ac39-19f48414f748)

- 도메인 설명
  1. <b>Member</b><br>
  매너온도, 재거래희망률, 응답률을 가진다.
  2. <b>Review</b><br>
  리뷰 쓴 유저와 리뷰 받은 유저를 Foreign key로 저장한다. is_buyer라는 속성으로 리뷰 남긴 이가 구매자인지 판매자인지 구별한다.
  3. <b>Post</b><br>
  가격 제안을 받는 여부, 구매 상태(판매 중, 예약 중, 거래 완료), 거래 희망 장소, 작성자의 pk 등 당근마켓의 게시물 요소를 반영하였다.
  4. <b>PostImage</b><br>
  한 게시물 당 8개까지 이미지를 올릴 수 있다. 게시물과 이미지가 Many-to-one 관계이므로 게시물 이미지 테이블을 생성하였다.
  5. <b>Purchase</b><br>
  구매자와 게시글을 Foreign key로 저장하고 구매 확정 여부를 저장한다.
  6. <b>Chatting</b><br>
  한 게시글 보고 여러 유저가 게시자에게 채팅할 수 있다. 따라서 채팅방 테이블에는 게시글, 채팅 한 유저, 채팅 받은 유저의 Foreign key를 저장한다.
  7. <b>Message</b><br>
  한 채팅방 안에서 유저끼리 주고 받은 메시지에 대한 테이블이다. 메시지를 보낸 사람을 구별하는 boolean 속성, 메시지 내용, 작성 시각을 저장한다. 
- 구현<br>

공통 사항
```java
// primary key 설정
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

// foreign key 설정: 지연 로딩 사용
@JoinColumn(nullable = false)
@ManyToOne(fetch = FetchType.LAZY)
private Post post;
```

객체 생성일 속성
```java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

@CreatedDate
@Column(updatable = false, nullable = false)
private LocalDateTime createdDate;

}
```

Post Entity의 status 속성은 enum을 사용
```java
// Post.java
@Column(nullable = false)
@Enumerated(EnumType.STRING)
private PostStatus status;

// PostStatus.java
public enum PostStatus {

  SELLING, RESERVATION, COMPLETION

}
```

#### ✦ Repository 계층 단위 테스트
- @DataJpaTest 는 JPA와 관련된 요소들(@Repository, @Entity)만 읽어와서 spring context를 만들고 @Test 함수가 실행가능하게 만든다.
```java
@DataJpaTest
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private MemberRepository memberRepository;

  @Test
  @DisplayName("게시글을 생성하고 조회한다.")
  void 게시글_생성하고_조회하기(){
    // given
    Member 테스트유저1 = Member.builder().nickname("테스트 유저1").town("서울시 마포구 대흥동").phoneNumber("00000000000").build();
    Member 테스트유저2 = Member.builder().nickname("테스트 유저2").town("서울시 마포구 대흥동").phoneNumber("00000000000").build();

    Post 게시글1 = Post.builder().title("테스트 제목1").price(15000).address("테스트 거래 주소1").isAuction(false).writer(테스트유저1).build();
    Post 게시글2 = Post.builder().title("테스트 제목2").price(15000).address("테스트 거래 주소2").isAuction(false).writer(테스트유저1).build();
    Post 게시글3 = Post.builder().title("테스트 제목3").price(15000).address("테스트 거래 주소3").isAuction(false).writer(테스트유저2).build();

    // when
    memberRepository.save(테스트유저1);
    memberRepository.save(테스트유저2);
    postRepository.save(게시글1);
    postRepository.save(게시글2);
    postRepository.save(게시글3);

    List<Post> 저장된_게시글들 = postRepository.findAll();

    // then
    assertThat(저장된_게시글들).hasSize(3)
        .extracting(Post::getTitle)
        .contains("테스트 제목1", "테스트 제목2", "테스트 제목3");
  }

}
```
- 테스트 실행 시 쿼리문 확인하기
  1. Member 객체 저장
<img width="730" alt="스크린샷 2023-09-30 오후 10 24 57" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/38862713-3c5f-4f4c-9d0c-b43f56205a30">

  2. Post 객체 저장
<img width="730" alt="스크린샷 2023-09-30 오후 10 25 27" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/21f09f9d-790e-4421-b97f-dcaf44efd84e">

  3. 모든 Post 객체 조회
<img width="730" alt="스크린샷 2023-09-30 오후 10 25 47" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/c8c0870a-5722-49cf-bbdb-2cd550d2288b">

#### ✦ 느낀 점 및 배운 점<br>
레포지토리 계층 테스트는 처음 해보아서 실행 결과를 보고 스프링 컨테이너가 올라가는 줄 알았으나, @DataJpaTest는 JPA 관련 요소들만 읽어서 spring context를 만든다는 것을 알게 되었다. 앞으로 Data Jpa 메서드를 쿼리문을 직접 작성하는 등 커스텀 한다면 레포지토리 계층 테스트부터 차근차근 진행해야겠다. 