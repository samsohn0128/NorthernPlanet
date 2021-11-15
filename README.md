![waving](https://capsule-render.vercel.app/api?type=waving&height=200&text=Focus:on-your-presentation&fontSize=50&fontAlignY=35&color=gradient)

## 🏆 FOCUS
### 프로젝트 소개
  * [Focus HomePage](https://i5a107.p.ssafy.io)
  * [YouTube](https://www.youtube.com/watch?v=RCCz8Ckgg8k)
  * [Notion 개발 WIKI](https://www.notion.so/7-FOCUS-a4db4765db0d47059f1a743e2e87347c)
  * ```FOCUS``` 는 온라인 발표환경과 오프라인 발표 환경의 차이를 줄이기 위해 화상영상에 발표자료를 함께 띄워서 제어하는 서비스입니다.

### 주차별 계획

| 시작일 | 종료일 |                      내용                      |          담당자          |     진행상황    |
| :----: | :----: | :--------------------------------------------: | :----------------------: |:--------------: |
| 10/12  | 10/12  |              새로운 팀원들 온보딩              |        팀원 전체         |    완료 ✔    |
| 10/12  | 10/15  |         보완할 점 및 새로운 기능 기획          |        팀원 전체         |     완료  ✔   |
| 10/18  | 10/18  |                 기존 코드 분석                 |        팀원 전체         |     완료 ✔    |
| 10/19  | 10/22  |   구글 로그인, 링크로 발표방에 초대하는 기능   |      김민지, 서영은      |      완료  ✔    |
| 10/19  | 10/26  |             업로드한 발표자료 수정             |  김애은, 손동우, 윤주엽  |      완료  ✔   |
| 10/19  | 10/26  |             마이페이지 FE 개발하기             |          윤주엽          |      완료  ✔    |
| 10/25  |  11/5  |                발표방 대본 기능                | 김민지,  서영은,  윤주엽 |      완료  ✔    |
| 10/27  |  11/1  | 이미지가 아닌 다른 파일 형식도 업로드하는 기능 |      김애은, 손동우      |      완료  ✔    |
|  11/2  | 11/10  |           모션으로 발표자료 제어하기           |      김애은, 손동우      |      진행중      |
|  11/8  | 11/12  |              발표 방에서 그룹채팅              |  김민지, 서영은, 윤주엽  |      완료  ✔      |
|  11/8  | 11/12  |                      배포                      |      서영은, 손동우      |      -      |
| 11/15  | 11/18  |                  통합 테스트                   |        팀원 전체         |      -      |
| 11/15  | 11/18  |             발표 준비 및 문서 작성             |        팀원 전체         |      -      |

### 상세 계획

|        |                   10/25-10/29                   |                11/1-11/5                 |              11/8-12               |         11/15-11/19         |
| :----: | :---------------------------------------------: | :--------------------------------------: | :--------------------------------: | :-------------------------: |
|  주차  |                      3주차                      |                  4주차                   |               5주차                |            6주차            |
| 김민지 | 구글 로그인, 링크로<br/> 발표방에 초대하는 기능 | 대본 BE, 마이페이지 <br /> 회원정보 수정 |   발표 방에서 <br /> 그룹채팅 BE   | 배포, 테스트<br /> 문서작성 |
| 김애은 |    발표 자료 수정<br/>다른 파일 형식 업로드     |    다른 파일 형식 업로드<br/>모션제어    | 다른 파일 형식 업로드<br/>모션제어 |            배포             |
| 서영은 |         대본 - 에디터, 백엔드 API 구현          |                대본, 채팅                |                배포                |       발표준비, 배포        |
| 손동우 |    발표 자료 수정<br/>다른 파일 형식 업로드     |    다른 파일 형식 업로드<br/>모션제어    |                배포                |            배포             |
| 윤주엽 |               마이페이지 FE 개발                |       대본<br/>발표연습 페이지 FE        |     발표 방에서<br/>그룹 채팅      |     테스트 및 문서 작성     |

### 주요 기능

  * 발표자료 제어
      * 발표자는 방 정보 페이지에서 발표자료를 __업로드__ 할 수 있습니다.
      * 발표자는 미팅룸에서 발표자료를 선택할 수 있습니다.
      * 발표자는 미팅룸에서 발표자료의 __애니메이션__ 을 선택할 수 있습니다.
      * 발표자는 미팅룸에서 발표자료를 __넘길 수__ 있습니다.
      * 발표자는 미팅룸에서 대본을 사용할 수 있습니다.
  * 대시보드
      * 방은 Now, Future, History로 구분됩니다.
  * Now 그룹은 __시작시간 < 현재시간__ 인 방 목록을 보여줍니다.
    * Future 그룹은 __현재시간 < 시작시간__ 인 방 목록을 보여줍니다.
    * History 그룹은 __종료시간__ 이 존재하는 방 목록을 보여줍니다.
* 마이페이지
     * 자신의 이름을 수정할 수 있습니다.
     * 발표 자료를 추가하고 수정할 수 있습니다.
     * 대본을 수정하고, 발표 화면을 미리 보며 연습할 수 있습니다.
### 실행화면
* 발표자료 제어
![GOMCAM 20210819_1639400155](https://user-images.githubusercontent.com/39117025/130028175-f3159a36-64ca-41a2-9eae-3da1428061c6.gif)

* 대시보드
![대시보드용량줄임](https://user-images.githubusercontent.com/39117025/130029101-293592fa-73eb-457c-9e9b-65ce3f1f7658.gif)



### 향후 계획
  * Secure 코딩
  * 다양한 상황에 대처하는 오류처리
  * 코드 스타일 개선

## 📌 목차
- [시작하기](#시작하기)   
  - [설치하기](#설치하기)
  - [실행하기](#실행하기)
  - [배포환경](#배포환경)
  - [배포하기](#배포하기)
- [역할](#역할)
- [프로젝트 명세](#프로젝트-명세)
  - [개발 환경](#개발-환경)
  - [시스템 아키텍처](#시스템-아키텍처)
  - [데이터베이스](#데이터베이스)
  - [핵심 라이브러리](#핵심-라이브러리)

- [디렉토리 구조](#디렉토리-구조)   
- [서비스 상세](#서비스-상세)
  - [Controller](#Controller)
  - [WebSocket](#WebSocket)
  - [Router](#Router)
  - [사용자 시나리오](#사용자-시나리오)
  - [기타 라이브러리](#기타-라이브러리)
  <br>

<br>

## 시작하기
### 설치하기
1. 깃헙의 레포지토리를 클론합니다.
    ```
    $ git clone https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107.git
    ```
2. frontend 폴더 내부에서 node_modules 를 설치합니다.
    ```
    $ npm install
    ```
### 실행환경
* java 1.8.0_301
* maven 3.8.1 
* lombok 1.18.20
* npm 6.14.13
* Eclipse STS 3.9.17
* Intellij 2021.1.3
* VisualStudioCode 1.59.0
* Chrome 92.0
### 실행하기
1. kms container를 실행합니다.
    ```    
    $ docker ps -a
    $ docker container start {kms container id}
    ```
2. springboot 프로젝트를 실행합니다.
    ```
    $ cd ~/springboot
    $ mvn spring-boot:run &
    (&을 붙이면 background에서 실행)
    ```
3. webrtc 프로젝트를 실행합니다.
    ```
    $ cd ~/webrtc
    $ mvn -U clean spring-boot:run -Dspring-boot.run.jvmArguments="-Dkms.url=wss://localhost:8433/kurento" & 
    
    # (&을 붙이면 background에서 실행)
    ```
    
4. 브라우저를 통해 접속합니다. (크롬 부라우저 권장)

### 배포환경
- __URL__ : https://i5a107.p.ssafy.io
- __배포 여부__ : O 
- __접속 가능__ : 접속 가능
- __HTTPS 적용__ : O 
- __PORT__ : 8443

### 배포하기
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/exec/21_08_18_%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0.md)



## 역할
### 김민지(FE, BE)
* 소셜 로그인(구글)
* 링크로 발표방에 초대하는 기능
* 대본
* 마이페이지 - 회원정보 수정
* 채팅 기능 구현
* 배포
### 김애은(FE, BE)
* JPA, JPQL
* 발표 자료 수정 
* 다른 파일 형식 업로드
* 모션 제어
### 서영은(FE, BE)

* 대본 - 에디터, 백엔드 API 구현
* 채팅 기능 구현
* 배포

### 손동우(FE, BE)
* 발표 자료 수정 
* 다른 파일 형식 업로드
* 모션 제어
* 배포
### 윤주엽(FE)
* 마이페이지
* 대본 페이지, 발표 연습 페이지
* 채팅 기능 구현
<br>

## 프로젝트 명세

### 개발 환경
#### Front-end

- HTML5, CSS3, JS(ES6)
- Bootstrap5
- Vue.js, Vuex
- Visual Studio Code


#### Back-end
- Java
- Spring Boot, Spring MVC, Spring Data JPA
- JPA, JPQL
- Gradle
- MySQL
- Intellij, Eclipse STS

#### DevOps

- AWS EC2
- Apache Tomcat

#### Collaboration
- Jira
- Gitlab, Sourcetree, GithubDesktop
- Mattermost
- [Notion](https://plaid-raja-512.notion.site/Put-your-hands-up-7c7767c9ba724182a6c1618d8536bd89)
<br>



### 시스템 아키텍쳐
![](https://i.imgur.com/QChANZP.png)





### 데이터베이스
![](https://i.imgur.com/twgFUFs.png)

### 핵심 라이브러리
- __Kurento MCU 서버__
  - __링크__ : https://doc-kurento.readthedocs.io/en/latest/tutorials/java/tutorial-groupcall.html
  - __소개__ : Kurento는 WebRTC 미디어 서버이자 WWW 및 스마트폰 플랫폼용 비디오 애플리케이션 개발을 간단하게 해주는 클라이언트 API입니다.
  - __사용 기능__ : 그룹콜 기능
- __Kurento-Utils__
    -  __소개__ :브라우저의 WebRTC API를 제어하기 위해 RTCPeerConnection 객체의 생성 및 처리를 단순화하는 데 사용된 자바스크립트 기반의 Kurento 유틸리티 라이브러리.
    -  __사용 기능__ : 발표자료 제어


<br>

## 디렉토리 구조
### Frontend
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/documents/21-08-18_%ED%94%84%EB%A1%A0%ED%8A%B8%EC%97%94%EB%93%9C_%EB%94%94%EB%A0%89%ED%86%A0%EB%A6%AC_%EA%B5%AC%EC%A1%B0.md)
### Backend
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/documents/21-08-18_%EB%B0%B1%EC%97%94%EB%93%9C_%EB%94%94%EB%A0%89%ED%86%A0%EB%A6%AC_%EA%B5%AC%EC%A1%B0.md)


<br>

## 서비스 상세
### Controller 
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/documents/21-08-19_Controller.md)
### WebSocket
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/documents/21-08-20_Kurento_WebSocket_Message.md)
### Component
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/documents/21-08-19_FrontendComponent.md)
### 사용자 시나리오
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/exec/%EC%8B%9C%EC%97%B0%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4.pdf)
### 기타 라이브러리
* [보러가기✔](https://lab.ssafy.com/s05-webmobile1-sub3/S05P13A107/-/blob/master/documents/21-08-18_%EA%B8%B0%ED%83%80_%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC.md)

<br>

