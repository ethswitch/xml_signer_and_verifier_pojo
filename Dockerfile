FROM eclipse-temurin:17-jdk-alpine
VOLUME  /opt/certs/keys/private_keys/:/tmp  /var/log/:/tmp
ARG JAR_FILE=/build/libs/ips-client-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]