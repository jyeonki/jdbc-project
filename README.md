
1. Gradle 한글깨짐 현상 해결
- 인텔리제이 파일 -> 설정 탭으로 이동
- 검색창에 gradle 검색 후 빌드 도구/ 실행 탭에서 Gradle 선택
- 다음을 사용하여 빌드 및 실행과 테스트실행을 Gradle에서 IntelliJ IDEA로 변경


2.데이터베이스 연결 설정
- C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib 에서 ojbc6.jar 찾아서 복사
- 아래 설정 경로 /src/main/webapp/WEB-INF/lib에 붙여넣기

- build.gradle 파일에 dependencies 블록에 아래 소스코드 추가 후 재빌드 (코끼리 새로고침 버튼)
```groovy

//오라클 라이브러리 (11g edition - gradle, maven 라이센스 문제 공식 지원 불가)
implementation fileTree(dir: '/src/main/webapp/WEB-INF/lib', include: ['*.jar'])
```

3. lombok 적용하기
- build.gradle - dependencies에 추가
```groovy
    //lombok 라이브러리
   compileOnly 'org.projectlombok:lombok:1.18.12'
   annotationProcessor 'org.projectlombok:lombok:1.18.12'
```
- 인텔리제이 플러그인 lombok 설치
- 설정에 annotation processor 검색 -> 아노테이션 활성화 체크