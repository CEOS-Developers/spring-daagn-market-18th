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
