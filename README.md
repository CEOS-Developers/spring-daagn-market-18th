# CEOS 백엔드 스터디 - 2주차

## 1️⃣ 당근 마켓의 DB를 모델링해요
### 당근마켓 ERD
![당근마켓](https://github.com/yj-leez/spring-daagn-market-18th/assets/77960090/72b3d055-056f-4d29-83a1-cde78869e1e2)

- User
    - 유저 테이블
- ActivityArea
    - 사용자가 활동하는 지역, 최대 두개까지 존재하므로 User와 다대일 매핑
- Area
    - 지역 엔티티
- Post
    - 판매자가 올린 상품 테이블. Category와 다대일 매핑
    - 상품을 판매하는 지역을 식별하기 위해 Area와 다대일 매핑
    - 상태 속성 : `SELLING`, `RESERVED`, `SOLDOUT`
- Category
- Image
- Purchase
    - 구매 확정 시 Post의 Status를 바꾸는 것만으로는 나중에 내가 구매한 상품들을 조회할 수 없다고 생각하여 Purchase 테이블 생성
    - Post 조회시 Purchase가 즉시로딩되지 않도록 Post 테이블에 외래키
- Chatroom
    - 상품에 해당하는 채팅방 개수를 식별하기 위해 Post와 다대일 매핑
- Chat
    - is_from_seller: 구매자가 보냈는지, 판매자가 보냈는지 확인할 수 있는 컬럼
- WishItem
- Review
    - 어떤 방식으로 후기를 남기는지 확실치 않아 평점과 후기를 남기도록 일단 erd 작성
    - Post의 seller_id와 Purchase의 buyer_id로 구매자와 판매자 확인 가능하므로 User는 작성자와만 연결
<br/>
<br/>

### 당근마켓 기능
- 물건 올리기
    - 카테고리, 판매자, 지역, 제목, 설명, 판매/ 나눔 선택, 상태, 가격 등을 넘겨받아 상품 엔티티 생성
- 물건 찾기
    - 유저의 활동 지역의 지역 외래키 & 상품 이름을 검색 조건으로 검색
- 1:1 채팅
    - 첫 채팅 시 ⇒ 구매자가 상품에 대해 채팅방 엔티티, 채팅 엔티티 생성
    - 그 이후 ⇒ 유저, 채팅방, 메세지 등을 넘겨받아 채팅 메세지 엔티티 생성
- 구매 확정
    - 구매 확정 시 채팅방에 연결된 상품의 상태를 `SOLDOUT` 변경, 구매 엔티티 생성
- 리뷰(온도)
    - 리뷰 엔티티 생성 시 상대의 평점에 반영
    - 앱 내에서 리뷰는 상품을 조회 후 확인 가능
<br/>
<br/>

### @OneToOne FetchType.LAZY 적용이 안 되는 이슈(N+1 문제)

- JPA 구현체인 Hibernate에서는 @OneToOne 양방향 매핑 시 지연 로딩으로 설정하여도 즉시 로딩으로 동작하는 이슈 존재
- mappedBy 속성으로 연결된 외래 키를 가지지 않은 쪽에서 테이블을 조회할 경우 외래 키를 가지고 있는 테이블(연관 관계의 주인)에 대해서는 지연 로딩이 동작하지 않고 N+1 쿼리가 발생
- 주 테이블에 외래키
    - 주 테이블에 외래 키를 두고 대상 테이블을 찾는 방식으로 객체지향 개발자가 선호
    - 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인이 가능
    - 단점: 값이 없으면 외래 키에 NULL을 허용해야함
- 대상 테이블에 외래키
    - 대상 테이블에 외래 키가 존재하는 방식으로 전통적인 데이터베이스 개발자가 선호
    - 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조를 유지 가능
    - 단점: JPA가 제공하는 기본 프록시 기능의 한계로 **지연 로딩으로 설정해도 항상 즉시 로딩**
<br/>
<br/>
  
## 2️⃣ Repository 단위 테스트를 진행해요

### @DataJpaTest

- 기본적으로 JPA 관련 설정 로드
- 인메모리 테스트 데이터베이스를 이용해 실제 데이터베이스를 이용하지 않고 사용하는 것이 가능
- @Transactional을 가지고 있기에 테스트가 진행된 후 자동으로 롤백

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(DataJpaTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
public @interface DataJpaTest {
```
<br/>
<br/>

### @AutoConfigureTestDatabase

- 테스트 데이터베이스의 구성정보를 자동으로 설정
- @AutoConfigureTestDatabase의 기본 내장 DB 커넥션이 H2, HSQLDB와 같이 인메모리 DB로 되어있음
- 실제 데이터베이스를 이용하고 싶다면 `Replace.NONE` 을 사용

### 쿼리문
```sql
-- given
Hibernate: 
    insert 
    into
        user
        (created,email,modified,name,nickname,password,phone_number,profile_img,rating,role) 
    values
        (?,?,?,?,?,?,?,?,?,?)
Hibernate: 
    insert 
    into
        user
        (created,email,modified,name,nickname,password,phone_number,profile_img,rating,role) 
    values
        (?,?,?,?,?,?,?,?,?,?)
Hibernate: 
    insert 
    into
        area
        (name) 
    values
        (?)
Hibernate: 
    insert 
    into
        area
        (name) 
    values
        (?)
-- when
Hibernate: 
    insert 
    into
        activity_area
        (area_id,user_id) 
    values
        (?,?)
Hibernate: 
    insert 
    into
        activity_area
        (area_id,user_id) 
    values
        (?,?)
Hibernate: 
    insert 
    into
        activity_area
        (area_id,user_id) 
    values
        (?,?)
-- then
Hibernate: 
    select
        count(*) 
    from
        activity_area a1_0
