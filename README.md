# auth-microservice

## Description
Authentication microservice using Spring Rest API, Spring Security, JPA, MySQL, JWT and RSA Encryption.

The article explaining the service can be found [here](https://simplersoftware.io/secure-backend-apis-using-a-custom-authentication-microservice/).

## Technology
Java 13, Spring, Maven

## Pre-requisites 
- [Docker](https://www.docker.com/get-started) 

## How to run the service
1. Download or clone the code
`git clone https://github.com/simpler-software/auth-microservice.git`

2. Generate the key pair
`keytool -genkeypair -alias ServerKeyPair -keyalg RSA -keysize 2048 -validity 365 -storetype PKCS12 -keystore keystore.p12 -storepass <the secret, this value is used in the next step>`

3. Set 2 system environment variables as follows
`export AUTH_SERVICE_KEYPAIR_SECRET=<the secret used while generating the key pair in step 2>`
`export AWS_MYSQL_DB_PASSWORD=<the password for your mysql database>`
Move the generated certificate file to auth-microservice/src/main/resources/key/keystore.p12

4. Set the mysql database connection properties in the auth-microservice/src/main/resources/application.properties
`SPRING.DATASOURCE.URL`
`SPRING.DATASOURCE.USERNAME`

5. Run the following docker command to build the image
`docker build -t auth-service .`

6- Run the service
`docker run auth-service`
