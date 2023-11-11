
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


## 1️⃣ JWT 인증(Authentication) 방법에 대해서 알아보기

- JWT를 이용한 인증 방식(액세스토큰, 리프레쉬 토큰)에 대해서 조사해봐요
- 추가로 세션, 쿠키, OAuth 등 다른 방식도 조사해봐요
- 자세하게 조사할수록 도움이 되겠죠? 🤩

## 2️⃣ 액세스 토큰 발급 및 검증 로직 구현하기
> 로그인은 email과 pwd로 진행

- TokenProvider 클래스에 적절한 메서드 구현
```java
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    @Value("${jwt.token.secret}")
    private String secret; // secret key 환경변수 설정
    private Key key;
    private Long expireTimeMs = 1000 * 60 * 60L; // 만료 시간 1시간

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

    public String createAccessToken(Long id, String email, Authentication authentication){
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("id", id);
        claims.put("email", email);
        claims.put("auth", authorities);
        claims.put("type", "access");

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(email) // 이메일을 subject
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact()
                ;
    }

    // 토큰
    public String getTokenUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 유저 이메일 반환
    }

    public Authentication getAuthentication(String token) {
        PrincipalDetails principalDetails =
                (PrincipalDetails)
                        principalDetailsService.loadUserByUsername(getTokenUserEmail(token));
        return new UsernamePasswordAuthenticationToken(
                principalDetails, token, principalDetails.getAuthorities());
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info(e.toString());
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
```

#### ⭐ UserDetails, UserDetailsService 왜 사용해야하는 것일까?
![context](https://github.com/nzeong/new-piro-game-BE/assets/121355994/3b3e2f12-8dc7-4af9-a620-b803344931a2) <br>
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
        collection.add(new SimpleGrantedAuthority("ROLE_USER")); // "ROLE_USER" 권한
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

            logger.debug("Security Context에 " + authentication.getName() + "인증 정보를 저장했습니다, uri: " + requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: " + requestURI);
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

## 4️⃣ 토큰이 필요한 API 1개 이상 구현하고 테스트하기

- 구현 후 API 테스트를 해봐요
- 요청에 토큰이 포함되지 않았다면 어떻게 할까요?

## 5️⃣ (도전 미션~!) 리프레쉬 토큰 발급 로직 구현하고 테스트하기

- 시간이 남는다면 `TokenProvider`에 리프레쉬 토큰 발급 로직을 구현해봐요
- 발급한 리프레쉬 토큰은 어떻게 사용할 수 있을까요?
