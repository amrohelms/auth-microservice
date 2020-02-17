FROM openjdk:8-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR '/auth-service'

ARG JAR_FILE=target/*.jar
ARG KEY_FILE=./keystore.p12

ENV AUTH_SERVICE_KEYSTORE_FILE=/keystore.p12
ENV AUTH_SERVICE_KEYPAIR_SECRET=$AUTH_SERVICE_KEYPAIR_SECRET
ENV AWS_MYSQL_DB_PASSWORD=$AWS_MYSQL_DB_PASSWORD

COPY ${JAR_FILE} /app.jar
COPY ${KEY_FILE} $AUTH_SERVICE_KEYSTORE_FILE

CMD ["java","-jar","/app.jar"]