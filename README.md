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
