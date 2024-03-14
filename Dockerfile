FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/FarmAPI-0.3.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
