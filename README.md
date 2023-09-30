
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
