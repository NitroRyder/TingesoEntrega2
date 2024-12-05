FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} evalua-service.jar
ENTRYPOINT ["java", "-jar", "/evalua-service.jar"]