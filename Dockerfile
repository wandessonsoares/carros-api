FROM openjdk:17-jdk-slim-buster
EXPOSE 8080
ARG JAR_FILE=target/carros-api-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} carros-api-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/carros-api-1.0-SNAPSHOT.jar"]
