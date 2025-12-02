# BangBang Codeboy (정보처리기사/SQLD 스피드 퀴즈 플랫폼)

## 1. 프로젝트 개요
- 정보처리기사와 sqld를 공부의 범위가 방대하여 공부에 어려움을 겪는 자격증 준비생 혹은 SSAFY생을 위해 제작
- 각 자격증에서 배우는 내용을 쉽게 복습할 수 있는 시스템을 만든다.
- 반 친구들과 스피드 퀴즈 대결을 하며 재미있게 학습한다.
- 랭킹 기능을 제공하여 학습의 성취감을 얻는다.

## 2. 핵심 기능
- 정보처리기사와 sqld 관련 키워드를 활용한 4지선다 퀴즈 제공
- 퀴즈방 생성 및 최대 20명과 실시간 대결
- 오답노트 및 해설 제공
- 유저 제작 문제 생성/수정/삭제
- 리더보드 랭킹
- 유저가 직접 문제를 제작 가능
- 혹은 ChatGPT, Gemini로 문제를 자동으로 생성하고 해설을 제공

## 3. Tech Stack & 아키텍처
  <img width="400" height="600" alt="image" src="https://github.com/user-attachments/assets/a3324b77-93d6-4209-a023-4c01f6a7f2b6" />
  <br/>

   - Spring, MySQL, MyBatis, Fast API -> 백엔드 API 개발과 데이터베이스, 외부 LLM 호출 서버 제작을 맡고 있음


## 4. 시스템 설계 문서
- [ERD]
  <img width="1200" height="700" alt="관통프로젝트" src="https://github.com/user-attachments/assets/cc8fa9d8-ef1d-4ed8-a239-c5bbcad12c26" />

- [유즈케이스 다이어그램]
  <img width="1200" height="700" alt="image" src="https://github.com/user-attachments/assets/28f040f0-5e32-47ef-84f7-9f59f790c8d1" />

- [기능 명세서 (구글 스프레드시트)](https://docs.google.com/spreadsheets/d/1pif1EVEQNDvyv444s-4GoqX6n816fivUlyM2uHmzNT8/edit?gid=0#gid=0)
- [WBS & 일정관리(Notion)](https://pointy-harpymimus-0cb.notion.site/2a76fbddf57780169009ed2301511ee1)
- [목업/화면 설계(Notion)](https://pointy-harpymimus-0cb.notion.site/2ab6fbddf57781839d13f9224dfa7a9c)


## 5. API 시나리오 테스트
### 테스트 시나리오
1. 모든 테이블의 데이터들만 삭제
2. 회원가입 + 해당 유저의 점수 등록
3. 로그인(전단계에서 회원가입한 유저의 id와 password를 이용)
4. 문제세트 전체조회
5. 로그인한 회원이 만든 유저제작문제세트 조회
6. 로그인한 회원이 유저제작문제 3개 추가
7. 로그인한 회원이 유저제작문제 하나 수정
8. 유저제작 문제의 댓글 조회
9. 로그인한 회원이 댓글 3개 작성
10. 로그인한 회원이 댓글 하나 수정
11. 로그인한 회원이 댓글 삭제
12. 로그인한 회원이 문제 세트 삭제
13. 모든 회원들의 점수 조회
14. 로그인한 회원 한명의 점수를 조회
15. 로그인한 회원 한명의 점수를 수정
16. 문제테이블(problem테이블) 에서 문제 전체 조회
17. 문제테이블(problem테이블) 에서 문제 전체 조회
18. 로그인한 회원의 오답노트에 문제 추가 후 로그인한 회원의 오답노트 안의 전체 문제 조회
19. 로그인한 회원의 오답노트에 있는 문제 삭제(problem과 user_problem테이블에 있는 문제도 삭제되면 안됨)
20. 모든 퀴즈방 목록 조회
21. 로그인한 회원이 퀴즈방을 생성하고 방장이 됨(isHost = 1)
22. 또 다른 mcp서버가 localhost:8082에서 가동되며 같은 DB 다른 회원을 가입하고 로그인 전체 퀴즈방들을 조회하여 조회된 퀴즈방에 들어가있는 회원들을 조회하고 isHost=0인 방장이 아닌 상태로 다른 방장이 만든 퀴즈방에 들어가기
23. 방장인 회원이 퀴즈방을 삭제

### 테스트 방법
1. html에 자바스크립트를 작성 후 axios 통신을 통해 Vite 프록시 서버에 요청을 보낸 후 응답을 console.log로 출력
2. 테스트 결과를 testResultYYYYMMDDHH형식으로 다운로드
<img width="2493" height="1701" alt="image" src="https://github.com/user-attachments/assets/dac14fda-938c-4e5f-8726-f9744f76e6c1" />



## 6. 향후 개선 계획
- DeepseekOCR을 활용하여 개념요약 PDF -> 텍스트 -> 문제 제작 Flow 개발 / Fast API로 외부 LLM 호출하여 문제 생성 및 해설 기능 개발
- API 테스트 파이프라인 제작
