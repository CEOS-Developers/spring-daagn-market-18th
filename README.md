# 📁CEOS 18th Backend Study - 2주차 미션
### 1️⃣ 당근 마켓의 DB를 모델링해요

이번 주차의 메인 과제인 모델링입니다!

차후 과제에서 **연속해서 사용할 예정** 이니 신중하게 구조를 만드는 것을 권장드립니다!

- 과제의 주제는 [당근마켓](https://www.daangn.com/) **데이터 모델링** 해보기!
    - 물건 올리기
    - 물건 찾기
    - 1:1 채팅
    - 구매 확정
    - 리뷰(온도)

  회원 기능은 필수로 추가해주셔야 합니다☺️ → 추후에 로그인 관련해서 사용됩니다!

- **README.md**에 당근마켓 **서비스에 대한 설명**과 이로부터 모델링 결과가 어떻게 나왔는지 **ERD와 함께 모델에 대한 설명**을 써주세요
    - `Domain`, `Repository` 계층 작성
- <**조건> User Entity는 꼭 구현해주세요!**

### 2️⃣ Repository 단위 테스트를 진행해요

모델링 제작을 완료하였다면 해당 모델이 제대로 되었는지 확인하기 위해서 `Repository` 계층의 단위 테스트를 작성해봅시다!

- **ForeignKey 필드를 포함하는 Entity**을 하나 선택하여 다음과 같은 테스트를 진행해주세요
    - given when then 에 따라서 테스트를 작성하기
    - 테스트에서 객체를 3개 이상 넣은 이후에 해당 객체가 출력되는지 확인하기
    - 테스트를 수행할 때 발생하는 JPA 쿼리를 조회해보기

-----
## 1️⃣당근마켓 DB 모델링
### 🥕당근마켓 서비스에 대한 설명
당근마켓은 한국의 대표적인 중고거래 어플리케이션 중 하나이다. 제공하는 주요 기능은 다음과 같다.
- 물품 판매
- 물품 나눔
- 관심 목록 등록
- 거래 후기 작성
- 거래 후기를 반영하여 회원의 거래 태도(매너온도) 수치화

다른 중고거래 어플리케이션과 차별화를 보이는 부분인 매너온도는 운영정책 준수와 다른 사용자와의 거래 태도를 수치화한 것으로, 36.5도에서 시작해서 최대 99도까지 올라갈 수 있다.
칭찬 매너 평가와 긍정 거래 후기를 받으면 매너온도가 올라가고, 비매너 평가와 부정 거래 후기를 받으면 매너온도가 내려간다.

### 🥕모델링 결과 설명
![당근마켓 DB 모델링 결과 ERD](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/08b0983e-4c26-4a18-85f3-0e9585b98d21)

#### 📌member
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/e46145f7-6907-4df7-8bdf-991ec342635a)

- 회원이 기본적으로 가지고 있어야 할 아이디, 비밀번호, 닉네임, 전화번호 등의 정보를 관리하는 테이블이다.
- 이메일과 프로필 이미지의 경우 선택사항이므로 nullable로 처리했으며, 매너온도는 기본 36.5에서 시작한다.

#### 📌town
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/e0690a68-44c8-4a66-8f5f-89cd8d9c7500)

- 지역 이름, 위도, 경도 등 지역 정보를 관리하는 테이블이다.

#### 📌member town
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/3e7a9da7-29df-4c77-8534-6afdbf2141b0)

- 회원의 지역 정보를 관리하는 테이블이다.
- 기존의 서비스는 회원 한 명당 최대 2개의 지역 정보를 가질 수 있지만, 편의상 1개만 가질 수 있도록 설정하였다.
- FK : member, town

#### 📌category
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/047ad903-6602-4979-a819-bb280dc542f6)

- 물품 판매/나눔 게시글 검색 시 비슷한 물품끼리 모아볼 수 있도록 하는 카테고리 정보를 관리하는 테이블이다.

#### 📌post
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/b4faabd4-555d-4de0-86c0-aa1585d388d2)

- 물품의 판매/나눔 게시글에 대한 정보를 관리하는 테이블이다.
- 판매자, 거래 희망 지역, 카테고리 정보를 제공하기 위해 FK로 설정되어있다.
- 판매와 나눔을 구분하기 위해 Boolean 타입의 sharing 키를 사용한다.
- 물품의 판매중/예약중/판매완료 상태를 구분하기 위해 Enum으로 따로 관리하는 status 키를 사용한다.
- FK : member, town, category

#### 📌post image
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/6c7f71ab-7e9d-4862-93f0-10846386bea3)

- 게시글과 함께 업로드될 이미지 정보를 관리하는 테이블이다.
- 한 게시물 당 최대 10개의 사진을 업로드할 수 있기 때문에 일대다 관계로 관리한다.
- FK : post

#### 📌chat room
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/f919ce91-e0c7-440b-883b-1fcfaf867c67)

- 판매자와 구매자 간의 1:1 채팅에 대한 정보를 관리하는 테이블이다.
- 어떤 게시글에 대한 채팅방인지 알 수 있어야하므로 post가 FK로 설정되어있다.
- FK : member, post

#### 📌chat content
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/06a14bde-8e9e-467a-9059-704bcdb853ba)

- 채팅방에서 이루어지는 채팅의 내용을 관리하는 테이블이다.
- 채팅의 참여한 사람 중 누가 작성한 채팅인지에 대한 정보를 알 수 있어야 할 것 같아 member를 FK로 설정하였다. (추후 수정 가능)
- FK : chat room, member

#### 📌wishlist
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/87e2b8de-b0df-4341-a628-8e8ad96c128a)

- 회원이 관심 목록에 추가한 게시글에 대한 정보를 관리하는 테이블이다.
- FK : member, post

#### 📌review
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/c745688a-636a-44af-ad2b-650d0132cb18)

- 거래 후기에 대한 정보를 관리하는 테이블이다.
- 기존의 서비스는 판매자도 후기를 작성할 수 있었으나, 편의상 구매자만 작성하는 것으로 설정하였다.
- 거래 평가 항목인 preference 키를 Enum으로 관리한다.
- FK : member, post

## 2️⃣ Repository 단위 테스트
### ForeignKey 필드를 포함하는 Entity을 하나 선택 : member town
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/3e7a9da7-29df-4c77-8534-6afdbf2141b0)

- given when then 에 따라 테스트 작성
```java
        //given
        Member memberA = Member.builder()
                .password("1234")
                .nickname("AAA")
                .phone("010-1234-5678")
                .temperature(36.5)
                .email("AAA@naver.com")
                .imageUrl("A.jpg")
                .activated(true)
                .build();

        Member memberB = Member.builder()
                .password("5678")
                .nickname("BBB")
                .phone("010-2345-6789")
                .temperature(36.5)
                .email("BBB@naver.com")
                .imageUrl("B.jpg")
                .activated(true)
                .build();

        Member memberC = Member.builder()
                .password("0000")
                .nickname("CCC")
                .phone("010-9876-5432")
                .temperature(36.5)
                .email("CCC@naver.com")
                .imageUrl("C.jpg")
                .activated(true)
                .build();

        Town townA = Town.builder()
                .name("경기도 부천시 상동")
                .latitude(35.6)
                .longitude(87.4)
                .build();

        Town townB = Town.builder()
                .name("서울특별시 은평구")
                .latitude(37.4)
                .longitude(12.15)
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);

        townRepository.save(townA);
        townRepository.save(townB);

        //when
        MemberTown memberTownA = MemberTown.builder()
                .member(memberA)
                .town(townA)
                .build();

        MemberTown memberTownB = MemberTown.builder()
                .member(memberB)
                .town(townB)
                .build();

        MemberTown memberTownC = MemberTown.builder()
                .member(memberC)
                .town(townB)
                .build();

        memberTownRepository.save(memberTownA);
        memberTownRepository.save(memberTownB);
        memberTownRepository.save(memberTownC);

        //then
        assertThat(memberTownRepository.findAll().size()).isEqualTo(3);

        MemberTown savedMemberTownA = memberTownRepository.findByMemberAndTown(memberA, townA).get();
        assertThat(savedMemberTownA.getMember().getNickname()).isEqualTo("AAA");
        assertThat(savedMemberTownA.getTown().getName()).isEqualTo("경기도 부천시 상동");

        MemberTown savedMemberTownB = memberTownRepository.findByMemberAndTown(memberB, townB).get();
        assertThat(savedMemberTownB.getMember().getNickname()).isEqualTo("BBB");
        assertThat(savedMemberTownB.getTown().getName()).isEqualTo("서울특별시 은평구");

        MemberTown savedMemberTownC = memberTownRepository.findByMemberAndTown(memberC, townB).get();
        assertThat(savedMemberTownC.getMember().getNickname()).isEqualTo("CCC");
        assertThat(savedMemberTownC.getTown().getName()).isEqualTo("서울특별시 은평구");
```

- 테스트에서 객체를 3개 이상 넣은 이후에 해당 객체가 출력되는지 확인
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/92f5614d-3e85-4b5e-ada2-cd824a26c306)


- 테스트를 수행할 때 발생하는 JPA 쿼리 조회
```
Hibernate: 
    insert 
    into
        member
        (activated,created_at,email,image_url,nickname,password,phone,temperature,update_at) 
    values
        (?,?,?,?,?,?,?,?,?)
Hibernate: 
    insert 
    into
        member
        (activated,created_at,email,image_url,nickname,password,phone,temperature,update_at) 
    values
        (?,?,?,?,?,?,?,?,?)
Hibernate: 
    insert 
    into
        member
        (activated,created_at,email,image_url,nickname,password,phone,temperature,update_at) 
    values
        (?,?,?,?,?,?,?,?,?)
Hibernate: 
    insert 
    into
        town
        (created_at,latitude,longitude,name,update_at) 
    values
        (?,?,?,?,?)
Hibernate: 
    insert 
    into
        town
        (created_at,latitude,longitude,name,update_at) 
    values
        (?,?,?,?,?)
Hibernate: 
    insert 
    into
        member_town
        (created_at,member_id,town_id,update_at) 
    values
        (?,?,?,?)
Hibernate: 
    insert 
    into
        member_town
        (created_at,member_id,town_id,update_at) 
    values
        (?,?,?,?)
Hibernate: 
    insert 
    into
        member_town
        (created_at,member_id,town_id,update_at) 
    values
        (?,?,?,?)
Hibernate: 
    select
        m1_0.member_town_id,
        m1_0.created_at,
        m1_0.member_id,
        m1_0.town_id,
        m1_0.update_at 
    from
        member_town m1_0
Hibernate: 
    select
        m1_0.member_town_id,
        m1_0.created_at,
        m1_0.member_id,
        m1_0.town_id,
        m1_0.update_at 
    from
        member_town m1_0 
    where
        m1_0.member_id=? 
        and m1_0.town_id=?
Hibernate: 
    select
        m1_0.member_town_id,
        m1_0.created_at,
        m1_0.member_id,
        m1_0.town_id,
        m1_0.update_at 
    from
        member_town m1_0 
    where
        m1_0.member_id=? 
        and m1_0.town_id=?
Hibernate: 
    select
        m1_0.member_town_id,
        m1_0.created_at,
        m1_0.member_id,
        m1_0.town_id,
        m1_0.update_at 
    from
        member_town m1_0 
    where
        m1_0.member_id=? 
        and m1_0.town_id=?
```
-----
# 📁CEOS 18th Backend Study - 3주차 미션
### 1️⃣ 새로운 데이터를 create하도록 요청하는 API 만들기

생성 시 필요한 필드 값을 담아 보내면 해당 값을 저장하고 있는 데이터를 생성하면 됩니다!

- **URL** : `api/items/`
- **Method** : `POST`
- **Body** : `{"필드명": 필드값, ... }`

저번 모델링 과제 결과 중 **모델 한 개**를 선택해주세요 (최대한 서비스에서 중심이 되는 모델이면 좋습니다!)

### 2️⃣ 모든 데이터를 가져오는 API 만들기

- **URL** : `api/items/`
- **Method** : `GET`

### 3️⃣ 특정 데이터를 가져오는 API 만들기

- **URL** : `api/items/<int:pk>/`
- **Method**: `GET`

### 4️⃣ 특정 데이터를 삭제 또는 업데이트하는 API 만들기

- **URL** : `api/items/<int:pk>/`
- **Method** : `DELETE`

-----
## 🥕구현 내용 및 추가 설명 (선택 모델 member)
### 📌domain
1️⃣ 회원 엔티티가 가지고 있어야 할 정보

2️⃣ member builder

3️⃣ 계정 활성화 여부를 나타내는 acivated 값을 false로 변경하는 메서드
```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Double temperature;

    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Boolean activated;

    @Builder
    public Member(String password, String nickname, String phone, String email, String imageUrl) {
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.temperature = 36.5;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = true;
    }

    public void updateActivatedFalse() {
        this.activated = false;
    }
}
```
#### ❓member 도메인에서 NoArgsConstructor(access = AccessLevel.PROTECTED)을 사용하는 이유?
기본 생성자의 접근 제어자를 public으로 해둘 경우, member 도메인이 가지고 있어야 할 정보가 제대로 들어있지 않은 채 무분별하게 객체가 생성될 수 있다. AccessLevel.PROTECTED를 설정해놓게 되면 무분별한 객체 생성에 대해 한 번 더 체크할 수 있는 수단이 된다.

#### ❓Builder를 사용하는 이유?
- 필요한 데이터만 가지고 객체를 생성할 수 있다.
- 유연성을 확보할 수 있다. (ex. 새롭게 추가되는 변수가 있어도 기존의 코드에 영향을 주지 않는다.)
- 직관적으로 데이터를 파악할 수 있으므로 가독성을 높일 수 있다.
- 불변성을 확보할 수 있다.

### 📌application
1️⃣ member 엔티티 관련 비즈니스 로직에 대한 service 인터페이스

2️⃣ service 인터페이스를 구현한 serviceImpl
```java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(MemberRequest memberRequest) {

        Member member = memberRequest.toEntity();

        memberRepository.save(member);
    }

    public List<MemberResponse> getAllMembers() {

        List<Member> memberList = memberRepository.findAllByActivated(true);
        if (memberList.isEmpty()) {
            throw new MemberNotFoundException();
        }

        List<MemberResponse> memberResponseList = memberList.stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toList());

        return memberResponseList;
    }

    public MemberResponse getMember(Long id) {

        Member member = memberRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new MemberNotFoundException(id));

        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public void deleteMember(Long id) {

        Member member = memberRepository.findByIdAndActivated(id, true)
                .orElseThrow(() -> new MemberNotFoundException(id));

        member.updateActivatedFalse();
    }
}
```
#### ❓Transactional(readOnly = true)를 사용하는 이유?
조회용 메서드에 readOnly = true를 적용하게 되면 가독성 뿐만 아니라 영속성 컨텍스트와 관련한 성능상의 이점도 존재한다.

영속성 컨텍스트는 entity 조회 시 초기 상태에 대한 snapshot을 저장했다가 트랜잭션의 commit이 이루어질 때, snapshot과 현재 entity의 상태를 비교한다. 이때, 변경된 내용이 있다면 개발자가 update 메서드를 호출하지 않아도 update query를 생성하여 쓰기 지연 저장소에 두었다가, 일괄적으로 flush하여 DB에 반영한다. 이것을 변경 감지(Dirty Checking)이라고 한다.

그러나 만약 readOnly = true를 설정하게 되면 JPA는 해당 트랜잭션 내에서 조회하는 entity는 조회용임을 인식하고, 변경 감지를 위한 snapshot을 따로 보관하지 않아 메모리가 절약된다.

또한, readOnly = true로 설정되면 트랜잭션 내에서 수동으로 flush를 호출하지 않으면 flush는 자동으로 수행되지 않는다. 따라서 조회용 entity의 예상치 못한 수정을 방지할 수 있다.

이외에도 트래픽 분산을 위해 Master-Slave 구조로 복제본 DB를 함께 운용하면 조회 작업은 Slave DB에서 수행하고 수정 작업은 Master DB에서 수행하도록 하는데, readOnly = true면 Slave DB에서 데이터를 가져오도록 동작하기 때문에 트래픽 분산의 목적을 온전히 달성할 수 있다.

#### ❓deleteMember에서 soft delete 사용
데이터베이스에서 데이터를 삭제하는 방법에는 물리삭제(hard delete)와 논리삭제(soft delete)가 있다.
- 물리 삭제 : SQL의 DELETE 명령어를 사용하여 데이터를 완전히 삭제하는 방법
- 논리 삭제 : SQL의 UPDATE 명령어를 사용하여 삭제 여부를 알 수 있는 컬럼에 값을 넣어 삭제를 의미하는 방법

member는 엔티티 중 가장 중요하다고 볼 수 있는 정보이기 때문에 안전한 데이터 관리를 위해 soft delete를 사용하였다.

### 📌exception
1️⃣ 조회하고자 하는 회원 정보가 respository에 존재하지 않을 경우 발생하는 MemberNotFoundException
```java
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("회원 정보가 존재하지 않습니다.");
    }

    public MemberNotFoundException(Long id) {
        super("요청한 id 값 " + id + "에 해당하는 회원 정보가 존재하지 않습니다.");
    }
}
```
#### ❓id 값을 인자로 받는 MemberNotFoundException 생성자가 존재하는 이유?
추후 exceptionController에서 관련 로그를 찍게 되는데, 어떤 잘못된 id 값으로 엔티티 조회가 요청된 것인지 정확한 로그 내용의 출력을 위해 id 값을 인자로 받는다.

### 📌presentation
1️⃣ 클라이언트로부터 member 엔티티 생성/조회/삭제 요청을 받아 처리하는 controller
```java
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {

        memberService.createMember(memberRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {

        List<MemberResponse> memberResponseList = memberService.getAllMembers();

        return ResponseEntity.ok(memberResponseList);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) {

        MemberResponse memberResponse = memberService.getMember(memberId);

        return ResponseEntity.ok(memberResponse);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.ok().build();
    }
}
```
#### ❓createMember에서 status code 201 Created 반환
201 Created는 요청이 성공적으로 처리되어서 리소스가 만들어졌음을 의미하는 상태 코드이다. 200 OK보다 member 엔티티 생성이라는 요청에 사용하기에 적합하다고 생각하였다.

2️⃣ member 관련 Custom Exception을 처리하는 exception controller
```java
@Slf4j
@RestControllerAdvice
public class MemberExceptionController {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> catchMemberNotFoundException(MemberNotFoundException e) {

        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
```
#### ❓log.error
로그 레벨에는 trace, debug, info, warn, error가 있다. member 엔티티가 제대로 조회되지 않으면 더 이상의 작업이 진행될 수 없기 때문에 error 레벨로 판단하였다.

### 📌dto
1️⃣ 회원 정보 생성 요청 시 controller에서 service로 요청한 회원 정보 넘겨주는 request dto

2️⃣ 회원 정보 조회 요청 시 service에서 controller로 요청한 회원 정보 넘겨주는 response dto
```java
@Getter
public class MemberResponse {

    private final Long id;

    private final String password;

    private final String nickname;

    private final String phone;

    private final Double temperature;

    private final String email;

    private final String imageUrl;

    private final Boolean activated;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    @Builder
    public MemberResponse(Long id, String password, String nickname, String phone, Double temperature, String email,
                          String imageUrl, Boolean activated, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.temperature = temperature;
        this.email = email;
        this.imageUrl = imageUrl;
        this.activated = activated;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .temperature(member.getTemperature())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .activated(member.getActivated())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdateAt())
                .build();
    }
}
```
#### ❓MemberResponse dto에서 정적 팩토리 메서드 사용
기존에는 entity -> dto, dto -> entity로 변환해주는 mapper 클래스를 따로 만들어 관리하였으나, 3주차 세션 내용을 참고하여 정적 팩토리 메서드를 사용하였다.

### 📌repository
1️⃣ member 엔티티를 저장하는 repository
```java
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndActivated(Long id, Boolean activated);

    List<Member> findAllByActivated(Boolean activated);
}
```
#### ❓activated를 사용한 엔티티 조회
soft delete를 사용하고 있기 때문에 활성화된 회원 정보만을 조회하도록 하기 위해 findBy 로직에 activated 매개변수를 추가하여 조회하도록 한다.

## 🥕새롭게 알게 된 점 & 느낀 점
코드를 작성할 때 당연하다고 여기며 작성하던 부분들이 많았는데, 미션 내용 정리를 위해 그런 부분들을 찾아보는 기회가 되었던 것 같다. 특히 Transactional(readOnly = true)를 설정하면 막연하게 성능상 이점이 있다고만 알고 있었는데, 정확히 어떤 부분에서의 이점인지 공부해보게 되었다. 또한, 정적 팩토리 메서드는 알고만 있던 개념인데 직접 적용해볼 수 있어 좋았다. 지난주에 단위 테스트 코드를 작성해보았는데, 이번 미션 코드에 대해서도 테스트 코드를 작성해보면 좋을 것 같다.
