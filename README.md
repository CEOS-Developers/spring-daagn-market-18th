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
