# 웹 애플리케이션 서버

## 진행 방법

* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능 목록

- [x] `/index.html` 파일을 서빙한다.
  - [x] 요청 헤더를 파싱한다.
  - [x] path가 `index.html`일 경우 해당 파일을 응답한다.
  - [x] 존재하지 않는 path 요청이나 null 값은 404로 응답한다.
- [x] CSS 파일을 지원한다.
- [x] `GET /user/create` 경로로 온 query string을 지원한다.
  - [x] key=value 형태로 전달된 query string을 `User` 객체로 매핑한다.
- [x] `POST /user/create` 경로로 온 회원가입 기능을 구현한다.
- [x] 리다이렉트 기능을 지원한다.
  - [x] 회원가입 완료 후 `/index.html`로 이동한다.
  - [x] 302 HTTP Status Code를 사용한다.
- [ ] 로그인 기능을 구현한다.
  - [ ] `/user/login.html` 경로로 온 로그인 요청을 처리한다.
  - [ ] 로그인 성공 시 `/index.html`로 리다이렉트한다.
  - [ ] 로그인 실패 시 `/user/login_failed.html`로 리다이렉트한다.
- [ ] 사용자 목록 출력 기능을 구현한다.
  - [ ] `GET /user/list` 경로로 온 요청에 대해 사용자 목록을 HTML 형식으로 반환한다.
  - [ ] 로그인이 되어 있지 않은 경우 로그인 페이지로 리다이렉트한다.
- [ ] Session 기능을 추가한다.
  - [ ] 로그인 여부를 체크하는 기능을 추가한다.
  - [ ] 로그인 성공 시 Session에 사용자 정보를 저장한다.
  - [ ] `/user/login` 접속시 로그인 되어 있으면 `/index.html`로 리다이렉트한다.
