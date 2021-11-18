# /api/presentation

## 발표자료를 관리하는 API

### POST
#### /
* 이미지 형식의 발표자료를 업로드합니다.
#### /slide
* 슬라이드를 추가합니다.
#### /pptpdf
* 파일 형식의 발표자료를 업로드합니다.
### GET
#### /{userId}
* 사용자 아이디로 발표자료 리스트 받아옵니다.
#### /{userId}/{presentationId}
* 발표자료 속 슬라이드를 받아옵니다.
### PUT
#### /
* 발표자료를 수정합니다.
### DELETE
#### /slide/{slideId}
* 발표자료 속 슬라이드를 1장 삭제합니다.  
#### /{presentationId}
* 발표자료를 삭제합니다.