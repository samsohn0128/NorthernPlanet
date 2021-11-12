# Dockerfile

FROM java:8
VOLUME /tmp
EXPOSE 8443
ARG JAR_FILE=target/WebRtc-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["mvn","spring-boot:run","-Dspring-boot.run.jvmArguments=\"-Dkms.url=ws://localhost:8888/kurento\"","&"]
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata
