# Make Project

## 인텔리제이 Project 생성

### 1. New Project

- Spring Intializer
  - Project SDK : 1.8
  - Choose Initializr Service URL.
    - Default: https://start.spring.io
- Project Metadata
  - Group: com.github.jamie9504
  - Artifact: board
  - Type: Gradle Project
  - Language: Java
  - Packaging: Jar
  - Java Version: 8
  - Version: 0.0.1-SNAPSHOT
  - Name: board
  - Description: jamie's board
  - Package: com.github.jamie9504.board
- Dependencies
  - Web - Spring Web
  - SQL - Spring Data JPA
  - SQL - H2 Database
- Project name: board / Project location: (git위치)/backend/board

### 2. 의존성 직접 추가

- Rest Assured 4.3.1 _(API 테스트용 Java 라이브러리)_

  ``` java
  // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
  testCompile group: 'io.rest-assured', name: 'rest-assured', version: '4.3.1'
  ```

