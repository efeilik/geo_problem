FROM openjdk:19-jdk-slim

EXPOSE 8080

ARG JAR_FILE=build/libs/geo_spring-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]