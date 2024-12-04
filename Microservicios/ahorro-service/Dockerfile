FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} ahorro-service.jar
ENTRYPOINT ["java", "-jar", "/ahorro-service.jar"]