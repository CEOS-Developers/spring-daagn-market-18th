# spring-dangn-market-18th
CEOS 18th Backend Study - Carrot Market

## 🥕당근마켓 DB 모델링
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

