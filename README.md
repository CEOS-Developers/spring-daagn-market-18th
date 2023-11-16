# spring-dangn-market-18th
CEOS 18th Backend Study - Carrot Market

## [2주차] 🥕당근마켓 DB 모델링
### 1️⃣ 회원 기능 ➡️ User 엔티티
![User2](https://github.com/yoonsseo/spring_core/assets/90557277/c1878a4c-566e-4bdd-8ec6-37e3ab831705)
![User1](https://github.com/yoonsseo/spring_core/assets/90557277/147b67be-8d3a-4963-9414-1d0ff9a41a69) 
* 회원 고유 번호 `userId` 
* 핸드폰번호 `phone`   
  → 핸드폰 번호는 숫자이지만 연산이 없고 검색이 편하도록 varchar/String으로 설정  
* 이메일 `email`  
```  
당근마켓은 우선 핸드폰 번호로 회원가입을 한 후 원한다면 이메일도 등록할 수 있다 
비밀번호?  당근마켓에서 전송하는 인증번호? 
```
* 닉네임 `nickname`
* 프로필 사진 `profileImage`
* 매너온도 `manners`
* 재거래희망률
* 응답률
* 응답시간


### 2️⃣ 동네 ➡️ Town 엔티티
|![Town1](https://github.com/yoonsseo/spring_core/assets/90557277/8e773645-6223-47aa-ac1e-feb364421b86)| ![Town2](https://github.com/yoonsseo/spring_core/assets/90557277/36c3b291-cb99-42e5-98a0-706751157c91) |
|-----|-----|
* `townId`
* `stateName`, `districtName`, `townName` ex. 서울시 서초구 방배동
```
이렇게 나눠서 저장하는 게 맞는지 
서울시 서초구 방배동으로 설정하고자 할 때
방배라고 검색해도 뜨고 서초라고 검색해도 관련 동네가 뜨는데
초구나 배동 이렇게 검색하면 안 뜬다 → 검색이 어떻게 이루어지는 거지???
처음에 행정구역별로 아예 세 개의 테이블로 나누었다가 그렇게까지 나누어야하나 싶었는데
근데 테이블이나 컬럼별로 굳이 분리해야하나 싶기도 하고 아무튼 고민 

그리고 근처 동네는 어떻게 설정하는거지 
```

### 3️⃣ 동네 인증 ➡️ UserTown 엔티티
유저 당 최대 두 개의 동네 정보
* `userTownId` `userId` `townId`
* 동네 범위 `townRange`   
    📌 `range`를 쓰면 mysql 예약어라 에러가 난다!! 나도 알고 싶지 않았다 🥹🥹
* 동네 인증 시간 `townAuthTime`
* 동네 인증 여부 `isTownAuth`
```java
유저 당 최대 2개의 주소를 설정할 수 있고,
주소마다 범위, 인증 시간, 인증 여부가 따로 관리되어 테이블 분리 
```

### 4️⃣ 물건 올리기 ➡️ Post 엔티티
![Post1](https://github.com/yoonsseo/spring_core/assets/90557277/7a644358-670d-4d21-ba94-1c6f61110ae5)
* 판매 게시글 고유 번호 `postId`
* 제목 `title` -> @Notnull
* 카테고리 `categoryId` -> @Notnull
* 거래방식 `tradeMethod` 
* 가격 `price`
* 가격 제안 여부 `isPriceOffer`
* 자세한 설명 `description` -> @Notnull
* 거래 희망 장소 `wishPlace`
* 판매자 user -> `seller`
* 보여줄 동네 설정 `townRange`
* 판매 상태 `postStatus`  
```java
판매자는 본인이 올린 게시글에서 판매 상태를 판매 완료로 바꾸면 구매 확정인데
구매자는 어떻게 처리되어야하는지 고민  
```
* 대표사진 `thumbnail`
* 나머지 사진 `image1~9`
* 브랜드 `brand`  
  → 카테고리에 따라 브랜드를 입력하는 칸이 뜨기도 하고 안 뜨기도 한다 신기 

### 5️⃣ 카테고리 ➡️ Category 엔티티
* 카테고리 고유 번호 `categoryId`
* 카테고리 이름 `name`

### 6️⃣ 채팅방 ➡️ ChatRoom 엔티티
![Chat1](https://github.com/yoonsseo/spring_core/assets/90557277/c79ce7ce-9b96-4dde-a953-4758ae57e031)
* 채팅방 고유 번호 `chatRoomId`
* 판매자/구매자 정보 user -> `seller`/`buyer`  
  → 채팅방 이름은 상대방 닉네임 
* 판매 게시글 정보 `postId`
* 안 읽은 채팅 수 

### 7️⃣ 채팅 내용 ➡️ Chat 엔티티
* 채팅 고유 번호 `chatId`
* 채팅방 번호 `chatRoomId`
* 채팅 내용 `content`
* 상대방이 읽었는지 여부 `isRead`
* 누가 보내고 받았는지 user -> `sender`/`receiver`  
  → `sender` 컬럼만 있으면 채팅방이랑 연결해서 받은 사람 알 수 있지 않나? 

### 8️⃣ 거래후기 ➡️ Review 엔티티
![Review4](https://github.com/yoonsseo/spring_core/assets/90557277/b03ebbcc-ec75-4dd8-a242-a98c102268a5)

| ![Review2](https://github.com/yoonsseo/spring_core/assets/90557277/9fcd6d6f-12fa-4406-bd46-f64c4eb6c4e3) | ![Review3](https://github.com/yoonsseo/spring_core/assets/90557277/1f3c6e2d-040d-4d23-8ea2-07111324d7b9)|
|-----|-----|
* 거래 후기 고유 번호 `reviewId`
* 작성자/대상자 `reviewer`/`reviewee`
* 어떤 판매 게시글에 대한 리뷰인지 `postId`
* 구매자가 적은 후기인지 판매자가 적은 후기인지 `reviewType`
* 거래선호도 `reviewLevel`
```java
이 리뷰로 매너온도가 변하는데  
```

### 🔢 BaseEntity 
* 생성시간 `created`와 마지막 수정시간 `modified` 컬럼은 거의 모든 테이블이 가지고 있는 컬럼이기 때문에
`@MappedSuperClass`로 엔티티 생성
* `@MappedSuperclass`  
  * 매핑 정보만 받는 부모 클래스, 상속과 관련된 것 아님  
  * 상속관계 매핑 아니고 엔티티가 아니어서 테이블과 매핑되지 않는다  
  → 조회, 검색 당연히 불가(em.find(BaseEntity) 불가)
  * 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공  
  * 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할


### 📅 ERD 
![당근ERD](https://github.com/yoonsseo/spring_core/assets/90557277/57e660a1-e24f-40e0-870e-a463c7ad7c4f)

## 🥕 레포지토리 단위 테스트

### ⚒️ Builder Pattern 

##### 🧱 Builder Pattern 이란? 
* 복잡한 Object들을 단계별로 구축할 수 있는 생성 디자인 패턴으로   
  * 복잡한 객체를 생성하는 방법을 정의하는 클래스와 표현하는 방법을 정의하는 클래스를 별도로 분리해, 
  * 서로 다른 표현이라도 이를 생성할 수 있는 동일한 절차를 제공하는 패턴
* 객체를 만들고 동시에 값을 설정가능한 생성자를 많이 사용하는데, 생성자를 사용하는 경우  
  * 필수가 아닌 값도 null로 채워주거나,  
  * ex.주소를 뺀 생성자 함수를 다시 만들어야 하고
  * 명확하게 어떤 값을 지정하는 지 알 수 없기 때문에 가독성이 좋지 않다 
* 생성자를 가독성 좋게 만들어주는 도구


##### Builder()
클래스 내부에서 Builder 클래스를 따로 정의해 사용할 수 있고  
값을 설정하고 자기자신을 반환하기 때문에 함수를 연속적으로 체이닝하듯 사용할 수 있다
```java

```


##### 🏗️ @Builder 사용 
* `@Builder`  
  빌더 클래스와 이를 반환하는 builder() 메서드 생성
* `@AllArgsConstructor(access = AccessLevel.PRIVATE)`  
`@Builder` 어노테이션을 선언하면 전체 인자를 갖는 생성자를 자동으로 만드는데, 이를 private 생성자로 설정
* **클래스 전체**에 Builder를 적용할 수도 있고 **특정 생성자**에서만 적용할 수도 있다
```java
@Getter @Builder //클래스 전체 필드를 빌더로 사용
public class User {
  private Long id;
  private String phone;
  private String nickname;
}
```
```java
public class User {
  ...
  @Builder //phone, nickname만 빌더 사용 
  public User(String phone, String nickname) {
    this.phone = phone;
    this.nickname = nickname;
  }
}
```

### 🎯테스트(Junit5)
##### @DataJpaTest
* JPA 관련된 Component만 로드  
  ApplicationContext 전체가 아닌 JPA에 필요한 설정들에 대해서만 Bean을 등록한다  
→ 컴포넌트 스캔을 하지 않아, @Component 빈들이 등록되지 않는다
* @Transactional 어노테이션 포함 → 테스트 종료 후 롤백도 같이 수행된다
* 디폴트로 h2 드라이버 사용

##### @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
* yml파일에서 DB를 MySql로 설정해 두었기 때문에 h2 의존성이 없으면 DataSource를 찾을 수 없다는 에러가 발생할 수 있다
```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
@PropertyMapping("spring.test.database")
public @interface AutoConfigureTestDatabase {

@PropertyMapping(skip = SkipPropertyMapping.ON_DEFAULT_VALUE)
Replace replace() default Replace.ANY;

EmbeddedDatabaseConnection connection() default EmbeddedDatabaseConnection.NONE;

// ...
}
```
* `@AutoConfigureTestDatabase`은 `@DataJpaTest`에서 설정을 자동으로 해주는 많은 어노테이션 중 하나
* 디폴트값 `Replace.ANY`의 `replace` 속성과  
  디폴트값 `EmbeddedDatabaseConnection.NONE`의 `connection` 속성을 설정할 수 있다
* `EmbeddedDatabaseConnection`의 enum 값에는 H2, DERBY, HSQLDB 등이 있는데 MySql은 없다  
  → MySql로 설정했다면 찾을 수 없기 때문에 에러 발생!!
* `replace` 기본값이 `ANY`이기 때문에 Embedded Database 를 찾게 된 것이고  
  → Embedded Database를 쓰지 않도록 `replace` 값을 
  ```java
  @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
  ```
  `Replace.NONE` 으로 설정하면 우리가 사용하는 실제 Database를 사용할 수 있다 

##### AssertJ
AssertJ는 assertion을 제공하는 자바 라이브러리로 테스트 코드와 에러 메세지의 가독성을 높여준다
```java
import static org.assertj.core.api.Assertions.assertThat;
...
assertThat(actual).isEqualTo(expected)
```
모든 테스트 코드는 `assertThat()` 메소드에서 출발하고, AssertJ에서 제공하는 다양한 메소드를 연쇄 호출 하면서 코드를 작성할 수 있다
```java
assertThat(테스트 타겟).메소드1().메소드2().메소드3();
```

![image](https://github.com/yoonsseo/spring_core/assets/90557277/8a0501c2-8cee-4100-be0d-52fe66749204)   

## [3주차] 📤 API 
### 📬 게시글 등록 API
##### API 명세서
![게시글 등록 API 명세서](https://github.com/yoonsseo/spring_core/assets/90557277/9c4e4136-43a1-4ba0-9eb8-ce102e92d79c)   
##### 로직
```java
    public Long registerPost(RegisterPostRequestDto requestDto) {
        //로그인된 유저의 올바른 정보가 넘어온다고 가정
        User seller = userRepository.findById(requestDto.getUser_id()).get();

        Post post = requestDto.toEntity(seller);

        TradeMethod tradeMethod = TradeMethod.valueOf(requestDto.getTradeMethod());
        post.setTradeMethod(tradeMethod);

        Category category = categoryRepository.findByName(requestDto.getCategory());
        post.setCategory(category);

        postRepository.save(post);

        return post.getId();
    }
```
1. RequestBody로 사용자 정보 및 게시글 등록에 필요한 정보 받기  
   `부득이하게 사용자 정보도 RequestBody로 받음`
2. `RegisterPostRequestDto` - `toEntity` 메소드 : DTO로 받은 정보 Post Entity로 바꿔주기  
  연관 관계를 위해 userId로 User Entity 찾아서 사용자 정보만 따로 넘겨준다
   ```java
    public Post toEntity(User seller) {
        return Post.builder()
                .seller(seller)
                .thumbnail(thumbnail)
                .title(title)
                .price(price)
                .isPriceOffer(isPriceOffer)
                .description(description)
                .wishPlace(wishPlace)
                .townRange(townRange)
                .build();
    }
   ```
3. TradeMethod 거래하기/나눔하기의 거래방식은 String으로 넘어오는데 Enum값으로 설정되어 있기 때문에 따로 설정해준다  
   카테고리도 String으로 넘어오기 때문에 `CategoryRepository`에서 엔티티 찾아서 연관 관계 설정해주기
4. 그리고 save 해주고 일단 Service에서는 postId 리턴해주었당 Controller에서는 ok 반환
##### 포스트맨
![게시글 등록 포스트맨](https://github.com/yoonsseo/spring_core/assets/90557277/1dfec823-68c5-4346-8da2-19cd0f3c4bd0)   
##### MySQL
![게시글 등록 DB](https://github.com/yoonsseo/spring_core/assets/90557277/e55c19e6-f854-4037-a725-73600c951f2a)   

### 🗂️ 모든 게시글 조회 API
![모든 게시글](https://github.com/yoonsseo/spring_core/assets/90557277/a89a52e0-3f41-4ea8-8043-d7fb10c0adfc)   
##### API 명세서
![모든 게시글 조회 API](https://github.com/yoonsseo/spring_core/assets/90557277/73d544fc-4f9b-48b5-acbe-7fdee41251ce)   

##### 🤯 고민
1. 정렬조건이 최신순이 아닌 것 같긴 한데 우선 Pageable 적용한 findAll로 갱신순으로 가져오려고 했다
2. 근데 생각해보니 근처 동네의 게시물만 가져와야하고 
3. 또 생각해보니까 사용자가 두 개의 동네를 설정할 수 있는데  
   사용자의 현재 동네랑  
  판매자가 어느 동네를 현재로 설정하고 올린 게시물인지도 알아야할 거 같은데  
   그거는 포스트 엔티티에 컬럼이 있어야할 것 같다
4. 타운 엔티티에 위도와 경도를 추가하긴 했는데   
   예를 들어 근처 동네 범위를 위도±50, 경도±50 으로 설정했을 때  
   그래서 정말로 그 위치의 동네 이름을 알려면 api가 필요할 것 같다  
##### 로직
```java
@Transactional(readOnly = true)
public PostListResponseDto getPostList(Pageable pageable) {
    Page<Post> findPosts = postRepository.findByIsDel(false, pageable);

    Page<PostDto> postDtos = findPosts.map(post -> new PostDto(post,
        chatRoomRepository.getTotalChatRoom(post),
        userTownRepository.findByUser(post.getSeller()).get(0).getTown().getTownName()));
        //편의상 첫 번째 주소로 가정

    return new PostListResponseDto(postDtos.getTotalPages(), postDtos.getNumber(), postDtos.getContent());
}
```
1. 현재 사용자의 동네로 설정된 근처 동네의 결과만 가져오는 방법은 적용하지 못했다 
   ```java
    Page<Post> findByIsDel(boolean isDel, Pageable pageable);
   ```
    그냥 정렬 조건을 `modifiedAt`의 ASC 순서로 Page 객체 생성 + 삭제 여부 확인   
   무한스크롤로 구현이 되어있는데, 잘 모르겠지만 프론트 측에서 스크롤 이벤트가 일어나거나 하는 상황에  
   벡으로 다음 페이지 번호로 요청하면, 일정 개수의 게시물 정보가 담긴 다음 페이지 반환   
   잘 모르겠지만 무한스크롤 형식이든 게시판 형식이든 그것은 프론트가 해야하는 일이 아닐까..? →   
2. 찾아온 게시물들에서 map으로 각 게시물 하나씩의 정보를 담은 `PostDto` 생성
    * post Entity 자체를 넘겨서 각 정보 뽑고,
    ```java
    @Query("SELECT COALESCE(COUNT(cr.id), 0) FROM ChatRoom cr WHERE cr.post = :post")
    int getTotalChatRoom(@Param("post") Post post);
    ```
   * 채팅방 개수는 `ChatRoomRepository`에 쿼리 생성해서 계산
   * 판매자 동네 정보 : post Entity의 seller 정보를 이용해   
   `UserTownRepository`에서 `findByUser`로 UserTown 리스트를 뽑은 다음에,  
     편의상 0번째 인덱스 값의 UserTown Entity → 의 Town으로 넘어가서 동네 이름 값 받아오기..
3. 마지막으로 `PostListResponseDto`에 Page 객체가 제공해주는 메소드를 사용해  
   전체 페이지 수와, 현재 페이지 수,  
   그리고 각 게시물 정보의 리스트를 담아서 ResponseBody로 반환     
   위시리스트 없다   
##### MySQL
![모든 게시글 조회 DB](https://github.com/yoonsseo/spring_core/assets/90557277/c6b863a9-82d0-4fa6-afe1-79c4f8f7061c)   
##### 포스트맨
![모든 게시글 조회 포스트맨](https://github.com/yoonsseo/spring_core/assets/90557277/8e8d9beb-0188-4643-b6be-52fcb32b2f5d)   
![모든 게시글 조회2](https://github.com/yoonsseo/spring_core/assets/90557277/a45f73a0-9944-47c9-90b6-e6a201d3cf32)   
3번 게시글은 isDel=1로 삭제된 게시글이라 나타나지 않는당👏🏻👏🏻

### 🔍 특정 게시글 조회 API - 검색할까 상세할까 고민 중
![게시물 상세](https://github.com/yoonsseo/spring_core/assets/90557277/e976f78c-fe94-40ad-9bca-4de77e000400)   
##### API 명세서
![특정 게시글 조회 API 명세서](https://github.com/yoonsseo/spring_core/assets/90557277/d4eecce6-2cb4-4566-a963-47bbcbcac4a5)    
##### 로직
```java
public PostResponseDto getPost(Long postId) {
   Optional<Post> findPost = postRepository.findById(postId);
   if (findPost.isPresent() && !findPost.get().isDel()) {
       //조회수 올려주기!
       postRepository.updateView(postId);

       Post post = findPost.get();

       //편의상 첫 번째 주소로 가정..
       String sellerTown = userTownRepository.findByUser(post.getSeller()).get(0).getTown().getTownName();

       return new PostDetailResponseDto(postId, post, sellerTown, chatRoomRepository.getTotalChatRoom(post));
   }
   else {
       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 게시물 요청");
   }
}
```
1. @PathVariable로 받아온 `postId`를 이용해 `postRepository`에서 게시물 찾기
2. 게시물이 있으면 해당 게시물의 조회수 올려주기 + 삭제되지 않았으면! 
   ```java
    @Modifying
    @Query("UPDATE Post p set p.view = p.view + 1 where p.id = :postId")
    void updateView(@Param("postId") Long postId);
    ```
3. 그리고 post Entity 받아오고, 판매자 주소 정보 찾은 거랑  
   채팅방 리포지토리에서 채팅방 개수 찾아서 `PostDetailResponseDto` 생성해서 반환
   ```java
    public PostDetailResponseDto(Long postId, Post post, String sellerTown, int totalChatRoom) {
        this.post_id = postId;

        this.seller_profileImage = post.getSeller().getProfileImage();
        this.seller_nickname = post.getSeller().getNickname();
        this.seller_town = sellerTown;
        this.seller_manners = post.getSeller().getManners();

        this.title = post.getTitle();
        this.category = post.getCategory().getName();
        this.description = post.getDescription();
        this.wishplace = post.getWishPlace();
        this.view = post.getView();

        this.total_ChatRoom = totalChatRoom;
    }
   ```
4. 게시물이 없으면 `404` 반환 
##### 포스트맨
![특정 게시글 조회 포스트맨](https://github.com/yoonsseo/spring_core/assets/90557277/dd8147df-696b-41b7-9bfe-3aba1a965c57)   
조회수가 1로 증가했고 채팅방 개수도 0으로 잘 반환됨😊😊
![삭제된 특정 게시글 조회](https://github.com/yoonsseo/spring_core/assets/90557277/02848af3-a24a-44f1-b7cd-c2dbbfa21814)   
삭제된 게시글은 `404 BAD REQUEST` 

### ❌ 특정 게시글 삭제 API
##### API 명세서 
![특정 게시글 삭제 API 명세서](https://github.com/yoonsseo/spring_core/assets/90557277/3dbcb306-e4a5-4b45-b060-289d484090c9)   
##### 로직
```java
    public void deletePost(Long postId) {
        postRepository.deletePost(postId);
    }
```
* Post Entity에 `isDel` 컬럼 추가  
   DB에서 물리적으로 삭제하는 것이 아니라 `isDel` 컬럼을 이용해 논리적으로 삭제하는 로직으로 구현  
   Post Entity는 리뷰, 채팅방, 그리고 구현하지 않았지만 위시리스트 등  
   여러 엔티티와 연결되어 있기 때문에 논리적으로 삭제하는 것이 낫다고 판당
   ```java
    @Modifying
    @Query("UPDATE Post p SET p.isDel = true WHERE p.id = :postId")
    void deletePost(@Param("postId") Long postId);
   ```
##### 포스트맨 
![특정 게시글 삭제 포스트맨](https://github.com/yoonsseo/spring_core/assets/90557277/10ed86f9-755c-46d1-9212-2478906666d2)   
![삭제된 특정 게시글 조회2](https://github.com/yoonsseo/spring_core/assets/90557277/f232d006-d85d-4993-bf5d-b76234f850b4)   
삭제 후 다시 조회하려고 하면 조회할 수 없음!!  
##### MySQL
![삭제 후 DB](https://github.com/yoonsseo/spring_core/assets/90557277/c75fe643-223b-455c-83cc-7bf3d55fd01a)   
DB에도 잘 반영되어 있음😆😆

### 🚨 트러블 슈팅
1. 초기 DB에 값을 잘 넣어놓아야 했다   
   사용자랑 동네 넣고 UserTown 때문에 둘이 연결해 두어야 했고, 카테고리도 미리 생성해두어야 했음
2. `Category`랑 `Post` 연관 관계 `@ManyToOne`으로 했다가 왜인지 `@OneToOne`으로 바꿨는데  
   `@ManyToOne`이 맞았음  
3. 모든 게시글 조회 API에서 계속 `406 not acceptable` 에러가 떴는데  
   DTO에 `@Getter` 붙여서 해결   
   JSON과 관련된 `jackson` 라이브러리가 없어서 나는 오류라고 한다  

### 느낀점
생각보다 당근마켓의 DB와 로직은 매우 복잡한 거 같다   
실제로 어떻게 구현되어 있는지 정말 궁금하다  

## [4주차] Spring Security 적용하기
### 1. 로그인 인증 방법 
#### 1.1. JWT - Access Token과 Refresh Token
##### 1.1.1. 🛡️ JWT : Json Web Token
* 서로 다른 기기에 데이터를 전달할 때 사용하는 방법 중 하나로, `Base64`의 형태를 가진다
* `Header`와 `Body(또는 Payload)`, 그리고 `Signature` 세 부분으로 나눠진다
###### 📑 Header
* JWT의 metadata들을 나타낸다
* Sign에 사용된 Algorithms, format, 그리고 ContentType 등의 정보
###### 📄 Payload (Body)
* `Claim` 단위로 저장
> **Claim**
>  * 사용자의 속성이나 권한, 정보의 한 조각 또는 Json의 필드라고 생각하면 된다
>  * `Claim`에는 JWT 생성자가 원하는 정보들을 자유롭게 담을 수 있는데  
     > Json 형식을 가지고 있기 때문에 단일 필드도 가능하고,  
     > Object와 같은 complexible한 필드도 추가할 수 있다
     >
     >   ```java
>    Claims claims = Jwts.claims(); //일종의 Map
>    claims.put("userName", userName);
>    ...
>        Jwts.builder()
>                .setClaims(claims)
>                ...
>    ```
> * Claim에 userName을 담아두면 따로 사용자 id를 입력받지 않아도 토큰에 들어있는 값을 꺼낼 수 있다

###### 📝 Signature
* Header와 Body는 Base64 형태로 인코딩되어 암호화되어 있지 않은데  
  공격자가 내용을 바꿀 수가 있다
* Signature로 서명을 통해 암호화 과정을 거친다
* 서명 이후 Header와 Body의 내용이 바뀐다면 Signature의 결과값이 바뀌어 받아들여지지 않는다
 
##### 1.1.2. Access Token
![access token 인증](https://github.com/yoonsseo/spring-security/assets/90557277/c092bd0f-8882-4283-8ce6-3fb902488958)
* 간편하고, 세션이나 쿠키와 달리 추가적인 저장소가 필요하지 않고,  
  한 번 발급되면 유효기간이 완료될 때까지는 계속 사용이 가능하지만,  


* 중간에 삭제가 불가능하기 때문에 `Access Token`이 탈취되면,
  토큰이 만료되기 전까지 토큰을 가진 사람은 누구나 권한 인증이 가능해진다는 문제점이 발생할 수 있다  

→ 이러한 문제점을 보완하기 위해 `Access Token`의 만료 기간을 짧게 주고, `Refresh Token`을 추가적으로 발급해 해결

##### 1.1.3. Refresh token
![refresh token 인증](https://github.com/yoonsseo/spring-security/assets/90557277/7aee766e-8600-4726-81b5-d184c481b4be)
* `Refresh Token`은 `Access Token`에 비해 훨씬 더 긴 유효 기간으로 발급되며,  
  `Refresh Token`의 경우 접근에 대한 권한을 가진 것이 아니라 `Access Token` 재발급에만 사용된다는 특징이 있다
> `Access Token` 유효 기간 30분 ~ 1시간 정도  
> 
> `Refresh Token` 유효 기간 1주일 ~ 1달 정도 

* `Refresh Token` 역시 탈취될 수 있는 문제가 있는데,  
  최초 로그인 시 로그인 요청 ip를 저장하고,  
  재발급 요청이 왔을 때, 요청이 온 ip와 저장된 ip를 비교하여  
  다른 경우 토큰을 재발급하지 않거나 알림을 보내는 등의 추가적인 조치를 취할 수 있다

#### 1.2. OAuth 
##### OAuth란?
* Open Authorization 
* 인터넷 사용자들이 특정 웹 사이트를 접근하고자 할 때, 접근하려는 웹 사이트에 비밀번호를 제공하지 않고,  
  서드파티 애플리케이션(구글, 카카오, 페이스북 등)의 연결을 통해 '인증 및 권한'을 부여받을 수 있는 프로토콜
* 외부서비스의 인증 및 권한부여를 관리하는 범용적인 프로토콜

##### Spring Boot OAuth 2 Client와 Spring Boot OAuth 2 Server
* Spring Boot OAuth 2 Client 
  * **외부 OAuth 2.0 서비스에 대한 인증을 처리하기 위한 모듈**
  * 간단한 설정만으로 OAuth 2.0 프로토콜을 따르는 서비스의 인증을 처리할 수 있다 
  

* Spring Boot OAuth 2 Server
  * **OAuth 2.0 서버를 빠르게 구축할 수 있도록 지원하는 모듈**
  * 간단한 설정만으로 OAuth 2.0 프로토콜을 따르는 서버를 구축할 수 있다

##### 기본 동작 원리
![OAuth 동작 방식](https://github.com/yoonsseo/spring-security/assets/90557277/15ba9158-7a8f-4cc1-8c8a-926f467c52d5)
> 1. 사용자가 서드파티 애플리케이션을 선택하면 로그인을 위해 해당 웹 사이트로 리다이렉션 된다  
>   (User → Client)
> 2. 로그인에 성공하면, 특정 웹사이트에서 요청한 특정 데이터에 대한 액세스 권한을 부여할지 묻는 메시지가 표시되고,  
>    원하는 옵션을 선택하면 인증 코드 또는 오류 코드와 함께 특정 사이트로 리다이렉션 된다  
>   (Client ↔ Authorization Server)
> 3. 타사 리소스의 작업에 따라 로그인 성공 또는 실패 (Client ↔ Resource Server)

##### Spring Boot OAuth 2 Client 인증방식
###### 1. Authorization Code Grant 권한 코드 승인 방식
* 클라이언트는 권한 부여 서버에서 권한 부여 코드를 요청하고, 이를 `Access Token`으로 교환
* 사용자의 리소스에 액세스해야 하는 웹 서버 애플리케이션에서 일반적으로 사용된다
* 가장 대중적이고 많이 사용되는 방식

###### 2. Implicit Grant 암시적 승인 방식
* 클라이언트 애플리케이션이 `Access Token`을 직접 발급받는 것이 아니라  
  사용자 에이전트(웹 브라우저 등)를 통해 인가 과정을 거쳐 `Access Token`을 발급받는 방식
* 클라이언트가 권한 부여 코드를 먼저 요청하는 것이 아니라, 직접 액세스 토큰을 요청하는데,  
  보안 취약점 때문에 권장되지 않는다

###### 3. Client Credentials Grant 클라이언트 자격 증명 방식
* 클라이언트 애플리케이션이 자신의 이름과 비밀번호를 사용하여 `Access Token`을 직접 발급받는 방법
* 클라이언트 애플리케이션 자체의 인증에 사용됨
* 일반적인 로그인 방법 

#### 1.3. 세션과 쿠키 
![세션 쿠키 로그인](https://github.com/yoonsseo/spring-security/assets/90557277/331370a6-b754-4298-9021-e3cedf0a46ed)
* 사용자의 정보는 세션 저장소에 저장되고, 쿠키는 그 저장소를 통과할 수 있는 출입증 역할
* 쿠키가 담긴 HTTP 요청이 도중에 노출되더라도 쿠키 자체에는 유의미한 값을 갖고있지 않아서 쿠키에 사용자 정보를 담아 인증을 거치는 것 보다 안전하다
* 각각의 사용자는 고유의 Session ID를 발급 받기 때문에 일일이 회원 정보를 확인할 필요가 없어 서버 자원에 접근하기 용이하다


* 세션 하이재킹 공격
  * 쿠키에 사용자 정보를 담아 인증을 거치는 것 보다 안전하지만, 해커가 쿠키를 탈취한 후 그 쿠키를 이용해 HTTP 요청을 보내면 서버는 사용자로 오인해 정보를 전달하게 된다
  * HTTPS 프로토콜 사용과 세션에 만료 시간을 넣어 어느 정도 보완할 수 있다
* 서버에서 세션 저장소를 사용하기 때문에 추가적인 저장공간이 필요하다 

### 2. Access Token 발급 및 검증 로직 구현
#### 2.1. 🌐 Spring Security Architecture
![Spring Security Architecture](https://github.com/yoonsseo/spring-security/assets/90557277/7d8cc2c4-3a4b-4a0c-9c5b-91b94ef0d0ba)
> 1. `Http Request` - 사용자가 로그인 정보와 함께 인증 요청
>
>
> 2. `AuthenticationFilter`가 요청을 가로채고,  
     >    가로챈 정보를 통해 `UsernamePasswordAuthenticationToken`이라는 인증용 객체 생성해서
>
>
> 3. `AuthenticationManager`의 구현체인 `ProviderManager`에게 생성한 `UsernamePasswordAuthenticationToken` 객체 전달
>
>
> 4. `AuthenticationManager`는 등록된 `AuthenticationProvider`들을 조회하고 인증 요구
>
>
> 5. `AuthenticationProvider`는 실제 DB에서 사용자 인증정보를 가져오는 `UserDetailsService`에 사용자 정보를 넘겨준다
>
>
> 6. `UserDetailsService`는 `AuthenticationProvider`에게 넘겨받은 사용자 정보를 통해,  
     >    DB에서 찾은 사용자 정보인 `UserDetails` 객체를 만든다
>
>
> 7. `AuthenticationProvider`들은 `UserDetails` 객체를 넘겨받고 사용자 정보 비교
>
>
> 8. 인증이 완료되면, 권한 등의 사용자 정보를 담은 `Authentication` 객체를 반환한다
>
>
> 9. 다시 최초의 `AuthenticationFilter`에 `Authentication` 객체가 반환되고
>
>
> 10. `Authenticaton` 객체를 `SecurityContext`에 저장

##### 1. Authentication
* 현재 접근하는 주체의 정보와 권한을 담는 인터페이스


* `Authentication` 객체는 `SecurityContext`에 저장되며,    
  `SecurityContextHolder`를 통해 `SecurityContext`에 접근하고,  
  `SecurityContext`를 통해 `Authentication`에 접근할 수 있다

##### 2. UsernamePasswordAuthenticationToken
* `Authentication`을 implements한 `AbstractAuthenticationToken`의 하위 클래스  
  즉, `Authentication`의 구현체이고, 그래서 `AuthenticationManager`에서 인증과정을 수행할 수 있다
* 추후 인증이 끝나고 `SecurityContextHolder`에 등록될 `Authentication` 객체


* User의 ID를 `Principal` 로, Password를 `Credential`로 생성한 인증 개체
  > 여기에서 말하는 `Principal` 역할을 하는 User의 ID 또는 Username은 로그인 시 ID와 PW의 ID를 똣한다  
  > 로그인 시 email을 ID로 사용한다면 email이, 전화번호를 ID로 사용한다면 전화번호가 곧 Username이 된다

* `UsernamePasswordAuthenticationToken`의 첫 번째 생성자는 인증 전의 객체를 생성하고,  
  두 번째는 인증이 완료된 객체를 생성한다
```java
public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
	super(null);
	this.principal = principal;
	this.credentials = credentials;
	setAuthenticated(false);
}
public UsernamePasswordAuthenticationToken(Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities) {
	super(authorities);
	this.principal = principal;
	this.credentials = credentials;
	super.setAuthenticated(true); // must use super, as we override
}
```

##### 3. AuthenticationManager
* 만들어진 `UsernamePasswordAuthenticationToken`은 `AuthenticationManager`의 인증 메소드를 호출하는 데 사용된다
* 인증에 대한 부분은 `AuthenticationManager`를 통해서 처리하게 되는데,  
  실질적으로는 `AuthenticationManager`에 등록된 `AuthenticationProvider`에 의해 처리된다
* 인증에 성공하면 두 번째 생성자를 이용해 객체를 생성하여 `SecurityContext`에 저장한다

##### 4. AuthenticationProvider
* `AuthenticationManager`의 구현체
* `AuthenticationProvider`에서는 **실제 인증에 대한 부분을 처리**하는데,  
  인증 전의 `Authentication` 객체를 받아서 인증이 완료된 객체를 반환하는 역할을 한다
* Custom한 `AuthenticationProvider`를 작성하고 `AuthenticationManager`에 등록하면 된다

##### 5. ProviderManager
* `AuthenticationManager`를 implements한 구현체 `ProviderManager`는  
  `AuthenticationProvider`를 구성하는 목록을 갖는다

##### 6. UserDetailsService
```java 
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```
* Spring Security의 **interface**이고, 구현체는 **직접 개발**해야한다 (customize)
* `username`을 기반으로 검색한 `UserDetails` 객체를 반환하는 하나의 메소드 `loadUserByUsername` 만을 가지고 있고,
  일반적으로 이를 implements한 클래스에 `UserRepository`를 주입받아 DB와 연결하여 처리한다
* `UserDetailsService`는 DB에 저장된 회원의 비밀번호와 비교하고,  
  일치하면 `UserDetails` 인터페이스를 구현한 객체를 반환한다

##### 7. UserDetails
* 인증에 성공하여 생성된 `UserDetails` 객체는 `Authentication` 객체를 구현한 `UsernamePasswordAuthenticationToken`을 생성하기 위해 사용된다

##### 8. SecurityContextHolder
* 보안 주체의 세부 정보를 포함하여 응용프로그램의 현재 보안 컨텍스트에 대한 세부 정보가 저장된다
* `SecurityContextHolder`는 `ThreadLocal`에 저장되어, `Thread`별로 `SecurityContextHolder` 인스턴스를 가지고 있기 때문에,  
  사용자 별로 `Authentication` 객체를 가질 수 있다

##### 9. SecurityContext
* 인증된 사용자 정보 `Authentication`을 보관하는 역할
* `SecurityContext를` 통해 `Authentication`을 저장하거나 꺼내올 수 있다
```java
SecurityContextHolder.getContext().setAuthentication(authentication);
SecurityContextHolder.getContext().getAuthentication(authentication);
```

##### 👀 그래서 우리가 사용할 `Authentication` 객체는?
→ `UsernamePasswordAuthenticationToken` 객체

##### 10. GrantedAuthority
* 현재 사용자(Principal)가 가지고 있는 권한 의미
* `ROLE_ADMIN`이나 `ROLE_USER`와 같이 `ROLE_*`의 형태로 사용한다
* `GrantedAuthority` 객체는 `UserDetailsService`에 의해 불러올 수 있고,
* 특정 자원에 대한 권한이 있는지 검사해 접근 허용 여부를 결정한다

#### 2.2. `SecurityFilterChain` 설정
> **변경**  
> 스프링 부트 3.0 이상부터 스프링 시큐리티 6.0.0 이상의 버전이 적용되며  
> Deprecated된 코드 변경

```java
//.httpBasic().disable()
.httpBasic(HttpBasicConfigurer::disable)
```
* UI쪽으로 들어오는 설정
* Http basic Auth 기반으로 로그인 인증창이 뜨는데, JWT를 사용할 거라 뜨지 않도록 설정   
  \+ `formLogin.disable()` : formLogin 대신 JWT를 사용하기 때문에 disable로 설정


```java
//.csrf.disable()
//.cors().and()
.csrf(AbstractHttpConfigurer::disable)
.cors(Customizer.withDefaults())
```
* API를 작성하는데 프론트가 정해져있지 않기 때문에 csrf 설정 우선 꺼놓기
###### CSRF
* **Cross Site Request Forgery**  : 사이트 간 위조 요청
* 웹 사이트 취약점 공격 방법 중 하나로, 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위를 특정 웹 사이트에 요청하게 하는 공격
* Spring Security에서는 CSRF에 대한 예방 기능을 제공한다
* **근데 이 좋은 기능을 왜 disable?**
    * 스프링 시큐리티 문서에서는 일반 사용자가 브라우저에서 처리할 수 있는 모든 요청에 CSRF 보호를 사용할 것을 권장하고,  
      브라우저를 사용하지 않는 클라이언트만 사용하는 서비스를 만드는 경우 CSRF 보호를 비활성화하는 것이 좋다고 함
    * 여기에서 브라우저를 사용하지 않는 클라이언트만 사용하는 서비스 → 대부분의 REST API 서비스라고 이해함  
      즉 대부분의 가이드는 REST API 서버 기준으로 disable을 적용하고 있다
###### CORS
* **Cross-Origin Resource Sharing** : 서로 다른 Orgin 간의 상호작용 시 브라우저에서 이를 중지하기 위해 제공하는 기본 보호 기능, 프로토콜
* HTTP 요청은 기본적으로 Cross-Site HTTP Requests가 가능 (다른 도메인 사용 가능)   
  하지만 Cross-Site HTTP Requests는 Same Origin Policy를 적용받기 때문에,  
  프로토콜, 호스트명, 포트가 같아야만 요청이 가능하다
* `cors()`로 cors에 대한 커스텀 설정 허용
    * `addAllowedOrigin()` : 허용할 URL 설정
    * `addAllowedHeader()` : 허용할 Header 설정
    * `addAllowedMethod()` : 허용할 Http Method 설정


```java
//.authorizeRequests()
//.requestMatchers("/api/**").permitAll()
//.requestMatchers("/api/**/users/join", "/api/**/users/login").permitAll()
.authorizeHttpRequests(authorize -> authorize
    .requestMatchers("/api/**").permitAll()
    .requestMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll())
```
* 특정한 경로에 특정한 권한을 가진 사용자만 접근할 수 있도록 하는 설정
* `authorizeRequests()` : 시큐리티 처리에 HttpServletRequest를 이용한다는 것, 각 경로별 권한 처리
* `requestMatchers()` : 특정한 경로 지정
    * 만약 spring-security 5.8 이상의 버전을 사용하는 경우에는  
      `antMatchers`, `mvcMatchers`, `regexMatchers`가 더 이상 사용되지 않기 때문에,   
      `requestMatchers`를 사용해야 한다고 함
  > __URL 패턴 `/*` 과 `/**`__
  >
  > * __`/*`__ : 경로의 바로 하위에 있는 모든 경로 매핑
  >
  >ex. `AAA/*` : `AAA/BBB`, `AAA/CCC` 해당, `AAA/BBB/CCC` 해당하지 않음
  > * __`/**`__ : 경로의 모든 하위 경로(디렉토리) 매핑
  >
  >ex. `AAA/**` : `AAA/BBB`, `AAA/CCC`, `AAA/BBB/CCC`, `AAA/.../.../DDD/...`, `AAA/BBB/CCC/.../.../...` 전부 해당

* `permitAll()` :  모든 사용자가 인증 절차 없이 접근할 수 있음
* `authenticated()` : 인증된 사용자만 접근 가능
* `hasRole()` : 시스템 상에서 특정 권한을 가진 사람만이 접근할 수 있음
* `anyRequest().authenticated()` : 나머지 모든 리소스들은 무조건 인증을 완료해야 접근이 가능하다는 의미


```java
//.sessionManagement()
//.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
.sessionManagement((sessionManagement) -> sessionManagement
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
```
* 스프링 시큐리티는 기본적으로 session을 사용해 웹을 처리하는데,  
  JWT를 사용하기 때문에 session을 stateless로 설정, 세션 사용하지 않음


#### 2.3. `BCryptPasswordEncode` 설정
##### 🪢 **`BCryptPasswordEncode`**
* Spring Seurity 프레임워크에서 제공하는 클래스 중 하나로 비밀번호를 암호화하는 데 사용할 수 있는 메서드를 가진 클래스
##### 🔒 **`BCryptPasswordEncoder.encode(CharSequence rawPassword)`**
* 패스워드를 암호화해주는 메서드, `String` 반환
* 똑같은 비밀번호를 인코딩하더라도 매번 다른 문자열을 반환한다
##### 🗝️ **`matches(CharSequence rawPassword, String encodedPassword)`**
* 제출된 인코딩 되지 않은 패스워드(일치 여부를 확인하고자 하는 패스워드)와 인코딩 된 패스워드의 일치 여부 확인
* 첫 번째 파라미터로 일치 여부를 확인하고자 하는 인코딩 되지 않은 패스워드,  
  두 번째 파라미터로 인코딩된 패스워드 입력
* `boolean` 반환 


#### 2.4. JwtTokenProvider 
##### 2.4.1. 의존성 추가 🐘
```java
implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
```
* JWT 라이브러리의 핵심 API를 제공하고 JWT의 생성 및 검증을 다룰 수 있다
```java
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
```
* `jjwt-impl` 의존성을 추가하지 않은 채 `Jwts.builder()` 를 호출하게 되면 오류가 발생한다

```java
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
```
* `jjwt-impl`의 구현체 라이브러리로, `jjwt-jackson` 외에도 `jjwt-gson`이 있다
* `jjwt-jackson` 의존성을 추가하지 않으면 `compact` 메서드를 처리하던 도중 오류가 발생한다  
  → `jjwt-impl`에서 구현체를 찾아보지만 없기에 오류가 발생

###### 의존성을 세 개나 추가해야 하는 이유는?
> `jjwt-api` 는 패키지 관리에 있어서 `implemenation` 과 `runtimeonly` 로 구분하여 의존성 추가를 권장하고 있다   
> 경고 없이 언제든 변할 수 있는 패키지는 `runtimeonly`로 관리하고 그렇지 않은 것은 `implemenation`으로 관리해   
> 안정적으로 `jjwt-api` 라이브러리를 사용하겠다는 의도   
> 즉, `jjwt-impl`, `jjwt-jackson` 또는 `jjwt-gson` 은 경고없이 언제든 변화할 수 있고   
> `jjwt-api`는 하위호환성을 맞춰가며 개발한다는 의미   
> 실제로 코드를 보면서 하위호환성에 대한 언급과 `@Deprecated`를 통해 코드를 유지하려는 노력을 살펴볼 수 있다

##### 2.4.2. JWT 생성 시 필요한 정보
###### Jwts 클래스
* JWT 인스턴스를 생성하는 역할을 하는 팩토리 클래스

###### `Jwts.builder()`
```java
public static String createToken(String userName, Key key, long expireTimeMs) {
    Claims claims = Jwts.claims(); //일종의 Map
    claims.put("userName", userName);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}
```
0. Header 설정
    * `.setHeaderParam("key", "value")` 또는 `.setHeader(header)`와 같은 방식 사용 가능

###### Body(Payload) 설정
1. `setClaims()` : JWT에 포함시킬 Custom Claims 추가 - 주로 인증된 사용자 정보
    * `.claim("key", "value")` 또는 `.setClaims(claims)`와 같은 방식 사용 가능

2. `setSubject()` : JWT에 대한 제목


3. `setIssuedAt()` : JWT 발행 일자 - 파라미터 타입은 `java.util.Date`


4. `setExpiration()` : JWT의 만료기한 - 파라미터 타입은 `java.util.Date`


5. `signWith()` : 서명을 위한 `Key(java.security.Key)` 객체 설정
    ```java
    //.signWith(SignatureAlgorithm.HS256, key)
    .signWith(key, SignatureAlgorithm.HS256)
    ```
   ###### `signWith(io.jsonwebtoken.SignatureAlgorithm, java.lang.String)' is deprecated`
    * 특정 문자열(String)이나 byte를 인수로 받는 메서드로 사용이 중단되었는데,  
      많은 사용자가 안전하지 않은 원시적인 암호 문자열을 키 인수로 사용하려고 시도하며 혼란스러워했기 때문이라고 한다
   ###### `signWith(java.security.Key key, io.jsonwebtoken.SignatureAlgorithm alg)`
    * `String`이 아니라 `Key` 값을 생성하고 서명을 진행해야 한다


6. `compact()` : JWT 생성하고 직렬화


#### 2.5. Secret Key 생성하기
##### 👀 Secret Key 란?
토큰을 생성하기 위한 Key

##### 코드
```java
String keyBase64Encoded = Base64.getEncoder().encodeToString(key.getBytes());
SecretKey key = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
```
* 사용하고자 하는 `plain secretKey`(암호화 되지 않음, 첫 번째 줄의 `key`)를 `byte`배열로 변환해주고,
* HMAC-SHA 알고리즘을 통해 암호화해주는 `Keys.hmacShaKeyFor`를 통해 암호화된 `Key` 객체로 만들어주는 코드
##### **`io.jsonwebtoken.security.WeakKeyException`**
* `secretKey`가 **`256bit`보다 커야** 한다는 `Exception` - 알파벳 한 글자당 `8bit`이므로 **32글자 이상**이어야 한다는 뜻
* 한글은 한 글자 당 `16bit`인데 16글자이면 생성될까? → 생성된다

#### 2.6. JWT - JWT 검증하기
> 1. `Jwts.parserBuilder()` 메소드로 `JwtParserBuilder` 인스턴스 생성
> 2. JWS 서명 검증을 위한 `SecretKey` 또는 `비대칭 공개키` 지정
     >    > `TOKEN` 발급 시 사용했던 `secretKey`
> 3. `build()` 메소드를 호출하면 thread-safe한 `JwtParser`가 반환된다
> 4. `parseClaimsJws(jwtString)` 메소드를 호출하면 오리지널 signed JWT가 반환된다
> 5. 검증에 실패하면 `Exception` 발생

##### JWT TOKEN 파싱하기
```java
Jws<Claims> jws = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token); 
```
* `parseClaimsJws(token)`
    * 파라미터로 주어진 `JWT 토큰` 파싱
    * `JWT 토큰`의 구성 요소 Header, Body(Payload), Signature를 분석하고,  
      서명을 확인해 JWT의 무결성 검증
    * `JWT 토큰` 생성 시의 `Claim` 정보를 추출할 수 있다


* `parseClaimsJwt()`
    * `parseClaimsJws()`가 아니라 `parseClaimsJwt()`를 사용하면 오류 발생
    * 처음에 `TOKEN`을 생성할 때 `signWith()`를 통해 **서명**을 했기 때문에  
      복호화 시에도 **서명에 대한 검증**을 진행해야 한다
    * `parseClaimsJwt()`는 서명 검증 없이 단순히 헤더와 클레임만 추출한다
    * `parseClaimsJwt()`를 사용하고 싶다면 `TOKEN` 생성 시 `signWith()`를 통해 서명에 대한 정보를 넘겨주지 않으면 된다

```java
Claims claims = jws.getBody();
```
* `getBody()`
    * `TOKEN`의 `Claim` 정보 또는 토큰에 포함된 데이터,  
      즉, `TOKEN` 생성 시 포함한 사용자 정보, 권한, 만료 시간 등을 추출할 수 있다


* 이 외에도 `getHeader()`와 `getSignature()`를 통해 각각 `TOKEN`의 메타데이터와 서명을 추출할 수 있다

##### Claim 추출하기
```java
String username = claims.get("username", String.class); // "username" 클레임 값 추출
String role = claims.get("role", String.class); // "role" 클레임 값 추출
Date expiration = claims.getExpiration();
Date issuedAt = claims.getIssuedAt();
```
* `get()`
    * 키와 값의 쌍으로 저장된 `Claim`은 키를 통해 값을 찾을 수 있다
  ```java
    public abstract <T> T get(String claimName, Class<T> requiredType)
  ```
    * `Claim` 키와 타입에 맞는 값 반환


* 이 외에도 `TOKEN` 만료 시간을 추출하는 `getExpiration()`이나  
  `TOKEN` 생성 시간을 추출하는 `getIssuedAt()` 등의 메소드가 있다  

### 3. 회원가입 및 로그인 API 구현하고 테스트하기
#### 3.1. 🪪 회원가입 
##### 회원가입 로직
> 1. 중복 체크 
>    * `UserDuplicatedException()`
> 2. 회원가입
>    * `BCryptPasswordEncoder.encode()` - 비밀번호 암호화해서 저장 
```java
    public ResponseEntity<Void> signUp(SignUpDto signUpDto) {
        //중복체크
        userRepository.findByPhone(signUpDto.getPhone())
                .ifPresent(user -> {
                    throw new UserDuplicatedException();
                });

        //회원가입
        userRepository.save(User.builder()
                .phone(signUpDto.getPhone())
                .nickname(signUpDto.getNickname())
                .role(Role.USER)
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
```
![회원가입 포스트맨](https://github.com/yoonsseo/spring-security/assets/90557277/2b80022b-5115-4030-a264-bf73bb2d934c)
![회원가입 디비](https://github.com/yoonsseo/spring-security/assets/90557277/32fd4884-ab31-42da-845f-c3ae3d0052c6)

#### 3.2. 🔐 로그인 
##### 3.2.1. 로그인 로직
> 1. 로그인용 ID 확인
>    * `UserNotFoundException`
> 2. 비밀번호 확인
>    * `InvalidPasswordException()`
> 3. `TOKEN` 발행
```java
public SignInResponseDto signIn(SignInDto signInDto) {
        //전화번호 확인
        User user = userRepository.findByPhone(signInDto.getPhone())
                .orElseThrow(UserNotFoundException::new);

        //비밀번호 확인
        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        //TOKEN 발행
        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), signInDto.getPhone(), user.getRole().toString());
        
        return SignInResponseDto.builder().accessToken(accessToken).build();
    }
```

![로그인 아이디 오류](https://github.com/yoonsseo/spring-security/assets/90557277/dd66a5ea-e1fe-4c78-b03b-311e266714df)
![로그인 비밀번호 오류](https://github.com/yoonsseo/spring-security/assets/90557277/aec26f7a-a9dc-4a8e-999f-426d17315930)
![로그인 포스트맨](https://github.com/yoonsseo/spring-security/assets/90557277/78432d58-a915-410f-9279-359dc83e7063)

### 4. 토큰이 필요한 API 구현하고 테스트하기
#### 🧿 인증과 인가
> 1. 모든 `POST` 접근 막기  
>    - JwtAuthenticationFilter 인증 계층 추가하기
>    - 모든 요청에 권한 부여하기
> 2. `TOKEN` 여부 확인
>    - TOKEN 있으면 권한 부여
>    - TOKEN이 없으면 권한 부여하지 않기
> 3. `TOKEN` 유효성 검증
>    - TOKEN의 유효시간이 지났는지 확인하기
> 4. `TOKEN`에서 userName(id) 꺼내서 Controller에서 사용하기

### 🔐 인증 Authentication
* **증명하다**라는 의미로, 예를 들어 아이디와 비밀번호를 이용하여 로그인 하는 과정


* 해당 사용자가 **본인이 맞는지** 확인하는 과정

### ✅ 인가 Authorization
* **권한부여**나 **허가**와 같은 의미로 사용되고, 어떤 대상이 특정 목적을 실현하도록 허용(Access) 하는 것 의미


* 해당 사용자가 요청하는 자원을 실행할 수 있는 **권한이 있는가**를 확인하는 과정


#### 4.1. 모든 요청에 권한 부여하기
##### API 요청에 대해 접근 권한 설정
앞서 로그인에서 설정했던 `SecurityConfig`의 `SecurityFilterChain` 재정의 이용  
→ `@EnableWebSecurity`
```java
.authorizeHttpRequests(authorize -> authorize
    .requestMatchers("/api/*/*/signup", "/api/*/*/signin").permitAll()
    .requestMatchers(HttpMethod.GET).permitAll()
    .requestMatchers(HttpMethod.POST, "/api/**").authenticated())
```
* 회원가입과 로그인은 누구나 권한 없이 언제나 접근할 수 있지만
* 리뷰 쓰기 등 다른 모든 요청에 대해서는 권한 필요

##### JwtFilter 인증 계층 추가하기
```java
.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), 
        UsernamePasswordAuthenticationFilter.class)
```
* `addFilterBefore()`
    * JWT 인증 필터 `JwtAuthenticationFilter`를 `UsernamePasswordAuthenticationFilter` 이전에 추가하는 역할
    * 토큰이 있는지 매번 항상 확인해야 한다
  ```java
  public HttpSecurity addFilterBefore(
      @NotNull jakarta.servlet.Filter filter,
      Class<? extends jakarta.servlet.Filter> beforeFilter)
  ```

##### 모든 요청에 대해 권한 부여하기
```java
@Override
protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain) throws ServletException, IOException { ... }
```
* `Filter` 인터페이스를 구현하는 클래스에서 오버라이드할 메소드 중 하나
* HTTP 요청을 필터링하고, 필터가 적용된 요청을 처리하는 역할
* > 1. Header에서 TOKEN 꺼내기
  > 2. TOKEN 여부와 유효성 확인
  > 3. TOKEN이 유효하면 - 권한 부여

```java
Authentication authentication = jwtTokenProvider.getAuthentication(token);
SecurityContextHolder.getContext().setAuthentication(authentication);
```
* 현재 사용자의 인증 정보를 `authentication`으로 변경
* `SecurityContextHolder.getContext()`
    * 현재 사용자 및 인증 정보를 관리하는 `SecurityContextHolder` 객체에서
    * 현재 사용자와 관련된 정보가 저장되는 보안 컨텍스트 가져오기
* `.setAuthentication(authentication)`
    * 현재 사용자의 인증 정보 `authentication`으로 설정

```java
filterChain.doFilter(request, response);
```
* `doFilter()`
  ```java
  public abstract void doFilter(
      jakarta.servlet.ServletRequest request,
      jakarta.servlet.ServletResponse response)
  ```
    * `Filter` 인터페이스를 구현한 필터에서 정의된 메소드
    * 필터가 요청(request) 및 응답(response)을 처리하는 메소드
    * 필터는 이 메소드를 통해 요청과 응답을 가로채고 수정할 수 있다  
      ex. 요청을 가로채 권한 확인하기
    * 현재 필터에서 요청 및 응답을 처리하고,  
      이후에 실행될 다음 필터를 호출하기 위해 `FilterChain`의 `doFilter()`를 호출하는데,     
      이 때, 다음 필터로 요청 및 응답 계속 전달

#### 4.2. `TOKEN` 여부 확인
>   * TOKEN 있으면 권한 부여
>   * TOKEN이 없으면 권한 부여하지 않기

##### TOKEN이 없으면 권한 부여하지 않기
```java
Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
```

##### 포스트맨
![토큰 여부 확인](https://github.com/yoonsseo/spring-security/assets/90557277/ee2417f8-8d15-4438-98e9-0bfed4a28aa8)
* 토큰이 없으면 작동하지 않음!

|![토큰 여부](https://github.com/yoonsseo/spring-security/assets/90557277/ffdc1741-87ba-45b4-a427-1a26716e3df9)| 근데 <br> 아무 `TOKEN`을 넣어도 <br> 작동하는 문제! |
|---|---------------------------------------|

#### 4.3. `TOKEN` 유효성 검증
> - TOKEN의 유효시간이 지났는지 확인하기

##### TOKEN 유효시간 만료되었는지 확인
```java
    public boolean validateToken(String token) {
        //Token 만료 시간 또는 null 반환
        Date expiration = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
        boolean isExpired = expiration.before(new Date());

        return !isExpired;
        }
```
* `TOKEN` 만료로 인한 `ExpiredJwtException` 발생

#### 4.4. `TOKEN`에서 로그인ID 꺼내서 Controller에서 사용하기
##### 로그인ID 추출
```java
public String getUserId(String token) {
    return Jwts.parserBuilder()
    .setSigningKey(key)
    .build()
    .parseClaimsJws(token)
    .getBody()
    .getSubject();
    }
```
* `TOKEN`에서 `userName(ID)`의 `Claim` 추출하는 메소드 `JwtUtil.getUsername()` 생성


* 그리고 추출한 `로그인ID`를 `UsernamePasswordAuthenticationToken`에 넣어주면 `Controller`에서 `로그인ID`를 사용할 수 있다

##### Controller에서 사용하기
```java
import org.springframework.security.core.Authentication;
...
@PostMapping
public ResponseEntity<String> writeReview(Authentication authentication) {
    return ResponseEntity.ok().body(authentication.getName());
}
```
또는
```java
@PostMapping
public ResponseEntity<Void> registerPost(@RequestBody RegisterPostRequestDto requestDto, @AuthenticationPrincipal User user) {
    postService.registerPost(requestDto, user);
    return ResponseEntity.ok().build();
}
```
![토큰 넣고 게시글 등록 포스트맨](https://github.com/yoonsseo/spring-security/assets/90557277/af0e477b-d822-4546-b8c9-101de2b81811)
![토큰 게시글 디비](https://github.com/yoonsseo/spring-security/assets/90557277/6e557796-69f7-447a-8d33-bac239ec4e19)
