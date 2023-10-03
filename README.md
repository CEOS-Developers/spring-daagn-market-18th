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
