# spring-daagn-market-18th

# 1️⃣ 당근 마켓의 DB를 모델링해요

## ERD

![](https://velog.velcdn.com/images/aeyongdodam/post/678f7371-3dcb-41f0-bfb7-207297c93a13/image.png)

    - 물건 올리기
    - 물건 찾기
    - 1:1 채팅
    - 구매 확정
    - 리뷰(온도)

제시된 기능을 위주로 erd를 짜보았습니다. 외래키에 대한 설정이 많은 형태로 짰는데 이게 좋은 db저장인지 잘 모르겠네요,,</br>
crud 상황에서 테이블 관리를 통일되게 하는 것을 목표로 하였습니다.

## 테이블 설명

### User

![](https://velog.velcdn.com/images/aeyongdodam/post/03c03cec-630c-43a8-bf0b-05a9b4bba08b/image.png)

- userId (primary key): int
- nickName : string → unique
- password : string
- profilePicture : string
- temperature(온도) : double
- retradingRate(재거래 희망률) : double
- responseRate(응답률) : double
- createdAt
- updatedAt
  </br></br>

### Achievement(뱃지)

![](https://velog.velcdn.com/images/aeyongdodam/post/a130d04e-77ab-428a-a0b8-f33851aac8ca/image.png)

- id(Primary Key) : int
- userId(Foreign Key to User)
- achievementNum :int
  </br>어떤 뱃지를 획득했는가에 대한 number
  </br>새로운 뱃지가 추가될 가능성이 높기때문에 enum 대신 int로 관리
- createdAt
- updatedAt

-> 어떤 유저가 어떤 뱃지를 획득했는지 관리하기 위한 테이블
userId를 외래키로 가짐

⭐️ 고민했던 부분 : Achievement 특성상 추가만 필요할 뿐, 삭제하는 기능은 없어보였는데 굳이 테이블로 빼야하나에 대한 고민
그러나 전체적인 설계과정에서 다대일 관계는 전부 테이블로 매핑시켰기 때문에 테이블로 생성
</br></br>

### Address

![](https://velog.velcdn.com/images/aeyongdodam/post/040e18df-9868-4329-a898-d37358d7c9eb/image.png)

- addressId(Primary key) : int
- userId(Foreign Key to User): int
- regionId(Foreign Key to Region): int
  </br>Region(동네) 테이블과 매핑
- isVerified: boolean
  </br>동네 인증을 받았는지에 대한 것
- expirationDate: timestamp
  </br>동네 인증에 대한 유효기간
- createdAt
- updatedAt
  </br>유저가 어떤 지역을 설정했는지 보여주는 테이블
  </br>한 유저 당 2개의 동네 설정 가능
  </br></br>

### Region

![](https://velog.velcdn.com/images/aeyongdodam/post/4b87a0fa-58b3-4186-9ef7-ed31a678b1f0/image.png)

- city: int
  </br>동네보다 더 큰 개념
- regionId(Primary Key) : int
- regionName : string

</br>전체 지역과 동네에 대한 정보를 다 담고 있는 테이블
</br></br>

### RegionDistance

![](https://velog.velcdn.com/images/aeyongdodam/post/1155c165-223f-4003-bd03-e1908b4daa6c/image.png)

- id(Primary Key) : int
- baseTownId(Foreign Key to Region) 기준 동네
- nearbyTownId(Foreign Key to Region)
  가까운 동네
- distance : double
  </br>
  ⭐️ 고민했던 부분 : 당근마켓은 자신이 설정한 동네의 거리에 따라 물건들을 보여줌, 그런데 동네 사이의 거리를 어떻게 해야할지에 대해 고민이 있었음. 이 정보를 메모리상에서 계산하는 것보다 db에 저장하는 것이 낫다고 생각하여 이렇게 작성
  </br></br>

### ChatRoom

![](https://velog.velcdn.com/images/aeyongdodam/post/18368c13-2ca1-42bc-bd83-388dbb27c7fb/image.png)

- id(Primary Key) : int
- sellerId: int
  </br>물건을 파는 사람
- buyerId: int
  </br>물걸을 사려는 사람
- productId(Foreign Key to product)
  </br>
  물건 별로 채팅방이 만들어지기 때문에 채팅방을 구성할 때 productId가 외래키로 들어감
  </br></br>

### ChatMessage

![](https://velog.velcdn.com/images/aeyongdodam/post/c3eab1cb-182c-4411-aa2f-ad56c1457963/image.png)

- id(Primary Key): int
- chatRoomId(Foreign Key to ChatRoom): int
- senderId: int
- content: text
- createdAt
- updatedAt
- status: enum(전송됨, 읽음 등등)
  </br>1:1채팅은 채팅룸에 이미 두 사람을 저장하기 때문에 메세지에서는 senderId만 저장하면됨. createdAt으로 정렬해서 보내주는 형식으로 짜면됨
  </br></br>

### Product

![](https://velog.velcdn.com/images/aeyongdodam/post/756e3246-d842-4d53-912f-f33daac1cbd1/image.png)

- Id(Primary Key): int
- userId(Foreign Key to User): int
- price : long
- status(판매완료, 판매중, 예약중) : enum
- category : int
- RegionId(Foreign Key to Region) : int

  </br>상품에 대한 테이블
  </br>올린 유저와 가격, 상품 정보에 대한 것을 저장
  </br>카테고리 설정에 따라 저장
  </br>설정한 동네에 따라 보여주기 때문에 RegionId를 외래키로 매핑
  </br></br>

### ProductImage

![](https://velog.velcdn.com/images/aeyongdodam/post/77bd64a7-4029-4ec2-a42f-1c04b9f0fd36/image.png)

- id(Primary Key) : int
- productId (Foreign Key to product) : int
- imageUrl : string
  </br>게시물 하나 당 여러 이미지를 올릴 수 있음
  </br>다대일 매핑을 위해 product와 productImage를 productId로 연결
  </br></br>

### Comment

![](https://velog.velcdn.com/images/aeyongdodam/post/bc421a1f-4808-486f-9f18-1bc9614c9ab6/image.png)

- id (Primary Key): int
- receiverId(Foreign Key to User) : int
  </br>코멘트를 받은 사람의 userId
- writerId(Foreign Key to User): int
  </br>코멘트를 적은 사람의 userId
- comment: text
- createdAt
- updatedAt

</br></br>

### WishList

![](https://velog.velcdn.com/images/aeyongdodam/post/b440003c-ca78-4511-bbc5-97b740828df1/image.png)

- id(Primary Key): int
- productId(Foreign Key to Product): int
- userId(Foreign Key to User)
  </br>유저가 하트를 누른 목록에 대한 것
  </br>상품 이름이 아니라 게시물에 대해 저장하기 때문에 productId를 외래키로 매핑
  </br></br>

## User Entity

```java

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String password;

    private String profileUrl;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double retradingRate;

    @Column(nullable = false)
    private double responseRate;

    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) //영속성 전이
    private List<Achievement> achievements;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
```

# 2️⃣ Repository 단위 테스트를 진행해요

## ForeignKey 필드를 포함하는 Entity을 하나 선택하여 다음과 같은 테스트를 진행해주세요

</br>

### given when then 에 따라서 테스트를 작성하기

given-when-then : given when then 에 따라서 테스트를 작성하기
given-when-then은 [준비 - 실행 - 검증]임
</br>
given : 테스트를 위해 준비를 하는 과정, 테스트에 사용하는 변수, 입력값을 정의하거나 Mock 객체를 정의하는 구문도 given에 포함됨

</br></br>
when : 실제로 액션을 하는 테스트를 실행하는 과정
</br></br>
then: 테스트를 검증하는 과정, 예상한 값, 실제 실행을 통해 나온 값을 검증한다.

user와 achievement를 이용해서 두 가지 테스트 코드를 작성하였습니다.

## UserTest

### 1. given

```java
	//given
	User user1 = new User();
	user1.setNickName("user");
	user1.setPassword("1234");
	user1.setTemperature(36.5);
	user1.setRetradingRate(80.0);
	user1.setResponseRate(80.0);
	userRepository.save(user1);
```

</br></br>

### 2. when

```java
//        when
        Optional<User> retrievedUser = userRepository.findById(user1.getId());
```

</br></br>

### 3. then

```java
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getNickName()).isEqualTo("user");
        assertThat(retrievedUser.get().getPassword()).isEqualTo("1234");
        assertThat(retrievedUser.get().getTemperature()).isEqualTo(36.5);
        assertThat(retrievedUser.get().getRetradingRate()).isEqualTo(80.0);
        assertThat(retrievedUser.get().getResponseRate()).isEqualTo(80.0);

        List<User> allUsers = userRepository.findAll();
        logger.info("유저목록 : ");
        for (User user : allUsers) {
            logger.info("User ID: {}, Nickname: {}", user.getId(), user.getNickName());
        }

```

유저를 한 명 생성하고, 정보가 잘 들어갔는지 테스트

## userId를 참조하는 achievement 레포지토리 테스트

### 1. given

```java
// Given
        User user1 = new User();
        user1.setPassword("1");
        user1.setNickName("user1");
        user1.setTemperature(36.5);
        user1.setRetradingRate(80.0);
        user1.setResponseRate(80.0);

        User user2 = new User();
        user2.setPassword("2");
        user2.setNickName("user2");
        user2.setTemperature(36.5);
        user2.setRetradingRate(80.0);
        user2.setResponseRate(80.0);

        User user3 = new User();

        user3.setPassword("3");
        user3.setNickName("user3");
        user3.setTemperature(36.5);
        user3.setRetradingRate(80.0);
        user3.setResponseRate(80.0);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
```

### 2. when

```java
        Achievement ac1 = new Achievement();
        ac1.setAchievementNum(1);
        ac1.setUser(user1);
        Achievement ac2 = new Achievement();
        ac2.setAchievementNum(2);
        ac2.setUser(user1);
        Achievement ac3 = new Achievement();
        ac3.setAchievementNum(3);
        ac3.setUser(user3);

        achievementRepository.save(ac1);
        achievementRepository.save(ac2);
        achievementRepository.save(ac3);
```

### 3. then

```java
        List<Achievement> achievements = achievementRepository.findAll();
        assertEquals(3, achievements.size());
        assertEquals("user1", achievements.get(0).getUser().getNickName(), "First achievement's user nickname mismatch.");
        assertEquals("user1", achievements.get(1).getUser().getNickName(), "Second achievement's user nickname mismatch.");
        assertEquals("user3", achievements.get(2).getUser().getNickName(), "Third achievement's user nickname mismatch.");


        List<User> allUsers = userRepository.findAll();
        logger.info("유저목록 : ");
        for (User user : allUsers) {
            logger.info("User ID: {}, Nickname: {}", user.getId(), user.getNickName());
        }
```

</br>
userId를 참조하고 있기 때문에 user테이블에 없는 id이면 애초에 achievement에 db에 생성되지 않음.
</br>
한 유저가 여러 가지 업적을 가질 수 있는 상황을 가정하여 테스트하고, db에 잘 들어갔는지에 대해 테스트
</br></br>

## 테스트에서 객체를 3개 이상 넣은 이후에 해당 객체가 출력되는지 확인하기

위 테스트의 로그

```
2023-09-30T20:54:17.071+09:00  INFO 11773 --- [    Test worker] com.carrot.clonecoding.user.UserTests    : 유저목록 :
2023-09-30T20:54:17.072+09:00  INFO 11773 --- [    Test worker] com.carrot.clonecoding.user.UserTests    : User ID: 1, Nickname: user1
2023-09-30T20:54:17.072+09:00  INFO 11773 --- [    Test worker] com.carrot.clonecoding.user.UserTests    : User ID: 2, Nickname: user2
2023-09-30T20:54:17.072+09:00  INFO 11773 --- [    Test worker] com.carrot.clonecoding.user.UserTests    : User ID: 3, Nickname: user3

```

## 테스트를 수행할 때 발생하는 JPA 쿼리를 조회해보기

![](https://velog.velcdn.com/images/aeyongdodam/post/d20242bf-1733-4fcc-96b7-e0b868db4e24/image.png)

### 느낀 점

역시 db 설계가 가장 어려운 것 같다. 테스트 특히 단위 테스트는 만들어 볼 기회가 없었는데 이번에 많이 배울 수 있었다.
테스트 코드를 작성할 때 여러 기능을 써보면서 테스트 코드를 쓰는 여러 방법을 배울 수 있었다.
이번 테스트 코드는 테스트가 종료되면 데이터베이스에는 테스트 데이터가 남아있지 않도록 하는 방향으로 짰다.

# 3주차 미션

## 2주차 수정사항

1. db column을 camel case에서 snake case로 바꿨습니다.
2. common/base폴더를 만들어서 BaseTimeEntity로 createdDate, updatedDate를 넣었습니다

   ```java
   @MappedSuperclass
   @Setter
   @Getter
   @EntityListeners(AuditingEntityListener.class)
   public class BaseTimeEntity {
       @CreatedDate
       @Column(updatable = false)
       private LocalDateTime createdDate;

       @LastModifiedDate
       private LocalDateTime updatedDate;
   }

   ```

3. common/enums를 만들어서 enum타입을 넣었습니다.
4. primary key id를 int 에서 Long으로 바꾸었습니다.
5. ProductImage에서 대표 이미지를 설정하기 위해 새로운 Column을 만들었습니다.

## TODO

address 테이블과 Region테이블 빠진 내용 포함하여 다시 설계하기
</br></br>

## CRUD를 만들 때 새로 배운 점

- URI에 HTTP Method 가 들어가면 안됨
  - 1.  RESTful 원칙 : REST 아키텍처 원칙에서는 URI는 자원을 나타냄
  - 2. HTTP 메서드는 해당 자원에 대한 동작을 나타냄

-> 장점 : 동일한 URI에 대해 다양한 HTTP 메서드 (GET, POST, PUT, DELETE 등)를 적용할 수 있음

- URI에 특정 동작을 포함시키면, 새로운 동작을 추가할 때마다 URI를 변경해야 함 -> 확장성이 좋지 않음</br>
- 동작을 URI에 포함시키면, 어떤 동작들이 가능한지 클라이언트가 알 수 있기 때문에 보안의 위험이 있음</br></br>

**이번 과제는 User model을 뽑아서 만들었습니다!**</br></br>

## 1️⃣ 새로운 데이터를 create하도록 요청하는 API 만들기

### URL : api/user

### Method : POST

### Body 예시

```json
{
  "nickName": "soh",
  "password": "1234",
  "email": "aaa@gmail.com",
  "phonenum": "010-1111-1111"
}
```

- 새로운 유저 데이터 행을 만드는 api
- password는 그대로 저장하면 안되고 암호화해서 저장해야함
- createUserDto를 만들어서 필요한 데이터만 저장할 수 있도록 함</br></br>

### controller

```java
    @PostMapping("api/user")
    public String createUser(@RequestBody final CreateUserDto createUserDto) throws Exception{
        userService.createUser(createUserDto);
        return "success";
    }
```

</br>
### service

```java
    @Transactional
    public User createUser(final CreateUserDto createUserDto) throws Exception{
        logger.info("phonenum {}",createUserDto.getPhonenum());
        if (userRepository.findByPhonenum(createUserDto.getPhonenum()).isPresent()) {
            logger.info("이미 존재");
            throw new Exception("already exists");
        }

        User user = User.builder()
                .nickName(createUserDto.getNickName())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .phonenum(createUserDto.getPhonenum())
                .temperature(36.5)
                .responseRate(0)
                .retradingRate(0)
                .build();
        user.passwordEncode(passwordEncoder);
        return userRepository.save(user);

    }
```

![](https://velog.velcdn.com/images/aeyongdodam/post/4095df68-8b19-42e9-8b4a-a454a20cba74/image.png)</br></br>

## 2️⃣ 모든 데이터를 가져오는 API 만들기

### URL : api/user

### Method : GET</br>

### controller

```java
    @GetMapping("api/user")
    public List<UserResponseDto> findAllUsers() {
        return userService.findAllUsers();
    }
```

</br>
### service

```java
    public List<UserResponseDto> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
```

User의 모든 정보를 단순히 findAll로 넘겨주게되면 password처럼 넘겨서는 안되는 정보가 포함될 수도 있음
그렇기 때문에 UserResponseDto를 만들어서 넘겨줄 정보만 저장한 다음, 해당 리스트의 요소들에 대한 스트림을 생성하는 방식으로 모든 유저의 정보를 넘겨주는 api를 만들었음</br></br>

```json
[
  {
    "nickName": "soh",
    "email": "aaa@gmail.com",
    "phonenum": "010-1111-1111",
    "temperature": 36.5,
    "responseRate": 0.0,
    "retradingRate": 0.0
  },
  {
    "nickName": "soh2",
    "email": "aaa@gmail.com",
    "phonenum": "010-1111-1112",
    "temperature": 36.5,
    "responseRate": 0.0,
    "retradingRate": 0.0
  }
]
```

</br></br>

## 3️⃣ 특정 데이터를 가져오는 API 만들기

### URL : api/user/{id}

### Method : GET

findAll과 마찬가지로 넘겨서 안되는 정보를 제외하기 위해 UserResponseDto를 만들어서 넘겨줄 정보만 저장</br></br>

### Controller

```java
    @GetMapping("api/user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) {
        UserResponseDto userDto = userService.findUserById(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }
```

</br></br>

### Service

```java
    public UserResponseDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDto::new)
                .orElse(null);
    }
```

```json
{
  "nickName": "soh",
  "email": "aaa@gmail.com",
  "phonenum": "010-1111-1111",
  "temperature": 36.5,
  "responseRate": 0.0,
  "retradingRate": 0.0
}
```

</br></br>

## 4️⃣ 특정 데이터를 삭제 또는 업데이트하는 API

### URL : api/user/{id}

### Method : DELETE

id에 해당하는 행을 삭제하는 방식으로 작동
만약 없는 id가 있다면 nullpointerexception이 나지 않도록 예외처리
</br></br>

### Controller

```java
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("delete success");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
```

</br></br>

### Service

```java
public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
```

</br></br>

# 4주차 미션

## 1️⃣ JWT 인증(Authentication) 방법에 대해서 알아보기

### JWT를 이용한 인증 방식(액세스토큰, 리프레쉬 토큰)에 대해서 조사해봐요

1. JWT란?

- Json Web Token
- Json 포맷을 이용하여 사용자에 대한 속성을 저장하는 Claim 기반의 Web Token
- JWT는 토큰 자체를 정보로 사용하는 안전한 Self-Contained 방식
  - cf) self-contained 방식이 안전한 이유 : 토큰 자체가 필요한 모든 정보를 내장하고 있다는 것이기 때문
- JWT는 헤더(Header), 페이로드(Payload), 서명(Signature) 세 부분으로 구성됨
  - Json 형태인 각 부분은 Base64Url로 인코딩 되어 표현됨
  - 각각의 부분을 이어 주기 위해 . 구분자를 사용
  - header와 payload는 암호화된 문자열이 아닌 base64Url인코딩된 문자열, 즉 같은 문자열에 대해 항상 같은 인코딩 문자열을 반환함
- 헤더 - typ과 alg 두 가지 정보로 구성
  typ: 토큰의 타입을 지정 ex) JWT
  alg: 알고리즘 방식을 지정하며, 서명(Signature) 및 토큰 검증에 사용 ex) HS256(SHA256) 또는 RSA
- SHA256 vs RSA
  - SHA256 : RSA(Secure Hash Algorithm) 알고리즘의 한 종류로서 256비트로 구성되며 64자리 문자열을 반환
    - 표준 해시 알고리즘인 SHA-2 계열 중 하나이며 블록체인에서 가장 많이 채택하여 사용
    - 2^256 만큼 경우의 수를 만들 수 있음
    - JWT 작동 방식 1. Header, Payload 는 Base64로 인코딩됨(복호화 가능 정보) 2. Signature 는 "Header + Payload + secret 값" 을 HS256 알고리즘으로 암호화 3. Header, Payload, Signature 로 JWT 토큰을 만들어 클라이언트로 보내고, 클라이언트는 로컬 스토리지에 토큰을 저장 4. 클라이언트는 서버에 요청이 있을 경우, 토큰과 요청 내용을 같이 보냄 5. Header 와 Payload 를 Base64 알고리즘으로 복호화함 6. secret 값를 가지고 다시 HS256 알고리즘을 이용해 암호화해보고, 클라이어트에서 보낸 토큰과 같은지 유효성 검증
      -> 다르다면 invalid token, 같다면 valid token
      </br></br>
  - RSA : 비대칭키 암호화 알고리즘
    - 비대칭키 암호화 알고리즘은 공개키(public key)와 개인키(private key)를 이용해 암호화를 진행
    - secret 값이 필요하지 않음
    - JWT 작동 방식
      1. Header, Payload 는 Base64로 인코딩됨(복호화 가능 정보)
      2. Header, Payload는 서버의 개인키로 암호화 Signature를 만들게 됨
      3. 토큰을 만들어 클라이언트에 보냄
      4. 클라이언트는 서버에 요청을 보낼 때 토큰과 요청 내용을 보냄
      5. 토큰의 유효성 검증을 하기 위해서, 공개키로 Signature 를 복호화
  - HMAC 방식(SHA256 방식과 같은 해시 기반 인증)의 핵심은 서버의 secret값을 노출시키지 않는 것
  - RSA 방식은 토큰의 서명을 생성하고 검증하는 데 두 개의 다른 키를 사용하기 때문에, 서버의 개인키가 안전하게 보관되는 한 공개키를 외부에 공개해도 안전함
    </br>
- 페이로드 - 토큰에서 사용할 정보의 조각들인 클레임이 담겨있음

  - Json(Key/Value) 형태로 여러 정보를 넣을 수 있음
  - 클레임의 종류는 등록된 클레임, 공개 클레임, 비공개 클레임이 있음
  - cf) 표준 스펙으로 정의되어있는 claim의 스펙
    </br></br>
    iss (Issuer) : 토큰 발급자

    sub (Subject) : 토큰 제목 - 토큰에서 사용자에 대한 식별 값이 됨

    aud (Audience) : 토큰 대상자

    exp (Expiration Time) : 토큰 만료 시간

    nbf (Not Before) : 토큰 활성 날짜 (이 날짜 이전의 토큰은 활성화되지 않음을 보장)

    iat (Issued At) : 토큰 발급 시간

    jti (JWT Id) : JWT 토큰 식별자 (issuer가 여러 명일 때 이를 구분하기 위한 값)

- 서명 - 토큰을 인코딩하거나 유효성 검증을 할 때 사용하는 고유한 암호화 코드

- 쿠키 : 브라우저에 저장되는 정보
  - 쿠키는 크롬이나 사파리 같은 브라우저에 저장되는 작은 텍스트 조각
  - 클라이언트 단에서 관리
  - 브라우저의 설정 화면이나 개발자 도구에서 쿠키를 확인하고 수정, 삭제할 수 있음
  - 쿠키는 당사자뿐만 아니라 제 3자가 조회하는 것도 가능하기 때문에 개인 정보를 담은 내용이나 보안상 민감한 정보를 저장하는 데에는 적합하지 않음
  - 남에게 탈취되거나 사용자에 의해 조작되어도 크게 문제되지 않을 정보만을 쿠키에 저장해야함
- 세션 : 서버가 나를 알아보는 방법
  - 서버는 어떤 요청이 어떤 클라이언트인지 구분할 수 없음
  - 사용자가 로그인을 하고 나서 인증받은 유저인지 아닌지를 요청 하나만으로는 구분할 수 없음
  - 사용자가 사이트에 한 번 로그인하면 유효기간이 끝날 때까지 더 이상 아이디와 비밀번호를 입력하지 않아도 되도록 사용자가 이미 서버로부터 인증받았음을 증명해 주는 세션이라는 증서를 발급해줌
  - 서버가 세션 아이디를 사용자에게 전달하고, 메모리에 어떤 아이디와 유저가 매핑되는지 저장
  - 서버가 정보를 메모리에 올리기 때문에, 서버에 접속하는 사람이 많아질수록 과부하가 걸리게 됨(매 요청마다 메모리에 있는 정보와 매핑해서 확인하기 때문)
- 토큰 - 로그인한 사용자에게 세션 아이디 대신 토큰을 발급해줌
  - 서버만이 유효한 토큰을 발행할 수 있음
  - 토큰을 받아간 클라이언트가 쿠키에 저장해두고 필요할 때마다 제시하면 서버가 알아보고 허가해주는 방식
  - 토큰은 탈취당할 위험이 있지만, 토큰에는 유효기간이 있음
- 캐시 : 한 번 전송받은 데이터는 저장해 놨다가 똑같은 요청이 들어오면 꺼내 쓰는 개념

  - 반복적으로 사용하는 콘텐츠를 빠르게 이용할 수 있고 데이터 사용량도 줄일 수 있음
  - 클라이언트 입장에서 가장 가까이 접하는 캐시는 브라우저 캐시
  - 같은 리소스에 대한 재요청 시 서버에 다시 요청하지 않고 캐시에서 빠르게 로드됨
  - 캐시를 잘 사용한다면 클라이언트는 정보를 빠르게 받아볼 수 있고, 서버는 서버비를 아낄 수 있음

- 쿠키와 캐시의 차이 :

  - 쿠키는 사용자의 상태 정보를 저장하고 유지하는 역할임(로그인, 장바구니 등), 브라우저는 동일한 서버로 요청을 보낼 때마다 쿠키를 HTTP 요청과 함께 전송함
  - 캐시는 데이터의 전송량을 줄이고 서비스 이용 속도를 높이는걸 목적으로 함, 자주 요청되는 데이터는 캐시에 저장하였다가 유저가 요청하면 서버에 요청하지 않고 캐시에서 로드해줄 수 있음

- CDN : 콘텐츠 전송 네트워크

  - 지리적으로 분산된 여러 개의 서버를 이용해 웹 콘텐츠를 사용자와 가까운 서버에서 전송함으로써 전송 속도를 높임
  - 서버가 데이터를 전 세계 각지에 세워진 캐시 저장 및 전달용 컴퓨터(CDN 업체 소유)들에 보내면 사용자는 본 서버가 아닌 본인에게서 가장 가까운 캐시 서버로 요청을 보내고 데이터를 받아오게 됨

- AccessToken과 RefreshToken의 차이

  - AccessToken : 본인을 인증하는 용도
  - RefreshToken : 새로운 AccessToken을 생성하는 용도
  - AccessToken은 짧은 유효기간(60분정도)을 가지고 RefreshToken은 긴 유효기간(30일정도)을 가짐

- AccessToken 만료와 RefreshToken을 통한 재발급 과정

  - AccessToken은 위와 같이 알고리즘에 따라 상이한 작동 방식을 가짐
  - RefreshToken을 쓰는 이유 : JWT는 stateless한 방식이기 때문에 서버측에서는 이 토큰을 갖고 있는 클라이언트가 정말 클라이언트 본인이 맞는지 확인할 수 없다는 문제에 대응하기 위해서
    - JWT 유출 문제를 해결하기 위해 AccessToken의 유효기간을 짧게 설정 -> 토큰이 탈취되어도 피해를 최소화 할 수 있음
    - 정상 클라이언트도 AccessToken을 계속 재로그인해서 받아야한다는 단점이 있음 -> 유효기간이 긴 RefreshToken을 이용하여 AccessToken을 발급받게 함

- cf) RefreshToken이 탈취당할 가능성?

  1. RefreshToken이 클라이언트와 서버 간에 전송되는 과정에서, 암호화되지 않은 채널을 통해 전송될 경우 탈취될 수 있음

  2. 크로스 사이트 스크립팅(XSS) 공격을 통해 탈취될 수 있음

  - RefreshToken이 탈취되었을 때, 피해를 최소화할 수 있는 방안 - 데이터베이스에 각 사용자에 1대1로 맵핑되는 Access Token, Refresh Token 쌍을 저장한다. - 정상적인 사용자는 기존의 Access Token으로 접근하며 서버측에서는 데이터베이스에 저장된 Access Token과 비교하여 검증한다. - 공격자는 탈취한 Refresh Token으로 새로 Access Token을 생성한다. 그리고 서버측에 전송하면 서버는 데이터베이스에 저장된 Access Token과 공격자에게 받은 Access Token이 다른 것을 확인한다. - 만약 데이터베이스에 저장된 토큰이 아직 만료되지 않은 경우, 즉 굳이 Access Token을 새로 생성할 이유가 없는 경우 서버는 Refresh Token이 탈취당했다고 가정하고 두 토큰을 모두 만료시킨다. - 이 경우 정상적인 사용자는 자신의 토큰도 만료됐으니 다시 로그인해야 한다. 하지만 공격자의 토큰 역시 만료됐기 때문에 공격자는 정상적인 사용자의 리소스에 접근할 수 없다.
    </br></br>
  - 출처 : https://velog.io/@park2348190/JWT%EC%97%90%EC%84%9C-Refresh-Token%EC%9D%80-%EC%99%9C-%ED%95%84%EC%9A%94%ED%95%9C%EA%B0%80

- RefreshToken 인증 과정
  ![](https://velog.velcdn.com/images/aeyongdodam/post/22032540-6424-4dac-9581-a05cf91a8da8/image.png)
  </br></br>

- OAuth2 로그인 인증 과정
  ![](https://velog.velcdn.com/images/aeyongdodam/post/c9c4cb6a-96bb-4eaa-9000-4e253a04970e/image.png)

  1. 서버에서 인증 대행 서버에게 웹 사이트 인증 요청

  2. 인증 대행 서버에서 권한을 부여 받음</br>
     2-1. 권한 부여 방식 4가지

     1. 인증 코드(Authorization Code) 사용하기 -> 구글에서 사용
     2. 암묵적인(Implicit) 방법
     3. 리소스 소유자의 암호 자격증명(Resource Owner Password Credentials)
     4. 클라이언트 자격증명(Client Credentials)

  3. 클라이언트가 구글 로그인 요청
  4. 내 서버에서 인증 대행 서버로 구글 인증 서버에 인증 요청을 보냄(리다이렉션 url 및 클라이언트 id 정보 제공)
  5. 인증 대행 서버에서 내 서버로 code 매개 변수 발급
  6. 내 서버에서 인증 대행 서버로 액세스 토큰 요청
  7. 인증 대행 서버에서 내 서버로 액세스 토큰 발급 및 제공
  8. 내 서버에서 인증 대행 서버로 유저 정보를 담은 액세스 토큰 제공
  9. 인증 대행 서버에서 내 서버로 유저 정보 변환
  10. 액세스 토큰을 통해 유저 정보가 잘 반환되었다면 내 서버에서 클라이언트에게 로그인 성공 및 헤더에 액세스 토큰 담아서 보내줌

### 2️⃣ 액세스 토큰 발급 및 검증 로직 구현하기

- TokenProvider

```java
@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidationTime;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access_token_validation}") long tokenValidationTime) {
        this.secret = secret;
        this.tokenValidationTime = tokenValidationTime;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Authentication authentication) {
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidationTime);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}

```

ParseClaimsJws 메서드를 호출하면 기본적인 포맷을 검증하고, jwt를 생성할 때 사용했던 secretKey로 서명했을 때 토큰에 포함된 signature와 동일한 signature가 생성되는지 확인 -> 동일하다면 클레임 셋을 반환함

JwtAccessDeniedHandler

```java
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
```

jwt 권한 인증에 실패한 경우 에러를 리턴 (사용자가 인증은 되었으나, 권한이 없는 경우)

</br></br>
JwtAuthenticationEntryPoint

```java
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
```

jwt 자격 없이 접근할 때, UNAUTHORIZED 에러 리턴(토큰이 유효하지 않은 경우)
</br></br>

JwtAuthenticationFilter

```java

@Slf4j
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private TokenProvider tokenProvider;

    public JwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = parsingJwt(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        if (StringUtils.hasText(jwt) && tokenProvider.validateAccessToken(jwt)) {
            Authentication auth = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            logger.debug("Invalid Token, uri: {}", requestURI);
        }
        filterChain.doFilter(request, response);
    }

    private String parsingJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

```

각 http 요청에 대해 실행되는 필터 클래스
jwt를 추출하고 검증하는 과정
OncePerRequestFilter : 각 HTTP 요청마다 정확히 한 번씩 실행되도록 보장함
ex) 서블릿이 실행되는 동안 다른 서블릿에 요청이 올 수도 있음
-> 어느 필터에서 헤더를 확인 한 후 특정 url로 포워딩시키는 경우
이경우 OncePerRequestFilter를 사용하지 않으면 앞서 거친 필터를 한 번 더 거치게 되고, 자원을 낭비하게 됨

</br></br>

JwtSecurityConfig

```java
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtAuthenticationFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}

```

</br></br>

SecurityConfig

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/api/auth").permitAll()
                                .requestMatchers("api/signUp").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                ).apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }

}
```

@EnableMethodSecurity를 붙여줘야 권한 검사가 가능해짐(이전에는 모두 유효한 토큰이기만 하면 되는 에러가 있었음)
</br>
/api/auth를 통해 로그인을 하고, api/signUp를 통해 회원가입을 함 -> 허용 해주어야함</br>
JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

</br></br>
User.java

```java
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
```

User와 Authority 간의 manytomany 관계 설정
user_authority에서 관리

</br></br>

### 3️⃣ 로그인 API 구현하고 테스트하기

UserController

```java

    @PostMapping("api/signUp")
    public String createUser(@RequestBody final CreateUserDto createUserDto) throws Exception {
        userService.createUser(createUserDto);
        return "success";
    }

```

</br></br>

UserService

```java
    @Transactional
    public User createUser(final CreateUserDto createUserDto){
        if (userRepository.findByPhonenum(createUserDto.getPhonenum()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        if (userRepository.findByNickName(createUserDto.getNickName()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        Authority userAuthority = new Authority("ROLE_USER");
        User user = User.builder()
                .nickName(createUserDto.getNickName())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .phonenum(createUserDto.getPhonenum())
                .temperature(36.5)
                .responseRate(0)
                .retradingRate(0)
                .isActivated(true)
                .authorities(Collections.singleton(userAuthority))
                .build();
        user.passwordEncode(passwordEncoder);
        return userRepository.save(user);
    }

```

유저 생성과 동시에 ROLE_USER라는 권한 부여

</br></br>

### 4️⃣ 토큰이 필요한 API 1개 이상 구현하고 테스트하기

UserController

- api/user

```java
    @GetMapping("api/user")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UserResponseDto> findAllUsers() {
        return userService.findAllUsers();
    }


```

모든 유저를 찾는 api를 ADMIN만 가능하게 수정

</br></br>

- api/user/{id}

```java
    @GetMapping("api/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) {
        UserResponseDto userDto = userService.findUserById(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

```

ADMIN과 USER의 ROLE을 가진 사람들은 특정 유저의 id로 조회 가능
</br></br>

### 작동 예시

user

![](https://velog.velcdn.com/images/aeyongdodam/post/da528e5a-5dd2-4d32-9a71-9105d4af7da6/image.png)
</br></br>
user_authority

![](https://velog.velcdn.com/images/aeyongdodam/post/683ebc7c-26e5-44e2-abea-46c6037abc7c/image.png)
</br></br>
일반 유저 어세스 토큰 생성

![](https://velog.velcdn.com/images/aeyongdodam/post/a837a6bc-d24c-48e8-ae71-bc875018ddb8/image.png)
</br></br>
토큰 없을 때(401 에러 리턴)
![](https://velog.velcdn.com/images/aeyongdodam/post/4c88b998-7e67-4aee-a18e-08c068ad0e9c/image.png)
</br></br>
유저 권한에게 허용된 다른 사람 id로 검색 기능 가능
![](https://velog.velcdn.com/images/aeyongdodam/post/f6e33e00-a821-4dfb-8441-8894f6a0633d/image.png)
</br></br>
어드민 권한에게만 허용된 모든 유저 검색 불가(401 리턴)
![](https://velog.velcdn.com/images/aeyongdodam/post/0d4894b0-dc76-4491-ad7e-a069518c2833/image.png)
</br></br>

# 느낀점

jwt 인증에 대해 직접 구현해보는 것이 두 번째인데, 스프링을 통한 개발은 처음이라, 많이 헷갈렸다. 구현하면서 각 부분에 대해 빼고 넣으면서 어떤 부분이 어떤 역할을 하는지 파악하는 방식으로 과제를 구현하려고 노력했다.
