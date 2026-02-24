# Project Specification: VibeApp

최소 기능 스프링부트 애플리케이션인 **VibeApp**의 프로젝트 명세서입니다. 이 명세서는 JDK 25 및 Spring Boot 4.0.1과 같은 최신 기능을 활용하는 미래 지향적인 설정을 바탕으로 작성되었습니다.

## 프로젝트 설정 (Project Settings)

| 항목 | 명세 |
| :--- | :--- |
| **JDK** | JDK 25 이상 |
| **Language** | Java |
| **Spring Boot** | 4.0.1 이상 |
| **Build Tool** | Gradle 9.3.0 이상 (Groovy DSL) |
| **Dependencies** | Web, Thymeleaf |

## 플러그인 (Plugins)

- `io.spring.dependency-management`: Spring Boot 버전에 호환되는 의존성 관리 플러그인

## 프로젝트 메타데이터 (Project Metadata)

- **Group**: `com.example`
- **Artifact**: `vibeapp`
- **Main Class Name**: `VibeApp`
- **Description**: 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트다.
- **Configuration**: YAML 파일 (`application.yml`) 사용

---

## Project Structure (Current)

```text
vibeapp/
├── build.gradle
├── settings.gradle
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── vibeapp/
    │   │               ├── VibeApp.java (Main Entry)
    │   │               ├── home/
    │   │               │   └── HomeController.java
    │   │               └── post/
    │   │                   ├── Post.java (Domain)
    │   │                   ├── PostController.java
    │   │                   ├── PostService.java
    │   │                   ├── PostRepository.java
    │   │                   └── Page.java (Pagination Model)
    │   └── resources/
    │       ├── application.yml
    │       └── templates/
    │           ├── home/
    │           │   └── home.html
    │           └── post/
    │               ├── posts.html
    │               ├── post_detail.html
    │               ├── post_new_form.html
    │               └── post_edit_form.html
    └── test/
        └── java/
            └── com/
                └── example/
                    └── vibeapp/
                        └── VibeAppTests.java
```

---

## Core Features & Conventions

### 1. Architecture: Feature-based Package
계층(Controller, Service 등) 중심이 아닌 **기능(Home, Post)** 중심으로 패키지를 구성하여 도메인별 응집도를 높였습니다.

### 2. View Templates: Structured Directories
뷰 템플릿 역시 기능별 디렉토리(`templates/home/`, `templates/post/`)로 구조화하여 관리 효율성을 증대했습니다.

### 3. Naming Convention: Standard CRUD
실무 관례를 따라 다음과 같은 표준 명칭을 사용합니다:
- **Repository/Service**: `findById`, `findAll`, `create`, `update`, `delete`
- **Domain**: 고유 식별자로 `no` 대신 `id` 필드 사용
- **URL Paths**: 게시글 상세/수정/삭제 시 `{id}` 경로 변수 사용

### 4. Pagination
`PostService`와 `Page` 모델을 통해 게시글 목록의 페이징 처리를 지원합니다 (기본 페이지 당 5개 게시글).

---

## Verification Plan

### Automated Tests
- Gradle 빌드 수행: `./gradlew build`
- 단위 테스트 실행: 프로젝트 컨텍스트 로딩 확인 (`VibeAppTests`)

### Manual Verification
- 애플리케이션 실행 확인: `./gradlew bootRun`
- 홈(` / `) 및 게시판(` /posts `) 기능 동작 확인
