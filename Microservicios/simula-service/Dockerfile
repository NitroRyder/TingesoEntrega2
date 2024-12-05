FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} simula-service.jar
ENTRYPOINT ["java", "-jar", "/simula-service.jar"]