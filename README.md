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
