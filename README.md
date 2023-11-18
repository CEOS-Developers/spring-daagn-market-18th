<div align="center">

# Spring Practice 🌱

<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/><br/>
<b>CEOS 18th Backend Study</b><br><br>




### 📒 목차
[2주차 | DB 모델링과 JPA](#2주차-미션-주제) <br>
[3주차 | CRUD API](#3주차-미션-주제) <br>
[4주차 | Spring Security](#4주차-미션-주제) <br>
[5주차 | Docker](#5주차-미션-주제) <br><br>

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
  ![CEOS_STUDY (1)](https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/e77a4a91-4398-4023-96b2-554e4c727add)

- 도메인 설명
  1. <b>Member</b><br>
  매너온도, 재거래희망률, 응답률을 가진다.
  2. <b>MannerEvaluation</b><br>
  리뷰 쓴 유저와 리뷰 받은 유저를 Foreign key로 저장한다. is_buyer라는 속성으로 리뷰 남긴 이가 구매자인지 구별한다. is_positive로는 긍정 매너 평가인지 부정 매너 평가인지 표시한다. 추후 유저의 매너 온도에 반영된다.
  3. <b>Post</b><br>
  가격 제안을 받는 여부, 구매 상태(판매 중, 예약 중, 거래 완료), 거래 희망 장소, 작성자의 pk 등 당근마켓의 게시물 요소를 반영하였다.
  4. <b>PostImage</b><br>
  한 게시물 당 8개까지 이미지를 올릴 수 있다. 게시물과 이미지가 Many-to-one 관계이므로 게시물 이미지 테이블을 생성하였다.
  5. <b>Purchase</b><br>
  구매자와 게시글을 Foreign key로 저장하고 구매 확정 여부를 저장한다.<br>
  `🚨 고려 사항: Post 속성으로 구매자와 구매 확정 여부가 존재할 경우 null 값에서 '구매가 되었을 때' 값이 업데이트 되므로 불필요한 null은 선호하지 않아 따로 구매(Purchase) 테이블을 만들었다.`<br>
  6. <b>Chatting</b><br>
  한 게시글 보고 여러 유저가 게시자에게 채팅할 수 있다. 따라서 채팅방 테이블에는 게시글, 채팅 한 유저, 채팅 받은 유저의 Foreign key를 저장한다.<br>
  `🚨 고려 사항: 게시글의 작성자와 채팅 받은 유저가 동일하지만, 게시글을 통해 작성자를 조회하는 것은 추가적인 join으로 성능 저하가 발생하므로 채팅 받은 유저도 저장한다.` <br>
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

Setter 사용은 지양하고 심플 빌더 패턴(Builder 어노테이션)을 사용 <br>
`➡️ 초기화 값을 명시하고 순서 관계 없이 값을 넣을 수 있으며 null 값을 일일히 넘겨주지 않아도 된다. 가독성도 좋다.`
```java
@Builder
public Member(String nickname, String town, String icon, String phoneNumber){
  this.nickname = nickname;
  this.town = town;
  this.icon = icon;
  this.phoneNumber = phoneNumber;
  this.temperature = 36.5;
  this.redeal = 0;
  this.responseRate = 0;
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
    memberRepository.saveAll(List.of(테스트유저1,테스트유저2));
    postRepository.saveAll(List.of(게시글1, 게시글2, 게시글3));

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

#### ✦ 추가 스터디 과제 질문들<br>
1. 어떻게  Data JPA는 <b>interface</b>만으로도 함수가 구현이 되는가?<br>
➡️ 인터페이스 JpaRepository를 구현한 SimpleJpaRepository를 타겟으로 프록시를 생성해서 빈으로 등록한다. 이때 프록시는 런타임에 동적으로 인스턴스가 변경되는 다이나믹 프록시 기법으로 구현된다. 프록시 클래스를 바이트코드( .class file )로 직접 만든다. <br><br>
<img width="784" alt="스크린샷 2023-10-01 오후 12 40 54" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/392742e6-b8cf-4e20-b282-f5d3c7625ca2"> <br><br>
   - 다이나믹 프록시 기법
     - 대상 클래스마다 프록시 클래스를 만들 경우 코드 중복과 번거로움이 생기기 때문에 컴파일 시점이 아니라 런타임 시점에 프록시 클래스를 만든다.
     - Java의 reflection API가 제공하는 <b>newProxyInstance()</b>로 런타임 시점에 프록시 클래스를 만들 수 있다.
     ```java
      class Proxy {
        @CallerSensitive
        public static Object newProxyInstance(
        ClassLoader loader, // 프록시 클래스를 만들 클래스로더
        Class<?>[] interfaces, // 프록시 클래스가 구현할 인터페이스 목록(배열)
        InvocationHandler h) // 메서드가 호출되었을때 실행될 핸들러
        {}
      } 
     ```
     - 인터페이스 <b>InvocationHandler</b>의 <b>invoke</b> 메서드가 동적 프록의 메서드가 호출되었을 때 실행된다.
     ```java
      public interface InvocationHandler {
      public Object invoke(
      Object proxy, // 프록시 객체
      Method method, // 호출한 메서드 정보
      Object[] args) // 메서드에 전달된 파라미터
      throws Throwable;
      }
     ```
     - 예제 코드로 다이나믹 프록시로 구현된 Spring Data JPA 이해하기
     ```java
     // 우리가 사용하는 Data JPA 인터페이스 
     public interface JpaRepository{
      void save(Object item);
     }
     
     // 실제 구현체
     public class SimpleJpaRepository implements  JpaRepository{
      @Override
      public void save(Object item){
        log.info("Save {}", item);
      }
     }
     
     // 우리가 작성한 레포지토리
     public interface MyRepository extends JpaRepository{
     }
     
     // reflection API 메서드를 사용하기 위한 재료 - invocationHandler
     public class RepositoryHandler implements InvocationHandler{
     
      private final JpaRepository target;
     
      public RepositoryHandler(JpaRepository target){
        this.target = target;
      }
     
      @Override
      public Object invoke(Object proxy, Method method, Object[] args){
        log.info("Save in Proxy");
        return method.invoke(target, args);
      }
     
     }
     
     // 스프링처럼 프록시 인스턴스를 사용해보기
     public class DynamicProxyTest{
      @Test
      void 동적프록시_Reflection으로_구현하기(){
        RepositoryHandler repositoryHandler = new RepositoryHandler(new SimpleJpaRepository());
        MyRepository myRepository = (MyRepository) Proxy.newProxyInstance(
                                    MyRepository.class.getClassLoader(),
                                    new Class[]{MyRepository.class},
                                    repositoryHandler);
        myRepository.save("NewItem");
      }
     }
     ```
     - MyRepository에 findAllByName과 같이 메서드를 만들면 동적으로 Proxy 인스턴스에 생성된다.
2. Data jpa를 찾다보면 SimpleJpaRepository에서  <b>entity manager</b>를 생성자 주입을 통해서 주입 받는다. 근데 <b>싱글톤</b> 객체는 한번만 할당을  받는데, 한번 연결 때 마다 생성이 되는 entity manager를 생성자 주입을 통해서 받는 것은 수상하지 않는가? 어떻게 되는 것일까?<br>
➡️ 주입 받는 Entity Manamger 역시 프록시 객체이다. Entity Manager의 메서드를 호출하면 SharedEntityManagerInvocationHandler를 호출하여 메서드를 실행한다. 현재 데이터베이스 트랜잭션과 관련된 실제 EntityManager의 메서드를 호출할 수 있다. 싱글톤은 유지하면서 동시성 이슈를 해결할 수 있다.
<img width="1300" alt="스크린샷 2023-10-02 오후 3 14 31" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/021aa6f4-f444-435c-a208-c3dd2044b9a6"><br><br>
   - 디버깅한 방법
   ```java
    // 영속성 매니저를 주입 받는 레포지토리 만들기
    @Repository
    @Transactional
    public class RepositoryForEmTest {
    
      private EntityManager em;
    
      @Autowired
      public RepositoryForEmTest(EntityManager em){
        this.em = em;
      }
    
      public void save(){
        em.persist(Member.builder().nickname("유저").town("서울시 마포구 대흥동").phoneNumber("00000000000").build());
      }
    }
   
    @TestConfiguration
    public class TestConfig {
    
      @PersistenceContext
      private EntityManager em;
    
      @Bean
      public RepositoryForEmTest repositoryForEmTest(){
        return new RepositoryForEmTest(em);
      }
    
    }
   
    // JPA repository가 아니기에 TestConfig에서 레포지토리를 등록해주어야 @DataJpaTest에서 사용할 수 있음
    @DataJpaTest
    @Import(TestConfig.class)
    public class JpaRepositoryTest {
    
      @Autowired
      private RepositoryForEmTest repository;
   
      @Test
      @DisplayName("스프링 레포지토리에 Entity Manager가 주입되는 방식을 확인한다.")
      void EntityManager_주입되는방식_확인하기() {
        repository.save();
        logger.info("Entity Manager Info: {}", repository.getEm().getClass());
      }
    }

   ```

#### ✦ 느낀 점 및 배운 점<br>
레포지토리 계층 테스트는 처음 해보아서 실행 결과를 보고 스프링 컨테이너가 올라가는 줄 알았으나, @DataJpaTest는 JPA 관련 요소들만 읽어서 spring context를 만든다는 것을 알게 되었다. 앞으로 Data Jpa 메서드를 쿼리문을 직접 작성하는 등 커스텀 한다면 레포지토리 계층 테스트부터 차근차근 진행해야겠다.<br>
추가 과제 질문들에 대한 답을 찾아보면서 프록시가 스프링에서 어떻게 사용되는지 실제 코드를 바탕으로 이해할 수 있었다. 그저 받아들이면서 공부를 끝내지 않을 수 있었고 미처 생각치 못한 의문을 해결할 수 있었다.
<br><br><br>

<div align="center">

### 3주차 미션 주제
### CRUD API를 만든다. 🛠️

</div>

#### ✦ API 문서<br>

<img width="1012" alt="스크린샷 2023-11-06 오후 6 07 41" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/cc75179d-a3ae-4bcb-8232-0dcc5d37fccb">
<img width="1012" alt="스크린샷 2023-11-06 오후 6 07 49" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/ac3e2f28-d20f-49fc-bae2-9780de811fd3">
<img width="1012" alt="스크린샷 2023-11-06 오후 6 08 01" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/af80898a-688f-49d7-beae-eddb779c6dd0">
<img width="1012" alt="스크린샷 2023-11-06 오후 6 08 08" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/bcee4e00-5fea-4544-8492-b2127c38d032">

#### ✦ 테스트 방식<br>
- DatabaseCleaner: 각 테스트 시 데이터베이스와 영속성 컨텍스트를 지운다.<br>
- SpringBootTest: 의존적인 레포지토리의 기능을 모두 사용하기 위해 스프링부트테스트로 진행하였다.<br><br>

#### ✦ 배운 점<br> 
- <b>조회 최적화: 쿼리 날리는 개수 즐이기</b><br><br>
이전 서비스 코드
   ```java
    // 각 post에 대해 이미지를 찾음
    for(Post post: postList){
      String image = null;
      Optional<PostImage> postImageOptional = postImageRepository.findFirstByPost(post);
      if (postImageOptional.isPresent()) {
          PostImage postImage = postImageOptional.get();
          image = postImage.getImageUrl();
      }
      responses.add(PostListResponse.fromEntity(post, image));
    }
   ```
  <br>
  리팩토링 후 서비스 코드
  
   ```java
  for(Object[] postAndImage: postList){
    PostImage image = (PostImage)postAndImage[1];
    String imageUrl = image == null ? null : image.getImageUrl();
    responses.add(PostListResponse.fromEntity((Post)postAndImage[0], imageUrl));
  }
  ```
  <br>
  리팩토링 후 레포지토리 코드
  
   ```java
  @Query("select p, i from Post p left join PostImage i on p.id = i.post.id and i.isThumbnail = true where p.id < :lastId order by p.id desc, i.id asc")
  Page<Object[]> findAllWithImage(@Param("lastId") Long lastId, Pageable pageable);
   ```
  <br>
- <b>지연 로딩 전략과 페치 조인 사용</b><br>
   ```java
  // Post.java (Entity)
  @JoinColumn
  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Member writer;
  
  // PostRepository.java
  @Query("select p, i from Post p left join fetch p.writer left join PostImage i on p.id = i.post.id and i.isThumbnail = true where p.id < :lastId order by p.id desc, i.id asc")
  
  // PostResponse.java (DTO)
    public static PostResponse fromEntity(Post post){
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .price(post.getPrice())
        .isAuction(post.getIsAuction())
        .description(post.getDescription())
        .address(post.getAddress())
        .status(post.getStatus())
        .writer(post.getWriter())
        .build();
    }
  ```
  <br>
- <b>요청할 때 사용하는 DTO에 생성자 빠뜨리지 않기</b><br>
 ` cannot deserialize from Object value` 라는 `Exception` 이 발생한다.<br><br>

#### ✦ 느낀 점<br>
직접 JPQL을 작성하고 문제를 해결하며 새로운 경험을 할 수 있어서 재미있었다. 또한 지연 로딩 전략, 페치 조인과 같은 비교적 최근에 공부한 내용을 바로 토이 프로젝트에 적용하며 복습할 수 있어서 좋았다.
<br><br><br>

<div align="center">

### 4주차 미션 주제
<h4>
1️⃣ JWT 인증(Authentication) 방법에 대해서 알아본다 👮🏻‍ <br><br>
2️⃣ 액세스 토큰을 발급하고 검증 로직을 구현한다 📄<br><br>
3️⃣ 로그인 API를 구현하고 테스트한다 🧪<br><br>
4️⃣ 토큰이 필요한 API를 1개 이상 구현하고 테스트한다 🏭 <br><br>
5️⃣ 리프레쉬 토큰 발급 로직을 구현하고 테스한다 ✅<br><br>
</h4>
</div>

#### ✦ HTTP 인증 방식<br>
> 💡 HTTP의 특징은 **비연결성**과 **무상태성**이다. 
> 요청에 응답하면 서버와 클라이언트 간의 연결이 끊어지기에, 서버는 클라이언트에 대한 이전 상태 정보와 현재 통신의 상태를 알 수 없다.<br>

➡️ 자원 낭비를 줄일 수 있지만, 서버는 클라이언트를 **식별할 수 없다.** <br><br>
➡️ 별도의 인증 방식을 도입한다.<br><br>
##### 1. Cookie & Session
- `Cookie` 
  - 서버를 통해 클라이언트의 **브라우저에** 설치되는 작은(4KB 이하의) 파일을 말한다.
  - Key-Value 형식의 문자열이다. 
  > 1. 클라이언트가 쿠키 없이 요청을 보낸다. (로그인)
  > 2. 서버는 클라이언트에 저장하고 싶은 정보를 응답 헤더의 `Set-Cookie`에 담아 응답한다.
  > 3. 이후 클라이언트는 매 요청마다 저장된 쿠키를 요청 헤더의 `Cookie`에 담아 보낸다.
  - 예시
  ```
  서버의 응답
  Status = 200
  Headers = [Set-Cookie:"userName=jongmee", "password=1234"]
  
  클라이언트의 요청
  HTTP Method = POST
  Request URI = /api/post
  Headers = [Cookie:"userName=jongmee"; "password=1234"]
  ```
  - **단점**
    1. 요청할 때 쿠키의 값을 그대로 보내기에 **보안에 취약하다.** (➡ 유출 및 조작의 가능성)
    2. 쿠키의 크기가 커질수록 네트워크에 부하가 심해지고 용량 제한이 있어 많은 정보를 담을 수 없다.
    3. 웹 브라우저마다 쿠키의 지원 형태가 다르기에 브라우저간 공유가 불가능하다.<br><br>
- `Cookie & Session` 
  - 세션은 클라이언트의 인증 정보를 **서버 측에** 저장하고 관리한다.
  - 역시 Key-Value 형식이다.
  > 1. 클라이언트가 로그인 요청을 보낸다.
  > 2. 서버는 회원을 확인하고 서버에 인증 정보(Session ID)를 저장하고 Session ID를 쿠키에 담는다.
  > 3. 클라이언트는 요청을 보낼 때마다 Session ID를 헤더에 담아 보낸다.
  - 예시
  ```
  서버의 응답
  HTTP/1.1 200
  Set-Cookie: JSESSIONID=FDB5E30BF21234E8Q9AAFC788383680C;
  ```
  - **장점**
    1. 세션 ID는 개인정보를 담고 있지 않다.
    2. 각 사용자마다 고유한 세션 ID가 발급되어 요청마다 회원 정보를 확인할 필요가 없다.
  - **단점**
    1. 해커가 세션 ID를 탈취하여 클라이언트로 위장하여 접근할 수 있다.(하이재킹)
    2. 서버에서 세션 저장소를 사용하기에 요청이 많아지면 서버에 부하가 심해진다.<br><br>
##### 2. JWT 기반 인증
  - JWT(JSON Web Token)란 인증에 필요한 정보들을 암호화시킨 토큰이다.
  - JWT 토큰(Access Token)을 HTTP 헤더에 담아 서버가 클라이언트를 식별한다.
  - JWT는 .을 구분자로 하는 세 가지 문자열의 조합이다.
  <div align="center">
  <img width="300" alt="스크린샷 2023-11-11 오후 2 33 33" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/9f913c68-2a62-4658-82ac-228cbef678c2">
  </div>
  
  - 디코딩된 JWT는 `Header`, `Payload`, `Signature`로 이루어진다.
  - `Header`
    <div align="center">
    <img width="400" alt="스크린샷 2023-11-11 오후 2 38 41" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/36348a5f-859f-4486-8419-0e177228c5d1">
    </div>
    
  - `Payload`
    <div align="center">
    <img width="400" alt="스크린샷 2023-11-11 오후 2 38 53" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/ccc59e2a-558b-4cc6-827b-79496ab3a401">
    </div>
    
    - 주로 클라이언트의 고유 ID 값, 유효 기간 등이 포함되는 영역으로 한 쌍의 정보(key-value)를 `Claim`이라고 부른다.
    
  - `Signature`
    <div align="center">
    <img width="400" alt="스크린샷 2023-11-11 오후 2 39 04" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/3bd9b0fb-b445-4f81-9511-544b08ee6624">
    </div>
    
    - 인코딩된 `Header`와 `Payload`를 더한 뒤 비밀키로 해싱하여 생성한다.
    - 서버에서 관리하는 비밀키가 없다면 복호화할 수 없다. ➡ `Signature`로 토큰의 위변조 여부를 확인한다.
  > 1. 클라이언트가 로그인을 요청한다.
  > 2. 서버는 검증 후 클라이언트 정보(고유 ID 등)를 `Payload`에 담는다.
  > 3. 비밀키로 JWT Access token을 발급한다.
  > 4. 클라이언트는 토큰을 저장하고 요청할 때 마다 헤더의 `Authorization`에 토큰을 담는다.
  > 5. 서버는 토큰의 `Signature`를 비밀키로 복호화하고 위변조 여부, 유효 기간 등을 확인한다.
  - **장점**
    1. `Header`와 `Payload`를 가지고 `Signature`를 생성하므로 데이터 위변조를 막을 수 있다.
    2. 인증 정보에 대한 별도의 저장소가 필요없다.
    3. 필요한 모든 정보를 자체적으로 지니고 있다.
    4. 인증 정보를 저장하는 세션과 다르게 서버는 무상태이다.
    5. 확장성이 우수하다.
    6. 토큰 기반으로 다른 로그인 시스템에 접근하거나 권한을 공유할 수 있다.
    7. 모바일 어플리케이션 환경에서도 잘 동작한다.
  - **단점**
    1. 쿠키&세션과 다르게 토큰의 길이가 길어, 인증 요청이 많아질수록 네트워크 부하가 심해진다.
    2. Payload 자체는 암호화되지 않아 유저의 중요한 정보는 담을 수 없다.
    3. 한 번 발급되면 유효기간이 만료될 때 까지 사용이 가능하기에 탈취당하면 대처하기 어렵다.
    4. 특정 사용자의 접속을 강제로 만료하기 어렵지만, 쿠키&세션은 서버 쪽에서 쉽게 세션을 삭제할 수 있다.
  - **극복 방안**
    1. 만료 기한 짧게설정하기: <br>
       토큰이 탈취되더라도 빠르게 만료되기 때문에 피해를 최소화할 수 있지만, 사용자가 자주 로그인해야 한다.
    2. Sliding Session: <br>
       서비스를 지속적으로 이용하는 클라이언트에게 자동으로 토큰 만료 기한을 늘려주는 방법이다. 글 작성 혹은 결제 등을 시작할 때 새로운 토큰을 발급해줄 수 있다. ➡ 사용자는 로그인을 자주 할 필요가 없어진다.
    3. Refresh Token: <br>
       1 . 서버가 Access Token보다 만료기간이 긴 Refresh Token도 발급한다. <br>
       2 . 클라이언트는 Access Token이 만료되었을 때 Refresh Token을 사용하여 Access Token의 재발급을 요청한다.<br>
       3 . 서버는 저장된 Refresh Token과 비교하여 유효한 경우 새로운 Access Token을 발급하고, 만료된 경우 사용자에게 로그인을 요구한다.<br>
       > ➡  사용자가 자주 로그인하지 않아도 되면서 Access Token의 만료 기한을 짧게 설정할 수 있다. ➕ 서버가 강제로 Refresh Token을 만료시킬 수 있다. <br><br>
       ⚠️ 서버는 Refresh Token을 별도의 저장소에 저장해야 한다. 추가적인 I/O 작업이 발생하기 때문에 I/O 작업이 없는 빠른 인증 처리라는 JWT의 장점을 완전히 누를 수 없다.
##### 3. OAuth
  - 사용자들이 비밀번호를 제공하지 않고 다른 웹사이트 상의 자신들의 정보에 대해 웹, 애플리케이션의 접근 권한을 부여할 수 있는 공통적인 수단으로서 사용되는, 접근 위임을 위한 개방형 프로토콜이다.
  - OAuth 동작에 관여하는 참여자
    -  `Resource Server`: 자원을 보유하고 있는 서버 `ex) Google, Twitter`
    -  `Resource Owner`: 자원의 소유자, 즉 `Client`로 로그인하는 유저
    -  `Client`: 정보를 가져오고자 하는 클라이언트, 즉 웹 어플리케이션<br><br>
#### ✦ JWT 토큰 발급과 스프링 시큐리티 설정<br>
##### 1. UserDetails 구현하기
```java
public class Member extends BaseTimeEntity implements UserDetails {}
```
##### 2. UserDetailsService 구현하기
```java
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
  private final MemberRepository memberRepository;
  @Override
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
    return memberRepository.findByPhoneNumber(phoneNumber)
      .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 멤버입니다"));
  }
}
```
- `loadUserByUsername`을 구현해서 유저의 ID(여기에서는 phoneNumber)로 UserDetails(Member가 구현한)를 찾는다.
##### 3. JwtTokenProvider 구현하기
```java
// 클래스 내 메서드들
public JwtTokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds, CustomUserDetailService customUserDetailService) {}
@Override public void afterPropertiesSet() {}
public String createToken(String userPk, List<String> roles) {}
public Authentication getAuthentication(String token) {}
public String getUserPk(String token) {}
public String resolveToken(HttpServletRequest request) {}
public boolean validateToken(String jwtToken) {}
```
- `JwtTokenProvider`: 생성자로 `CustomUserDetailService`를 주입한다.
- `afterPropertiesSet`: 암호화하는 key를 세팅한다.
- `createToken`: User의 ID(phoneNumber)와 role로 토큰을 생성한다.
- `getAuthentication`: `loadUserByUsername`로 불러온 UserDetails로 `UsernamePasswordAuthenticationToken`를 생성한다.
- `getUserPk`: 토큰에서 유저의 ID(Primary key 역할을 하는 속성)을 찾는다.
- `resolveToken`: `HttpServletRequest`의 header에서 `Authorization`인 key의 값을 찾는다.
- `validateToken`: 토큰을 파싱해서 유효성을 검사한다.
##### 4. JwtAuthenticationFilter 구현하기
```java
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {
  // .. 생략
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest)request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            setErrorResponse(response, e.getMessage());
        }
    }
}
```
##### 5. MemberService에서 로그인할 때 토큰 발급하기
```java
public LoginResponse login(LoginRequest loginRequest) {
        // ..생략
        String token = jwtTokenProvider.createToken(member.getPhoneNumber(), member.getRoles());
        return LoginResponse.builder()
            .accessToken(token)
            .build();
}
```
##### 6. 권한 설정하기
```java
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // .. 생략
    }
}
```
##### 7. 인증 주입 받기
```java
  @PostMapping("/post")
  public ResponseEntity<PostResponse> postSave(@AuthenticationPrincipal final Member member,
      @RequestPart final PostCreateRequest request, @RequestPart List<MultipartFile> images) throws IOException {
    return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(member, images, request));
  }
```
- `@AuthenticationPrincipal`로 인증 주입 받기
<br><br>
#### ✦ 느낀 점 및 배운 점<br>
스프링 시큐리티 설정이 복잡해서 이론적인 부분들을 놓치며 구현만 따라가기 쉬운데, 이론 공부를 선행하고 바로 적용시키니 세세한 과정들도 쉽게 받아들이고 이해할 수 있었다. 시간적 여유가 따라줄 때 공식 문서를 보며 자세히 공부해보고 싶다.
<br><br><br>

<div align="center">

### 5주차 미션 주제
<h4>
1️⃣ 로컬에서 도커를 실행해본다 🛳️‍<br><br>
2️⃣ API 추가 구현하고 리팩토링한다 ♻️<br><br>
</h4>
</div>

#### ✦ 도커 실행하기

<img width="1121" alt="스크린샷 2023-11-18 오후 8 07 36" src="https://github.com/jongmee/spring-daagn-market-18th/assets/101439796/6e2cef14-bd4d-4202-885e-9d4f057bc387"><br><br>
현재 H2를 사용하고 있어 H2를 함께 올렸습니다 😊
- `Dockerfile`
```
FROM openjdk:17
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
```
```
/*
Dockerfile만으로 H2를 도커로 올리려면
아래 명령어를 입력 후 컨테이너 내부에 들어가서 data base를 생성해주어야 합니다.
*/
docker run -d -p 1521:1521 -p 8081:81 -v /Users/jongmi/Idealproject/ceos-daagn-market/h2:/opt/h2-data -e H2_OPTIONS="-ifNotExists" --name=h2 oscarfonts/h2
```
- `docker-compose.yml`
```
version: "3"
services:
  db:
    container_name: h2
    image: oscarfonts/h2:latest
    ports:
      - 1521:1521
      - 8081:81
    environment:
      H2_OPTIONS: -ifNotExists
    volumes:
      - ./h2/:/opt/h2-data
    restart: always

  web:
    container_name: web
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:h2:tcp://h2:1521/demo
      SPRING_DATASOURCE_USERNAME: sa
      SPRING_DATASOURCE_PASSWORD: 1234
    restart: always
    volumes:
      - .:/appdocker-compose -f docker-compose.yml up --build
```
```
// 아래 명령으로 실행
docker-compose -f docker-compose.yml up --build
```
<br>

#### ✦ Patch API 추가 구현하기

