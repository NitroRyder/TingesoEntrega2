FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} total-service.jar
ENTRYPOINT ["java", "-jar", "/total-service.jar"]