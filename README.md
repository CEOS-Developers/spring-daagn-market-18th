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
## 🥕API 동작 확인 (선택 모델 member)
### 1️⃣ createMember
![createMember1](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/aa000643-8d05-4351-a20e-7cdbf40c5fcf)
![createMember2](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/8ce5f4b3-08f8-48a9-87d9-3751db23656e)

### 2️⃣ getAllMembers
![getAllMembers](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/5c387a53-22d2-4910-903a-f744d3db4043)

### 3️⃣ getMember
![getMember](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/92868a0e-fb9a-40ac-870f-a585d9973263)

### 4️⃣ deleteMember
![deleteMember](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/b67a6e79-0435-4d30-aa97-65f25a583122)

삭제 후 조회되지 않음 확인
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/4966cc2d-1634-4903-9d09-de3f67c23ef3)

## 🥕구현 내용 및 추가 설명
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

-----
# 📁CEOS 18th Backend Study - 4주차 미션
### 1️⃣ JWT 인증(Authentication) 방법에 대해서 알아보기

#### 1. Access Token
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/00bdc6b5-06df-4cfe-b7c8-c0d181ff5540)

1. 사용자의 로그인 
2. DB에 저장되어있는 사용자인지 확인
3. access token 발급하여 포함된 응답 전송
4. 이후, 사용자는 요청 시 access token을 포함
5. access token 검증 후 요청한 데이터 반환

- 토큰을 검증하는 과정만 거치면 되기 때문에 따로 저장해둘 필요가 없다.
- 한 번 발급되면 유효기간까지는 계속 사용 가능하기 때문에 토큰 값이 유출될 경우 대처 방법이 없다. (=> 유효기간을 무한정 길게 설정해놓을 수 없다.)

#### 2. Access Token + Refresh Token
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/8048a93e-0ffa-41a4-99ea-c7671fba9514)

1. 사용자의 로그인 
2. DB에 저장되어있는 사용자인지 확인
3. access token, refresh token 발급하여 포함된 응답 전송
4. 이후, 사용자는 요청 시 access token을 포함
5. access token 검증 후 요청한 데이터 반환
6. access token 만료된 경우, 만료되었다는 응답 전송
7. 사용자는 access token, refresh token 포함하여 access token 재발급 요청
8. refresh token 검증 후 새로운 access token 발급하여 반환

- access token의 유효기간을 짧게 설정할 수 있다.
- 구현이 복잡하다.

### 2️⃣ 액세스 토큰 발급 및 검증 로직 구현하기
#### 1. TokenProvider
```java
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private final CustomUserDetailsService customUserDetailsService;

    private final String secret;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;
    private Key key;

    public TokenProvider(
            CustomUserDetailsService customUserDetailsService,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration-time}") Long accessExpirationTime,
            @Value("${jwt.refresh-expiration-time}") Long refreshExpirationTime){
        this.customUserDetailsService = customUserDetailsService;
        this.secret = secret;
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims()
                .setSubject(authentication.getName());
        claims.put("authorities", authorities);

        Date expirationTime = new Date((new Date()).getTime() + this.accessExpirationTime);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + accessToken;
    }

    public String createRefreshToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims()
                .setSubject(authentication.getName());
        claims.put("authorities", authorities);

        Date expirationTime = new Date((new Date()).getTime() + this.refreshExpirationTime);

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + refreshToken;
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 입니다.");
        }

        return false;
    }
}
```
#### 📌getAuthentication
UserDetails를 이용하여 Authentication 리턴하는데, customUserDetailsService의 loadUserByUsername을 사용하면 DB에 접근하여 사용자 정보를 가져오게 된다.

이렇게 하지 않고 토큰에서 추출한 정보만으로 UserDetails 객체를 생성하여 Authenntication을 반환할 수도 있지만, 그럴 경우 추후 사용할 @AuthenticationPrincipal에서 값이 제대로 주입되지 않는다.

#### 2. CustomUserDetails
```java
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
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
#### 📌UserDatails란?
Spring Security에서 사용자의 정보를 담는 인터페이스이다.

Member의 email을 로그인 아이디로 가정하여 getUsername에서 email을 반환하도록 설정하였고, 인증 정보에서 Member 정보를 꺼내 사용하는 것을 용이하게 하기 위해 getMember 메서드를 추가하였다.

#### 3. CustomUserDetailsService
```java
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmailAndActivated(username, true)
                .orElseThrow(() -> new MemberNotFoundException(username));

        return new CustomUserDetails(member);
    }
}
```
#### 📌UserDetalsService란?
Spring Security에서 사용자의 정보를 가져오는 인터페이스이다.

loadByUsername은 사용자 정보를 불러와 UserDetails로 반환한다.

#### 4. JwtAuthenticationFilter
```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request){

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.replace(TOKEN_PREFIX, "");
        }

        return null;
    }
}
```
#### 📌GenericFilterBean vs OncePerRequestFilter
한 클라이언트에는 하나의 서블릿으로만 서비스되는 것이 이상적이지만, 다른 서블릿 객체가 생성되는 경우가 있다. 

Spring security에서 인증과 접근 제어는 RequestDispatcher 클래스에 의해 다른 서블릿으로 dispatch 되는데, 이동할 서블릿에 도착하기 전에 다시 한번 filter chain을 거치게 된다. 이때, 또 다른 서블릿이 GenericFilterBean로 구현된 filter를 또 타면서 필터가 두 번 실행되는 현상이 발생할 수 있다.

OncePerRequestFilter는 모든 서블릿에 일관된 요청을 처리하기 위해 만들어진 필터이다. 따라서 사용자의 요청 당 딱 한 번만 실행되는 필터를 만들 수 있다.

#### 5. WebSecutiryConfig
```java
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        requests -> requests.requestMatchers("/api/member/signup", "/api/member/login").permitAll()
                                .requestMatchers("/api/member/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
```
#### 📌csrf(CsrfConfigurer::disable), httpBasic(HttpBasicConfigurer::disable), sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
Cross-Site Request Forgery(CSRF) 공격은 사용자의 권한을 사용하여 악의적인 요청을 실행하는 공격이다. 즉, 공격자는 사용자가 의도하지 않은 요청을 수행하게 한다. 일반적으로 쿠키와 세션을 이용해서 인증을 하는 경우 발생하는 일이므로 서버에 인증 정보를 보관하지 않고 jwt를 통해 인증할 때는 csrf를 disable 처리해둘 수 있다.

또한 httpBasic은 Http basic Auth 기반으로 로그인 인증창이 뜨게 하는 설정이다. 우리는 따로 로그인 기능을 구현해주고 있기 때문에 필요없는 설정이다.

세션의 경우, JWT를 사용한 사용자 인증 방식을 채택하였기 때문에 더이상 사용하지 않도록 stateless로 설정한다.

#### 📌권한 설정
회원가입과 로그인의 경우, 권한 없이 접근 가능해야하므로 permitAll로 설정한다. 이전에 member 관련 API로 전체 조회를 구현하였는데, 관리자에게만 제공되어야하는 기능이라고 생각되어 ADMIN이라는 권한을 가지고 있는 경우에만 접근 가능하도록 설정하였다. 이외의 주소들은 로그인 이후에 접근 가능하도록 특정 권한인 것과 관계없이 인증이 필요하도록 설정하였다.

#### 6. Member에서 추가된 부분
```java
@ElementCollection(fetch = FetchType.EAGER)
private List<String> roles;


@Builder
public Member(String password, String nickname, String phone, String email, String imageUrl) {
    this.password = password;
    this.nickname = nickname;
    this.phone = phone;
    this.temperature = 36.5;
    this.email = email;
    this.imageUrl = imageUrl;
    this.roles = new ArrayList<>() {{
        add("USER");
    }};
    this.activated = true;
}

public void encodePassword(String password) {
    this.password = password;
}
```
#### 📌ElementCollection
@OneToMany처럼 엔티티를 Collection으로 사용하는 것이 아니라 Integer, String, 임베디드 타입 같은 값 타입을 컬렉션으로 사용하는 것을 값 타입 컬렉션이라고 한다. 관계형 데이터베이스는 컬렉션을 담을 수 있는 구조가 없기 때문에 별도의 테이블을 만들어서 저장해야 하는데, 이때 사용하는 것이 ElementCollection이다. 

값 타입 컬렉션은 개념적으로 보면 1대 N 관계이기 때문에, 값 타입을 소유한 엔티티의 기본 키를 PK 겸 FK로 사용하는 테이블을 생성하여 One-To-Many 관계로 다룬다.

#### 7. MemberServiceImpl에서 수정 및 추가된 부분
```java
@Transactional
public void createMember(SignupMemberRequest signupMemberRequest) {

    Member member = signupMemberRequest.toEntity();
    member.encodePassword(passwordEncoder.encode(member.getPassword()));

    memberRepository.save(member);
}

public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) {

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginMemberRequest.getEmail(), loginMemberRequest.getPassword());

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
    String token = tokenProvider.createAccessToken(authentication);

    return LoginMemberResponse.fromEntity(member, token);
}
```
- 회원가입(createMemeber) 시 비밀번호 암호화 과정 추가
- new UsernamePasswordAuthenticationToken을 통해 email, password를 기반으로 Authentication 객체 생성(인증 여부 나타내는 authenticated 값은 false)
- authenticationManagerBuilder.getObject().authenticate 통해 검증, CustomUserDetailsService의 loadUserByUsername 메서드 실행

### 3️⃣ 로그인 API 구현하고 테스트하기
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/175a634a-3ba0-4bbc-9dc7-3a6438551098)

- 로그인 후, 사용자 닉네임과 access token 포함된 body 반환
#### 📌access token을 어디에 담을까?
클라이언트에서 편리하게 json 타입의 token을 추출하여 사용할 수 있도록 body에 넣어 반환하는 것을 권장한다.

반대로 클라이언트의 요청 시에는 header에 담게 되는데, GET, DELETE 같은 HTTP 메서드는 보통 body가 필요하지 않고 access token이 요청에 필요한 실질적인 데이터가 아닌 인증을 위해 사용하는 부가적인 정보이기 때문이다.


### 4️⃣ 토큰이 필요한 API 1개 이상 구현하고 테스트하기
```java
@GetMapping
public ResponseEntity<MemberResponse> getMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

    MemberResponse memberResponse = memberService.getMember(customUserDetails.getMember().getId());

    return ResponseEntity.ok(memberResponse);
}

@DeleteMapping
public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

    memberService.deleteMember(customUserDetails.getMember().getId());

    return ResponseEntity.ok().build();
}
```
- AuthenticationPrincipal을 통해 인증된 사용자의 정보를 받아올 수 있다.
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/616ec6b4-90c8-4f93-a3d8-225462fe5ccf)

-----
# 📁CEOS 18th Backend Study - 5주차 미션
### 1️⃣ 로컬에서 도커 실행해보기
#### 📌 도커 이미지 빌드
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/c815271f-7485-45eb-a087-d64d2750071e)
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/b70673e8-b400-4491-a1a5-2d4e027ead93)

#### 📌 빌드한 이미지 실행
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/4b0b540b-c770-40e1-9a14-791f5c8dfe0f)

#### ✔ 이미지 실행 관련 오류 해결
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/bf69cb83-e9f9-4774-951f-7e050ae7eb1d)

➡ db connection 관련 오류 발생

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/3556a4ac-3f8b-46c7-90d0-bb04fa74704f)

- 도커 실행 시, localhost는 도커 컨테이너 자기 자신을 가리키게 된다.
- localhost 대신 host.docker.internal 사용하면 해결 가능

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/5c47d4ed-bf03-490f-b841-c50fb74b73c8)

#### 📌 docker-compose.yml 실행
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/009311cd-a43a-449e-aea9-36a13caed222)
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/e6ec23e0-6646-4a44-8f10-72319ffe675f)

#### ✔ docker-compose 실행 관련 오류 해결
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/ba4ae615-6047-4059-b612-d812dcb49c8d)

➡ db 관련 오류 발생

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/9473d4b2-7ec5-4417-8952-e268f435c605)

- 이미 로컬에서 3306 포트를 사용하고 있기 때문에 3306:3306으로 포트를 설정하면 제대로 동작하지 않는다.
- 포트번호 변경하면 해결 가능

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/eab1b032-a7f4-4725-a709-5250e172b452)

### 2️⃣ API 추가 구현 및 리팩토링

#### 1. 원하는 도메인/기능을 골라 API를 추가해주세요

생성/수정/삭제 등 자유롭게 원하는 API를 구현해주시면 됩니다🤓🤓

#### 📌 Post API 추가
```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest createPostRequest) {

        postService.createPost(createPostRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {

        postService.deletePost(postId);

        return ResponseEntity.ok().build();
    }
}
```

#### 2. 지금까지 과제를 하면서 아쉬웠던 부분이나 더 고치고 싶은 부분을 리팩토링 해주세요

#### 📌 권한 관리 위한 enum Role 추가 및 관련 코드 수정

```java
@Getter
public enum Role {

    ROLE_ADMIN,
    ROLE_USER
}
```

#### 📌 CustomUserDetails의 isEnabled 메서드 반환값 알맞게 수정
``` java
@Override
public boolean isEnabled() {
    return member.getActivated();
}
```

#### 📌 예외 처리 방식 변경

❓ 이전 방식 (Member)

- Member 객체를 찾지 못했을 때를 위한 MemberNotFoundException 생성
```java
public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("회원 정보가 존재하지 않습니다.");
    }
    public MemberNotFoundException(Long id) {
        super("요청한 id 값 " + id + "에 해당하는 회원 정보가 존재하지 않습니다.");
    }

    public MemberNotFoundException(String email) {
        super("요청한 email " + email + "에 해당하는 회원 정보가 존재하지 않습니다.");
    }
}
```

- Member 관련 예외를 처리하는 MemberExceptionController 생성하여 MemberNotFoundException 처리
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

➡ Member 관련 커스텀 예외를 새로 생성할 때마다 처리 위한 catch~ 메서드 생성해야 한다.

❓ 새로운 방식

- Member 관련 예외 정보 가지는 MemberErrorCode enum 생성
```java
@Getter
public enum MemberErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 회원을 찾을 수 없습니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;

    MemberErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
```

- Member 관련 예외 만들어주는 MemberExcpetion 생성
```java
@Getter
public class MemberException extends RuntimeException {

    private final HttpStatus errorCode;

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
    }

    public MemberException(MemberErrorCode errorCode, Long id) {
        super(errorCode.getErrorMessage() + " 요청받은 id : " + id);
        this.errorCode = errorCode.getErrorCode();
    }

    public MemberException(MemberErrorCode errorCode, String email) {
        super(errorCode.getErrorMessage() + " 요청받은 email : " + email);
        this.errorCode = errorCode.getErrorCode();
    }
}
```

- 모든 예외 관리하는 ExceptionController 생성하여 MemberExcpetion 처리
- log에 stackTrace 정보 찍히도록 두번째 인자로 MemberException 객체 포함
```java
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<String> catchMemberException(MemberException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
    }
}
```
-----
# 📁CEOS 18th Backend Study - 6주차 미션
### 1️⃣ 도커 이미지 배포하기

- 방식은 자유롭게 진행해주시면 됩니다!
    - ECR, API Gateway, App runner, Elastic Beanstalk, …
 
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/a49c8819-169c-4015-abbf-6c336fe4f8a7)
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/434991c0-081d-4a40-a283-e65d52b6a0e0)
지난주 미션 머지되기 이전에 푸시해 지난주 미션 pr에 포함된 커밋들이 있습니다...!

#### 📌 트러블슈팅 및 삽질기...
1. 셰어마인드 배포에서 이미 elastic beanstalk을 사용한 바가 있어 이번 미션에서도 '(Optional) AWS Elastic Beanstalk 배포' 문서 참고하여 진행해보기로 결정
2. 에러 발생 1

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/1ac2e8b6-a86a-4f48-9de3-949bf1a33db1)

github actions script에서 uses 앞에 '-'을 붙임...

```
name: deploy

on:
  push:
    branches:
      - 'letskuku'

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: checkout
      - uses: actions/checkout@v3 # 이 부분

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'


      - name: Grant execute permission for gradlew
        run: chmod +x ./gradlew
        shell: bash


      - name: Build with Gradle
        run: |
          ./gradlew -version
          ./gradlew clean build -x test
        shell: bash

      - name: Login to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_HUB_USERNAME }}
          password: ${{ secrets.DOCKER_HUB_PASSWORD }}


      - name : Build Docker Image & Push to Docker Hub
        run: |
          docker build . -t ${{ secrets.DOCKER_HUB_USERNAME }}/spring-daagn-market-18th
          docker build ./proxy -t ${{ secrets.DOCKER_HUB_USERNAME }}/nginx
          docker push ${{ secrets.DOCKER_HUB_USERNAME }}/spring-daagn-market-18th
          docker push ${{ secrets.DOCKER_HUB_USERNAME }}/nginx
```

3. gradle 빌드하는 과정에서 에러 발생

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/04a3f2af-83cd-4b22-9371-2b42da1f5ae1)

gradle-wrapper.jar 파일이 깃허브에 올릴 필요없는 파일이라고 판단하여 커밋되어있지 않았음... 문제 원인 확인 후 추가

4. 도커 이미지 빌드하고 도커 허브에 푸시하는 과정에서 에러 발생

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/3fd28fee-935f-4cbb-8592-07fb4aa376da)

찾아보니 도커파일 관련 네이밍 이슈로 발생하는 경우가 많았음... nginx 관련 도커파일 이름을 Dockerfile-nginx에서 Dockerfile로 수정

-> 드디어 성공!

5. elastic beanstalk 생성 시작
- github actions에서 docker-compose로 만들어진 이미지가 업로드되는 것이기 때문에 일단 샘플 코드로 생성, 인스턴스 배포 성공했다는 메시지 출력됨

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/443ba4a9-93e0-4989-b797-c47def3b84f3)

- 이후 대략 이런 메시지 출력되면서 상태가 Ok로 바뀌어야 했음...

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/d5edb83e-778b-42c9-aadf-c9f23b40d9a7)
![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/06199b53-bca2-4db8-a290-7fba34cd19d0)

- 그런데 아무리 기다려도 상태가 grey에서 바뀌지 않음...? why.....
- 위의 이미지처럼 도메인 부분 클릭하면 샘플 코드 넣었을 때 나오는 화면도 정상적으로 나오는 것 확인. 그러나 상태 바뀌지 않는 문제 발생
- 그 결과, github actions script에 elastic beanstalk에 배포하는 스크립트 포함하면 health check에서 에러 발생하기 시작

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/5787bd33-6418-4e02-b243-172e4ac488e2)

-> 아무리 검색+생각해봐도 원인을 찾을 수 없어 ec2 배포로 변경하기로 결정, 'deploy' 노션 페이지 참고하여 파일 수정

6. .github/workflows/deploy.yml에서 syntax error 발생

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/a72278a9-b2b0-4110-8c38-ff7042c1dbf3)

-> 주석 지우고 script 모두 붙여써서 해결

```
script: |
            cd /home/ubuntu/
            sudo touch .env
            echo "${{ secrets.ENV_VARS }}" | sudo tee .env > /dev/null
            sudo touch docker-compose.yml
            echo "${{ vars.DOCKER_COMPOSE }}" | sudo tee docker-compose.yml > /dev/null
            sudo chmod 666 /var/run/docker.sock
            sudo docker rm -f $(docker ps -qa)
            sudo docker pull ${{ secrets.DOCKER_HUB_USERNAME }}/spring-daagn-market-18th
            sudo docker pull ${{ secrets.DOCKER_HUB_USERNAME }}/nginx
            docker-compose -f docker-compose.yml --env-file ./.env up -d
            docker image prune -f
```

7. 이후 ec2에서 실행되는 도커 이미지 없는 것을 확인, 수동으로 docker-compose up 명령어 입력해주니 에러 발생하는 것 확인

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/d5c43da9-bd2b-40c8-a02d-777378d348e3)

-> ec2에서 docker-compose.yml 파일 열어서 version: 3 부분 version: "3"으로 수정해서 해결

-> 매번 ec2에서 파일 수정해주는 것은 말이 안되니 다른 해결책이 있을 것으로 생각됨...

8. 일단 docker-compose.yml 파일에 문제 없는지 확인 위해 docker-compose up 수동 입력 후 에러 발생

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/884d93c1-9fc7-4686-b2fb-f7f6494c3c6c)

-> .env가 제대로 들어가지 않은 것으로 추정

```
## .env 파일을 생성합니다.
    sudo touch .env
    echo "${{ secrets.ENV_VARS }}" | sudo tee .env > /dev/null
          
## docker-compose.yaml 파일을 생성합니다.
    sudo touch docker-compose.yaml
    echo "${{ vars.DOCKER_COMPOSE }}" | sudo tee docker-compose.yaml > /dev/null
```

대략 이 부분쯤에서 문제가 발생했을 것으로 추측됨...

#### Q. vars.DOCKER_COMPOSE에 값을 잘못 넣어준 것 같은데 값으로 뭐가 들어가야 하는건가요?

뭔가 처음부터 잘못된 것 같은 이 느낌... 천천히 처음부터 다시 해보도록 하겠습니다...

-----
#### 📌 2차 도전

아직 elastic beanstalk에 대한 미련을 버리지 못해서 '(Optional) AWS Elastic Beanstalk 배포' 문서를 아예 처음(Sample code 배포)부터 따라해보기로 결정

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/859960a2-704c-41f4-81b8-e8fa5b8a0537)

- actions 정상적으로 동작하는 것 확인

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/2897a31e-c662-4c92-a0cd-60e07dc96f52)

- Local 에서 docker-compose 실행 확인

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/20017934-4173-496e-b882-f1ec85807654)

- Terminal 에 관련 로그 추가 확인

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/a07de9e5-9c42-4528-a395-3ebf7f805d27)

- elastic beanstalk 생성 완료

![image](https://github.com/letskuku/spring-daagn-market-18th/assets/90572599/f1c3e528-3cc4-41b8-9dcc-a750ad554d3a)

- elastic beanstalk에 배포 성공


### 2️⃣ 배포환경에 대한 테스트 스크린샷 올리기

- Postman / 브라우저를 통해 요청/응답을 테스트합니다.
    - HTTP → HTTPS 리디렉션이 제대로 이루어지는지 확인
    - 구현한 API 하나 이상 제대로 응답하는지 확인
