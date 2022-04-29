# <img src="https://i.imgur.com/JD6WL4Y.png" width="300px"/>
### 프로젝트 소개
  * [북쪽행성 HomePage](https://northernplanet.p.ssafy.io)
  * [YouTube](https://www.youtube.com/watch?v=hzazfNSwmiw)
  * [Notion 개발 WIKI](https://www.notion.so/Put-your-hands-up-7c7767c9ba724182a6c1618d8536bd89)
  * **북쪽행성**은 화면 공유와 마우스 만으로 진행되는 비대면 발표의 한계를 극복하여, 비언어적 요소를 포함한 인상 깊은 발표를 진행할 수 있도록 도와주는 온라인 발표 플랫폼입니다.
### 실행화면
* 발표 방(1)
  * 제스쳐를 활용해서 발표 자료를 제어할 수 있습니다.


<img src="https://i.imgur.com/htYMCzI.gif" width="700px"/>

* 발표 방(2)
  * gif 발표 자료를 활용하면 더욱 효과적인 발표를 진행할 수 있습니다.


<img src="https://i.imgur.com/N4ky5dD.gif" width="700px"/>


### 주요 기능
  * 발표 방
     * 발표자는 자신이 올린 **발표 자료를 선택**할 수 있습니다.
     * 발표자는 손 동작과 단축키를 이용해서 발표자료 **넘기기, 이동하기, 크기 키우기, 크기 줄이기**를 할 수 있습니다.
     * 발표자는 **대본**을 통해 발표를 더 효과적으로 할 수 있습니다.
   #### 모션인식
     * 오른손으로 넘기는 모션: 페이지 넘기기
     * 오른손을 다 펴는 모션: 페이지 크기 키우기
     * 오른손을 주먹쥐는 모션: 페이지 크기 줄이기
     * 왼손을 드는 모션: 모션인식 일시정지 / 다시 실행하기
     * 왼손을 화면 위쪽에 가져가는 모션: 페이지 위로 이동하기
     * 왼손을 화면 오른쪽에 가져가는 모션: 페이지 오른쪽으로 이동하기
   #### 단축키
    * a: 페이지 왼쪽으로 이동하기
    * w: 페이지 위쪽으로 이동하기
    * d: 페이지 오른쪽으로 이동하기
    * <-: 이전 페이지로 돌아가기
    * ->: 다음 페이지로 넘어가기
    * 0~4: 페이지 크기 조절하기

  * 마이페이지
     * 발표 자료를 다양한 형식으로 올릴 수 있습니다. **(PPT, PDF, GIF, PNG, JPG)**
     * 발표자료를 수정하며 애니메이션을 적용하고, 대본을 작성할 수 있습니다.
     * 발표 연습 페이지에서 발표를 연습할 수 있습니다.


## 📌 목차
- [시작하기](#시작하기)  
  - [실행환경](#실행환경)  
  - [실행하기](#실행하기)
  - [배포환경](#배포환경)
  - [배포하기](#배포하기)
- [역할](#역할)
- [프로젝트 명세](#프로젝트-명세)
  - [개발 환경](#개발-환경)
  - [시스템 아키텍쳐](#시스템-아키텍쳐)
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
### 실행환경
* Deploy
  * Ubuntu 20.04 LTS
  * Docker 20.10.12
  * docker-compose 1.29.2
* Northernplanet API Server
  - OpenJDK 1.8.0_322
  - Gradle 7.2
  - Spring Boot 2.5.5
  - Spring 5.5.2
* Northernplanet Kurento Signaling Server
  - OpenJDK 17.0.2
  - Apache Maven 3.8.5
  - Spring Boot 2.5.3
  - Spring 5.3.9
* Northernplanet FrontEnd
  - Node 14.19.1
  - npm 6.14.16
  - Nginx 1.20.2
  - Vue 2.6.14
* IDE
  * Intellij 2021.1.3
  * VisualStudioCode 1.59.0
* Browser
  * Chrome 92.0
### 실행하기
* [보러가기✔](exec/21-11-18_실행하기.md)

### 배포하기
* [보러가기✔](exec/21-11-18_배포하기.md)

## 역할
### 김민지(FE, BE)
* 구글 소셜 로그인
* 링크로 발표방에 접속
* 대본
### 김애은(FE, BE)
* JPA
* 발표 자료 수정 
* 다른 파일 형식 업로드
* 모션 인식
### 서영은(FE, BE)
* 대본
* 채팅
* 배포
*  디자인 및 css

### 손동우(FE, BE)
* 발표 자료 관련 API 코드 리팩토링
* 발표 방 관련 API 코드 리팩토링
* 다른 파일 형식 업로드
* 모션 인식
* 배포
### 윤주엽(FE)
* 발표 수정 페이지
* 발표 연습 페이지
* 채팅
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
- Spring Boot, Spring Data JPA
- Gradle
- MySQL
- Intellij

#### DevOps
- AWS EC2
- Nginx
- Docker

#### Collaboration
- Jira, Mattermost
- Gitlab, Sourcetree, GithubDesktop
- [Notion](https://plaid-raja-512.notion.site/Put-your-hands-up-7c7767c9ba724182a6c1618d8536bd89)
<br>



### 시스템 아키텍쳐
![Imgur](https://imgur.com/nGNdX2Z.png)

### 데이터베이스 ERD
![](https://i.imgur.com/fjsRpcf.png)


### 핵심 라이브러리
- __Kurento MCU 서버__
  - __링크__ : https://doc-kurento.readthedocs.io/en/latest/tutorials/java/tutorial-groupcall.html
  - __소개__ : Kurento는 WebRTC 미디어 서버이자 WWW 및 스마트폰 플랫폼용 비디오 애플리케이션 개발을 간단하게 해주는 클라이언트 API입니다.
  - __사용 기능__ : 그룹 화상 통화
- __Kurento-Utils__
    -  __소개__ :브라우저의 WebRTC API를 제어하기 위해 RTCPeerConnection 객체의 생성 및 처리를 단순화하는 데 사용된 자바스크립트 기반의 Kurento 유틸리티 라이브러리.
    -  __사용 기능__ : 발표자료 제어

<br>


## 디렉토리 구조
### Frontend
* [보러가기✔](documents/21-11-18_프론트엔드_디렉토리_구조.md)
### Backend
* [보러가기✔](documents/21-11-18_백엔드_디렉토리_구조.md)


<br>

## 서비스 상세
### Controller 
* [보러가기✔](documents/21-11-18_Controller.md)
### WebSocket
* [보러가기✔](documents/21-11-18_WebSocket.md)
### 사용자 시나리오
* [보러가기✔](exec/21-11-18_사용자시나리오.pdf)
### 기타 라이브러리
* [보러가기✔](documents/21-11-18_기타_라이브러리.md)

<br>

