
## Code build and test container ##

FROM maven:3-jdk-14 as builder

ARG KEY_FILE=/var/keystore.p12
ARG AUTH_SERVICE_KEYPAIR_SECRET=default
ARG AWS_MYSQL_DB_PASSWORD=default

ENV AUTH_SERVICE_KEYSTORE_FILE=/auth-service/keystore.p12
ENV AUTH_SERVICE_KEYPAIR_SECRET=$AUTH_SERVICE_KEYPAIR_SECRET
ENV AWS_MYSQL_DB_PASSWORD=$AWS_MYSQL_DB_PASSWORD

WORKDIR '/auth-service'

COPY ./ ./

COPY ${KEY_FILE} $AUTH_SERVICE_KEYSTORE_FILE

RUN mvn clean install

###############################

## Code deploy container ##

FROM openjdk:8-jdk-alpine

ARG KEY_FILE=/var/keystore.p12
ARG AUTH_SERVICE_KEYPAIR_SECRET=default
ARG AWS_MYSQL_DB_PASSWORD=default

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENV AUTH_SERVICE_KEYSTORE_FILE=/auth-service/keystore.p12
ENV AUTH_SERVICE_KEYPAIR_SECRET=$AUTH_SERVICE_KEYPAIR_SECRET
ENV AWS_MYSQL_DB_PASSWORD=$AWS_MYSQL_DB_PASSWORD

WORKDIR '/auth-service'

COPY --from=builder /auth-service/target/*.jar app.jar
COPY ${KEY_FILE} $AUTH_SERVICE_KEYSTORE_FILE

CMD ["java","-jar","app.jar"]
