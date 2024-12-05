FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} credito-service.jar
ENTRYPOINT ["java", "-jar", "/credito-service.jar"]