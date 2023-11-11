# daangn-market

## ERD
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/106292f1-b891-45be-af48-ea05cb0faad2)


- 장소 정보 테이블 : sido_area, sigg_area, emd_area
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/049b4650-2e63-448c-8234-46f05f27ff42)
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/c4734b8c-9064-4828-8d5e-d47a4aac7e19)
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/c6c204e5-ebdb-4342-8e92-e5ebff7821f0)


- 사용자 정보 테이블 : user, activity_area
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/d7747ff7-11a4-45e2-8572-72ce0d6cce59)

- 파일 관련 테이블 : file
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/751341a6-2045-4f06-8256-bf0dbc05ebe9)

- 상품 관련 테이블 : category, product, price_offer, product_image
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/f0ec1250-6a39-437e-a5c8-d4037bb4161e)
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/2e1fbd16-bc79-4cba-ab81-177216c6f112)
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/1aaef436-7ffa-4e8c-bcde-1e40fca078d9)
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/0902f26a-dce2-43cd-a9ef-16da7e34dc54)

- 관심 상품 테이블 : wish_list
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/f04349e1-47ce-49a0-9509-534b1ce0d978)

- 리뷰 (온도) 테이블 : review
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/9597b59c-5c29-4819-a0f8-e005c68cac9b)

- 채팅 관련 테이블 : chat_room, chat_message
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/c006e029-b309-4644-ab69-a16e95f93cc8)
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/e30b605b-f6d2-499b-8acc-f9d0e83d9f7d)


## Repository Test
```java
  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("사용자 저장")
  void saveUser() {
    // given
    User user = new User(1L, "ROLE_USER", "01012345678", true, 100.0);
    // when
    User savedUser = userRepository.save(user);
    // then
    Assertions.assertThat(user).isSameAs(savedUser);
    Assertions.assertThat(user.getPhoneNumber()).isEqualTo(savedUser.getPhoneNumber());
    Assertions.assertThat(savedUser.getId()).isNotNull();
    Assertions.assertThat(userRepository.count()).isEqualTo(1);
  }
```

## CRUD API
1. 게시글 생성 API ```/api/product```
    - Service
    ```java
    public Product postProduct(PostProductRequest postProductRequest, Long userId){
      User user = userRepository.findById(userId).orElseThrow();
      EmdArea emdArea = emdAreaRepository.findById(postProductRequest.getAreaId()).orElseThrow();
      Category category = categoryRepository.findById(postProductRequest.getCategoryId()).orElseThrow();
    
      Product createdProduct = productRepository.save(
      Product.builder()
      .user(user)
      .emdArea(emdArea)
      .category(category)
      .title(postProductRequest.getTitle())
      .status(postProductRequest.getStatus())
      .sellPrice(postProductRequest.getSellPrice())
      .viewCount(0)
      .description(postProductRequest.getDescription())
      .build()
      );
    
      return createdProduct;
    }
    ```

2. 게시글 전체 조회 API ```/api/product```
    - Service
    ```java
    public List<Product> getAllProduct(){
      return productRepository.findAll();
    }
    
    ```
3. 게시글 개별 조회 API ```/api/product/{productId}```
    - Service
    ```java
    public Product getProductById(Long productId){
      return productRepository.findById(productId).orElseThrow();
    }
    ```
4. 게시글 삭제 API ```/api/product/{productId}```
    - Service
    ```java
    @Transactional
    public String deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow();
    
        productRepository.delete(product);
    
        return "Product #"+productId+" deleted successfully";
    }
    ```

## 후기
- 아직 repository test에서 오류가 발생하고 있어, 빠른 시일 내에 해결을 하고 싶습니다... 흔히 나오는 해결 방법이었던 setting 설정 변경으로는 해결되지 않더라고요...
![image](https://github.com/gmkim20713/daangn-market/assets/68195241/22d888a8-a402-42bd-a893-baeb9e4ec96c)

***

# Authorization
> 인증(Authentication) : 보호된 리소스에 접근하는 대상, 즉 사용자에게 적절한 접근 권한이 있는지 확인하는 과정 (로그인)
>
> 인가(Authorization) : 인증 절차가 끝난 접근 주체가 보호된 리소스에 접근 가능한지를 결정하는 것 (계정이 있는 사용자만 사용할 수 있는 기능 확인)

## Cookie
- 인증 정보를 브라우저에서 저장하고 관리하는 것
- 용량 제한
- 브라우저 간 공유 불가능
- 보안에 취약
- 크기가 커지면 네트워크 부하가 심해짐

## Session
- 인증 정보를 브라우저가 아닌 서버 측에 저장하고 관리하는 것
- 추가적인 저장 공간이 필요
- session id 사용
- 사용자가 많아지면 그만큼 메모리를 많이 차지

## JWT (Json Web Token)
- JSON 객체를 통해 안전하게 정보를 전송할 수 있는 웹표준
- '.'을 구분자로 세 부분으로 구분되어 있는 문자열로 이루어짐
  - 헤더 : 토큰 타입과 해싱 알고리즘
  - 내용 : 실제로 전달할 정보
  - 서명 : 위변조를 방지하기 위한 값

### JWT를 이용한 인증 방식
- 사용자 로그인 시 access token과 refresh token 생성 -> 클라이언트 응답
- access token을 담아 데이터를 요청하면
  - 유효한 토큰일 경우 요청 값 반환
  - 유효하지 않거나 만료된 토큰이면 x
- 만료된 토큰의 경우, refresh token을 통해 access token을 새로 발급 가능

## OAuth
- 외부서비스의 인증 및 권한 부여를 관리하는 범용적인 프로토콜
- 외부서비스의 기존 사용자 정보를 사용하는 방식

# JWT 토큰 발급
- TokenProvider
```java

```

- JwtAuthenticationFilter
```java

```

# 로그인 API

# 사용자 프로필 API
> 토큰이 필요한 API

