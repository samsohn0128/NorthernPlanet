# Dockerfile
#build stage
FROM node:14.19-alpine as build-stage
WORKDIR /app
ADD . .
RUN npm install
RUN npm run build
RUN echo npm build success

#production stage
# nginx 이미지를 사용합니다. 뒤에 tag가 없으면 latest 를 사용합니다.
FROM nginx:stable-alpine as production-stage
# root 에 app 폴더를 생성
# RUN mkdir /app

# work dir 고정
# WORKDIR /app

# work dir 에 build 폴더 생성 /app/build
# RUN mkdir ./build

# host pc의 현재경로의 build 폴더를 workdir 의 build 폴더로 복사
COPY --from=build-stage /app/build /app/build
# ADD ./build ./build

# nginx 의 default.conf 를 삭제
RUN rm /etc/nginx/conf.d/default.conf

# host pc 의 nginx.conf 를 아래 경로에 복사
COPY ./nginx/nginx.conf /etc/nginx/conf.d

# 80 포트 오픈
EXPOSE 80 443

# container 실행 시 자동으로 실행할 command. nginx 시작함
CMD ["nginx", "-g", "daemon off;"]