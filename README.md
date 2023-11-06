# spring-daagn-market-18th

CEOS 18th Backend Study - Carrot Market

# 2주차 - DB 모델링과 JPA

당근마켓 DB 모델링을 할 때 아래와 같은 기능을 구현할 수 있도록 참고했다.<br>

- 물건 올리기
- 물건 찾기
- 1:1 채팅
- 구매 확정
- 리뷰(온도)
- 회원 기능

한가지씩 기능에서 어떤 부분을 중점으로 집중해서 설계를 했는지 정리해보겠습니다.<br>
기본적으로 모든 테이블의 공통 컬럼인 생성일(`FRST_REG_DT`)과 수정일(`LAST_CHG_DT`)은 `BaseTimeEntity`로 엔티티를 하나 빼서 자동 등록할 수 있게 설계하였습니다. 참고 부탁드립니다.

### 물건 올리기

물건 올리기 기능은 `물건 팔기` 페이지를 확인하며 필요한 정보들을 테이블에 담았다.<br>
해당 테이블은 `PROD_LIST`이며, 스마트폰을 기준으로 최상단에 이미지가 최대 10개까지 등록할 수 있도록 구현되어 있는데, `PROD_IMG_LIST` 테이블로 분리해서 이미지들을 저장할 수 있게 설계했다.<br>
제목(`PROD_TIT`), 제목을 설정하면 나타나는 키워드(`PROD_KEYW`), 키워드에 따라 선택적으로 등장하는 브랜드(`PROD_BRN`)와 사이즈(`PROD_SIZ`), 거래 방식으로 판매하기와 나눔하기 등 2가지 방법으로 나눌 수 있어(`TRD_CD`)를 Enum 타입으로 설계를 하였고, 각 거래 방식에 따라 박스 하단 가격 제안 받기 혹은 나눔 이벤트 열기 옵션을 선택할 수 있어(`TRD_OPT_CD`), 가격을 설정할 수 있는(`PROD_PRC`), 자세한 설명을 적는 란(`PROD_CONT`), 거래 희망 장소와 보여줄 동네 선택을 마지막에 확인해서 시간이 부족해서 아직 추가하지 못 했습니다.. 제출하고 바로 수정할게요 ㅜㅜ

### 물건 찾기

물건 찾기 기능은 물건 올리기를 설계하며 `PROD_LIST` 테이블에 해당 물건이 현재 팔린 물건인지, 예약되어 있는 물건인지 등을 확인할 수 있는 `TRD_STAT` 컬럼을 추가하며 마무리했습니다.

### 1:1 채팅

채팅 기능은 `CHAT_LIST` 테이블에 정의되어 있습니다.<br>
채팅을 보낸 사람(`SEND_NO`)와 받는 사람(`RECV_NO`)는 유저 테이블 `USR_LIST`의 PK를 FK로 받아와서 설계했고, 채팅창을 고정하는 핀 기능(`CHAT_PIN_YN`), 채팅의 알람 설정 기능(`CHAT_ALM_YN`), 상대 차단 기능(`CHAT_BLK`) 등으로 채팅을 관리하는 역할을 하는 테이블을 구성했습니다.<br>
추가로, 채팅을 주고 받았을 때 메세지를 관리하는 테이블(`CHAT_MSG_LIST`)을 설계했으며, 이는 보낸 사람(`SEND_NO`), 보낸 채팅방(`CHAT_NO`), 메세지 내용(`MSG_CONT`), 보낸 시간 등으로 구성되어 있습니다.

### 구매 확정

구매 확정 기능은 사실 당근 마켓 기준으로, 직접 만나서 거래하는 형식으로 진행되기 때문에 `Order` 테이블을 빼진 않고, `PROD_LIST`의 현재 판매 진행 상태(`TRD_STAT`)를 기준으로 구매가 진행되었는지 확인할 수 있도록 설계했습니다.<br>
추가로, 리뷰를 작성하면 구매자를 알 수 있기 때문에 `REVW_LIST`의 `USR_NO`로 구매자를 확인할 수 있으나, 현재 당근마켓 또한 구매자가 리뷰를 작성하지 않으면 누가 구매했는지 알 수 없는 상태인 것으로 보입니다. 제가 당근마켓을 통해 구매, 판매를 해본 경험이 없어서 더 많은 기능이 있는 건 확인하지 못했습니다..

### 리뷰(온도)

리뷰 기능은 `REVW_LIST` 테이블을 확인하면 알 수 있습니다.<br>
리뷰는 정말 간단하게 진행되는데, 거래가 어땠는지 상태를 총 3가지("별로에요", "좋아요!", "최고에요!")로 표현할 수 있어 이를 Enum 타입으로 `REVW_STAT`로 설계했습니다. 어떤 점이 좋았는지 체크박스 형태로 한 줄? 정도 작성할 수 있기에 `REVW_LINE` 컬럼을 정의했으며, 선택 사항으로 거래했던 경험(`REVW_CONT`)을 작성할 수 있으며 이는 선택사항이기 때문에 NULL도 가능합니다. 거래하는 이미지도 추가할 수 있어, (`REVW_IMG_URL`)을 테이블에 추가했습니다. 이미지 테이블로 옮기지 않은 이유는 1장밖에 첨부할 수 없는데, Join 하는 비용보다 테이블 내에 배치하는 것이 더 비용적으로 아낄 수 있지 않을까.. 라는 개인적인 생각입니다.

### 회원 기능

정말 마지막 회원 기능은 처음 회원가입을 진행해보면서 알 수 있었습니다. 이는 `USR_LIST` 테이블을 확인하면 됩니다.<br>
제일 처음 핸드폰 번호로 로그인을 할 수 있으며(`PH_NM`), 닉네임을 정해서(`NIC_NM`) 회원가입을 할 수 있습니다. 닉네임 같은 경우 12자 이상으로 작성할 수 없기 때문에 `varchar(12)`로 크기를 고정했습니다. 자신이 현재 위치한 곳(`ADR`)과 매너 온도(`MNN_TEMP`)는 36.5도가 기본값이기에 숫자를 점까지 총 4개의 문자를 넣을 수 있도록 크기를 4로 고정했습니다. 주소를 인증했는지(`ADR_CERT_YN`)와, 처음 회원가입을 한 기기는 어떤 것인지(`UA`) -> 이는 나중에 자기는 회원가입을 했던 기억이 없다. 막 이런 컴플레인이 걸렸을 때 해결할 수 있도록 기기값까지 받는다고 하던데 사실 잘 모르겠습니다. 그리고, 개인정보를 동의했는지(`PRI_YN`) 이는 동의를 해야 들어올 수 있기 때문에 default 로 'Y' 값을 주었습니다. 마케팅 수신 동의(`MKTG_YN`)를 마지막으로 모든 테이블의 컬럼을 설명드렸습니다.

### 최종 ERD

<img width="710" alt="스크린샷 2023-09-30 오후 10 03 27" src="https://github.com/Remedi2022/backend/assets/89639470/e3c1a2f3-8996-4cf1-b673-98b84be465bb">

### 놓친 부분

유저가 자신이 원하는 지역을 여러 군데 설정할 수 있으나, 이는 아직 생각을 못 했습니다.

### 설계하며 고려했던 사항들

기본적으로 개인 취향에 따라 디비를 설계하는 방법은 다르겠지만, 개인적으로 테이블, 컬럼 등 대문자와 약어를 사용하는 것을 좋아합니다.. 풀네임으로 컬럼의 이름을 작성하면 너무 오브젝트의 명이 너무 길어 코딩하기에 어려워진다는?? 생각과 이전에는 dbms 자체에서 30바이트로 이름을 설계할 수 있는 것을 제한했었는데,, 물론 요즘에는 100바이트 넘게 가능하다고 하나, 여러모로 길면 불편하다는 판단하에 대부분의 사람들이 30을 기준으로 설계한다고 들었습니다. 이번에는 char와 varchar의 차이점에 대해서 구글링을 통해 알아낸 결과, 고정 크기는 char라는 정보를 알고 이게 또 연산이 더 빠르다는 장점이 있다고 해서 Enum 값으로 설계할 수 있는 부분은 모두 char로 작성해봤습니다. Id 값은 필드를 구분하기 위한 것이면 Varchar, 서로 계산하기 위한 목적이면 INT를 사용한다고 합니다. 물론 체계를 어떻게 나누는지에 따라 다르겠지만, 이번 당근마켓을 기준으로 봤을 때 유저의 Id 값은 #GH249124 등 순서대로 증가하는 것이 아닌, 문자가 섞여 있는 것으로 확인하여 Varchar 작성했습니다. 하나의 테이블에 많은 컬럼을 다 넣은 경향이 없지는 않지만, 조인하는 것이 비용적으로 많이 든다는 생각을 계속 가져가면서 실제로 사용하면서 이 부분은 나눠야겠다라고 판단할 때 나누는 것이 더 좋다고 했던 조언이 기억에 남아 한 테이블에 넣었습니다. 테이블을 쪼개는 것은 성능보다도 사실 데이터 무결성과 정합성 차원에서 먼저 검토를 해야 한다고 하지만, 이 부분에 대해서 이해를 완벽하게 하지 못해 일단 시간이 부족하여 돌아갈 수 있도록 설계했습니다. 여기까지가 이번 모델링을 하면서 고려했던 부분들 입니다.

---

# 3주차 - CRUD API 만들기

이번 주차에는 하나의 모델을 설정하여 REST API를 설계하고, 구현하는 것이 과제였다.<br>
과제에 사용한 모델은 `Product`였고, 이는 당근마켓 서비스에서 중심이 되는 모델이라고 판단했기 때문이다.<br>
4가지의 API를 만드는 것을 목표로 했다. API 목록은 아래와 같다.<br>

- `POST` 새로운 데이터
- `GET` 모든 데이터
- `GET` 특정 데이터
- `DELETE` 특정 데이터

설계하기 위한 개념과 방법은 미션을 안내해주는 페이지에 정리가 잘 되어 있기 때문에 구현하며 고민했던 부분과 알게 된 점을 정리해보려고 한다. <br>

우선 설계한 API를 먼저 소개하겠다.

## Product

### 새로운 데이터 생성 기능 - POST /api/product

RequestBody

```json
{
    // URL : http://localhost:8080/api/product
    // Method : POST

    "userNo" : string,
    "title": string,
    "keyword": string,
    "content": string,
    "tradingCode": string,
    "tradingOptioncode": string,
    "price": int,
    "brand": string,
    "size": string,
    "tradingStatus": string
}
```

ResponseBody

```json
{
    "productNo": int
}
```

구현하며 알았던 정보가 두가지 존재한다.<br>

1. Entity 상에서 `User`를 저장하는 user column이 존재하는데, 이를 DTO로 값을 받을 때는 User 객체를 RequestBody에 전달할 수 없다는 것.
   - 해결 방법은 단순했다. 원하는 user의 ID 값을 전달 받은 후, Service Layer에서 `findById`로 객체를 찾아서 DTO에서 Entity로 변결할 때 저장하는 방법이다. 오랜만에 해서 헷갈려서 적어봤습니다.
2. ENUM값도 RequestBody에 String으로 전달할 수 있다는 점.
   - 정확한 워딩으로 작성해야 한다는 것이 문제이지만, String 형태로 Body에 전달하면 이를 DTO 상에서는 ENUM으로 받을 수 있다는 것을 알게 되었다.

### 모든 데이터 조회 - GET api/product

ResponseBody

```
{
    [
        {
            "productNo": int,
            "userNo" : string,
            "title": string,
            "keyword": string,
            "content": string,
            "tradingCode": string,
            "tradingOptioncode": string,
            "price": int,
            "brand": string,
            "size": string,
            "tradingStatus": string
        },
        ...
    ]
}
```

Service Layer에서 `List<DTO>`를 하기 위해 Stream 방식을 활용했다.<br>
<img width="758" alt="스크린샷 2023-10-07 오후 5 55 24" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/89639470/c280eb83-3949-42df-94cc-f24a9092aeef">

### 특정 데이터 조회 - GET api/product/{id}

ResponseBody

```
{
    "productNo": int,
    "userNo" : string,
    "title": string,
    "keyword": string,
    "content": string,
    "tradingCode": string,
    "tradingOptioncode": string,
    "price": int,
    "brand": string,
    "size": string,
    "tradingStatus": string
}
```

### 특정 데이터 삭제 - DELETE api/product/{id}

단순히 실행 결과의 상태값(status)만 전달한다.<br>

## 추가 학습한 부분

1. DTO를 만들 때 정적 팩토리 메서드 명칭 컨벤션?
   - `of`는 매개변수를 2개 이상의 값을 받을 때 사용하고, `from`은 매개변수가 1개일 때 사용한다고 하는데 왜 이렇게 사용하는지 알 수는 없다. 물론 `toEntity`, `toDto`같은 명칭도 있지만 개인적으로 위와 같은 방법을 더 주로 사용한다.
2. 새로운 폴더 구조
   - 기본적으로 폴더 구조는 layer끼리 모아두는 방법, domain 별로 묶어두는 방법 크게 2가지가 보편적으로 사용되는 것 같다. 이번에는 새로운 방법을 도입해봤다. 서비스의 크기가 커지고, 멀티 모듈로 코드를 나눈다고 했을 때를 가정하고 mysql에 들어가는 Entity를 묶어두는 폴더, 다른 기능들은(Controller, Service, Repository) domain 별로 묶었고, 마지막으로 global 폴더를 만들었다.
   - Entity와 Repository를 한 군데에 묶어 domain 폴더에는 Controller, Service, Dto 정도만 남겨두는 구조는 어떨까 고민하고 있다. 조금 바보같은 생각일까..?<br>
     <img width="211" alt="스크린샷 2023-10-07 오후 6 11 18" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/89639470/fa791377-333a-4ea6-8bcc-4718c3f9f917">
3. 한가지 궁금한 점
   - 항상 고민했던 부분이지만, `ProductController`는 `ProductService`를 참고하고, Service는 Repository를 참조하도록 설계가 되어있다. 여기서 `ProductService`가 `UserRepository`를 찾고하는 방법은 뭔가 이상하지 않은가..? 유저 정보가 필요하다고 해서 `ProductRepository` 이외의 다른 `Repoistory`를 참고하는 방법이 뭔가 이래도 되나? 라는 고민이 들긴 하지만 기능 구현을 위해 일단 작성했다.
   - 여기서 궁금한 점은 한마디로 자신의 도메인이 아닌 다른 도메인의 `Repository`를 가져와서 사용해도 되는가?
   - 이것도 바보같은 고민이다..

## 생성자 대신 정적 팩터리 매서드를 고려하라

핵심을 정리하면 다음과 같다.<br>

1. 정적 팩터리 메서드와 public 생성자 각자의 상대적인 장단점이 존재한다.
2. 정적 팩터리 메서드 사용이 유리한 경우가 더 많다.

정적 팩터리의 장점은 다음과 같다.

1. 이름을 가질 수 있다.
   - public 생성자보다 객체의 특성을 제대로 설명
   - 한 클래스에 시그니처가 같은 생성자를 여러 개 생성 가능
2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
   - 불변 클래스(immutable class)는 `Instance`를 재활용 -> 불필요한 객체 생성 X
   - 인스턴스 통제 가능 -> 싱글턴으로 만들 수 있고, 인스턴스화 불가로 만들 수 있음
3. 반환 타입의 하위 타입 객체를 반환할 능력이 있다.
   - 객체 생성 시, 분기 처리를 통해 하위 타입의 객체를 반환할 수 있음<br>
   ```
   public class Grade {
   ...
   private static Grade of(int semester) {
           if(0 < semester && semester <= 2) {
               return new Freshman();
   	    }
           if(2 < semester && semester <= 4) {
               return new Sophomore();
           }
           ...
      }
   }
   ```
4. 입력 매개벼수에 따른 다른 클래스 객체를 반환할 수 있다.
5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

단점은 다음과 같다.

1. 상속하려면 public, protected 생성자가 필요함으로, 정적 팩터리 메서드만 제공할 시 하위 클래스를 만들 수 없다.
2. 정적 팩터리 메서드는 프로그래머가 찾기가 어렵다.
   - 생성자처럼 API설명에 명확히 들어나지 않기 때문에 인스턴스화하는 방법을 알아야 한다.

### 매서드 시그니처

메서드 명과 파라미터의 순서, 타입, 개수를 의미. 리턴 타입과 Exceptions은 포함되지 않음.

```java
// 서로 다른 시그니처
void doSomething(String[] x); // doSomething(String[]) - 메서드 시그니처 예 1
void doSomething(String x); // doSomething(String)

// 같은 시그니처
int doSomething(int x); // doSomething(int)
void doSomething(int y) throws Exception; // doSomething(int)
```

## Builder 패턴

- 점층적 생성자 패턴의 안정성 + 자바빈즈 패턴의 가독성
- **필요한 매개변수만으로 생성자를 호출**해 빌더 객체 생성
- 생성할 클래스 안에 **정적 멤버 클래스**로 만들어두는 게 일반적이다
  - **플루어트 API(fluent API)** - 물 흐르듯 연결된다는 의미
  - **메서드 연쇄(method chaining)**

```java
public class NutritionFacts {
	private final int servingSize;
	private final int servings;
	private final int fat;
	...

	// 정적 멤버 클래스로 생성할 클래스 안에 빌더 만들기
	public static class Builder {
		// 필수 매개변수
		private final int servingSize;
		private final int servings;

		// 선택 매개변수
		private int fat = 0;
		...

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}

		public Builder fat(int val) {
			fat = val; return this;
		}
		...

		// 마지막에 매개변수 없는 build 메서드를 호출하여 객체 생성
		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}

	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		fat = builder.fat;
		...
	}
}

// 클라이언트 코드
NutritionFacts food = new NutritionFacts.Builder(240,8)
								.fat(50).build();
```

- **빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기에 좋다**
  - 가변인 매개변수를 여러 개 사용할 수 있다는 장점
  - 하단의 `addTopping` 메서드 참고

```java
// 루트 추상 클래스
public abstract class Pizza {
	public enum Topping { HAM, ... }
	final Set<Topping> toppings;

	// 추상 클래스는 추상 빌더를 가짐. 하위 클래스에서 구체 빌더로 구현
	abstract static class Builder<T extends Builder<T>> {
		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
		public T addTopping(Topping topping) {
			toppings.add(Objects.requireNonNull(topping)); // Null 체크
	    return self();
    }

		abstract Pizza build();

		// 하위 클래스에서 메소드 재정의하여 "this"를 반환하게 해야 함.
    protected abstract T self();
	}

  Pizza(Builder<?> builder) {
		toppings = builder.toppings.clone(); // 복사본 만들기
	}
}

// 뉴욕 피자
public class NyPizza extends Pizza {
	public enum Size { SMALL, ... }
	private final Size size;

	public static class Builder extends Pizza.Builder<Builder> {
		private final Size size;

		public Builder(Size size) {
			// 잘못된 매개변수 확인하는 용도 -> invariant(불변식) 만족하는 조건
			this.size = Objects.requireNonNull(size);
		}

		@Override public NyPizza build() {
			return new NyPizza(this);
		}

		@Override protected Builder self() { return this; }
	}

	private NyPizza(Builder builder) {
		super(builder);
		size = builder.size;
	}
}

// 클라이언트 코드
NyPizza pizza = new NyPizza(SMALL)
						.addTopping(SAUSAGE).addTopping(ONION).build();
```

## 후기

이번 주차에는 간단한 CRUD API를 만들어보면서 새로운 폴더 구조를 만들어봤다. Entity를 수정, 열람하는 일이 많고 도메인에 Entity까지 저장하게 되면, 한번에 폴더를 열 때 복잡해서 간소화하기 위한 벙법? 으로 한번 테스트 해봤는데 생각보다 괜찮은 것 같다. 물론 왜 이렇게 했는지 이상하게 보일 수 있다는 점..
