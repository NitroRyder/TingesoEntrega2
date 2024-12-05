FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} documentos-service.jar
ENTRYPOINT ["java", "-jar", "/documentos-service.jar"]