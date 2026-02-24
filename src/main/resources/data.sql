-- 서버 재시작 시 중복 삽입 방지
TRUNCATE TABLE POSTS;

-- 초기 게시글 데이터 (ID 포함, 중복 삽입 방지)
INSERT INTO POSTS (ID, TITLE, CONTENT, CREATED_AT, UPDATED_AT, VIEWS) VALUES
(1, 'Spring Boot 4.0 주요 변경사항 정리', 'Spring Boot 4.0에서는 Jakarta EE 11 기반으로 전환되었으며, Java 25 이상을 지원합니다. 주요 변경사항으로는 자동 구성 개선, 성능 향상, 그리고 새로운 보안 기본값이 포함되어 있습니다.', DATEADD('DAY', -9, CURRENT_TIMESTAMP), NULL, 320),
(2, 'MyBatis와 JPA 비교: 어떤 걸 선택할까?', 'MyBatis는 SQL을 직접 제어할 수 있어 복잡한 쿼리에 강점이 있고, JPA는 객체 중심으로 자동 쿼리를 생성해줍니다. 프로젝트 성격에 따라 선택이 달라집니다.', DATEADD('DAY', -8, CURRENT_TIMESTAMP), DATEADD('DAY', -7, CURRENT_TIMESTAMP), 245),
(3, 'H2 Database 개발 환경 설정 가이드', 'H2는 인메모리 또는 파일 기반으로 사용할 수 있는 경량 Java 데이터베이스입니다. 개발 및 테스트 환경에서 빠른 세팅이 가능하며, 브라우저 기반의 H2 Console을 제공합니다.', DATEADD('DAY', -7, CURRENT_TIMESTAMP), NULL, 198),
(4, 'Thymeleaf 템플릿 엔진 기초', 'Thymeleaf는 Spring MVC와의 통합이 뛰어난 서버사이드 템플릿 엔진입니다. th:text, th:href, th:each 등의 속성을 통해 HTML을 자연스럽게 렌더링할 수 있습니다.', DATEADD('DAY', -6, CURRENT_TIMESTAMP), NULL, 174),
(5, 'Bean Validation으로 입력값 검증하기', '@NotBlank, @Size, @Email 등의 어노테이션을 사용하여 DTO의 유효성을 검증하는 방법을 알아봅니다. BindingResult와 함께 사용하면 폼 검증 에러를 쉽게 처리할 수 있습니다.', DATEADD('DAY', -5, CURRENT_TIMESTAMP), DATEADD('DAY', -4, CURRENT_TIMESTAMP), 156),
(6, 'DTO 패턴 도입의 장점과 주의사항', 'DTO(Data Transfer Object) 패턴은 레이어 간 데이터 전달을 캡슐화합니다. Entity를 직접 노출하지 않으므로 보안과 유지보수성이 향상됩니다. 정적 팩토리 메서드 from()과 toEntity()를 활용해보세요.', DATEADD('DAY', -4, CURRENT_TIMESTAMP), NULL, 132),
(7, '기능 기반 패키지 구조 설계', '계층형(Controller/Service/Repository) 패키지 구조 대신 기능별(home, post) 패키지로 구성하면 응집도가 높아지고 관련 코드를 한 곳에서 관리할 수 있습니다.', DATEADD('DAY', -3, CURRENT_TIMESTAMP), NULL, 98),
(8, '게시판 페이징 처리 구현하기', '게시글 목록에서 페이징을 구현하는 방법을 알아봅니다. Page 모델 클래스를 별도로 만들어 현재 페이지, 전체 페이지, 페이지당 항목 수를 관리하면 깔끔하게 구현할 수 있습니다.', DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', -1, CURRENT_TIMESTAMP), 87),
(9, 'Spring MVC 요청 처리 흐름 이해하기', 'DispatcherServlet이 요청을 받아 HandlerMapping으로 컨트롤러를 찾고, ViewResolver로 뷰를 반환하는 Spring MVC의 전체 흐름을 살펴봅니다.', DATEADD('DAY', -1, CURRENT_TIMESTAMP), NULL, 65),
(10, 'Gradle 빌드 스크립트 작성 가이드', 'Groovy DSL 기반의 build.gradle 작성법을 알아봅니다. 의존성 관리, 태스크 설정, 플러그인 적용 방법과 함께 gradlew 명령어 사용법도 정리해봤습니다.', CURRENT_TIMESTAMP, NULL, 42);
