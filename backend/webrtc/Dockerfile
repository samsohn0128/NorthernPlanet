# Dockerfile

FROM java:8
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/WebRtc-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dkms.url=ws://localhost:8888/kurento","/app.jar"]
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata
