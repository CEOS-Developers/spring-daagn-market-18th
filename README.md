<h1 align="center">  
📂 CEOS BE Spring 📂  
<br/>  
</h1>  

<div align="center">  

### 💛 Chapter 💛
[WEEK 0 | 스프링의 이해](#CEOS-WEEK-0:-스터디-안내-및-스프링의-이해) <br>  
[WEEK 1 | 스프링 튜토리얼](#CEOS-WEEK-1:스프링-튜토리얼) <br>  
[WEEK 2 | DB 모델링과 JPA](#CEOS-WEEK-2:DB-모델링과-JPA-🥕) <br>  
[WEEK 3 | CRUD API 만들기](#CEOS-WEEK-3:-CRUD-API-만들기-🎁) <br>
[WEEK 4 | Spring Security와 로그인](#CEOS-WEEK-4:-Spring-Security와-로그인) <br>
[WEEK 5 | Docker](#CEOS-WEEK-5:-Docker) <br>


</div>  

# 📂 CEOS WEEK 0: 스터디 안내 및 스프링의 이해
<br>  

### 🌱 0주차 목표

### 1️⃣ IntelliJ IDEA(Ultimate Edition)를 설치해요
### 2️⃣ Spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사해요
  
---  

### 🌱 0주차 미션

### 1️⃣ IntelliJ IDEA 설치

![image](https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/0c62a68b-991c-44d5-a2e8-5ba3d63512fc)

➡ 설치 완료

### 2️⃣ Spring이 지원하는 기술 조사

#### 1. IoC/DI

<b> (1) IoC </b>  
- 정의  
- IoC란? Inversion of Control로 **제어의 역전**을 의미합니다.  
- 특징:  
- 메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것입니다.  
- 개발자가 직접 객체를 관리하지 않고 **스프링 컨테이너에서 제어 객체를 생성하여 해당 객체에 주입** 시켜줄 수 있습니다.  
- 기존의 객체가 만들어지고 실행되는 과정과 다릅니다.  
- 🔎<기존> 1. 객체 생성 2. 의존성 객체 생성_ _클래스 내부에서 생성_ 3. 의존성 객체 메소드 호출  
- 🔎<스프링> 1. 객체 생성 2. 의존성 객체 주입_ _스스로가 만드는것이 아니라 제어권을 스프링에게 위임하여 스프링이 만들어놓은 객체 주입._ 3. 의존성 객체 메소드 호출  


🎯개발자가 직접 객체를 생성하여 코드를 제어하는 경우
```Java  
public class A {  
  
private B b;  
  
public A()  
b = new B();  
}  
}  
```  
➡ 직접 객체를 제어하여 A객체는 B객체에게 의존하고 있는 걸 클래스를 통해 표현합니다.  
<br></br>

🎯컨테이너에 의해서 생성한 객체를 사용하는 경우
```Java  
public class A {  
  
@Autowired  
private B b;  
  
}  
```  
➡ B라는 객체를 @Autowired 통해 객체를 주입 받을 수 있게 됩니다.  
<br>

#### (2) DI
- 정의
- DI란? Dependency Injection로 **의존 관계 주입** 기능입니다.
- 특징:
- A가 B에 의존한다는 의미는 B가 어떠한 이유로 변경이 발생하면 그 영향이 A에 미친다는 것입니다. 그래서 클래스간에 직접적 의존 관계를 맺는 것보다 느슨하게 인터페이스를 통해 의존관계 맺어서 결합도를 낮추는 것이 좋기에 등장했습니다.
- **객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식**입니다.

- 종류:

**(1) 생성자 주입(Constructor-based Dependency Injection)**: 생성자 앞에 `@Autowired` 어노테이션을 명시
```Java  
@Service  
public class MemberService {  
private MemberRepository memberRepository;  
  
@Autowired  
public MemberService(MemberRepository memberRepository) {  
this.memberRepository = memberRepository;  
}  
}  
```  

**(2) 세터 주입(Setter-based Dependency Injection)**: setter 함수에 `@Autowried` 어노테이션을 명시

```Java  
@Service  
public class MemberService {  
private MemberRepository memberRepository;  
  
@Autowired  
public void setMemberRepository(MemberRepository memberRepository) {  
this.memberRepository = memberRepository;  
}  
}  
```  
**(3) 필드 주입(Field-Based Dependency Injection)**: 필드 앞에 `@Autowired` 어노테이션을 명시

```Java  
@Service  
public class MemberService {  
@Autowired  
private MemberRepository memberRepository;  
}  
```  
<br></br>
#### 2. AOP
- 정의
- AOP란? Aspect Oriented Programming으로, 핵심 로직과 부가 기능을 분리하여 어플리케이션 전체에 걸쳐 사용되는 부가 기능을 모듈화하여 재사용할 수 있도록 지원하는 것입니다.
- 즉, **공통된 기능을 재사용**하는 기법입니다.
- 특징:
- 공통 관심 사항과 핵심 관심 사항(코어 코드)을 분리하여 **반복된 작업을 줄입니다**.
- 프록시 객체를 자동으로 만들어 실제 객체의 기능을 실행하기 전,후에 공통기능을 호출합니다.  
  ![image](https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/46f780c6-a838-408c-8a85-3c2005e0db75)

- 용어:

|용어 | 내용 |  
|--|--|  
|Advice | **언제** 공통기능을 핵심로직에 적용할 지 정의 |  
|JointPoint | Advice를 적용가능한 지점(**메서드**) |  
|PointCut | 실제 Advice가 **적용되는 JointPoint** |  
|Weaving | Advice를 **핵심로직 코드에 적용**하는 것 |  
|Aspect | **공통기능** |  

- 구현방법:
- Aspect로 사용할 클래스에 **@Aspect** 애노테이션을 붙입니다.
- **@Pointcut** 애노테이션으로 공통기능을 적용한 Pointcut을 정의합니다.
- 공통기능을 구현한 메서드에 원하는 시점에 따라 **Advice**를 택하여 애노테이션을 적용합니다.



#### 3. PSA
- 정의
- PSA란? Portable Service Abstraction으로, 환경의 변화와 관계없이 일관된 방식의 기술로의 접근 환경을 제공하는 추상화 구조입니다.
- 예시:  
  **(1) Spring Web MVC**
- `@Controller`, ` @GetMapping`, `@PostMapping`과 같은 어노테이션과 뒷단의 여러 가지 복잡한 인터페이스들 그리고 기술들을 기반으로 하여 사용자가 기존 코드를 수정하지 않고 간편하게 바꿀 수 있습니다.

**(2) Spring Transaction**
- `@Transactional` 어노테이션을 사용하여 트랜잭션 처리를 합니다.

**(3) Spring Cache**
- `@Cacheable`, `@CachePut`, `@CacheEvict` 등의 애노테이션으로 캐시 관련 동작을 지정합니다.  
  <br></br>

#### 4. Spring Data
- 정의
- Spring Data란? DB에 대한 특성은 유지하며, 데이터 액세스 방법에 대하여 친숙하고 익숙한 접근 방법을 제시하는 목적을 가진 Spring 기반 프로그래밍 모델입니다.
- 특징
- Repository라는 제네릭한 인터페이스를 제공하여 공통된 연산에 implementation을 동적으로 제공합니다.
- 각 데이터 저장소는 Spring Data의 Repository를 구현하여 자신의 데이터 저장소에 맞는 repository를 제공합니다.
- 사용하려는 repository를 상속하여 각 저장소에서 정의한 convention에 맞게 메서드만 선언하기만 하면 Spring Data가 Runtime시 이름에 맞는 적절한 구현 내용을 제공합니다.
- 예시
- Person 엔터티를 데이터베이스에 저장하고, findByLastName 메서드를 사용하여 데이터를 조회하고 출력합니다.

```Java  
@Entity  
public class Person {  
  
@Id  
@GeneratedValue(strategy = GenerationType.IDENTITY)  
private Long id;  
private String firstName;  
private String lastName;  
}  
```  

```Java  
public interface PersonRepository extends JpaRepository<Person, Long> {  
List<Person> findByLastName(String lastName);  
}  
```  
---  
#### 🙋‍♀️느낀 점
이번 주차 스터디를 통해서 Spring의 로직과 개념에 대한 이해를 향상시킬 수 있었습니다.  
이전에 Spring Boot로 프로젝트를 개발한 경험이 있지만 Spring Framework의 핵심적인 동작 원리와 개념에 대한 이해가 부족하다고 느끼고 있었습니다.

IoC/DI로 객체의 생성과 의존성 관리를 스프링 컨테이너에게 위임하여 유연한 코드를 작성할 수 있다는 것을 알게 됐습니다. 또한, AOP를 통해서 핵심 로직과 부가 기능을 분리하여 가독성이 높고 재사용성을 높일 수 있다는 점도 알 수 있었습니다. PSA로는 추상회된 구조를 통해 일관된 방식으로 다양한 기술에 접근할 수 있다는 것도 알게 되었습니다. 평소 사용했던 Spring Data에 대한 부분도 공식 문서를 통해 다시 정리해볼 수 있었습니다.

이번 주차에서 배운 내용을 토대로 실제 프로젝트에 적용하여, 효율적이고 안정된 구조의 애플리케이션을 개발하고자 합니다!
  
---  
#### 🏄‍♀️참고자료
IoC/DI
- https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html
- https://biggwang.github.io/2019/08/31/Spring/IoC,%20DI%EB%9E%80%20%EB%AC%B4%EC%97%87%EC%9D%BC%EA%B9%8C/
- https://velog.io/@gillog/Spring-DIDependency-Injection
- https://baek.dev/post/21/
- https://mimah.tistory.com/entry/Spring-%EC%9D%98%EC%A1%B4%EC%84%B1-%EC%A3%BC%EC%9E%85-Dependency-Injection-DI-%EC%98%88%EC%A0%9C

AOP

- https://life-with-coding.tistory.com/479
- https://java-is-happy-things.tistory.com/41

PSA
- https://gwamssoju.tistory.com/96

Spring Data
- https://spring.io/projects/spring-data
- https://ckddn9496.tistory.com/99
---  
# 📂 CEOS WEEK 1: 스프링 튜토리얼
<br>  

## 🌱 1주차 목표

### 1️⃣ spring-boot-tutorial-18th 수행
### 2️⃣ H2 데이터베이스 연결
### 3️⃣ 스프링 어노테이션 심층 분석
### 4️⃣ 단위 테스트와 통합 테스트 탐구
<br>  

## 🌱1주차 미션

## 1️⃣ spring-boot-tutorial-18th 수행
**(1) Spring Initializr 로 시작**  
<img width="728" alt="스크린샷 2023-09-21 오후 3 44 47" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/74f3b184-50a9-41db-9a27-d40f0ca41a72">- 프로젝트 설정
- Project : Gradle - Groovy
- Language : Java
- Spring Boot : 3.1.2
- Project Metadata
- Group : com.ceos18
- Artifact : spring-boot
- Name : spring-boot
- Description : Demo project for Spring Boot
- Package name : com.ceos18.spring-boot
- Packaging : Jar
- Java version : 17
- Dependencies : Spring Web
- 🕵🏻 **Spring Web**이란? Web MVC를 사용하여 웹 애플리케이션을 만드는데 필요한 스프링부트의 기본적인 요소(annotation 등)를 가지고 있습니다.
- 또한, 내장형 컨테이너로 tomcat 을 탑재하고 있습니다.<br></br>

**(2) IntelliJ에서 gitignore 파일 추가 후 Run application**  
<img width="1215" alt="스크린샷 2023-09-21 오후 4 56 37" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/916f88d7-d6ae-47be-b37d-7036c341d20e">
>💡Tip: 로그의 색상을 단위별로 세팅하고 싶다면 resources - application.properties 파일에  
`spring.output.ansi.enabled=always` 코드를 추가하기!  
<br></br>

**(3) localhost:8080에서 서버 접속 확인**  
<img width="435" alt="스크린샷 2023-09-21 오후 5 06 19" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/0f16f3f2-7b5b-46f0-91d7-5a466b19f4e4">  
resources/static/index.html 생성해 테스트 해보았습니다!  
<br></br>  
**(4) 간단한 Web Application 만들기**

**1. Controller 생성 및 Application class 수정 후 Application 실행
2. Bean 확인
3. 서버 작동 확인**<br></br>  
   방법1) curl 명령어<br></br>  
   <img width="410" alt="스크린샷 2023-09-21 오후 5 30 00" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/1748029c-24e1-41a7-9bee-2510feae3f21"><br></br>  
   방법2) 웹 브라우저 <br></br>  
   <img width="283" alt="스크린샷 2023-09-21 오후 5 31 46" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/c534c597-d580-4554-a25b-31495800b13e"><br></br>  
   **(5) 단위 테스트 실행**

**1. build.gradle에 dependency 추가**
```java  
testImplementation('org.springframework.boot:spring-boot-starter-test')  
```  
**2. Controller에 대한 Test Class 추가**  
<img width="1325" alt="스크린샷 2023-09-21 오후 5 43 19" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/d88d91e1-1ecf-4ff0-91a7-30a010962b21">
### 2️⃣ H2 데이터베이스를 연결해요
**1. H2 Database Engine 를 설치해요**  
<img width="941" alt="스크린샷 2023-09-21 오후 5 57 04" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/4dccdd47-6fda-48f1-a5a5-03fd8f0e1f82">  
데이터베이스 파일을 생성한 후 TCP 소켓을 통해 접속하는 이유?

파일 직접 접근이 아닌 TCP 소켓을 통해 접속해야 어플리케이션과 콘솔이 동시에 접근했을 때 오류가 발생하지 않기 때문이다.  
<br></br>

**2. dependency 추가**  
**3. application.yml 작성**  
**4. Domain, Repository, Service, Controller 를 작성**  
=> GitHub에 업로드 완료!  
<br></br>

## 3️⃣ 스프링 어노테이션을 심층 분석해요

### 🔗 어노테이션이란 무엇이며, Java에서 어떻게 구현될까요? 🔗

**(0) Bean 이란?**
- **정의**: 애플리케이션의 주요 구성 요소 중 하나로, **객체를 생성하고 관리하는 역할**을 합니다.

**(1) Annotation이란?**
- **정의**: Java에서 코드 사이에 주석처럼 쓰이며 특별한 의미, 기능을 수행하도록 하는 기술입니다. 즉, 프로그램에게 추가적인 정보를 제공해주는 메타데이터라고 볼 수 있습니다.
- **특징**:
- 컴파일러에게 코드 작성 문법 에러를 체크하도록 정보를 제공합니다.
- 소프트웨어 개발 툴이 빌드나 배치시 코드를 자동으로 생성할 수 있도록 정보를 제공합니다.
- 실행 시 특정 기능을 실행하도록 정보를 제공합니다.
- `@Bean` vs `@Component`  
  **공통**: `Spring(IOC) Container`에 Bean을 등록하도록 하는 메타데이터를 기입하는 어노테이션이다.  
  **차이**: `@Bean`의 경우, 개발자가 직접 제어가 불가능한 외부 라이브러리등을 `Bean`으로 만들려할 때 사용된다.  
  `@Component` 의 경우, 개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 어노테이션이다. <br></br>
- **종류**

**[1] @Component**

- 개발자가 생성한 Class를 Spring의 Bean으로 등록할 때 사용하는 Annotation
- 빈으로 등록된 클래스는 다른 컴포넌트나 서비스에서 주입(DI)하여 사용할 수 있습니다.

```java  
@Component  
public class CEOSMember {  
  
private String position;  
private String name;  
  
public String getPosition() {  
return position;  
}  
  
public void setPosition(String position) {  
this.position = position;  
}  
  
public String getName() {  
return name;  
}  
  
public void setName(String name) {  
this.name = name;  
}  
}  
```  
`@Component` 어노테이션을 사용하였기에 `CEOSMember` 클래스는 Spring 빈으로 정의됩니다.<br></br>

**[2] @Controller**

- Spring MVC의 **Controller로 사용되는 클래스 선언을 단순화** 시켜주는 Annotation
- Controller 클래스는 **웹 요청을 처리하고 적절한 응답을 생성**하는 역할을 합니다.  
  <br></br>

**[3] @RestController**
- **@Controller에 @ResponseBody가 결합**된 Annotation
- Controller class에 @RestController를 붙이면, Controller class 하위 메서드에 @ResponseBody 어노테이션을 붙이지 않아도 문자열과 JSON 등을 전송할 수 있습니다.  
  <br></br>

**[4] @RequestMapping**
- **요청 URL을 어떤 method가 처리할지 mapping**해주는 Annotation
- 요청을 받는 형식인 **GET, POST, PATCH, PUT, DELETE** 를 정의합니다. (정의하지 않는다면, 자동적으로 GET으로!) <br></br>

**[5] @ResponseBody**
- **자바 객체를 json 기반의 HTTP Body로 변환**하는 Annotation

```java  
@Controller  
public class CEOSMemberController {  
  
private final CEOSMemberService ceosMemberService;  
  
public CEOSMemberController(CEOSMemberService ceosMemberService) {  
this.ceosMemberService = ceosMemberService;  
}  
  
@GetMapping("/ceos-member")  
@ResponseBody  
public String getCEOSMemberInfo() {  
CEOSMember ceosMember = ceosMemberService.getCEOSMember();  
return "Position: " + ceosMember.getPosition() + ", Name: " + ceosMember.getName();  
}  
}  
```  
- `@Controller` 가 **클라이언트의 요청을 받고 처리**하는 역할을 합니다.
- `@RequestMapping("/ceos-member")`: 이 어노테이션은 이 컨트롤러가 처리하는 모든 요청 URL의 시작 경로를 "/ceos-member"로 지정합니다. **즉, "/ceos-member"로 시작하는 모든 URL을 이 컨트롤러가 다룹니다.**
- `@GetMapping`: HTTP **GET 요청**을 처리합니다.
- `@ResponseBody`: 해당 메서드가 **반환하는 문자열이 직접 HTTP 응답의 본문으로** 전송되도록 합니다. 즉, 메서드가 반환하는 값은 HTTP 응답의 내용으로 클라이언트에게 전달됩니다.  
  <br></br>

**[6] @Service**
- **비즈니스 로직을 수행하는 Class**라는 것을 나타내는 Annotation
```java  
@Service  
public class CEOSMemberService {  
  
private final CEOSMember ceosMember;  
  
public CEOSMemberService(CEOSMember ceosMember) {  
this.ceosMember = ceosMember;  
}  
  
public String getPosition() {  
return ceosMember.getPosition();  
}  
}  
```  
`CEOSMemberController` 는 `getCEOSMemberInfo` 메서드에서 `CEOSMemberService`를 호출할 때 `position` 값을 반환하게 됩니다. <br></br>

**[7] @Repository**
- **DB연동 작업을 하는 클래스인 DAO에 특화**된 Annotation
- 해당 클래스에서 발생하는 **DB 관련 예외**를 spring의 DAOException으로 전환할 수 있는 장점이 있습니다.
```java  
@Repository  
public interface CEOSMemberRepository extends JpaRepository<CEOSMember, Long> {  
List<CEOSMember> findByPosition(String position);  
}  
```  
`position` 값을 기준으로 `CEOSMember`를 조회할 수 있습니다.  
✴️ 예를 들어, `Leader`라는 `position` 값을 가진 멤버를 조회하려면 다음과 같이 사용할 수 있습니다!
```java  
List<CEOSMember> ceosMembers = ceosMemberRepository.findByPosition("Leader");  
```  

<br></br>

### 🔗 스프링에서 어노테이션을 통해 Bean을 등록할 때, 어떤 일련의 과정이 일어나는지 탐구해보세요. 🔗

참고) Bean Life Cycle  
![](https://blog.kakaocdn.net/dn/lc33n/btrnVaFWE0Z/QJMpuILvLXXOXwkoX2YF21/img.png)

**1) 패키지와 클래스 스캔**: 패키지들을 스캔하여 패키지 내에 있는 클래스들 중 어노테이션을 확인합니다.  
**2) 어노테이션 처리**: 클래스에 사용된 어노테이션을 처리합니다.  
**3) 클래스를 Bean으로 등록**  
**4) 의존성 주입**: `@Autowired`, `@Inject`, 또는 생성자 주입을 사용하여 의존성을 주입합니다.  
**5) Bean 초기화 작업 수행**: Bean 초기화 메서드(`@PostConstruct` 어노테이션이 적용된 메서드/`InitializingBean` 인터페이스를 구현한 `afterPropertiesSet()` 메서드)를 호출합니다.  
(ex)
```java  
public interface InitializingBean {  
void afterPropertiesSet() throws Exception;  
}  
```  
`InitializingBean`을 구현하여 초기화 메서드를 설정할 수 있습니다.  
**6) Bean 사용**  
**7) Bean 소멸 전 callback** :
```java  
public interface DisposableBean {  
void destroy() throws Exception;  
}  
```  
`InitializingBean`을 구현하여 소멸 메서드를 설정할 수 있습니다.

### 🔗 `@ComponentScan` 과 같은 어노테이션을 사용하여 스프링이 컴포넌트를 어떻게 탐색하고 찾는지의 과정을 깊게 파헤쳐보세요. ###  
- `@ComponentScan` 이란? 빈으로 등록 될 준비를 마친 클래스들을 스캔하여, 빈으로 등록해주는 것입니다.
- **동작과정**  
  **ConfigurationClassParser 가 Configuration 클래스를 파싱**  
  @Configuration 어노테이션 클래스를 파싱하는 것입니다.  
  ⬇  
  **ComponentScan 설정 파싱**  
  base-package 에 설정한 패키지를 기준으로 ComponentScanAnnotationParser가 스캔하기 위한 설정을 파싱합니다.  
  ⬇  
  **모든 클래스 로딩**  
  base-package 설정을 바탕으로 모든 클래스를 로딩합니다.  
  ⬇  
  **생성할 빈에 대하여 정의**  
  ClassLoader가 로딩한 클래스들을 BeanDefinition으로 정의합니다.  
  ⬇  
  **빈 생성**  
  생성할 빈에 대한 정의를 토대로 빈을 생성합니다.

- **기본 스캔 대상**
- @Component
- @Controller
- @Service
- @Repository
- @Configuration
- **스캔 범위**
- **`@ComponentScan` 어노테이션이 있는 파일의 패키지 아래**를 찾는다.
- **`basePackages` / `basePackageClasses`로 지정도 가능**  
  **- 스캔 범위를 지정하고 싶다면?**
```java  
// 1.단일 경로  
@ComponentScan(basePackage = "경로")  
// 2.다수 경로  
@ComponentScan(basePackage = "경로1", "경로2")  
// 3.특정 파일 이용하여, 해당 위치부터 탐색 경로 설정  
@ComponentScan(basePackageClass = 파일명1.class, 파일명2.class)  
```  

- **Filter를 사용하여 스캔 대상 범위 지정**
- includeFilters : 컴포넌트 스캔 대상을 추가로 지정
- excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정
- ANNOTATION : 기본값. 어노테이션으로 인식해서 동작
- ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작
- ASPECTJ : AspectJ 패턴 사용
- REGEX : 정규표현식
- CUSTOM : TypeFilter라는 인터페이스를 구현해서 처리

- **예시**  
  `includeFilters`로 `beanA`를 컴포넌트 스캔에 포함하고,  
  `excludeFilters`를 이용하여 `beanB`를 컴포넌트 스캔에서 제외해보겠습니다.

코드는 [woply님의 블로그](https://velog.io/@woply/%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8-%EC%8A%A4%EC%BA%94-%EB%8C%80%EC%83%81%EC%9D%84-%EC%B6%94%EA%B0%80-%EB%98%90%EB%8A%94-%EC%A0%9C%EC%99%B8%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95)를 통해 테스트 해보았습니다.
```java  
public class ComponentFilterAppConfigTest {  
  
@Test  
void filterScan() {  
ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);  
BeanA beanA = ac.getBean("beanA", BeanA.class);  
Assertions.assertThat(beanA).isNotNull();  
  
assertThrows(  
NoSuchBeanDefinitionException.class,  
() -> ac.getBean("beanB", BeanB.class));  
  
}  
  
@Configuration  
@ComponentScan(  
includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),  
excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)  
)  
static class ComponentFilterAppConfig {  
}  
}  
```  
**컴포넌트 스캔 대상에 추가할 애노테이션**
```java  
@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface MyIncludeComponent {  
  
}  
```  

**컴포넌트 스캔 대상에서 제외할 애노테이션**

```java  
@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface MyExcludeComponent {  
}  
```  

**컴포넌트 스캔 대상에 추가할 클래스**

```java  
@MyIncludeComponent  
public class BeanA {  
}  
```  

**컴포넌트 스캔 대상에서 제외할 클래스**

```java  
@MyExcludeComponent  
public class BeanB {  
}  
```  

**🔎결과🔎**
1. `BeanA` 클래스는 `MyIncludeComponent` 어노테이션을 가지고 있으므로 스캔 대상이며, 빈으로 등록됩니다.
2. `BeanB` 클래스는 `MyExcludeComponent` 어노테이션을 가지고 있으므로 스캔 대상에서 제외되므로 해당 빈을 찾을 수 없으며 `NoSuchBeanDefinitionException`이 발생합니다.
```java  
Let's inspect the beans provided by Spring Boot:  
beanA  
```  
  
---  
### 4️⃣ 단위 테스트와 통합 테스트 탐구
### 🔗 단위 테스트와 통합 테스트의 의미를 알아봅시다! 🔗

**(1) 단위 테스트란?**

- 정의: 전체 애플리케이션의 **단일 부분(모듈, 컴포넌트)** 을 완전히 분리하여 집중적으로 테스트하는 것
- 특징:
- 테스트 대상 단위의 크기를 작게 설정해서 단위 테스트를 최대한 간단하고 디버깅하기 쉽게 작성해야 해야 합니다.

**(2) 통합 테스트란?**
- 정의: **두 소프트웨어 단위 또는 모듈 간의 인터페이스**를 테스트하는 것
- 특징:
- 장점은, 단위 테스트에서 발견하기 어려운 버그를 찾을 수 있다는 것입니다.
- 단점은, 단위 테스트보다 더 많은 코드를 테스트하기 때문에 신뢰성이 떨어질 수 있다는 점과 어디서 에러가 발생했는지 확인하기 쉽지 않아 유지보수하기 힘들다는 점입니다.



| 어노테이션 | 설명 | 부모 클래스 | Bean |  
|--|--|--|--|  
|@SpringBootTest |통합 테스트, 전체 | IntegrationTest |Bean 전체 |  
|@WebMvcTest |단위 테스트, Mvc 테스트 |MockApiTest | MVC 관련된 Bean |  
|@DataJpaTest |단위 테스트, Jpa 테스트 |RepositoryTest |JPA 관련 Bean |  
|None |단위 테스트, Service 테스트 |MockTest |None |  
|None |POJO, 도메인 테스트|None | |  

```java  
@SpringBootTest  
@AutoConfigureMockMvc  
public class HelloControllerTest {  
  
@Autowired  
private MockMvc mvc;  
  
@DisplayName("DisplayName : 테스트 이름을 설정할 수 있습니다")  
@Test  
public void getHello() throws Exception {  
mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))  
.andExpect(status().isOk())  
.andExpect(content().string(equalTo("Greetings from Spring Boot!")));  
}  
}  
```  
Spring Boot 에서는 클래스 상단에 `@SpringBootTest` 어노테이션을 붙여 통합 테스트를 수행합니다!
>- `@SpringBootTest`: 통합 테스트를 설정. Spring 애플리케이션 컨텍스트를 로드하여 테스트를 실행
>- `@AutoConfigureMockMvc`: 컨트롤러를 테스트할 때 `MockMvc` 객체를 자동으로 구성
>- `MockMvc`: 컨트롤러의 엔드포인트를 모의 요청하고 응답을 검증하는 데 사용
>- `@Test`: 테스트 메서드를 표시
>- `MvcResultMatchers`: 사용하여 HTTP 응답의 상태와 내용을 검증. HTTP 상태 코드가 OK(200)이고 응답 본문의 내용이 "Greetings from Spring Boot!"인지를 확인합니다!

즉, 컨트롤러 동작을 테스트하고 해당 컨트롤러의 엔드포인트가 예상대로 동작하는지를 검증합니다.

### 🔗 스터디 자료의 단위 테스트 예제는 엄밀한 의미의 단위 테스트라고 부를 수 있을까요? 아니라면 엄밀한 의미의 단위 테스트로 구현하기 위해 어떻게 바꾸어야 할지 생각해 보아요. 😁 🔗
```java  
@Service  
@RequiredArgsConstructor  
public class TestService {  
  
private final TestRepository testRepository;  
  
/* Read All*/  
@Transactional(readOnly = true)  
public List<Test> fetchAllTests() {  
return testRepository.findAll();  
}  
}  
```  
엄밀한 의미의 단위 테스트라고 부를 수 없다고 생각합니다.  
DB 읽기 작업을 수행하고 있으며 component를 독립시켜 테스트하고 있지 않기 때문입니다.

단위 테스트로 구현하기 위해서,`TestRepository`의 Mock 객체를 생성하고 이를 `TestService`에 주입하여 `fetchAllTests` 메서드를 테스트해볼 수 있습니다.

🧺🧺🧺 ~HelloControllerTest가 예제인줄 알고 썼던 내용..~🧺🧺🧺
1) **테스트 환경에서 필요한 Bean만 수동으로 설정하기**

- Spring Boot 애플리케이션 컨텍스트를 로드하지 않도록 변경합니다. `@SpringBootTest` 는 필요한 빈을  
  모두 초기화하므로 통합 테스트를 수행할 때 사용합니다.
- `@SpringBootTest` 대신에 `@RunWith(SpringRunner.class)` 와 `@WebMvcTest` 를 사용하여, 테스트 환경에서 필요한 빈만 수동으로 설정합니다.

```java  
@RunWith(SpringRunner.class)  
@WebMvcTest(HelloController.class)  
public class HelloControllerTest {  
...  
}  
```  
**2) `TestController`에서 의존하는 `TestService`를 Mock 객체로 대체**
```java  
@MockBean private TestService testService;  
```  
이후에 Test 메서드에서 Mock 객체의 동작을 정의해주는 것으로 수정합니다.
  
---  
#### 🙋‍♀️느낀 점
이번 주차 스터디를 통해서 스프링 부트를 초기 세팅하고, H2 데이터베이스를 연결해보았습니다. 스프링 어노테이션에 대해서도 공부해보고 단위 테스트와 통합 테스트에 대해서도 배워볼 수 있었습니다.

이 중 가장 많이 배울 수 있던 부분은 테스트 파트였습니다. 기존에는 통합 테스트 위주로만 작성하던 습관을 개선하고 단위 테스트에 대해서도 초점을 맞추고자 합니다. 테스트 코드를 작성하고 실행함으로써 코드의 신뢰성을 높일 수 있다는 것을 몸소 느꼈고, 앞으로의 개발 프로세스에서 이를 더욱 활용하고자 합니다! 😊

  
---  
#### ☄️Troubleshooting
1️⃣ Intellij로 Spring 코드 첫 실행 시 finished with non-zero exit value 1 오류 <br>  
🔸 원인: Intellij 내 Build and run using과 Run tests using 설정 변경이 필요 <br>  
🔸 해결방법:
1. [File > Settings] 메뉴 클릭 (맥 기준 단축키 : Command + ,)
2. [Build, Excution, Deployment > Build Tools > Gradle] 클릭
3. Build and run using과 Run tests using이 아마도 **Gradle(Default)** 로 되어있을텐데, 이것을  
   **Intellij IDEA**로 바꿔준다.

---  
#### 🏄‍♀️참고자료
Spring-boot-tutorial-18th 수행
- https://daegwonkim.tistory.com/230

스프링 어노테이션
- https://melonicedlatte.com/2021/07/18/182600.html
- https://sddev.tistory.com/225
- https://hongs-coding.tistory.com/115
- https://dahye-jeong.gitbook.io/til/spring/2021-05-17-bean-lifecycle
- https://jh-labs.tistory.com/108
- https://velog.io/@hyun-jii/%EC%8A%A4%ED%94%84%EB%A7%81-component-scan-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EB%8F%99%EC%9E%91-%EA%B3%BC%EC%A0%95
- https://ittrue.tistory.com/229
- https://velog.io/@woply/%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8-%EC%8A%A4%EC%BA%94-%EB%8C%80%EC%83%81%EC%9D%84-%EC%B6%94%EA%B0%80-%EB%98%90%EB%8A%94-%EC%A0%9C%EC%99%B8%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95

단위 테스트&통합 테스트
- https://tecoble.techcourse.co.kr/post/2021-05-25-unit-test-vs-integration-test-vs-acceptance-test/
- https://parkcheolu.tistory.com/410
- https://velog.io/@kimhalin/Test-%EC%BD%94%EB%93%9C-%ED%83%90%EA%B5%AC1-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%A2%85%EB%A5%98
---  
# 📂 CEOS WEEK 2: DB 모델링과 JPA 🥕
<br>  

## 🥕 2주차 목표
### 1️⃣ 당근의 DB를 모델링해요
### 2️⃣ Repository 단위 테스트를 진행해요
<br>  

## 🥕2주차 미션

### 1️⃣ 당근의 DB를 모델링해요

### **💭 DB 설계 과정 💭**
**기획서는 당근의 UI로 가정하였습니다.  
당근의 '중고거래'를 메인 서비스로, 선정하였습니다.  
기획서를 바탕으로 요구사항 및 기능을 분석하여 DB를 설계하였습니다.  
화면별 요구사항에 초점을 맞추어 진행해보았습니다!**

### **(1) USER**
- 회원 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
- 회원 가입 시 닉네임, 전화번호, 비밀번호를 입력한다.
- 회원 정보에는 이메일, 프로필 이미지, 동네, 매너온도가 있다.
- 모델링:

**📦 Table 📦**  
<br>  
<img width="334" alt="스크린샷 2023-09-26 오후 8 24 20" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/69ad2bc9-a312-4579-918f-4bd8baaa04c1">  
**🔑 Keys 🔑**  
주요 Key를 소개합니다.
- **userId**:
- PK(기본키)로, 유저를 고유하게 식별하기 위해 사용됩니다.
- 'AUTO_INCREMENT' 속성을 가지며, 새로운 사용자가 추가될 때마다 자동으로 1씩 증가합니다.
- NULL일 수 없으며 각 유저는 유일한 'userId' 값을 가져야 합니다.
- **email**:
- NULL 값을 허용하도록 설정하였으므로, 추후 등록할 수 있습니다.
- ****temperature****:
- 36.5로 기본값을 설정해놓았습니다.

**✴️ 제외한 옵션✴️**
- 국가
- 당근 약관 및 동의사항 동의여부, 최소 연령 확인 => 미선택 시 가입을 하지 못하므로 DB에는 포함하지 않았습니다.


### **(2) POST**
- 게시물 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
- 회원은 '내 물건 팔기'를 통해 판매 상품을 등록할 때, 상품 이미지, 제목, 카테고리, 거래방식, 가격, 가격 제안받기 여부, 상품 설명, 거래 희망 장소를 작성합니다.

**📦 Table 📦**  
<br>  
<img width="454" alt="스크린샷 2023-09-26 오후 10 30 02" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/e57415d4-213c-4d8e-ace6-37e9e24daa94">  
**🔑 Keys 🔑**  
주요 Key를 소개합니다.
- **postId**:
- PK(기본키)로, 게시물을 고유하게 식별하기 위해 사용됩니다.
- **userId**:
- FK(외래키)로, user 테이블의 userId 열과 관련이 있으며, 게시물과 사용자 간의 관계를 나타냅니다.
- **categoryId**:
- FK(외래키)로, Category 테이블의 categoryId 열과 관련이 있으며, 게시물의 카테고리를 나타냅니다.
- **dealMethod:**
- 거래 방식을 나타냅니다.
- '판매하기'와 '나눔하기' 중 하나의 값을 가질 수 있으므로 enum형으로 정의하였습니다.

**✴️ 제외한 옵션 ✴️**
- 게시물 종류- 알바, 과외/클래스, 농수산물, 부동산, 중고차
- 게시물 작성 옵션- 선호하는 거래 장소

**(3) POSTIMG**
- 게시물의 이미지에 대한 정보를 담고 있는 테이블입니다.
- 하나의 게시물에 1개 이상의 사진이 존재하므로 테이블을 생성하였습니다.

**📦 Table 📦**  
<br>  
<img width="275" alt="스크린샷 2023-09-26 오후 10 30 24" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/637cee9f-28cb-4f84-8235-29bf351ad238">  
**🔑 Keys 🔑**  
주요 Key를 소개합니다.
- **postImgId**:
- PK로, 게시물 이미지를 고유하게 식별하기 위해 사용됩니다.
- **postId**:
- FK로, 'post' 테이블의 'postId' 열과 관련이 있으며, 이미지와 게시물 간의 관계를 나타냅니다.


**(4) CATEGORY**
- 게시물의 카테고리 목록 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
- 회원은 카테고리를 선택해 상품을 볼 수 있습니다.

**📦 Table 📦**  
<br>  
<img width="317" alt="스크린샷 2023-09-26 오후 10 31 05" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/65010f4d-2ab7-4f7c-a4e0-defbd8a53b75">  
**🔑 Keys 🔑**
- 간단해서 생략하도록 하겠습니다!

**(5) CHATROOM**
- 구매자와 판매자의 채팅방에 대한 정보를 담고 있는 테이블입니다.
- 요구사항 분석:
- 회원 간의 채팅을 통해 거래를 할 수 있습니다.

**📦 Table 📦**  
<br>  
<img width="343" alt="스크린샷 2023-09-26 오후 10 31 33" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/40c2b96d-5a60-479e-88fc-721c5fad697d">


**🔑 Keys 🔑**
- **chatroomId**:
- PK(기본키)로, 채팅방을 고유하게 식별하기 위해 사용됩니다.
- **sellerUserId:**
- FK로, 채팅방의 판매자 유저를 나타냅니다.
- **buyerUserId:**
- FK로, 채팅방의 구매자 유저를 나타냅니다.

**(6) CHATCONTENT**
- 구매자와 판매자의 채팅방 내용에 대한 정보를 담고 있는 테이블입니다.

**📦 Table 📦**  
<br>  
<img width="350" alt="스크린샷 2023-09-26 오후 10 32 00" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/61e4f155-e79a-4080-8084-6de507a446f8">  
**🔑 Keys 🔑**
- **chatId**:
- PK(기본키)로, 채팅 내용을 고유하게 식별하기 위해 사용됩니다.
- **chatroomId:**
- FK로, 채팅 메시지가 어떤 채팅방에 속한 것인지를 식별하는 데 사용됩니다.

**(7) AREA**
- 지역 정보를 담고 있는 테이블입니다.

**📦 Table 📦**  
<br>  
<img width="346" alt="스크린샷 2023-09-26 오후 10 32 32" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/9752abb9-0298-4cc4-9b36-0c9a44773643">  
**🔑 Keys 🔑**
- **areaId**:
- PK(기본키)로, 지역을 고유하게 식별하기 위해 사용됩니다.
- **latitude**:
- decimal 형으로 위도 정보를 저장합니다.
- **longitude**:
- decimal 형으로 경도 정보를 저장합니다.


**(8) USER_AREA**
- 회원이 설정한 동네 정보를 담고 있는 테이블입니다.
- 예) 여의동, 여의도동

**📦 Table 📦**  
<br>  
<img width="302" alt="스크린샷 2023-09-26 오후 10 33 02" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/fbe853dd-ca4a-4c4a-8611-c018aceecc5f">  
**🔑 Keys 🔑**
- **areaId**:
- FK로, 지역 정보를 나타냅니다.
- **userId:**
- FK로, 사용자와 지역 간의 관계를 나타냅니다.


**(9) WISHLIST**
- 관심 목록에 추가한 게시물 정보를 담고 있는 테이블입니다.

**📦 Table 📦**  
<br>  
<img width="330" alt="스크린샷 2023-09-26 오후 10 33 25" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/8653f7a2-9a1f-4edf-b9d0-22ae15366314">  
**🔑 Keys 🔑**
- **wishlistId**:
- PK로, 관심 게시물을 고유하게 식별하기 위해 사용됩니다.
- **userId:**
- FK로, 유저 정보를 식별합니다.
- **postId:**
- FK로, 찜 목록 항목과 게시물 간의 관계를 나타냅니다.

**(10) REVIEW**
- 리뷰 게시물 테이블입니다.

**📦 Table 📦**  
<br>  
<img width="427" alt="스크린샷 2023-09-26 오후 10 33 56" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/0c2fddc7-120a-452f-8eb9-dc08be3cc65b">


**🔑 Keys 🔑**
- **reviewId**:
- PK로, 리뷰 게시물을 고유하게 식별하기 위해 사용됩니다.
- **reviewerId:**
- FK로, 리뷰를 작성한 사용자를 식별합니다.
- **revieweeId:**
- FK로, 리뷰를 받은 사용자를 식별합니다.
- **preference:**
- 리뷰의 평가를 나타내는 ENUM 열로, '별로에요', '좋아요!', '최고에요!' 중 하나의 값을 가집니다.

**✴️변경한 사항✴️**
- 기존 당근 서비스와 달리 유저에 대한 리뷰/ 상품에 대한 리뷰가 나뉘는 것이 아닌 유저에만 리뷰가 저장되도록 하였습니다.
- 리뷰 중 '어떤 점이 좋았나요?' 부분은 생략하였습니다.
---  
### 2️⃣ Repository 단위 테스트를 진행해요
모델링 제작을 완료하였다면 해당 모델이 제대로 되었는지 확인하기 위해서 `Repository` 계층의 단위 테스트를 작성해봅시다!

- **ForeignKey 필드를 포함하는 Entity**을 하나 선택하여 테스트를 진행해주세요 ➡️ **🙋‍♀️ `User` 를 선택하였습니다!**

#### 0) main application 실행 확인
<img width="1351" alt="스크린샷 2023-09-27 오전 12 13 43" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/59f0f913-1d9a-40f1-926b-95cb5ba6b417">  

👩🏻‍🔧 *자잘한 트러블슈팅*은 맨 아래 섹션에 있습니다..

#### 1) given when then 에 따라서 테스트를 작성하기

**1️⃣ given**
```java  
User myMelody = User.builder()  
.nickname("마이멜로디")  
.email("mymelody@gmail.com")  
.phone("010-1234-5678")  
.password("mamerie0v0")  
.created(Timestamp.valueOf(LocalDateTime.now()))  
.temperature(BigDecimal.valueOf(36.7))  
.updated(Timestamp.valueOf(LocalDateTime.now()))  
.build();  
  
User kuromi = User.builder()  
.nickname("쿠로미")  
.email("kurooooo@gmail.com")  
.phone("010-3434-3434")  
.password("kuromzzang!")  
.created(Timestamp.valueOf(LocalDateTime.now()))  
.temperature(BigDecimal.valueOf(40))  
.updated(Timestamp.valueOf(LocalDateTime.now()))  
.build();  
  
User kitty = User.builder()  
.nickname("키티")  
.email("kitty@gmail.com")  
.phone("010-1111-3434")  
.password("kitty!0!")  
.created(Timestamp.valueOf(LocalDateTime.now()))  
.temperature(BigDecimal.valueOf(40))  
.updated(Timestamp.valueOf(LocalDateTime.now()))  
.build();  
```  

> 팁 아닌 팁..? created, updated도 test시 넣어줘야 에러가 나지 않습니다..

**2️⃣ when**
```java  
userRepository.save(myMelody);  
userRepository.save(kuromi);  
userRepository.save(kitty);  
```  

**3️⃣ then**
```java  
List<User> users = userRepository.findAll();  
assertThat(users.size()).isEqualTo(3);  
  
// 닉네임이 "쿠로미"인 사용자 찾기  
Optional<User> foundUser1 = userRepository.findByNickname("쿠로미");  
assertTrue(foundUser1.isPresent());  
assertEquals("쿠로미", foundUser1.get().getNickname());  
  
// 이메일이 "mymelody@gmail.com"인 사용자 찾기  
Optional<User> foundUser2 = userRepository.findByEmail("mymelody@gmail.com");  
assertTrue(foundUser2.isPresent());  
assertEquals("mymelody@gmail.com", foundUser2.get().getEmail());  
  
// 이메일 "kitty@gmail.com" 존재 여부 확인  
boolean emailExists = userRepository.existsByEmail("kitty@gmail.com");  
assertTrue(emailExists);  
  
// 닉네임 "쿠로미" 존재 여부 확인  
boolean nicknameExists = userRepository.existsByNickname("키티");  
assertTrue(nicknameExists);  
```  
<br> 같은 파일 내 코드지만 가독성을 위해 로그 출력 부분은 다른 box에 적어보았습니다~  

``` java// 저장된 유저들 로그에 출력  
logger.info("저장된 유저들:");  
for (User user : users) {  
logger.info("User ID: {}, Nickname: {}, Email: {}", user.getId(), user.getNickname(), user.getEmail());  
}  
  
// 유저 정보 로그에 출력  
logger.info("Found User1: ID={}, Nickname={}, Email={}",  
foundUser1.map(User::getId).orElse(null),  
foundUser1.map(User::getNickname).orElse(null),  
foundUser1.map(User::getEmail).orElse(null));  
  
logger.info("Found User2: ID={}, Nickname={}, Email={}",  
foundUser2.map(User::getId).orElse(null),  
foundUser2.map(User::getNickname).orElse(null),  
foundUser2.map(User::getEmail).orElse(null));  
  
logger.info("Email exists: {}", emailExists);  
  
logger.info("Nickname exists: {}", nicknameExists);  
}  
```  


#### 2) 테스트에서 객체를 3개 이상 넣은 이후에 해당 객체가 출력되는지 확인하기
<center>⬇️ <br>  
💡 결과 확인! 💡  
<img width="1179" alt="스크린샷 2023-09-27 오전 1 06 27" src="https://github.com/CAPSTON-EIGHT/EIGHT_SERVER/assets/77966605/e68c25c0-2cdf-43ed-a73a-5d74855988d6">  


**✅ 저장된 유저들 출력하기** <br>  
**✅ 닉네임이 "쿠로미"인 사용자 찾기** <br>  
**✅ 이메일이 "kitty@gmail.com"인 사용자 찾기** <br>  
**✅ 이메일 "mymelody@gmail.com" 존재 여부 확인** <br>  
**✅ 닉네임 "키티" 존재 여부 확인**

<br><br>  
모두 체크 완료!

#### 3) 테스트를 수행할 때 발생하는 JPA 쿼리를 조회해보기
**`UserRepository.java`**
``` java@Repository  
public interface UserRepository extends JpaRepository<User, Integer> {  
  
Optional<User> findByNickname(String nickname);  
Optional<User> findByEmail(String email);  
boolean existsByEmail(String email);  
boolean existsByNickname(String nickname);  
  
}  
```  

**1) findByNickname**: 닉네임을 기준으로 사용자를 조회  
**2) findByEmail:** 이메일을 기준으로 사용자를 조회  
**3) existsByEmail:** 이메일이 데이터베이스에 존재하는지 확인  
**4) existsByNickname:** 닉네임이 데이터베이스에 존재하는지 확인
  
---  
### **❓ 고민해볼 사항 ❓**
- 지역 정보를 담는 테이블을 고민하는 부분이 과제 수행 중 가장 어려웠던 것 같습니다. 스터디 이후 더 개선해봐야할 것 같습니다!

---  
### 🤖 Troubleshooting 🤖
**1) entity has no identifier**  
분명 entity 파일에 **@Id** 어노테이션을 갖는 필드가 존재함에도 위와 같은 에러가 발생하며 애플리케이션이 실행되지 않았습니다.  
**👩🏻‍🔧 해결방법**: 잘못된 패키지에서 Id 클래스를 import 하고 있었습니다.  
아래와 같이 변경하면 됩니다!  
import org.springframework.data.annotation.Id; ➡️ import javax.persistence.Id;  
<br><br>  
**2) Caused by: java.sql.SQLSyntaxErrorException: Table 'karrot.post' doesn't exist**  
DB 연동도 잘 되어있는데 위와 같은 문제가 발생했습니다. 원인은 엔티티 클래스의 관계 설정 부분이 잘못되어 있어서 발생했던 것..!  
**👩🏻‍🔧 해결방법**:  
**변경 전)**
``` java@ManyToOne  
@JoinColumn(name = "postId", nullable = false) private Post post;  
```  
**변경 후)**
```java  
@ManyToOne  
@JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false) private Post post;  
```  
**`referencedColumnName`** 속성을 추가해 연관된 엔티티 ( `Post` 엔티티)의 PK 이름을 지정하니 해결됐습니다~
  
---  
### **❎ 읽어볼 만한 사이트들 ❎**
https://www.techm.kr/news/articleView.html?idxno=97322  
https://aws.amazon.com/ko/solutions/case-studies/danggeun/
  
---  
---  
# 📂 CEOS WEEK 3: CRUD API 만들기 🎁
<br>  

## 🎁 3주차 목표
### 1️⃣ 새로운 데이터를 create하도록 요청하는 API 만들기
### 2️⃣ 모든 데이터를 가져오는 API 만들기
### 3️⃣ 특정 데이터를 가져오는 API 만들기
### 4️⃣ 특정 데이터를 삭제 또는 업데이트하는 API

<br>  

## 🎁 3주차 미션

✴️ 아직 회원가입 구현 전이기 때문에,  
당근의 주요 기능중 하나인 '**게시물**'과 관련된 API를 만들었습니다.

✳️ 프론트 분들과 협업하기 이전이기 때문에,  
API 명세서에 Header, Body, Path Variable, Query String에 대한 정보들은 포함시키지 않았습니다!


### 1️⃣ 새로운 데이터를 create하도록 요청하는 API 만들기

### Info

- **분류** : `게시판`
- **기능** : `상품 게시글 작성`
- **URL** : `api/board/posts`
- **Method** : `POST`

<img width="651" alt="스크린샷 2023-10-07 13 26 19" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/2b7e1d3a-7ffe-4722-baa9-2c12f54a1712">  

**→ 테스트 완료 ✅**

### Request Example

```json  
#req.body  
{  
"townId": 1,  
"categoryId": 1,  
"postTitle": "쿠로미 인형 판매합니다.",  
"postContent": "홍대 산리오 스토어에서 구매한 쿠로미 인형입니다.",  
"cost": 50000,  
"productImage": "kuromi_doll.jpg",  
"dealMethod": "SELL",  
"postStatus": "IN_PROGRESS"  
}  
  
```  
- `"townId"`: 게시글이 속한 지역의 UID
- `"categoryId"`: 게시글이 속한 카테고리의 UID
- `"postTitle"`: 게시글의 제목
- `"postContent"`: 게시글의 본문 내용
- `"cost"`: 게시글에 표시되는 상품의 가격 또는 비용입니다.
- `"productImage"`: 게시글에 첨부되는 이미지 URL
- `"dealMethod"`: 거래 방법 (판매하기 / 나눔하기)
-
- `"postStatus"`: 게시글의 상태 (판매중 / 예약중 / 거래완료)


### Response Example


```json  
#200 Success  
{  
"status": "SUCCESS",  
"message": null  
}  
  
```  
### Comment
**NormalResponseDto**  
➤ API 요청의 성공 또는 실패 여부를 나타내기 위해 데이터 전송 객체를 생성했습니다.

```java  
public static NormalResponseDto success() {  
return new NormalResponseDto("SUCCESS");  
}  
  
public static NormalResponseDto fail() {  
return new NormalResponseDto("FAIL");  
}  
  
```  
- `status`: API 요청의 상태를 나타내는 값으로, "SUCCESS" 또는 "FAIL"로 설정됩니다.

- `message`: 필요한 경우, 추가적인 상태 메시지를 담을 수 있도록 생서했습니다. 추후 로그인 구현 등에서 상태 메시지를 포함할 때 활용될 예정입니다.

```java  
public void setMessage(String message) {  
this.message = message;  
}  
```  
TODO: 로그인 구현 후 해당 메서드 사용 예정입니다!

<br>  

### 2️⃣ 모든 데이터를 가져오는 API 만들기

### Info

- **분류** : `게시판`
- **기능** : `상품 게시글 전체 조회`
- **URL** : `api/board/posts`
- **Method** : `GET`

<img width="638" alt="스크린샷 2023-10-07 13 27 14" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/ec35b4c0-4b0f-49cc-a03b-2b423dca6876">  


**→ 테스트 완료 ✅**

### Response Example

```json  
#200 Success  
[  
{  
"id": 1,  
"townId": 1,  
"categoryId": 1,  
"postTitle": "쿠로미 인형 판매합니다.",  
"postContent": "홍대 산리오 스토어에서 구매한 쿠로미 인형입니다.",  
"cost": 50000,  
"productImage": "kuromi_doll.jpg",  
"dealMethod": "SELL",  
"postStatus": "IN_PROGRESS",  
"createdAt": "2023-10-07 00:19:22"  
},  
{  
"id": 2,  
"townId": 1,  
"categoryId": 2,  
"postTitle": "마이멜로디 키보드 나눔합니다.",  
"postContent": "직거래 나눔만 가능합니다.",  
"cost": 35000,  
"productImage": "mymelody_keyboard.jpg",  
"dealMethod": "SHARE",  
"postStatus": "IN_PROGRESS",  
"createdAt": "2023-10-07 00:56:28"  
}  
]  
  
```  
### Comment

➤ stream()으로 `List<Post>` 객체를 스트림 형태로 변환  
➤ map 함수로 엔티티(`Post`)를 상품 게시글 응답DTO(`PostResponseDto`)로 변환  
➤ collect(Collectors.toList())collect(Collectors.toList())로 다시 리스트로 수집

<br>  


### 3️⃣ 특정 데이터를 가져오는 API 만들기

### Info

- **분류** : `게시판`
- **기능** : `상품 게시글 조건별 조회`
- **URL** : `api/board/posts/{postId}`
- **Method** : `GET`

<img width="647" alt="스크린샷 2023-10-07 13 28 44" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/d797e047-0d14-459c-bd5a-2acbc35c6ea5">  

**→ 테스트 완료 ✅**


### **Request Parameters**

- `keyword`: 검색 키워드로, 상품 게시글의 제목(`postTitle`) 또는 내용(`postContent`) 중 하나 이상에 해당 키워드를 포함한 게시글을 검색합니다.

### Response Example

```json  
#200 Success (ex. keyword=마이멜로디)  
[  
{  
"id": 2,  
"townId": 1,  
"categoryId": 2,  
"postTitle": "마이멜로디 키보드 나눔합니다.",  
"postContent": "직거래 나눔만 가능합니다.",  
"cost": 35000,  
"productImage": "mymelody_keyboard.jpg",  
"dealMethod": "SHARE",  
"postStatus": "IN_PROGRESS",  
"createdAt": "2023-10-07 00:56:28"  
}  
]  
```  

### Comment

➤ `findByPostTitleContainingIgnoreCase`와 `findByPostContentContainingIgnoreCase` 메서드로 검색 키워드를 포함한 게시글을 각각 제목과 내용으로 검색합니다.

> ContainingIgnoreCase를 사용하여 대소문자를 구분하지 않도록 했습니다!

➤ 제목 검색 결과와 내용 검색 결과를 `responseDtos` 리스트에 추가합니다. 이때 중복 게시물이 들어갈 수 있습니다!  
➤ 중복된 게시글을 제거하기 위해 `Set`에 `responseDtos`의 내용을 복사합니다.  
( `Set` 는 중복된 요소를 허용하지 않으므로 중복이 제거되기 때문입니다.)  
➤ 중복이 제거된 요소를 가져와 다시 리스트로 변환합니다.

<br>  

### 4️⃣ 특정 데이터를 삭제 또는 업데이트하는 API
### Info

- **분류** : `게시판`
- **기능** : `상품 게시글 삭제`
- **URL** : `api/board/post/{postId}`
- **Method** : `DELETE`

<img width="638" alt="스크린샷 2023-10-07 13 29 47" src="https://github.com/CEOS-Developers/spring-tutorial-18th/assets/77966605/2ccacfae-ecdd-4e5b-a70c-eaa79d6c0b72">  

**→ 테스트 완료 ✅**

### **Path Variable**

- `postId`: 게시물 id로, 해당 id에 해당하는 게시물을 삭제합니다.

### Response Example

```json  
#200 Success (ex. keyword=마이멜로디)  
[  
{  
"id": 2,  
"townId": 1,  
"categoryId": 2,  
"postTitle": "마이멜로디 키보드 나눔합니다.",  
"postContent": "직거래 나눔만 가능합니다.",  
"cost": 35000,  
"productImage": "mymelody_keyboard.jpg",  
"dealMethod": "SHARE",  
"postStatus": "IN_PROGRESS",  
"createdAt": "2023-10-07 00:56:28"  
}  
]  
```  

### Comment

➤ `postOptional.isPresent()`로 게시글이 존재하는지 확인합니다.  
➤ 게시글이 존재할 시, `Post` 엔티티를 가져옵니다.  
➤ 게시글이 존재하지 않을 시, `KarrotException(ErrorCode.POST_NOT_FOUND)` 게시글이 존재하지 않음을 나타내는 예외를 반환합니다!

<br>  

### **🚫 예외 처리**

#### GeneralExceptionHandler & KarrotException & ErrorCode
➤ **`GeneralExceptionHandler`** 로 예외 처리를 담당합니다.
- 여러 예외를 처리하기 위해 `@ExceptionHandler` 어노테이션을 사용합니다.
- 현재까지는 사용자 정의 예외인 `KarrotException`과 Spring의 `MethodArgumentNotValidException`에 대한 예외 처리가 있습니다.
```java 
    // KarrotException이 발생하면 예외 상태, 메시지, 해결 방법을 JSON 응답으로 반환
@ExceptionHandler(KarrotException.class)
public ResponseEntity<Map<String, Object>> handleKarrotException(KarrotException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ex.getStatus());
        response.put("message", ex.getMessage());
        response.put("solution", ex.getSolution());
        return ResponseEntity.status(ex.getStatus()).body(response);
        }

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 400); // 상태 코드는 400 (Bad Request)
        response.put("message", "입력 값의 유효성 검증에 실패했습니다. 데이터를 수정하세요.");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
        });
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
```

➤ **`KarrotException`** 클래스를 사용해 사용자 정의 예외를 생성했습니다.

- 예외 발생 시 HTTP 응답 상태 코드(`status`)와 메시지(`message`)를 설정합니다.
- 예외에 대한 해결 방법(`solution`)도 포함합니다.
- `ErrorCode` 를 매개변수로 받아와 해당 예외에 대한 정보를 설정합니다.

```java  
public KarrotException(ErrorCode errorCode, String message, String solution) {  
this.message = message;  
this.status = errorCode.getHttpStatus().value();  
this.solution = solution;  
}  
```  

ex) 게시글이 존재하지 않는데 delete 요청한 경우  
<img width="692" alt="스크린샷 2023-11-06 16 49 41" src="https://github.com/yeni-choi/yeni-choi/assets/77966605/68e1edcd-c377-42b4-809f-6dbcc794a9d0">

<br>  


➤ **`ErrorCode`** enum을 사용해 서로 다른 예외 상황에 대한 정보를 제공합니다.
```java  
POST_NOT_FOUND(HttpStatus.NOT_FOUND, "글을 찾지 못했습니다.", "존재하는 글인지 확인해주세요."),  
FORBIDDEN_ARTICLE(HttpStatus.FORBIDDEN, "게시글 수정, 삭제에 대한 권한이 없습니다.", "잘못된 접근입니다. 입력값을 확인해주세요."),  
VALUE_IS_NONNULL(HttpStatus.BAD_REQUEST, "필수값을 입력하지 않았습니다.", "null 값이 허용되지 않으므로 반드시 값을 전달해주세요."),  
```  


  
---  
### **🐣 이전 주차 기준 변경 사항 🐣**

- 공통- BaseTimeEntity를 통해 생성 시간/수정 시간 자동화
- CHATCONTENT 테이블- sender 식별 필드 추가
- POST 테이블- id 타입 변경
- PURCHASE 테이블- 생성  
  등등...  
  다 기록은 하지 못했지만 이외에도 많은 수정을 거쳤습니다.  
  꼼꼼하게 코드 리뷰 주신 분들 다시 한 번 감사드립니다 🙇🏻‍♀️
---  
### **❎ 참고 ❎**

- https://hianna.tistory.com/554

---

# 📂 CEOS WEEK 4: Spring Security와 로그인
<br>  

### 🔐 4주차 목표

### 1️⃣ JWT 인증(Authentication) 방법에 대해서 알아보기
### 2️⃣ 액세스 토큰 발급 및 검증 로직 구현하기
### 3️⃣ 로그인 API 구현하고 테스트하기
### 4️⃣ 토큰이 필요한 API 1개 이상 구현하고 테스트하기

---  
### 🔐 4주차 미션

### 1️⃣ JWT 인증(Authentication) 방법에 대해서 알아보기
### 🪙 JWT  (**JSON Web Token**)🪙
**- JWT 란?**
- 인증에 필요한 정보들을 암호화시킨 토큰

**- 언제 JWT를 사용하는지?**

**1) Authorization (권한 부여)**
- 유저가 로그인하면, 이후 각 요청에 JWT가 포함되는데 해당 토큰으로 허용된 경로, 서비스 및 리소스에 엑세스한다.
- 최근에는  Single Sign On (SSO) 기능이 자주 사용된다. 오버헤드가 작고 다양한 도메인에서 쉽게 사용할 수 있기 때문이다.

**2) Information Exchange (정보교환)**
- 당사자 간(parties) 정보를 안전하게 전송할 때 쓰인다.
- public/private key 쌍을 사용하여 서명할 수 있기 때문에 보낸 사람이 당사자가 맞는지 확인할 수 있다.
- 또한 서명은 header 와 payload를 사용하여 계산되기 때문에 내용 변경 유무도 확인할 수 있다.

**3) JWT의 구조?**
Header, Payload, Signature 세 부분으로 구성되어 있다.

> xxxxx.yyyyy.zzzzz

- **Header**
  - **토큰 유형(즉, JWT)** 과 **서명 알고리즘 (HMAC SHA256 or RSA)** 두 부분으로 구성된다.

>
```
{
  "alg": "HS256",
  "typ": "JWT"
}

```

- 이 JSON 은  `BASE64Url`  로 인코딩되어서 JWT의 첫 번째 부분을 구성한다.

➤ Base64Url? Binary 데이터를 String으로 바꾸는 인코딩방식

- **Payload**
  - claims을 포함하는 부분이다.
  - 클레임은 Entity(주로 사용자) 및 추가 데이터에 대한 설명이다.
  - 클레임은 registered, public, private 세가지로 나뉜다.

    - **Registered claims** :  유용하고 상호간에 사용 가능한 클레임을 제공하기 위해 권장되는 사전에 정의된 클레임 집합.
      - (EX)![](https://images.velog.io/images/sozohoy/post/6f1ac388-993b-4b0f-9933-30135cb60b47/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202022-01-12%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%205.35.19.png)
    - **Public claims** :  사용자가 마음대로 정의할 수 있는 클레임 집합.
      - 주의) IANA JSON Web Token Registry에 정의하거나 URL 포맷을 사용해야 충돌을 피할 수 있다.
      - EX) ``` { "[https://naver.com"](https://naver.com%22/): true } ```
    - **Private claims** :  사용에 동의하는 당사자 간(parties) 정보를 교환하기 위해 공유하는 커스텀 클레임 집합.
      - EX)  ``` { "token_type": access }```
```
{ "sub": "1234567890", 
  "name": "John Doe", 
  "admin": true }
```
- 이 JSON 은  `BASE64Url`  로 인코딩되어서 JWT의 두 번째 부분을 구성한다.

- **Signature**
  - 이 부분에서는 인코딩된 Header와 Payload, Secret (Header에 사용된 알고리즘)이 필요하다.
  - 토큰이 전송 도중에 변경되지 않았는지 확인하는 데 사용된다.
  - private key 로 서명된 토큰의 경우, JWT의 전송자가 누구인지도 알 수 있다.
  - EX) HMAC SHA256 algorithm 을 사용한다면

```
HMACSHA256( base64UrlEncode(header) + "." 
+ base64UrlEncode(payload), 
+ secret)
```

- **전체 구조**
  ![Encoded JWT](https://cdn.auth0.com/content/jwt/encoded-jwt3.png)

➤ 출력은 이와 같이, HTML 및 HTTP 환경에서 쉽게 전달할 수 있는 점으로 구분된 3개의 Base64-URL 문자열로 구성된다.


**- JWT의 작동방식?**

- 공식 문서에서 소개된 JWT를 가져와 API 또는 정보에 접근하는 방식
  ![](https://velog.velcdn.com/images/jhyun_k/post/ec3cb1b4-e003-4467-9a78-bd640b9568e0/image.png)

- **Access Token:** 클라이언트가 갖고있는, 유저의 정보가 담긴 토큰. 클라이언트에서 요청이 오면 서버에서 해당 토큰에 있는 정보를 활용하여 사용자 정보에 맞게 응답을 진행

- **Refresh Token:** 새로운 Access Token을 발급해주기 위해 사용하는 토큰으로, 짧은 수명을 가지는 Access Token에게 새로운 토큰을 발급해주기 위해 사용. 보통 DB에 유저 정보와 같이 기록.


[![json-web-token](https://blog.kakaocdn.net/dn/t2DrY/btrqGTOykhT/bpeE1EZ0YeP9xIec1uU9g0/img.png)](https://blog.kakaocdn.net/dn/t2DrY/btrqGTOykhT/bpeE1EZ0YeP9xIec1uU9g0/img.png)
4.  사용자가 ID, PW를 입력하여 서버에 로그인 인증을 요청한다.
5.  서버에서 클라이언트로부터 인증 요청을 받으면, Header, PayLoad, Signature를 정의한다. Hedaer, PayLoad, Signature를 각각 Base64로 한 번 더 암호화하여 JWT를 생성하고 이를 쿠키에 담아 클라이언트에게 발급한다.
6.  클라이언트는 서버로부터 받은 JWT를 로컬 스토리지에 저장한다. API를 서버에 요청할때 Authorization header에 Access Token을 담아서 보낸다.
7.  서버는 클라이언트가 Header에 담아서 보낸 JWT의 일치 여부를 확인하여 일치한다면 인증을 통과시켜주고 아니라면 통과시키지 않으면 된다. 인증이 통과되면 페이로드에 들어있는 유저의 정보들을 select해서 클라이언트에 돌려준다.
8.  클라이언트가 서버에 요청을 했을때, 액세스 토큰의 시간이 만료되면 클라이언트는 리프래시 토큰을 이용해 새로운 엑세스 토큰을 발급 받는다.

### 🪙 세션 🪙
- 세션이란 지정된 기간 내에 웹사이트에서 발생한 사용자 상호작용의 집합.  (다수의 페이지 조회, 이벤트, 소셜 상호작용, 전자상거래 등..)
  ![](https://blog.kakaocdn.net/dn/c3Bo35/btreh26fOqF/rNQ03e5vKbqUOqE8NKXyV0/img.png)

1. 클라이언트가 페이지를 요청한다.
2. 서버는 접근한 클라이언트의 Request-Header 필드인 Cookie를 확인하여 클라이언트가 해당 session-id를 보냈는지 확인한다.
3. session-id가 존재하지 않는다면, 서버는 session-id를 무작위로 생성해 클라이언트에게 돌려준다.
4. 서버에서 클라이언트로 돌려준 session-id를 쿠키를 사용해 서버에 저장한다.
5. 클라이언트가 재접속 시, 쿠키를 이용하여 session-id 값을 서버에 전달합니다. 서버는 session-id를 통해 재방문 접속자임을 인식하고 그에 맞는 페이지를 전송한다.

### 🪙 쿠키 🪙

- 사용자가 방문한 웹페이지에서 이용된 환경설정 및 기타 정보를 사용자의 컴퓨터에 저장하는 작은 파일이다.
- 웹 사이트는 쿠키를 통해 접속자를 인식하고, 접속자의 설정과 과거 이용내역에 대한 일부 데이터를 저장한다.
  ![](https://blog.kakaocdn.net/dn/pQQVY/btreotht0Oe/pKBA5g40bD2Qk6MIfF1hc0/img.png)

1. 클라이언트가 서버에게 페이지를 요청한다.
2. 서버는 쿠키를 생성하여 HTTP 화면을 돌려줄 때 쿠키도 클라이언트에게 보내준다.
3. 쿠키는 클라이언트가 가지고 있다가 서버에 다시 요청할 때 요청과 쿠키를 전송한다.
4. 쿠키를 받은 서버는 재방문한 접속자임을 인식하고 그에 맞는 요청 페이지와 쿠키를 전송한다.

**⭐️ 쿠키와 세션의 차이 ⭐️**
![](https://velog.velcdn.com/images%2Fpu1etproof%2Fpost%2F06e6baea-0150-496d-8062-1614f718d89d%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-09-23%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%207.14.29.png)

**➤ 그렇다면?**
세션만 사용하기에는 세션은 서버의 자원을 사용하기 때문에 사용자 수가 많아질수록 서버의 메모리를 많이 차지한다.
그래서, 서버 자원의 낭비를 방지하고 웹 사이트의 속도 개선을 위해선 세션과 쿠키를 적절한 요소에 **병행 사용**한다.
(EX) 로그인에 세션, 아이디 비번 저장에 쿠키, 장바구니 기능에 쿠키

### 🪙 OAuth 🪙
-   사용자 이름, 비밀번호 등의 실제 사용자 자격 증명을 공유하지 않고 한 서비스에서 다른 서비스로 권한 부여를 전달하기 위한 프로토콜.
- 인증(Authentication)과 인가(Authorization) 중  **인가**에 좀 더 초점을 맞추고 있다.
- OAuth 2.0에는 4가지 인증 방식이 있는데 그중 **Authorization Code Grant**가 주요 방식이다.
  - OAuth를 통해 인증, 인가를 제공해주는 서버
    -  Resource Server: 이름, 이메일 등과 같은 자원을 제공한다.
    -   Authorization Server: 토큰을 발급해준다.

![](https://velog.velcdn.com/images%2Fmax9106%2Fpost%2F5620524a-4359-4abd-b90c-07b65359b3ca%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202021-07-12%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%204.16.43.png)

- OAuth 서버에서 client Application에게 바로 access token을 넘겨주지 않고, Authorization code를 넘겨준다.
- client Application은 Authorization code를 통해 access token을 발급 받아, access token으로 허가된 리소스 요청을 하는 방식 (→ access token은 백엔드 내에만 존재하게 되므로, 탈취 위험 감소)


---
### 2️⃣ 액세스 토큰 발급 및 검증 로직 구현하기
**1. SecurityConfig**

**(1)  `@Bean passwordEncoder()`**: 암호화된 패스워드를 생성하기 위한 `PasswordEncoder` 빈을 생성. `BCrypt 해시 함수`를 사용해 비밀번호를 해시한다.
이외에도 PasswordEncoder 구현 클래스에는  Argon2PasswordEncoder, Pbkdf2PasswordEncoder, SCryptPasswordEncoder가 있다.

**(2)  `.csrf(AbstractHttpConfigurer::disable)`**: REST api를 이용한 서버라면, session 기반 인증과는 다르게 stateless하기 때문에 서버에 인증정보를 보관하지 않아서 disable로 설정한다.

**(3) `.authorizeHttpRequests(Requests -> ...)`**: URL별 권한 설정. `anyRequest().authenticated()`은 그 외의 모든 요청에 대한 접근을 인증된 사용자에게만 허용한다.

**(4) .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)** : `JwtAuthenticationFilter`를 `UsernamePasswordAuthenticationFilter` 앞에 추가해서 JWT를 사용한 사용자 인증을 처리

**(5) `.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEn tryPoint).accessDeniedHandler(jwtAccessDeniedHandler))`:**  인증 entry에서, 권한이 없을 경우의 예외 처리 (각각 401, 403)

**2. TokenProvider**

**(1) `JwtTokenProvider` 클래스 생성자** : 클래스의 인스턴스를 초기화하고 필요한 의존성을 주입하는 역할
```java 
public JwtTokenProvider(  
	PrincipalDetailsService principalDetailsService,  
	@Value("${jwt.token.secret}") String secretKey,  
	@Value("${jwt.token.access-token-validity-inseconds}") Long accessTokenValidTime  
) {  
	this.principalDetailsService = principalDetailsService;  
	this.accessTokenValidTime = accessTokenValidTime * 1000L;  
	byte[] keyBytes = Decoders.BASE64.decode(secretKey);  
	this.signingKey = Keys.hmacShaKeyFor(keyBytes);  
}
```

- 사용자 정보를 가져오기 위한 `PrincipalDetailsService`, JWT 서명키인 `secretKey`, 액세스 토큰 유효시간 `accessTokenValidTime`을 주입 받는다.
- `accessTokenValidTime` 변수에 설정된 값에 1000을 곱해서 밀리초 단위로 변환한다.
- `byte[] keyBytes = Decoders.BASE64.decode(secretKey)`: `secretKey` 변수에 저장된 JWT 키를 디코딩하여 바이트 배열로 변환한다.
- `this.signingKey = Keys.hmacShaKeyFor(keyBytes)`: 이 부분은 JWT 서명 키를 생성하는 과정입니다. 디코딩된  `keyBytes`를 HMAC SHA 알고리즘을 통해 서명 키를 생성하고, 이를 `signingKey` 변수에 저장합니다.
  ➤ 이러한 서명 키는 나중에 JWT를 생성하거나 검증할 때 사용한다.

**(2) `createToken`: access token을 생성하는 메서드**
```java 
public TokenDto createToken(String email, String authorities) {  
	Date now = new Date();  
	Date expiration = new Date(now.getTime() + accessTokenValidTime);  
  
	String accessToken = Jwts.builder()  
		.setHeaderParam("typ", "JWT")  
		.setHeaderParam("alg", "HS512")  
		.setSubject("access-token")  
		.claim(EMAIL_KEY, email)  
		.claim(AUTHORITIES_KEY, authorities)  
		.setIssuedAt(now)  
		.setExpiration(expiration)  
		.signWith(SignatureAlgorithm.HS512, signingKey)  
		.compact();  
  
	return TokenDto.builder()  
		.accessToken(accessToken)  
		.build();  
}
```
- 현재 시각과 access token의 만료 시각을 설정한다.
- `Jwts.builder()` 메서드를 사용하여 JWT 빌더를 생성한다.
  -  `setHeaderParam("typ", "JWT")`,`setHeaderParam("alg", "HS512")`: JWT의 헤더 부분 설정 (토큰의 타입, 서명 알고리즘)
- `setSubject("access-token")`: JWT의 subject 설정. (access-token)
- `claim(EMAIL_KEY, email)` , `claim(AUTHORITIES_KEY, authorities)`: 토큰의 클레임. (이메일, 권한 정보)
-  `setIssuedAt(now)`: 토큰의 발급 시간 설정.
- `setExpiration(expiration)`: 토큰의 만료 시각 설정.
- `signWith(SignatureAlgorithm.HS512, signingKey)`: 서명 알고리즘과 서명 키를 설정하여 토큰을 서명(Sign).
- `compact()`: 설정한 내용으로 토큰을 생성하고 문자열로 반환한다.

**(3) `validateToken`: 토큰의 유효성 검사하는 메서드**
```java 
public boolean validateToken(String token) {
	Jwts.parserBuilder() 
		.setSigningKey(signingKey) 
		.build() 
		.parseClaimsJws(token); 
	return true; 
}
```
- JWT 파서 빌더를 생성해 파서에 서명키를 설정하고 파서를 빌드한다. `parseClaimsJws(token)`를 사용하여 파서를 통해 토큰을 파싱하고 서명을 확인한다.
- 서명이 유효하면 파싱된 claim을 반환한다.

**(4) `parseClaims`: 액세스 토큰에서 claim 추출하는 메서드**

```java 
private Claims parseClaims(String accessToken) {
    try {
        // 올바른 토큰이면 true
        return Jwts.parserBuilder().setSigningKey(signingKey).build()
                .parseClaimsJws(accessToken)
                .getBody();
    } catch (ExpiredJwtException e) {
        // 만료 토큰이어도 토큰 정보 꺼내서 return
        return e.getClaims();
    }
}
```

- 토큰을 검증하고 pareClaimsJws 메서드로 클레임을 추출한다.
- 만료되었을 때는 예외를 발생한다.

**(5) `getAuthentication`: 토큰에서 이메일 정보 추출하고 사용자 정보 가져오는 메서드**

```java 
public Authentication getAuthentication(String token) {
    String email = parseClaims(token).get(EMAIL_KEY).toString();
    PrincipalDetails principalDetails = principalDetailsService.loadUserByUsername(email);

    return new UsernamePasswordAuthenticationToken(principalDetails, "",
            principalDetails.getAuthorities());
}
```
-   토큰으로부터 사용자의 이메일 정보 추출하고, `PrincipalDetailsService`를 사용하여 해당 이메일에 대한 사용자 정보를 가져온다.
  -   `loadUserByUsername` 메서드로  `PrincipalDetails` 객체를 생성하고 `UsernamePasswordAuthenticationToken`로 `Authentication` 객체를 생성하여 반환한다.


**3. JwtAuthenticationFilter**
- 이 필터는 요청이 처리될 때마다 한 번만 실행되며, JWT 토큰을 사용하여 인증하는 역할

**(1) `doFilterInternal`: 필터링이 이루어지는 부분을 정의**

```java 
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        // 토큰 추출
        String token = resolveToken(request);

        try {
            // 유효한 토큰인지 검사하고 인증 수행
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UsernameNotFoundException e) { // 회원을 찾을 수 없는 경우
            throw new KarrotException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }
```
- HTTP 요청 헤더에서 토큰을 추출하고 추출된 토큰이 null이 아니며  validateToken(token)이 true인 경우에만 유효한 토큰으로 판단한다.
- getAuthentication을 호출해서 사용자를 인증하고 SecurityContextHolder에 인증 정보를 설정한다.
- 사용자를 찾을 수 없는 경우에는 MEMBER_NOT_FOUND 응답을 처리한다.
- 검증/인증이 완료되면 다음 필터로 요청을 전달한다.

**(2) `resolveToken`: Authorization 헤더에서 토큰을 추출**
```java
// request header에서 토큰 추출
public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        return bearerToken.substring(7);
    }
    return null;
}

```
- Bearer 를 제외한 나머지 부분을 추출한다.


**4. PrincipalDetails**
- `UserDetails` 인터페이스를 구현하여 현재 인증된 사용자의 정보를 제공한다.
```java
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if (user != null) {
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            return principalDetails;
        }
        return null;
    }
}
```

-  현재 사용자의 권한 목록을 반환한다.
- 이외에도 사용자의 이메일, 비밀번호, 계정이 만료되지 않았는지, 계정이 잠겨 있지 않은지, 자격 증명이 만료되지 않았는지, 계정이 활성화되었는지 여부를 제공한다.

**5. PrincipalDetailsService**
-  `UserDetailsService` 사용자의 인증 정보를 데이터베이스에서 가져온다.
```java
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if (user != null) {
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            return principalDetails;
        }
        return null;
    }
}
```
- 사용자 이메일을 받아 해당 사용자의 정보를 데이터베이스에서 조회하고 만약 사용자가 존재하지 않으면 `UsernameNotFoundException`을 던진다.
- 사용자 정보를 조회하고, 이 정보를 기반으로 `PrincipalDetails` 객체를 생성한다.

> ⭐️ 즉, `PrincipalDetailsService`는 사용자 정보를 로드하고, 이 정보를 기반으로
> `PrincipalDetails` 객체를 생성하여 Spring Security에 제공한다. 이 객체는 Spring
> Security가 사용자를 인증하고 권한을 부여하는 데 사용된다! ⭐️


### 3️⃣ 로그인 API 구현하고 테스트하기

### <center>🔐 회원가입</center>

- **STEP 1. signup으로 회원가입 매핑**

```java 
@PostMapping("/signup")  
public ResponseEntity<NormalResponseDto> join(@RequestBody @Valid UserRequestDto requestDto) {  
	authService.joinMember(requestDto);  
	return ResponseEntity.ok(NormalResponseDto.success());  
}
```

- **STEP 2. Request Dto를 통해 회원가입 요청 객체 생성**
  - 비밀번호가 영문 대소문자, 숫자, 특수문자를 최소한 1개씩 포함하고, 총 길이가 8~16자여야 한다는 규칙을 정의했다.
  - 이 규칙에 맞지 않는 비밀번호가 입력되면 해당 필드에 대한 유효성 검사에서 실패하게 했다.

```java 
@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")  
private String password;
```

- **STEP 3. authService의 joinMember 메서드로 회원가입**
  - 비밀번호는 `passwordEncoder`를 통해 인코딩하여 저장한다.
  - `@Transactional` 어노테이션으로 메서드가 성공적으로 실행되면 변경된 내용이 DB에 저장되고, 실패하면 롤백되게끔 한다.

```java 
@Transactional  
public void joinMember(UserRequestDto requestDto) {  
	User user = requestDto.toMember(passwordEncoder); // 비밀번호를 인코딩하여 저장  
	userRepository.save(user);  
}
```

### <center>🔐 로그인</center>

- **STEP 1. login으로 로그인 매핑**
  - authService.login을 통해 로그인 시도 및 토큰 발급한다.
  - **리프레시 토큰**은 **`HttpCookie`** 로 만들어 응답에 추가한다.
    - `maxAge`로 쿠키의 유효기간 설정
    - `httpOnly`로 JavaScript에서 쿠키에 접근하는 것을 방지
    - `sameSite`로 서드파티 쿠키 사용을 허용하도록 설정하되 `secure`로 HTTPS에서만 전송되도록 설정 (소셜 로그인과 같이 다른 도메인에서 제공되는 서비스 사용할 수 있으므로)
  - **액세스 토큰**은 **`HttpHeaders.AUTHORIZATION 헤더`** 에 넣어 응답에 추가한다.

```java 
@PostMapping("/login")  
public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {  
	TokenDto tokenDto = authService.login(loginRequest);  
  
	HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())  
		.maxAge(COOKIE_EXPIRATION)  
		.httpOnly(true)  
		.secure(true)  
		.sameSite(Cookie.SameSite.NONE.attributeValue()) //서드파티 쿠키 사용 허용  
		.build();  
	return ResponseEntity.ok()  
		.header(HttpHeaders.SET_COOKIE, httpCookie.toString())  
		.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())  
		.build();  
}
```

- **STEP 2. AuthService의 login 메서드**
```java 
@Transactional  
public TokenDto login(LoginRequestDto loginRequestDto) {  
	UsernamePasswordAuthenticationToken authenticationToken =  
		new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());  
  
	Authentication authentication = authenticationManager.getObject()  
		.authenticate(authenticationToken);  
	SecurityContextHolder.getContext().setAuthentication(authentication);  
	return generateToken(SERVER, authentication.getName(), getAuthorities(authentication));  
}
```
-   사용자가 로그인 시도를 하면, `AuthService`에서는 제공된 이메일과 비밀번호로 `UsernamePasswordAuthenticationToken`을 생성하여 Spring Security의 `authenticationManager`에 전달한다.
- `authenticationManager`는 토큰을 검증하고, 유효한 경우에 `Authentication` 객체를 반환한다.
- 반환된 `Authentication` 객체는 `SecurityContextHolder`에 설정되어 현재 사용자로 인식된다.
- 로그인이 성공하면 `JwtTokenProvider`를 사용하여 토큰이 생성되고, 클라이언트에게 전달된다.

- **STEP 3. AuthService의 generateToken & getAuthorities메서드**
```java 
// 토큰 발급  
@Transactional  
public TokenDto generateToken(String provider, 	String email, String authorities) {  
  
	TokenDto authToken = jwtTokenProvider.createToken(email, authorities);  
	return authToken;  
}
```
- 이메일과 권한으로 JwtTokenProvider를 사용하여 토큰을 생성한다.

```java 
// 권한 이름 가져오기
public String getAuthorities(Authentication authentication) {
    // 권한 이름들을 ","로 구분하여 하나의 문자열로 변환하기
    return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
}
```
- `authentication.getAuthorities()`로 사용자의 권한 목록을 반환하고, `stream()` 과`map(GrantedAuthority::getAuthority)`을 사용하여 권한의 이름을 추출한다.
- 이후 `Collectors.joining(",")`을 사용하여 모든 권한 이름을 하나의 문자열로 합친다.
- 즉, 객체에서 권한 이름을 추출하여 ","로 구분된 하나의 문자열로 반환하는 메서드다.

### 4️⃣ 토큰이 필요한 API 1개 이상 구현하고 테스트하기
### <center>🔐 회원 프로필 이미지 조회</center>

```java 
@GetMapping("/myInfo")  
public ResponseEntity<UserResponseDto> myInfo(@CurrentUser User user) {  
	UserResponseDto responseDto = userService.getMyInfo(user);  
	return ResponseEntity.ok(responseDto);  
}
```
currentUser 사용해 현재 로그인된 사용자의 (헤더에 토큰 넣어서 테스트) 정보 불러오기

```java 
public UserResponseDto getMyInfo(User currentUser) {  
	return userRepository.findById(currentUser.getId())  
		.map(UserResponseDto::of)  
		.orElseThrow(() -> new KarrotException(ErrorCode.MEMBER_NOT_FOUND));  
}
```
유저 정보를 찾지 못했을시 MEMBER_NOT_FOUND: "유저 정보를 찾지 못했습니다.", "email 과 password 를 올바르게 입력했는지 확인해주세요"

---
<img width="500" alt="스크린샷 2023-11-11 12 21 33" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/54c5a298-de40-4ac2-868b-e6cbe34d3367">
<br>

**↓ 비밀번호 충족 못 할 경우**
<br>
<img width="500" alt="스크린샷 2023-11-11 12 22 33" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/91abb131-880f-4a38-9f54-ed406cbf2a7b">
<br>

**↓ 이미 존재할 회원일 경우**
<br>
<img width="500" alt="스크린샷 2023-11-11 12 26 02" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/0c883e6e-dda0-447e-8851-dbc454e7f158">
<br>

<img width="500" alt="스크린샷 2023-11-11 12 29 14" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/6ad01b25-525f-47fa-90d4-d6146ef41b40">
<br>
<img width="500" alt="스크린샷 2023-11-11 12 45 59" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/4010939c-f1fc-45c6-a16d-458d833bd152">
<br>
JWT는 세션 데이터의 서버 측 스토리지가 필요하지 않으므로 분산 시스템에서 확장성이 좋다는 이점도 있지만 페이로드에 3종류의 클레임을 저장하기에 네트워크에 부하가 걸릴 수 있다는 단점도 있는 것 같다.

이번 스터디를 통해 배운 내용을 실전에서 적용하면서 더 나은 코드와 안전한 서비스를 제공하고 싶다.

---

### 🤐 트러블슈팅

    Encoded password does not look like BCrypt

⭐️ member.setPassword(Dto.getPassword()) 대신 member.setPassword(password)로 **데이터베이스에 암호화된 비밀번호**로 저장해야됨

---
### 🫰 참고
- https://jwt.io/introduction
- https://velog.io/@sozohoy/%ED%86%A0%ED%81%B0-%EC%9D%B8%EC%A6%9D-JWTJson-Web-Token-Access-Token-Refresh-Token
- https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-JWTjson-web-token-%EB%9E%80-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC
- https://ablue-1.tistory.com/70
- https://velog.io/@pu1etproof/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EC%8A%A4%ED%84%B0%EB%94%94-2%EC%A3%BC%EC%B0%A8-%EC%BF%A0%ED%82%A4-%EC%84%B8%EC%85%98-%ED%86%A0%ED%81%B0-%EC%BA%90%EC%8B%9C
- https://velog.io/@corgi/Spring-Security-PasswordEncoder%EB%9E%80-4kkyw8gi

<br>
🖍️ OAuth + JWT 구현 시 프엔과 백엔의 역할 <br>
- https://velog.io/@max9106/OAuth
---

# 📂 CEOS WEEK 5: Docker
<br>  

### 🐳  5주차 목표

### 1️⃣ 로컬에서 도커 실행해보기
### 2️⃣ API 추가 구현 및 리팩토링
---  
### 🐳 5주차 미션

### 1️⃣ 로컬에서 도커 실행해보기
###  1. 기존 VM vs Docker
### 기존 VM

[![](https://github.com/kiworkshop/2021-docker-study/raw/master/1%ED%9A%8C%EC%B0%A8/yoonseo/img/container-vm-whatcontainer_2.png)](https://github.com/kiworkshop/2021-docker-study/blob/master/1%ED%9A%8C%EC%B0%A8/yoonseo/img/container-vm-whatcontainer_2.png)

-   운영체제 위에서 가상화 소프트웨어를 사용해 하드웨어를 애뮬레이션해 게스트 운영체제를 생성
-   가상 머신은 물리적 호스트와 그 위에 설치되는 가상화 하이퍼바이저에서 작동
  -   **하이퍼바이저**란? 서로 다른 복수 개의 OS를 단일 물리 머신 위에서 스케줄링 할 수 있는 소프트웨어
  - <**Type1**> 운영체제 없이 바로 Hypervisor가 설치됨. 해당 머신은 Hypervisor로만 사용할 수 있음. ex) XEN
  - <**Type2**> 운영체제를 설치하고, 그 위에 Hypervisor가 실행되는 형태. Hypervisor 외에 다른 프로세스들도 띄울 수 있음. ex) KVM, QEMU, Virtualbox
    <br>
    - KVM vs QEMU?: QEMU는 가상화 옵션이 폭넓지만 컴파일 시간이 오래 걸림. KVM는 같은 종류의 CPU 아키텍처만 가상화할 수 있지만 Binary Translation을 수행하지 않기 때문에 성능이 향상됨.
    - ➤ 서로의 단점을 보완하며 같은 호스트 머신상에서 필요에 따라 함께 사용할 수 있음
    - ➤ ex. CPU는 호스트 머신과 게스트 머신을 동일한 아키텍처로 가져가며 KVM을 통해 가속화 + DD는 QEMU의 도움을 받아 드라이버로 에뮬레이션 하여 전 가상화

> ⭐️전 가상화(**“Full Virtualizaion”**): 가상 머신이 구동할 OS 혹은 DD를 수정하지 않고 그대로 사용할 수 있는 경우

 <br>
📝 위 하이퍼바이저 내용은 모두 

[네이버클라우드 기술블로그](https://medium.com/naver-cloud-platform/%EB%84%A4%EC%9D%B4%EB%B2%84%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EA%B8%B0%EC%88%A0-%EA%B2%BD%ED%97%98-%EA%B0%80%EC%83%81%ED%99%94-%EA%B0%9C%EB%85%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-1-qemu-vs-kvm-962113641799) 를 참조했습니다.


<br>

![Hyperviseur svg](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/9ee85a44-620f-4165-8ac5-20a9a2f1241d)


### Docker
-   게스트 OS를 설치하지 않고 이미지에 애플리케이션만 격리해서 설치
-   호스트 OS와 실행 환경인 도커 엔진을 공유
-   메모리 접근, 파일시스템, 네트워크 속도가 가상머신에 비해 월등히 빠름
    ![Container what is container](https://www.docker.com/wp-content/uploads/2021/11/container-what-is-container.png)

###  2. Docker에 대해 더 알아보기
### 정의
-   리눅스 **컨테이너** 기반의 오픈소스 가상화 플랫폼
-   다양한 환경을 컨테이너로 추상화하고, 동일한 인터페이스를 제공해 프로그램의 배포 및 관리를 단순하게 제공 - 컨테이너의 다양화
    -   컨테이너는 격리된 공간에서  `프로세스`  가 동작하는 기술
    -   프로세스 자체를 격리시키기 때문에 CPU나 메모리는 프로세스가 필요한 만큼만 추가로 사용

###  구조
기본적으로 server-client 아키텍처를 사용

**도커 Client**
-   도커 CLI로, Docker Daemon에게 Docker API를 통해 전달

**도커 Server(Daemon)**
-   컨테이너 생성, 실행, 이미지 관리 등 도커 엔진을 통한 모든 작업을 수행
-   도커 서비스를 관리하는 다른 도커 데몬과 통신
-   로컬에선 유닉스 소켓, 원격에선 TCP 소켓을 통해 클라이언트의 요청을 받아서 처리

### 이미지와 컨테이너
컨테이너를 만들기 위해서는 총 세가지가 필요함 :  **도커파일, 이미지, 컨테이너**

**Dockerfile**
-   이미지 생성 과정을 작성한 것
-   Dockerfile에 작성된 내용을 토대로 이미지를 생성하게 됨
-   의존성 패키지, 설정파일, script, 환경변수 등 모든 것을 관리

**Image**
-   컨테이너 실행에 필요한 파일과 설정값 등(코드, 런타임, 환경, 시스템 툴, 시스템 라이브러리등)을 포함하고 있는 것
-   상태값이 변하지 않음
-   추가되거나 변하는 값은  **컨테이너에 저장**

**Container**
-  Image를 고립된 환경에서 개별적인 시스템 안에서 실행할 수 있는 공간
- container 안에서 image를 이용해 어플리케이션이 구동

### Docker Network
컨테이너를 생성하게 되면 컨테이너는 **NET namespace**라는 기술을 통해 구현된 가상화 기법을 사용하여 각자 독립된 네트워크 공간을 할당받음

> ⭐️ NET namespace  
> Network interface, iptables 등 네트워크 리소스와 관련된 정보를 분할하여 각각 다른 namespace에 할당함

### 종류
**bridge**
-   기본(default) 네트워크 드라이버
-   브리지 네트워크는 동일한 Docker 데몬 호스트에서 실행되는 컨테이너에 사용

**host**
-   컨테이너를 host 네트워크와 격리시키지 않으며, 컨테이너는 호스트의 네트워킹 네임스페이스를 공유
-   이를 통해 호스트의 포트를 이용하여 바로 서비스할 수 있음

### overlay
-   여러 Docker 데몬을 함께 연결하고 스웜 서비스(도커의 Container Ochestartion 시스템)가 서로 통신할 수 있도록 하는 것

### none
-   컨테이너의 네트워크를 사용하지 않도록 설정하여 외부와 통신 단절

### macvlan
-   컨테이너에 MAC 주소를 할당하여 네트워크에서 물리적 장치로 직접 연결
-   Docker 데몬은 MAC 주소로 트래픽을 라우팅


### 용어 정리
**docker0**
- 호스트의 `veth` 가상 인터페이스와 호스트의 `eth0` 인터페이스를 이어주는 중간 다리 브릿지
```
$ ifconfig
docker0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 172.17.0.1  netmask 255.255.0.0  broadcast 172.17.255.255
        inet6 fe80::42:1ff:fe49:e168  prefixlen 64  scopeid 0x20<link>
        ether 02:42:01:49:e1:68  txqueuelen 0  (Ethernet)
        RX packets 1946  bytes 770155 (752.1 KiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 564  bytes 44678 (43.6 KiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
```
#### **veth**
- 가상의 네트워크 인터페이스로, 자신과 연결된 컨테이너의 네트워크 인터페이스와 패킷을 주고받는 식으로 동작
- 사용자가 직접 생성할 필요없이 도커가 자동으로 생성해주며, 항상 쌍으로(pair)로 생성됨
- 호스트에 컨테이너가 한개도 실행중이지 않은 경우 실제로 docker0에 veth가 바인딩되지 않는 것을 확인할 수 있음
```
$ brctl show docker0
bridge name     bridge id               STP enabled     interfaces
docker0         8000.02420149e168       no
```
- 호스트에 컨테이너가 실행중인 경우 docker0 확인
```
$ brctl show docker0
bridge name     bridge id               STP enabled     interfaces
docker0         8000.02420149e168       no              veth9159bb2
```

#### **eth0**
- 호스트가 사용하는 네트워크 인터페이스를 의미

### 구조
![](https://blog.kakaocdn.net/dn/m9R1m/btrIFH4bS30/zJkupwtBS1WeGWXrfSbu40/img.png)

1️⃣ 컨테이너를 생성하면 컨테이너는 호스트와 통신하기 위한  eth0라는 네트워크 인터페이스를 할당받음 <br>
2️⃣ 동시에 호스트에도  veth (virtual ethernet)라는 네트워크 인터페이스가 할당되고 컨테이너에 할당된  eth0 인터페이스와 통신 <br>
3️⃣ 호스트에 할당된 veth 인터페이스는 docker0와 바인딩되고 docker0는 호스트의 eth0 인터페이스와 연결되어 외부로부터 들어온 요청을 처리 <br>

###  3. Docker 로컬에서 실행해보기
**Dockerfile**
- 컨테이너에 설치해야하는 패키지, 소스코드, 명령어, 환경변수설정 등을 기록한 하나의 파일
- Docker의 **build** 명령은 Dockerfile에 기술된 구성 정보(**Dockerfile**)를 바탕으로 **Docker 이미지를 작성**

![](https://blog.kakaocdn.net/dn/cNbRJe/btq3XbjEavE/imEWh6QDA8QBlUDAZPi8e1/img.png)


**docker-compose**
- 다중 컨테이너 Docker 애플리케이션을 정의하고 실행하기 위한 툴
> Compose is a tool for defining and running multi-container Docker applications.
<br>
-   docker-compose로 개발환경 구성 시 장점 (출처: [지마켓 기술블로그](https://dev.gmarket.com/72)) <br>
-   언제/어디서나, OS가 다른 상황에서도 동일한 환경구성이 가능 <br>
-   모두 동일한 개발환경을 경험하기 때문에 개발환경에 이슈가 발생해도 소통이 쉬움 <br>
-   복잡한 환경도 스크립트화 할 수 있기 때문에 자동화가 가능하고 조작이 쉬움 <br>
-   `docker-compose cli`를 이용하여 쉽게 애플리케이션을 관리할 수 있고 자동화가 가능 <br><br>

STEP 1. jar 파일 생성: Gradle 탭에서 Tasks-build-bootJar 실행<br>
STEP 2. Dockerfile 작성 후 Docker 이미지 빌드
`docker build -t {docker image 이름} {Dockerfile의 위치}`
<br>
STEP 3. docker-compose 작성 후 로컬 도커 실행
`docker-compose -f docker-compose.yml up --build`

<br>
docker ps 명령어로 확인했을 때 두 개의 이미지가 실행되고 있고 [http://localhost:8080](http://localhost:8080/) 에 접속해 
서버가 잘 띄워져있다면 성공

↓<br>
<img width="1338" alt="스크린샷 2023-11-18 21 29 23" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/f2090190-1c1e-4f7e-9a98-5ba1f6d09b39">
<br>
<img width="995" alt="스크린샷 2023-11-18 21 31 56" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/782776c8-5e4c-4225-90d6-a6521baa8641">



---
### 2️⃣ API 추가 구현 및 리팩토링
`User` , `Chatting` 부분을 추가 구현하기로 함

### 📬 이메일 본인인증 요청
### Info

-   **분류**  :  `회원`
-   **기능**  :  `이메일 회원 본인인증 요청`
-   **URL**  :  `api/auth/signup/mailConfirm`
-   **Method**  :  `POST`

#### Request Example
```java
{ "email" : "ceos@ceos.com" }
```
#### Response Example
```java
{ "status": "SUCCESS", "message": "DmdIw86H" }
```
#### Comment
1. Reponse의 data로 사용자 이메일로 보낸 본인인증 코드를 보냄
2. 사용자가 입력한 본인 인증코드와 동일한지 확인한 이후 회원가입을 진행하도록 함
4. 이미 가입된 이메일이라면 status는 FAIL로 보내짐

#### Logic

**STEP1. MailConfig**
이메일 전송에 사용되는 JavaMailSender를 설정하는 클래스로 이메일 전송 환경을 구성함
- 이메일 발송을 위한 **`JavaMailSender`** 빈을 생성하여 프로퍼티 값 주입 및 인코딩 설정
- **`getMailProperties()`** 로 `JavaMailSender` 빈에 속성을 적용
- 전송 프로토콜을 SMTP로 설정 및  활성화, SMTP StartTLS를 사용하도록 설정, SSL을 사용하도록 설정하고 서버 주소 세팅

**STEP2. controller 매핑**
```java
// 이메일 인증  
@PostMapping("/signup/mailConfirm")  
@ResponseBody  
public ResponseEntity<NormalResponseDto> mailConfirm(@RequestBody EmailRequestDto requestDto) throws Exception {  
	if (isAlreadyExistEmail(requestDto.getEmail()))  
		return ResponseEntity.ok(NormalResponseDto.fail());  
  
	String code = signupEmailService.sendSimpleMessage(requestDto.getEmail());  
	NormalResponseDto responseDto = NormalResponseDto.success();  
	responseDto.setMessage(code);  
return ResponseEntity.ok(responseDto);  
}
```

**STEP3. Service**
회원가입 인증 코드를 생성하고 해당 코드를 이메일로 발송하는 기능을 제공함
- 랜덤 8자리 인증 코드 생성 (영문 소문자, 대문자, 숫자 중에서 랜덤하게 조합)
```java
 public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);

            switch (idx) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(10));
                    break;
            }
        }
        return key.toString();
    }
```
- 수신자 이메일 주소를 받아와 해당 주소로 보낼 메일의 `MimeMessage`를 생성하고 HTML 형식으로 메일 내용 구성
```java
public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
    log.info("보내는 대상: " + to);
    log.info("인증 번호: " + ePw);

    MimeMessage message = emailsender.createMimeMessage();

    message.addRecipients(MimeMessage.RecipientType.TO, to);
    message.setSubject("Karrot 이메일 인증 코드입니다.");

    String msg = String.format("""
            <div style="width: 80vw; margin: 0 auto; text-align: center; background-color: #f5f5f5; padding: 20px; border-radius: 10px;">
                <h1 style="font-size: 32px; font-weight: 600; margin-bottom: 20px;">Karrot 회원가입을 위한 인증코드입니다.</h1>
                <p style="font-size: 18px; font-weight: 400; margin-bottom: 20px;">안녕하세요, Karrot입니다. <br /> 회원가입 페이지로 돌아가 아래 인증코드를 입력해주세요.</p>
                <div style="padding: 15px; font-size: 1.5rem; font-weight: 600; background-color: lightgray; border-radius: 10px; display: inline-block;">
                    %s
                </div>
            </div>
            """, ePw);

    message.setText(msg, "utf-8", "html");
    message.setFrom(new InternetAddress(id, "Karrot_Admin"));

    return message;
}
```
- 메시지를 이메일 발송 서비스에 전달하여 이메일을 발송
  - 메일 전송 중에 발생하는 예외: `MailException`으로 처리, 해당 예외가 발생하면 `IllegalAccessException`을 던짐

```java
public String sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);

        try {
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalAccessException();
        }
        return ePw;
    } 
```

<img width="550" alt="스크린샷 2023-11-18 19 31 17" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/33c3c0e7-96f7-4e10-ace8-8bf9dd27902a">
<br>↓<br>
<img width="550" alt="스크린샷 2023-11-18 19 32 16" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/01345a24-e784-4801-abbe-eb74a6fb586b">




### 💬 채팅
웹 소켓을 이용하여 채팅을 구현함

### WEB Socket
- 반 영구적 양방향 통신이며, 하나의 TCP 커넥션으로 full duplex 통신을 제공하는 프로토콜
-  HTTP 통신은 지속적으로 데이터를 요청하는 polling의 방식을 사용해야 하지만, 웹소켓은 그럴 필요가 없음 **→** 낮은 부하로 클라이언트와 서버 간 통신이 가능
- HTTP로 handshake를 초기 통신을 시작한 후, 웹소켓 프로토콜로 변환하여 데이터를 전송함
- 클라이언트는 서버에 HTTP 프로토콜로 handshake를 요청 **→** 서버에서 응답 코드 101 반환
  <img width="687" alt="스크린샷 2023-11-18 14 18 10" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/540a8efc-3d08-446c-ad73-70108d69fad2">

### Logic
### ⚙️ Config

**SocketConfig**

WebSocketMessageBrokerConfigurer를 인터페이스로 구현하고 StompHandler를 인터셉트로 적용한 클래스

> **WebSocketMessageBrokerConfigurer**: 메세징 프로토콜(STOMP)로 메시지를 처리하는 방법들을 정의한 interface

> _**STOMP(Simple Text Oriented Messaging Protocal)**_ -> 데이터 교환을 위한 형식과 규칙을 정의하는 메세징 프로토콜

> _**메시지 브로커(Message Broker)**_-> 송신자(Publisher)의 메시지 프로토콜 형식으로 부터의 메세지를 수신자(Subscriber)의 메세지 프로토콜 형식으로 변환해서 전달하는 중간 프로그램 모듈

- 메시지를 중간에서 라우팅할 때 사용하는 메시지 브로커를 설정함
  - 해당 주소 (prefix)를 구독하고 있는 클라이언트들에게 메시지를 브로드캐스트함 (=메시지를 보냄)
  - 클라이언트에서 메시지를 송신할 때 사용할 도착지(prefix)를 설정함.
  - 클라이언트에서 발신한 메시지가 도착하는 경로를 지정하며, 클라이언트가 `/pub`로 시작하는 주소로 메시지를 보낼 경우, 서버에서는 이를 처리할 적절한 메서드에 라우팅
```java
public void configureMessageBroker(MessageBrokerRegistry registry) {  
	registry.enableSimpleBroker("/sub");
	registry.setApplicationDestinationPrefixes("/pub");  
}
```

- 클라이언트에서 연결할 STOMP endpoint를 지정함
- 모든 cors 요청을 허용
- 수정해야할 사항: 클라이언트의 브라우저가 WebSocket을 지원하지 않는 경우 polling 방식 수행하도록 해주어야 함 (SockJS?)
```java
public void registerStompEndpoints(StompEndpointRegistry registry) {  
	registry.addEndpoint("/ws/chat")
		.setAllowedOriginPatterns("*"); 
}
```

**StompHandler**
`ChannelInterceptor`를 구현하여 STOMP 프로토콜을 사용하는 WebSocket 통신에서 메시지가 채널로 전송되기 전에 실행되는 클래스

- 클라이언트가 `StompCommand.CONNECT`를 사용하여 WebSocket 연결을 시도할 때,  `JwtTokenProvider`를 사용하여 Authorization 헤더에 있는 토큰을 검증
  → WebSocket 연결 시에 사용자의 토큰이 유효한지 확인하고, 유효하지 않은 경우에는 연결을 거부

```Java
@Override public Message<?> preSend(Message<?> message, MessageChannel channel) { 
	StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message); 
	if (StompCommand.CONNECT.equals(accessor.getCommand())) { 
      jwtTokenProvider.validateToken(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7)); 
    } 
    return message; }
```
### 🤝 ChatRoom (채팅방) 생성

**ChatRoomController**
현재 인증된 사용자 정보를 주입받고 HTTP 요청 바디에 있는 데이터를 `ChatRoomRequest` 객체로 매핑
```Java
@PostMapping("/room")  
public ResponseEntity<ChatRoomInfoResponse> createRoom(@CurrentUser User user,  
@RequestBody ChatRoomRequest request) {  
	ChatRoomInfoResponse chatRoomInfoResponse = chatService.createChatRoom(user, request.getPostId());  
	return ResponseEntity.ok(chatRoomInfoResponse);  
}
```

**ChatRoomRequest**
게시물 ID와 웹소켓 세션을 포함
- 생성된 채팅방에 속한 웹소켓 세션을 나타내는 Set → 채팅방에 참여한 각 사용자의 웹소켓 세션은 이 Set에 추가+ 이를 통해 채팅방에 참여한 사용자들의 세션 정보를 관리.

```java 
private Long postId;

@Builder.Default  
private Set<WebSocketSession> sessions = new HashSet<>();
```

**chatRoomInfoResponse**
채팅방 정보를 응답으로 제공 (채팅방 생성자 여부, 생성자 id, 채팅방 생성 날짜 등..)

**ChatService**
채팅방 생성 로직을 수행

- `chatRooms` 맵을 LinkedHashMap으로 초기화

> 👀 왜 LinkedHashMap? 채팅방이 생성된 순서대로 맵에 저장되기 때문에 나중에 채팅방 목록을 조회하거나 특정 순서로 순회할 때 유용! (UI에 표시, 목록 정렬, 이력 기록..)

- 주어진 게시글 ID를 사용하여 해당 게시글을 조회하고, 게시물 없을 시 `POST_NOT_FOUND` 에러 반환
- 거래방법 (판매하기/나눔하기) 에 따라 `ChatRoom` 객체를 다르게 생성하도록 함

```java 
   public ChatRoomInfoResponse createChatRoom(User member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new KarrotException(ErrorCode.POST_NOT_FOUND));

        ChatRoom chatRoom;
        User user;

        // 거래방법 (판매하기, 나눔하기) 에 따라 다르게 저장
        if (post.getDealMethod().equals(DealMethod.SELL)) {
            chatRoom = ChatRoom.builder()
                    .caller(post.getUser())
                    .helper(member)
                    .post(post)
                    .build();
            user = chatRoom.getCaller();

        } else {
            chatRoom = ChatRoom.builder()
                    .caller(member)
                    .helper(post.getUser())
                    .post(post)
                    .build();
            user = chatRoom.getHelper();
        }

```

- 생성된 `ChatRoom`은 `chatRooms` 맵에 저장되고, `chatRoomRepository`를 통해 데이터베이스에 저장
- 게시글의 상태를 '진행 중' -->  '채팅 중'으로 변경

```java 
        chatRooms.put(chatRoom.getId(), chatRoom);
        chatRoomRepository.save(chatRoom);

        if (post.getPostStatus().equals(PostStatus.IN_PROGRESS))
            post.updatePostStatus(PostStatus.CHATTING);

        return ChatRoomInfoResponse.of(chatRoom, user, false);
```
↓ 생성 완료

<img width="550" alt="스크린샷 2023-11-18 17 18 03" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/ab3332ba-9e50-4762-81e5-e4d505216ba3">

### ✉️ ChatMessage 보내기 (구현 중)

**ChattingController**
웹소켓을 이용해 실시간 채팅 메시지를 처리하고, 채팅방에 새로운 메시지가 도착할 때 해당 채팅방의 구독자에게 메시지를 전송하는 역할을 수행


- **채팅방 메시지 처리 및 전송**
  - WebSocket을 통해 전달된 메시지 `SendMessage` 를 받아와 채팅방 ID를 이용하여 `chatRoomRepository`에서 해당 채팅방을 찾음
  - 메시지의 유형(`ENTER` / `TALK`)에 따라 채팅 메시지를 생성하여 `chatMessageRepository` 에 저장
  - 생성된 채팅 메시지를 해당 채팅방에 추가하고 마지막 메시지를 업데이트
  - 메시지의 유형이 대화(=`TALK`)인 경우 → 추가 정보(보낸 사람 ID, 닉네임, 이미지 URL 등)를 설정
  - 최종적으로 `sendingOperations`를 사용하여 채팅방의 구독자에게 새로운 메시지를 전송
```java 
@MessageMapping("/message")
public void message(SendMessage sendMessage) {
    ChatRoom chatRoom = chatRoomRepository.findById(sendMessage.getRoomId())
            .orElseThrow(() -> new KarrotException(ErrorCode.CHATROOM_NOT_FOUND));

    ChatMessage message;

    if (ChatMessage.MessageType.ENTER.equals(sendMessage.getType())) {
        message = createEnterMessage(chatRoom, sendMessage.getType());
        sendMessage.setMessage(message.getMessage());
    } else {
        User user = userRepository.findById(sendMessage.getSenderId())
                .orElseThrow(() -> new KarrotException(ErrorCode.MEMBER_NOT_FOUND));
        message = createTalkMessage(chatRoom, user, sendMessage.getType(), sendMessage.getMessage());
        sendMessage.setMessage(message.getMessage());
    }

    chatMessageRepository.save(message);
    chatRoom.addChatMessage(message);
    chatRoom.updateLastMessage(message.getMessage());
    chatRoomRepository.save(chatRoom);

    if (message.getType().equals(ChatMessage.MessageType.TALK)) {
        sendMessage.setSenderId(message.getSender().getId());
        sendMessage.setSenderNickname(message.getSender().getNickname());
        sendMessage.setImgUrl(message.getSender().getImgUrl());
    }
    sendMessage.setSendTime(message.getSendDate());
    sendingOperations.convertAndSend("/sub/chat/room/" + message.getChatRoom().getId(), sendMessage);
}
```


- 채팅방 ENTER 메시지 생성 (채팅방 입장 메시지)
  - 입장 메시지 내용은 거래 방법(`DealMethod`)에 따라 다르게 설정


```java
private ChatMessage createEnterMessage(ChatRoom chatRoom, ChatMessage.MessageType messageType) {
    DealMethod dealMethod = chatRoom.getPost().getDealMethod();
    User member = dealMethod.equals(DealMethod.SELL) ? chatRoom.getHelper() : chatRoom.getCaller();
    String messageContent = dealMethod.equals(DealMethod.SELL) ?
            "Karrot!" :
            member.getNickname() + "님이 채팅을 시작했어요.";

    return ChatMessage.builder()
            .sender(null)
            .chatRoom(chatRoom)
            .messageType(messageType.name())
            .message(messageContent)
            .build();
}
```

- 채팅방 TALK 메시지 생성 (일반 대화 메시지)
  - 채팅방, 보낸 사용자, 메시지 유형, 메시지 내용을 받아와서 `ChatMessage` 객체 생성

```java 
private ChatMessage createTalkMessage(ChatRoom chatRoom, User sender, ChatMessage.MessageType messageType, String messageContent) {  
	return ChatMessage.builder()  
		.sender(sender)  
		.chatRoom(chatRoom)  
		.messageType(messageType.name())  
		.message(messageContent)  
		.build();  
}

```

↓ 웹소켓으로 접속<br>
<img width="550" alt="스크린샷 2023-11-18 17 44 53" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/3382596f-e261-466d-bdf8-da5d4fbf7479">
<br>
↓ 타 유저로 메시지 전송 <br>
<img width="854" alt="스크린샷 2023-11-18 19 15 39" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/1f847e29-c32f-4145-b8ee-c7b1629514bc">
<br>
🖊️ 수정해야 할 사항: 서버->클라이언트 메시지 전송되도록 고치기
 
---
### 트러블 슈팅
**Docker**
**⛔️ Error starting userland proxy: listen tcp 0.0.0.0:3306: bind⛔️**
해결 방법: db 포트 3308 로 변경 <br>
**⛔️jdbc.exceptions.CommunicationsException: Communications link failure⛔️**
해결 방법: autoReconnect=true&useSSL=false 붙이고 db 설정 수정<br>
**WebSocket**
**⛔️ Error: No enum value sending message using websockets**
해결 방법: Request body를 newline으로 쓰지 않고 한줄에 다 써서 보내기
[참고한 스택오버플로우](https://stackoverflow.com/questions/70555184/no-enum-value-sending-message-using-websockets)

---
### 참고
- https://www.docker.com/resources/what-container/
- https://docs.docker.com/compose/
- https://be-developer.tistory.com/18
- https://yoo11052.tistory.com/208
- https://velog.io/@whattsup_kim/Docker-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC
- https://wildeveloperetrain.tistory.com/79
- https://medium.com/naver-cloud-platform/%EB%84%A4%EC%9D%B4%EB%B2%84%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EA%B8%B0%EC%88%A0-%EA%B2%BD%ED%97%98-%EA%B0%80%EC%83%81%ED%99%94-%EA%B0%9C%EB%85%90-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-1-qemu-vs-kvm-962113641799

---

---
# 📂 CEOS WEEK 6: Github Action을 이용한 CI/CD
<br>  

### 🐳  6주차 목표

#### AWS EC2, RDS 서비스를 활용하여 배포 환경을 구축합니다.
#### Github Actions와 Docker Hub를 이용해 CI/CD 환경을 구축합니다.
---  
### 🐳 6주차 미션

### 1️⃣ 도커 이미지 배포하기

> **👋🏻 참고**
>  첫번째 시도는 6주차의 실패 기록기, 두번째 시도는 7주차에 다시 진행한 성공 기록기입니다.

### 1️⃣ 첫번째 시도 (Github Action 연동없이 시도. 실패 기록기☠️)
dockerhub에 push 후 EC2에서 pull 해와 배포 완료하였습니다.

- docker image commit

``` sudo docker commit ```

- docker image 확인

``` sudo docker images ``` <br>
<img width="469" alt="스크린샷 2023-11-24 01 24 27" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/d7118ce2-2412-44a4-ac38-0afc3e182b83">


- docker hub 로그인<br>
  ``` sudo docker login ``` <br>
  -  ⛔️ **에러 발생 - Error saving credentials: error storing credentials - err: exit status 1, out: write permissions error**
  - 🔧 **해결방법**: `~/.docker/config.json` 삭제


- docker hub 재로그인
  ``` sudo docker login ``` <br>

- docker hub에 push
  ``` sudo docker push <레포지토리명>/<이미지명>:<태그> ``` <br>

![image](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/d28b5753-088f-44dc-a7ff-d476a5d8f36e)

- docker hub에서 확인<br>
  <img width="550" alt="스크린샷 2023-11-24 01 25 13" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/263dbaf5-87dd-4c04-9248-98f2332ffa1c">

- EC2 생성<br>
  <img width="550" alt="스크린샷 2023-11-24 01 53 47" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/be514f27-2d61-4862-a27b-751af86d4ffa">

- EC2에서 docker 설치
 ```
//도커 설치  
$ sudo yum install docker
$ docker -v

// 도커 compose 설치 
$ sudo curl -L https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose   

// 도커 시작하기     
$ sudo systemctl start docker

// 실행 권한 적용   
$ sudo chmod +x /usr/local/bin/docker-compose    
$ sudo chmod 666 /var/run/docker.sock
$ docker-compose -v

```

![image](https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/d9d7a2ba-b931-46fb-be7c-f9bc02149cbb)

- EC2 내 docker-compose에 환경변수 적용
 ```
docker-compose config    
```

- jar 파일 생성
  - ⛔️ **문제 1: AWS EC2 Gradle 배포 시 :compileJava 부분에서 stuck되는 현상 발생!**
  - 👀 **원인**: Gradle과 버전 호환 문제
  - 🔧 **해결방법**:
    `sudo apt-get install lib32stdc++6 `
    `sudo apt-get install lib32z1`
  - [참고 링크](https://stackoverflow.com/questions/32360632/gradle-build-gets-stuck-at-executing-tasks-appgeneratedebugsources-appgen)

  - ⛔️ **문제 2: When using COPY with more than one source file, the destination must be a directory and end with a /**
  - 👀 **원인**: plain jar, executable jar 두 개가 있어서 발생
  - **🔧 해결방법1**: plain jar 삭제
  - **🔧 해결방법2**: **executable jar만 생성**하도록 build파일에 설정
    ` jar { enabled = false }`
  - [참고 링크](https://velog.io/@jeongmin78/CICD-When-using-COPY-with-more-than-one-source-file-the-destination-must-be-a-directory-and-end-with-a%20https://dev-j.tistory.com/22)

- jar 파일 재생성

```
// 테스트 제외하고, jar 파일 빌드만
$ ./gradlew clean build -x test
``` 
<br>

test를 제외하지 않으면
> Task :compileTestJava FAILED
FAILURE: Build failed with an exception.
발생하게 됨.

→ 이제 무사히 빌드되었다.

<br>

- docker-compose 이용해 백그라운드로 애플리케이션 실행

  - **⛔️  문제: Couldn’t connect to Docker daemon at http+docker://localhost - is it running?**
  - **👀 해결방법**: `sudo docker-compose up` (앞에 sudo 붙여 실행)
  - [참고 링크](https://forums.docker.com/t/couldnt-connect-to-docker-daemon-at-http-docker-localhost-is-it-running/87257/4)

<br>
<img width="550" alt="스크린샷 2023-11-24 23 33 56" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/3ed3f5ef-e84b-46a4-926e-27d9a513a82a">

→ 실행 성공적으로 됨

-실행 중 확인<br>
<img width="550" alt="스크린샷 2023-11-24 23 34 50" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/f2b3d287-967f-4a8a-86a8-49865d0aacd9">
<br>
-EIP 할당<br>
<img width="550" alt="스크린샷 2023-11-25 13 10 07" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/9d7f98e0-0f6e-4a93-b376-4455b7c9e025"><br>

-도메인 구매 후 EC2에 연결 & 레코드 업데이트<br>
<img width="550" alt="스크린샷 2023-11-25 13 28 48" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/fb593c44-ae53-4e28-a4e2-7ee3476d105e">
<br>**↓**<br>
<img width="550" alt="스크린샷 2023-11-25 14 09 31" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/75bf5e5b-fc07-46e2-bfc4-2ffa9511d2c9"><br>

- Target Group 생성<br>
  <img width="550" alt="스크린샷 2023-11-25 15 53 13" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/28a77bb4-b0de-479c-b980-ccbf7eca58b9"><br>


- ELB 생성<br>
  <img width="550" alt="스크린샷 2023-11-25 14 08 00" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/0db3b0be-50e3-4ec2-8c00-9061981f7be5"><br>

- 인증서 발급 및 HTTPS 리다이렉트 규칙 생성<br>
  <img width="550" alt="스크린샷 2023-11-25 17 45 10" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/47bc4ffa-3d5f-4384-8432-530faa957684"><br>


- ELB 생성 후 EC2 재부팅을 하고, docker-compose 로 애플리케이션 재실행했는데..

``` 
ubuntu@ip-172-xx:~/spring-daagn-market-18th$ docker-compose up -d
Starting db ... done
Starting web ... done
ubuntu@ip-172-xx:~/spring-daagn-market-18th$ docker-compose ps
``` 

⛔️ 실행 후 docker-compose ps로 조회했는데 shell이 stuck되는 현상 발생 <br>
EC2를 거의 오십번 껐다 키고 ~~수백가지~~ 방법을 시도해보았는데 문제 해결이 안 됨. <br>
설마 설마 해서 확인해보니.. <br>
``` 
               total        used        free      shared  buff/cache   available
Mem:             949         540          84           0         324         253
Swap:              0           0           0
``` 
메모리 사용량을 확인하였는데 현재 사용 가능한 메모리가 253MB밖에 남아있지 않았다. 충분한 자원이 없어서 애플리케이션이나 도커 컨테이너가 정상적으로 실행되지 않았나 싶음.

- **🔧 EC2 메모리 스왑 진행** <br>

  1. **스왑 파일 생성:**

  `sudo fallocate -l 1G /swapfile`

  1GB 크기의 스왑 파일을 생성

  2. **스왑 파일 권한 설정:**

  `sudo chmod 600 /swapfile`

  스왑 파일에 대한 보안을 위해 권한 설정

  3. **스왑 파일 포맷:**

  `sudo mkswap /swapfile`

  4. **스왑 영역 활성화:**

  `sudo swapon /swapfile`

  생성한 스왑 파일 활성화

  5. **부팅 시 자동으로 스왑 파일 활성화:**

  `echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab`

<br>

- 메모리 스왑 후 애플리케이션 재실행 및 조회<br>
  <img width="550" alt="스크린샷 2023-11-25 17 30 28" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/cf46aa7b-fef5-4640-baac-fa802f904595">

→ ps 후 stuck 안되고 조회 잘 됨


**⛔️ web 컨테이너가 정상적으로 실행된 이후 약 2분 뒤 쯤 exit 되어버리는 문제**

<img width="180" alt="스크린샷 2023-11-25 17 47 44" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/6b92c5d7-7daf-43c3-b984-7970eda149df"> <br>

→ HTTP로 리다이렉트는 잘 되나 현재 Web container 문제로 접근이 불가능한 상황

결론만 정리하자면, **첫번째 시도의 문제는 컨테이너의 DB와의 연동 문제**였다.

### 2️⃣ 두번째 시도 (Github Actions+Docker+Nginx. 드디어 성공🪽)

일단 위에 과정에서 했던 파일이나 설정을 따로 삭제하는 건 없이 Github Action과 연동을 먼저 해주었다.

### **🔧 Github Actions 연동** <br>
**1. 워크플로우 작성**
```yml
name: Deploy Development Server

on:
  push:
    branches: ["yeni-choi"]

permissions:
  contents: read

jobs:
  build:
    runs-on: ubuntu-latest
    steps:

      - name: checkout
        uses: actions/checkout@v3

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      ## gradle build
      - name: Grant execute permission for gradlew
        run: chmod +x ./gradlew

      - name: Build with Gradle
        run: ./gradlew build -x test

      ## 웹 이미지 빌드 및 도커허브에 push
      - name: web docker build and push
        run: |
          docker login -u ${{ secrets.DOCKER_USERNAME }} -p ${{ secrets.DOCKER_PASSWORD }}
          docker build -t yeniwithchoi/ceos-karrot .
          docker push yeniwithchoi/ceos-karrot
          docker build -f dockerfile-nginx -t yeniwithchoi/ceos-nginx .
          docker push yeniwithchoi/ceos-nginx

      - name: executing remote ssh commands using password
        uses: appleboy/ssh-action@master
        with:
          host: ${{ secrets.HOST }}
          username: ubuntu
          key: ${{ secrets.KEY }}
          script: |
            
            ## home/ubuntu로 이동합니다.
            cd /home/ubuntu/
            
            ## .env 파일을 생성합니다.
            sudo touch .env
            echo "${{ secrets.ENV_VARS }}" | sudo tee .env > /dev/null
            
            ## docker-compose를 실행합니다.
            sudo chmod 666 /var/run/docker.sock
            sudo docker rm -f $(docker ps -qa)
            sudo docker pull yeniwithchoi/ceos-karrot
            sudo docker pull yeniwithchoi/ceos-nginx
            docker-compose -f docker-compose.yml --env-file ./.env up -d
            docker image prune -f

```

- **빌드 (Build)**
  -   Java 17을 설치하고, Gradle을 사용하여 프로젝트를 빌드
  -    `./gradlew build -x test` 로 테스트를 실행하지 않도록 설정하여 빌드함
- **도커 이미지 빌드 (Build Docker Images):**

  -   Docker 이미지를 빌드하고, Docker Hub에 이미지를 푸시
  -   `yeniwithchoi/ceos-karrot` 및 `yeniwithchoi/ceos-nginx` 이미지를 빌드하고 Docker Hub에 업로드
- **서버 배포 (Deploy Server):**
  -   원격 서버에 SSH를 사용하여 접속하고, 필요한 작업을 수행하여 배포
  -   `/home/ubuntu/` 디렉토리로 이동하고, `.env` 파일을 생성
  -   Docker Compose로 컨테이너를 배포하고 실행합니다.
  -   마지막으로, 사용하지 않는 Docker 이미지를 정리
      <br>

**2. Github Secrets 등록** <br>
아래와 같이 필요한 환경변수들을 등록하였습니다.
-   `DOCKER_USERNAME` : 도커 계정 유저네임
-   `DOCKER_PASSWORD` : 도커 계정 비밀번호
-   `HOST` : EC2의 퍼블릭 IPv4 DNS
-   `KEY` : EC2를 생성하며 같이 생성했던 .pem 파일
-   `ENV_VARS` : 환경 변수를 key-value 형식으로 담음
    <br>

**3. dockerfile과 docker-compose, nginx.conf 작성**

**dockerfile-nginx**

```bash
FROM nginx
RUN rm -rf /etc/nginx/conf.d/default.conf
COPY ./nginx/conf.d/nginx.conf /etc/nginx/conf.d
CMD ["nginx", "-g", "daemon off;"]

```

-  `default.conf` 파일을 삭제하고 프로젝트 내의 `nginx/conf.d/nginx.conf` 파일을 Nginx 설정 디렉토리로 복사함
-   Nginx를 백그라운드에서 실행하도록 설정 <br>

**docker-compose.yml**

```bash
version: '3'
services:

  web:
    container_name: web
    image: yeniwithchoi/ceos-karrot
    env_file:
      - .env
    expose:
      - 8080
    ports:
      - 8080:8080
    tty: true
    environment:
      - TZ=Asia/Seoul

  nginx:
    container_name: nginx
    image: yeniwithchoi/ceos-nginx
    ports:
      - 80:80
    depends_on:
      - web

```

-  `web` : `yeniwithchoi/ceos-karrot` 이미지를 사용하며, `.env` 파일에서 환경 변수를 로드
-   `nginx` : `yeniwithchoi/ceos-nginx` 이미지를 사용하며, 80번 포트를 노출시키고, `web` 서비스에 의존 <br>

**nginx/conf.d/nginx.conf**

```bash
server {
    listen 80;
    server_name *.compute.amazonaws.com;

    access_log /var/log/nginx/access.log;
    error_log /var/log/nginx/error.log;

    location / {
        proxy_pass <http://web:8080>;
        proxy_set_header Host $host:$server_port;
        proxy_set_header X-Forwarded-Host $server_name;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

```

- Nginx 서버 블록을 정의함
-   80번 포트에서 듣고, `*.compute.amazonaws.com` 도메인을 처리
-   로그 파일 경로를 설정하고, `/` 경로로 들어오는 요청을 `web` 서비스로 프록시 <br>

**3. 트러블슈팅**
<br>
<img width="550" alt="스크린샷 2023-11-29 19 38 13" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/3120bc49-bf42-4a9c-9504-f861420975ff"><br>

**- ⛔️ 1차 오류:
`/home/runner/work/_temp/e8b7db6b-8cab-48ff-9f35-de1690e6a741.sh: line 1: ./gradlew: Permission denied`**
-  👉 gradlew 권한 설정
```yml 
- name: Grant execute permission for gradlew  
run: chmod +x ./gradlew
```
<br>

**- ⛔️ 2차 오류: ERROR: failed to solve: failed to read dockerfile: open /var/lib/docker/tmp/buildkit-mount215074990/dockerfile-nginx: no such file or directory**
- 👉 단순한 오류. dockerfile-nginx 위치 설정 바꿔주면 됨

### **📦  RDS 생성 및 EC2 연동** <br>

<img width="550" alt="스크린샷 2023-11-29 19 44 58" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/8d2ebf7b-2b51-42f8-8870-329bed666c5e"> <br>

- EC2 ↔ MySQL 의 트래픽을 보안 그룹에서 허용
-   MySQL (3306) 포트에 대한 인바운드 규칙을 할당<br>

### 2️⃣ 배포환경에 대한 테스트 스크린샷 올리기<br>

### ☁️  EC2 확인 <br>

<img width="600" alt="스크린샷 2023-11-29 19 01 53" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/4abe7d22-ed9a-4528-a1e1-3992f41af1e2"> <br>

➤ 두 컨테이너(**web, nginx**) 모두 잘 실행되고 있는 것 확인 완료

### 🔑 API 테스트 <br>

<img width="600" alt="스크린샷 2023-11-29 19 02 48" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/ec2e7b8c-55e3-416c-b0e7-28328e3b844c"><br>

➤ HTTP → HTTPS 리디렉션 및 ALB에 연결된 도메인 확인 완료  
➤ 회원가입 API 성공 <br>

<img width="600" alt="스크린샷 2023-11-29 19 52 56" src="https://github.com/CEOS-Developers/spring-daagn-market-18th/assets/77966605/974626d3-306b-4760-875d-8eb5f79602f9"><br>

➤ DB(RDS)에도 성공적으로 저장된 것 확인!

---
**6주차 스터디를 통해 느낀 점**
데이터베이스 연동, 환경변수 관리 등 다양한 문제가 있었지만 문제 해결 과정을 통해 환경 설정, 컨테이너 관리, 애플리케이션 배포 등 다양한 측면에서 경험을 쌓을 수 있어서 많이 배울 수 있었던 것 같다.
또한, 해결하면서 서버-네트워크 상호 작용에 대한 이해도 높일 수 있었던 것 같다. 이러한 경험들이 앞으로의 개발 및 배포 과정에서 도움이 될 것 같다 :) 
