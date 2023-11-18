
# 💙 CEOS 18th Backend Study 2주차 💙
> Carrot Market

## ⭐ 당근 마켓의 DB를 모델링해요
<h3><a href="https://www.erdcloud.com/d/vXaKFFLfH32vRQKZq">ERD 클라우드 링크</a></h3>

![전체](https://github.com/nzeong/Spring-study/assets/121355994/3f4b3d18-686e-4380-88dc-2f82a8dcceb1)<br>

![회원](https://github.com/nzeong/Spring-study/assets/121355994/3013040b-7ed6-435d-b4d0-7059b0b540b8)<br>

- user_id: 기본키
- img_url: 프로필 사진 링크는 null일 수 있다
- temp: 매너 온도로, 36.5 를 디폴트 값으로 가진다
- town_id: Town 테이블과 1:1로 대응되기 때문에 외래키로 설정해주었다

![2](https://github.com/nzeong/Spring-study/assets/121355994/0491ac91-28dc-4e8c-8011-c7e229c8e859)<br>

- town_id: 기본키
- town_x, town_y: 지역의 위도와 경도를 나타내서 위치를 알 수 있다

![3](https://github.com/nzeong/Spring-study/assets/121355994/5a6961ce-d587-4ce3-ab87-48326b8675e5)<br>

- post_id: 기본키
- status: enum으로 설정해서 판매중 or 예약중 or 거래완료 중 하나의 값을 가질 수 있다
- category_id: 물건의 분류를 나타내며 PostCategory 테이블과 1:1로 대응되기에 외래키 설정해주었다
- user_id: User 테이블과 ManyToOne 관계이며 해당 테이블의 기본키를 외래키로 가진다

![4](https://github.com/nzeong/Spring-study/assets/121355994/4836b96e-e7f6-4e06-8058-dbf51ecc533d)<br>

- img_pk: 기본키
- post_id: Post 테이블과 ManyToOne 관계이며 해당 테이블의 기본키를 외래키로 가진다

![5](https://github.com/nzeong/Spring-study/assets/121355994/4c80af67-c3a7-4630-bbab-b12c4be32ad5)<br>

- category_id: 기본키

![6](https://github.com/nzeong/Spring-study/assets/121355994/9c502203-7480-47b6-9c8c-e89cdcb4487b)<br>

- chatroom_id: 기본키
- chatroom_check_status: 0(읽음), 1(읽지않음) 두 가지 상태로 나타내진다
- post_id: Post 테이블과 ManyToOne 관계이며 해당 테이블의 기본키를 외래키로 가진다
- seller_id, buyer_id: User 테이블 참조하는 외래키

![7](https://github.com/nzeong/Spring-study/assets/121355994/6d889807-2527-49f0-a5d0-bb889604197c)<br>

- msg_id: 기본키
- msg_check_status: 0(읽음), 1(읽지않음) 두 가지 상태로 나타내진다
- chatroom_id: ChatRoom 테이블과 ManyToOne 관계이며 해당 테이블의 기본키를 외래키로 가진다
- from_id: User 테이블 참조하는 외래키

![8](https://github.com/nzeong/Spring-study/assets/121355994/23da869c-2c51-4d96-9ba4-d6277a325ce6)<br>

- review_pk: 기본키
- preference: 거래 후기를 enum 으로 별로에요 or 좋아요 or 최고에요 중 하나로 남길 수 있으며 이는 매너온도에 반영된다
- reviewer_id, reviewee_id: User 테이블 참조하는 외래키, 리뷰를 남긴 사람과 리뷰를 받는 사람의 id
- post_id: Post 테이블과 ManyToOne 관계이며 해당 테이블의 기본키를 외래키로 가진다

## ⭐ Repository 계층 단위 테스트
```java

    @Test
    @DisplayName("유저 findById 작동 테스트")
    public void findbyUserid() {
    
        //given
        Town townA = Town.builder()
                .town_name("경기도 수원시 영통구")
                .town_x(33.8)
                .town_y(12.3)
                .build();

        Town townB = Town.builder()
                .town_name("서울시 서대문구 아현동")
                .town_x(100.2)
                .town_y(15.0)
                .build();

        Town townC = Town.builder()
                .town_name("경기도 구리시 교문동")
                .town_x(130.0)
                .town_y(4.0)
                .build();

        townRepository.save(townA);
        townRepository.save(townB);
        townRepository.save(townC);

        User userA = User.builder()
                .img_url("a.jpg")
                .user_name("김수한")
                .user_nick("수수")
                .pwd("1234")
                .phone("01022223333")
                .town(townA)
                .build();

        User userB = User.builder()
                .img_url("b.jpg")
                .user_name("철수한")
                .user_nick("척척")
                .pwd("2222")
                .phone("01033334444")
                .town(townB)
                .build();

        User userC = User.builder()
                .img_url("c.jpg")
                .user_name("한석봉")
                .user_nick("떡떡")
                .pwd("3333")
                .phone("01055554444")
                .town(townC)
                .build();


        User savedUserA = userRepository.save(userA);
        User savedUserB = userRepository.save(userB);
        User savedUserC = userRepository.save(userC);

        // then
        User findUserA = userRepository.findById(userA.getUser_id()).get();
        User findUserB = userRepository.findById(userB.getUser_id()).get();
        User findUserC = userRepository.findById(userC.getUser_id()).get();

        //when
        Assertions.assertThat(savedUserA).isSameAs(findUserA);
        Assertions.assertThat(savedUserB).isSameAs(findUserB);
        Assertions.assertThat(savedUserC).isSameAs(findUserC);

    }
}
```
User repository의 findById() 메소드가 잘 작동하는지 알기 위하여 먼저 외래키로 연결된 Town 객체를 3개 생성 후 save() 해주었고 이를 이용하여 User 객체도 3개 생성 후 save() 해주었다. 이후 저장한 객체가 findById()로 찾은 객체와 같은지 테스트해주었다.

```sql
Hibernate: 
    insert 
    into
        town
        (town_name,town_x,town_y) 
    values
        (?,?,?)
Hibernate: 
    insert 
    into
        town
        (town_name,town_x,town_y) 
    values
        (?,?,?)
Hibernate: 
    insert 
    into
        town
        (town_name,town_x,town_y) 
    values
        (?,?,?)
Hibernate: 
    insert 
    into
        user
        (created_at,email,img_url,phone,pwd,temp,town_id,updated_at,user_name,user_nick) 
    values
        (?,?,?,?,?,?,?,?,?,?)
Hibernate: 
    insert 
    into
        user
        (created_at,email,img_url,phone,pwd,temp,town_id,updated_at,user_name,user_nick) 
    values
        (?,?,?,?,?,?,?,?,?,?)
Hibernate: 
    insert 
    into
        user
        (created_at,email,img_url,phone,pwd,temp,town_id,updated_at,user_name,user_nick) 
    values
        (?,?,?,?,?,?,?,?,?,?)
> Task :test
```

그랬더니 이처럼 insert 구문이 잘 작동한 것을 볼 수 있다.


---
test가 잘 안돌아가서 왜 그런가 했더니 application.yml 의 ddl-auto: create 를 안 해줘서 그런 거였다.
이번 과제를 하면서 배운 것이 정말 많았고 효율적인 도메인 설계에 대해 고민해보게 되었다. 시간관계상 or 미처 생각하지 못해서 구현하지 못한 것들이 좀 있어서 아쉽다. 다음에 좀 더 공부하고 리팩토링 해봐야겠다.
- BaseEntity 활용하기
- @NoArgsConstructor @AllArgsConstructor 사용 상황 (나는 일단 둘 다 넣었다 😅) 
- @Builder 사용법 (다른 분들 코드 보니까 어떤 사람은 전체 class에 붙이고 어떤 사람은 생성자 만든 뒤 거기 위에 붙였다. 차이가 뭘까?!)
- notNull의 표기법 (@NotNull vs @Column(nullable = false) 둘 중에 뭐 써야되는건지 헷갈려서 찾아보니까 @NotNull 쓰는 게 좋다고는 하는데 곧 사라질 어노테이션이라고 경고떠서 뭐지 싶었다)

파이팅 🤣

---
# 💙 CEOS 18th Backend Study 3주차 💙

> POST 모델 선택해서 api 만들기

## ⭐ 수정사항
- BaseEntity 활용하기
- JAVA CamelCase, SQL snake_case 적용
- erd SQL TYPE 에 맞춰서 수정 & 구조 변경

## ⭐ Dto 계층

- PostRequestDto

```java
@NoArgsConstructor
@Getter
public class PostRequestDto {

  private String title;
  private Long price;
  private String content;
  private PostCategory category;
  private User user;

  @Builder
  public PostRequestDto(String title, Long price, String content, PostCategory category, User user) {
    this.title = title;
    this.price = price;
    this.content = content;
    this.category = category;
    this.user = user;
  }

  public Post toEntity() {
    return Post.builder()
            .title(title)
            .price(price)
            .content(content)
            .status(PostStatus.SALE)
            .category(category)
            .user(user)
            .build();
  }
}
```
status에 enum으로 설정한 SALE("판매중")를 기본값으로 넣어주었고, 아직 로그인 기능을 구현 못해서 일단 지금은 user도 받아올 수 있게 해주었다.

- PostResponseDto
```java
@Getter
@NoArgsConstructor
public class PostResponseDto {

  private Long id;
  private String title;
  private Long price;
  private String content;
  private PostStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String categoryName;
  private String userNick;
  private List<PostImg> projectImages = new ArrayList<>();

  @Builder // Entity to Dto
  public PostResponseDto(Long id, String title, Long price, String content, PostStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String categoryName, String userNick) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.content = content;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.categoryName = categoryName;
    this.userNick = userNick;
//        this.projectImages = projectImages;
  }

  public static PostResponseDto from(Post post){
    return PostResponseDto.builder()
            .id(post.getId())
            .title(post.getTitle())
            .price(post.getPrice())
            .content(post.getContent())
            .status(post.getStatus())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .categoryName(post.getCategory().getName())
            .userNick(post.getUser().getNick())
//                .projectImages(post.getProjectImages())
            .build();
  }
}
```
post_img 테이블에에 아직 데이터를 안 넣어둬서 지금은 무시해주었고, 카테고리 이름과 유저의 nickname을 출력해준다.

## ⭐ Service 계층

- PostService
```java
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public void createPost(PostRequestDto request) {
    Post post = request.toEntity();
    postRepository.save(post);
  }

  @Transactional
  public List<PostResponseDto> getAllPosts() {
    List<Post> postList = postRepository.findAll();
    return postList.stream().map(post -> PostResponseDto.from(post)).toList();
  }

  @Transactional
  public PostResponseDto getPost(Long id) {
    return PostResponseDto.from(postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException()));
  }

  @Transactional
  public void deletePost(Long id) {
    Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException());
    postRepository.delete(post);
  }
}
```

## ⭐ Controller 계층
- PostController
```java
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "danggun/posts")
public class PostController {

  private final PostService postService;
  //@RequiredArgsConstructor를 사용했기 때문에 의존성이 자동 주입된다

  @PostMapping
  public ResponseEntity<Long> createPost(@RequestBody @Valid PostRequestDto request) {
    log.info("상품 게시글 생성하기");
    postService.createPost(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<List<PostResponseDto>> getAllPosts() {
    log.info("모든 상품 게시글 조회하기");
    List<PostResponseDto> PostResponseList = postService.getAllPosts();
    return ResponseEntity.status(HttpStatus.OK).body(PostResponseList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
    log.info("상품 게시글 상세 조회하기");
    PostResponseDto postResponse = postService.getPost(id);
    return ResponseEntity.status(HttpStatus.OK).body(postResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    log.info("상품 게시글 삭제하기");
    postService.deletePost(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
```

- (nullable=false)로 설정해두었기 때문에 town, user, category 테이블에 임의로 테스트용 데이터를 넣어두었음
  
  ![town](https://github.com/nzeong/new-piro-game-BE/assets/121355994/a527f7a7-db09-48d4-a257-398452bc3c23)
  ![user](https://github.com/nzeong/new-piro-game-BE/assets/121355994/e58d95cc-fa52-4134-ae3b-567dae10f1c6)
  ![cate](https://github.com/nzeong/new-piro-game-BE/assets/121355994/3ecf21b4-88af-4192-835f-fb11ef76b8cf)

## 1️⃣새로운 데이터를 create하도록 요청하는 API 만들기

- **URL**:`danggun/posts`
- **Method**:`POST`
- **Body**:`{
  "title":"책상입니다",
  "price": 2000,
  "content": "완전 새거같아요",
  "category": {
  "id": 1
  },
  "user": {
  "id": 1
  }
  }
`
`{
  "title":"과자 팔아요",
  "price": 500,
  "content": "이거 맛있어요",
  "category": {
  "id": 2
  },
  "user": {
  "id": 1
  }
  }
`
<br>

![1](https://github.com/nzeong/new-piro-game-BE/assets/121355994/5b10d878-dbef-4bd1-bbe1-c22584fb2032)
![2](https://github.com/nzeong/new-piro-game-BE/assets/121355994/5d398262-fdea-4ef6-bf79-331c88ae67d9)

![post 테이블](https://github.com/nzeong/new-piro-game-BE/assets/121355994/cb0b8d3a-17ec-4b55-b66c-87446026ea4d)

## 2️⃣모든 데이터를 가져오는 API 만들기

- **URL**:`danggun/posts`
- **Method**:`GET`

![전체 내용](https://github.com/nzeong/new-piro-game-BE/assets/121355994/61aef303-287d-4def-9ed4-ad4f169e6d85)

## 3️⃣ 특정 데이터를 가져오는 API 만들기

- **URL**:`danggun/posts/<int:pk>`
- **Method**:`GET`

![1 내용](https://github.com/nzeong/new-piro-game-BE/assets/121355994/a4c17117-f16b-4d0d-a853-7ea5fe360530)
![2 내용](https://github.com/nzeong/new-piro-game-BE/assets/121355994/291ede25-607a-4ee2-ae7f-bf3867c17bf5)

## 4️⃣ 특정 데이터를 삭제 또는 업데이트하는 API

### [삭제]
- **URL**:`danggun/posts/<int:pk>`
- **Method**:`DELETE`

![delete](https://github.com/nzeong/new-piro-game-BE/assets/121355994/adb35f04-37de-4c9c-9627-1fe4c01731ae)
![delete 결과](https://github.com/nzeong/new-piro-game-BE/assets/121355994/bb6ddbaf-70e8-4714-80d2-248f65101810)

---

Post 객체로 CRUD API를 만들어 보면서 DTO가 왜 필요하고 Controller와 Service 계층이 구체적으로 어떻게 동작하는지 이해할 수 있었다.
특히 외래키로 연결된 데이터들에 대해서 어떻게 request를 받고, 어떤 response를 보내줄지 고민을 많이 했던 것 같다.
해당 부분은 내가 코드를 작성하면서 뜯어보고 프로그램이 동작하는 것을 눈으로 보는 게 빠르게 학습할 수 있는 방법인 것 같다.

---

# 💙 CEOS 18th Backend Study 4주차 💙

## ⭐ 수정사항
- softDelete로 수정

## 1️⃣ JWT 인증(Authentication) 방법에 대해서 알아보기

### JWT Access Token + Refresh Token
JWT는 JSON Web Token의 약자로 사용자를 인증하고 식별하는데 필요한 JSON 데이터들을 URL로 이용할 수 있는 문자(Base64 URL-safe Encode)로 인코딩하여 직렬화, 암호화시킨 토큰을 말한다. 전자 서명도 있어 JSON의 변조를 체크할 수 있다. 단순히 HTTP 요청시 헤더에 토큰을 첨부하는 것만으로 데이터를 요청하고 응답을 받아올 수 있다. JWT를 생성하기 위해서는 Header, Payload, Verify Signature 객체를 필요로 한다. Header, Payload는 누구나 디코딩하여 확인할 수 있기에 정보가 쉽게 노출될 수 있습니다. 하지만 Verify Signature는 SECRET KEY를 알지 못하면 복호화할 수 없어 보안에 좋다.
<br><br>
JWT는 Access Token만으로도 인증 방식을 구현할 수 있다. 하지만 한 번 발급되면 유효기간이 만료될 때까지 삭제를 할 수 없어 만료 전에 해커에게 정보가 털린다면 대처할 방법이 없다. 그렇다고 유효기간을 짧게 하면 자주 인증해야된 불편함이 생긴다. 이에  Access Token과 같은 형태인 Refresh Token을 같이 발급하여 이 문제를 해결할 수 있다. Refresh Token은 Access Token보다 긴 유효기간을 가지고, Access Token이 만료됐을 때 새로 발급해주는 열쇠가 된다.

### OAuth (현재 범용적으로 사용되고 있는 것은 OAuth 2.0)
OAuth는 외부서비스의 인증 및 권한부여를 관리하는 범용적인 프로토콜로, 카카오 로그인, 구글 로그인 등을 구현할 때 사용한다.
- 자원 서버(Resource Server): Client가 제어하고자 하는 자원 보유하고 있는 서버
- 자원 소유자(Resource Owner): 자원의 소유자(로그인하는 실제 사용자)
- 클라이언트(Client): 자원 서버에 접속해서 정보를 가져오고자 하는 클라이언트(우리의 웹 어플리케이션)
- 권한 서버(Authorization Server): 권한 관리 및 Access Token, Refresh Token을 발급해주는 서버(ex. 구글, 페이스북 등)
- Access Token: 자원 서버에 자원을 요청할 수 있는 토큰
- Refresh Token: 권한 서버에 접근 토큰을 요청할 수 있는 토큰

![스터디 4444444](https://github.com/nzeong/Spring-study/assets/121355994/b871e1b6-36a4-40d8-a1fb-297e672d5a19)
1. 자원 소유자(사용자)가 구글 로그인을 요청한다.
2. 클라이언트는 인증 서버에 로그인 페이지를 요청한다.
3. 인증 서버가 로그인 페이지를 제공한다.
4. 사용자는 제공받은 로그인 페이지에 ID와 비밀번호를 입력한다.
5. 입력받은 값으로 인증 서버에 요청한다.
6. 인증 서버에 Authorization code를 발급한다.
7. 이 code로 인증 서버에 Access Token를 요청한다.
8. 인증 서버에서 Access Token을 발급해준다.
9. 인증이 완료되었다.
10. 자원 서버에 Access Token을 담아 데이터를 요청한다.
11. Access Token을 검증 후 응답을 준다. (만일 Access Token이 만료됐거나 위조되었다면, Client는 Authorization Server에 Refresh Token을 보내 Access Token을 재발급 받는다)

    
### 쿠키
Key/Value 쌍으로 이루어진 문자열로, 사용자 브라우저에 저장된다. 용량이 제한되어 있으며 브라우저마다 쿠키 지원 형태가 달라 브라우저간 공유가 불가능하고 보안에 취약하다는 단점이 있다.

### 세션
쿠키의 보안 문제를 해결할 수 있는데, Key/Value 쌍으로 이루어져 비밀번호같은 민감한 인증 정보를 브라우저가 아닌 서버 측에 저장하고 관리하는 것이다. 사용자가 많아지면 정보를 찾는 데이터 매칭에 오랜 시간이 걸리면서 부하가 가해질 수 있고 해커가 세션 ID 자체를 탈취하여 위장하여 접근할 수 있다는 한계가 있다.




## 2️⃣ 액세스 토큰 발급 및 검증 로직 구현하기
> 로그인은 email과 pwd로 진행

- TokenProvider 클래스에 적절한 메서드 구현
```java
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    @Value("${jwt.token.secret}")
    private String secret; // 환경변수로 secret key 설정

    private Key key;

    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간

    private final PrincipalDetailsService principalDetailsService;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public String createAccessToken(Long id, String email, Authentication authentication) {
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("id", id);
        claims.put("email", email); // 이메일(로그인 id) 넣어주기
        claims.put("auth", authorities);
        claims.put("type", "access");

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key)
                .compact()
                ;
    }

    // 토큰
    public String getTokenUserEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("email"); // 이메일(로그인 id) 꺼내기
    }

    public Authentication getAuthentication(String token) {
        PrincipalDetails principalDetails =
                (PrincipalDetails)
                        principalDetailsService.loadUserByUsername(getTokenUserEmail(token));

        // 여기의 return 값이 @AuthenticationPrincipal 사용시의 파라미터로 사용됨
        return new UsernamePasswordAuthenticationToken(
                principalDetails, token, principalDetails.getAuthorities()); 
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info(e.toString());
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info(e.toString());
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info(e.toString());
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info(e.toString());
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
```

### ⭐ UserDetails, UserDetailsService 왜 사용해야하는 것일까?
![context](https://github.com/nzeong/new-piro-game-BE/assets/121355994/3b3e2f12-8dc7-4af9-a620-b803344931a2) <br><br>
스프링 시큐리티는 로그인 완료 시 Authenticication을 생성하게 되는데, Authenticication 객체는 UserDetails type으로 인증된 사용자 정보를 저장하기 때문이다.
따라서 UserDetails를 상속받는 PrincipalDetails와 PrincipalDetails를 생성하는 PrincipalDetailsService를 만들어주었다.

```java
@Data
public class PrincipalDetails implements UserDetails {
    private final User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority("ROLE_USER")); // "USER" 권한
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    // 이메일이 id 역할을 하기 때문에 user email 반환
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```

```java
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override // email로 user 찾아 UserDetails 타입의 PrincipalDetails 객체를 반환해준다
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService.loadUserByUsername");
        log.info("Login");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return new PrincipalDetails(user);
    }
}
```
<br>

- TokenProvider를 이용해서 custom filter 내용 채우기
  
```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = tokenProvider.getAccessToken(request);
        String requestURI = request.getRequestURI();

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (StringUtils.isNotBlank(token) && tokenProvider.validateAccessToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("Security Context에 " + authentication.getName() + "인증 정보를 저장했습니다, uri: " + requestURI);
        } else {
            logger.info("유효한 JWT 토큰이 없습니다, uri: " + requestURI);
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        }

        filterChain.doFilter(request, response);
    }
}
```

JwtAuthenticationFilter는 HttpServletRequest에서 토큰 추출해서 토큰에 대한 유효성을 검사하고, 유효하다면 Authentication 객체를 생성해서 SecurityContextHolder에 추가하는 역할을 한다.

```java
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다");
            setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);

        } catch (JwtException | IllegalArgumentException e) {
            log.error("유효하지 않은 토큰이 입력되었습니다.");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);

        } catch (NoSuchElementException e) {
            log.error("사용자를 찾을 수 없습니다.");
            setErrorResponse(response, ErrorCode.USEREMAIL_NOT_FOUND);

        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("토큰을 추출할 수 없습니다.");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);

        } catch (NullPointerException e) {
            filterChain.doFilter(request, response);
        }
    }
}
```

JwtExceptionHandlerFilter는 JwtAuthenticationFilter 전에 호출되어 Security 필터에서 발생하는 오류를 예외처리한다.

## 3️⃣ 로그인 API 구현하고 테스트하기

- 앞에서 구현한 `TokenProvider`를 이용해요
- 연결한 DB에 회원을 만든 후, 로그인 API가 잘 작동하는지 테스트를 해봐요(회원가입 API를 만들어서 테스트 한다면 더욱 좋겠죠?)
  
![user table 데이터 삽입](https://github.com/nzeong/Spring-study/assets/121355994/05c19978-d57a-43f5-ae63-e0f58ab981c1) <br>

```java
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHelper userHelper;
    private final TokenProvider tokenProvider;

    public TokenResponse login(UserLoginRequest request){
        String email = request.getEmail();
        String pwd = request.getPwd();

        final User selectedUser = userHelper.findByEmail(email);
        final Authentication authentication = userHelper.adminAuthorizationInput(selectedUser); // 유저의 권한 반환

        // password 맞는지 확인하기
        userHelper.validatePwd(selectedUser, pwd);

        //access 토큰 생성
        String accessToken = tokenProvider.createAccessToken(selectedUser.getId(), selectedUser.getEmail(), authentication);

        return TokenResponse.from(accessToken);
    }
}
```

```java
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request){

        //로그인 시 TokenResponse return
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(request));
    }
}
```
### ⭐ 로그인 성공한 경우
![로그인 성공 후 accessToken 발급](https://github.com/nzeong/Spring-study/assets/121355994/0afe25de-039c-4372-8c28-3027e69e1803)

### ⭐ 비밀번호 잘못된 경우
![비번 잘못됨](https://github.com/nzeong/Spring-study/assets/121355994/ad44bc8e-8735-4251-80da-753d8aa15008)

### ⭐ 유저가 존재하지 않는 경우
![존재안함](https://github.com/nzeong/Spring-study/assets/121355994/d0d34997-37af-45b6-a2c0-57d1d7d2143d)

## 4️⃣ 토큰이 필요한 API 1개 이상 구현하고 테스트하기

- 구현 후 API 테스트를 해봐요 (POST API)
  
```java
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(Customizer.withDefaults())
                .csrf((csrf) -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/posts/**").hasAnyRole("USER") // USER 권한 있어야지 POST 가능
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasAnyRole("USER")
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
}
```
```java
public class PostController {

    @PostMapping
    public ResponseEntity<Long> createPost(
            @RequestBody @Valid PostRequestDto request,
            @AuthenticationPrincipal PrincipalDetails user) {
            //@AuthenticationPrincipal 이용해서 PrincipalDetails type의 로그인 유저 정보 가지고 오기

        log.info("상품 게시글 생성하기");
        postService.createPost(request, user.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

...

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal PrincipalDetails user) {
        log.info("상품 게시글 삭제하기");
        postService.deletePost(id, user.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
```
### ⭐ 전체 조회
![전체 조회](https://github.com/nzeong/Spring-study/assets/121355994/5e9dc1b0-3da2-4e81-9ebe-91772562d904)

### ⭐ 게시글 생성
![헤더 토큰](https://github.com/nzeong/Spring-study/assets/121355994/4e4e2b86-5196-4032-9e2e-15ce0973a610)
![게시글 생성](https://github.com/nzeong/Spring-study/assets/121355994/9cf99347-f61d-4cad-8969-5286998ff14e)
![게시글 생성 db](https://github.com/nzeong/Spring-study/assets/121355994/233f3158-37da-4308-9cf6-364a388c64f0)

### ⭐ 삭제(soft delete)
![권한 x](https://github.com/nzeong/Spring-study/assets/121355994/3f7e0c73-a5ec-4eee-b1fe-737153ddab75)
![soft delete 성공](https://github.com/nzeong/Spring-study/assets/121355994/9dcdf3a3-65a1-45de-ad74-05af21c22743)
![삭제되어 나타남](https://github.com/nzeong/Spring-study/assets/121355994/3bb94db1-c869-4bff-897c-26745a54e0f3)

- 요청에 토큰이 포함되지 않았다면 어떻게 할까요?
```java
public class JwtAuthenticationFilter extends OncePerRequestFilter {
...

        if (token == null) {
            // 값이 들어오지 않은 경우
            logger.info("토큰을 찾을 수 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isNotBlank(token) && tokenProvider.validateAccessToken(token)) {
...
            //잘못된 token인 경우
            logger.info("Security Context에 " + authentication.getName() + "인증 정보를 저장했습니다, uri: " + requestURI);
        } else {
            logger.info("유효한 JWT 토큰이 없습니다, uri: " + requestURI);
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);
...
```
---

#### 참고한 사이트 
- https://velog.io/@jsang_log/Security-Filter-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0-JWT
- https://ws-pace.tistory.com/251
- https://velog.io/@seho100/Spring-boot%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-JWT-%EA%B5%AC%ED%98%84

---

스프링 Security랑 jwt 토큰을 연동해서 제대로 구현해본 건 이번이 처음인데, configuration, dependency 등 구현해야하는 내용도 많고 복잡하다보니 정말 쉽지 않은 과제였다. 개발 도중 자잘한 에러들이 많았는데, 에러를 해결하면서 힘들었던 점은 어디서 에러가 생겼는지 바로 알기 어렵다는 것이었다. 처음에 코드를 짤 때부터 생길 수 있는 여러가지 예외사항을 고려해서 예외/response/log 등을 반환해주는 것이 중요하다는 것을 느꼈다. 이번에 제대로 구현을 해보았으니 다음에 개발할 때는 개발 속도가 좀 더 빨라졌으면 좋겠고, refresh 토큰도 구현해보고 싶다.

---

# 💙 CEOS 18th Backend Study 5주차 💙

## ⭐ 1️⃣ 로컬에서 도커 실행해보기
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbxpwWR%2Fbtssv2wofvh%2Fku5xdm67ClGvJIjpHVn1EK%2Fimg.png)
Docker의 개념에 대해 알아보자면, 도커는 다양한 의존성을 추상화하여 어떤 환경에서든 실행하기 위해 필요한 모든 의존성을 포함하는 패키지로, 서비스 파일이 어디서나 동일하게 실행될 수 있도록 하는 환경을 조성해주는 것이라고 생각하면 된다. 그래서 서비스 운영에 필요한 서버 프로그램, 소스코드나 라이브러리, 컴파일된 실행 파일을 묶은 형태를 Docker Image 라고 한다. (jpeg 같은 이미지가 아니다) 이 이미지를 바탕으로 서비스를 돌린 것이 Docker Container인 것이다.

도커 이미지(Docker Image)는 컨테이너(Container)를 만드는 데 사용되는 읽기 전용 템플릿(Read-only templates)이고, 컨테이너(Docner Container)는 이러한 템플릿에서 생성된 배포된 인스턴스(Deployed Instances)라고 생각하면 좋다.

```
FROM openjdk:20
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
```

```yml
version: "3"

services:
  web:
    container_name: web
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/ceos18
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=
    volumes:
      - .:/app


  db:
    image: mysql:5.7
    environment:
      MYSQL_ALLOW_EMPTY_PASSWORD: 'yes'
      MYSQL_DATABASE: ceos18
    volumes:
      - dbdata:/var/lib/mysql
    ports:
      - 3306:3306
    restart: always

volumes:
  app:
  dbdata:
```
#### 실행 완료
![docker 실행](https://github.com/nzeong/Spring-study/assets/121355994/29589e99-85a3-4925-91a2-b0663b2f323d)

## ⭐ 2️⃣ API 추가 구현 및 리팩토링

####  회원가입 API 구현

![회원가입1](https://github.com/nzeong/Spring-study/assets/121355994/0f380cc8-1ea8-4afd-a818-b6d1caac34cb)
![회원가입1db](https://github.com/nzeong/Spring-study/assets/121355994/fc4fae3e-b69b-40f1-a2b0-69e83c00800f)
