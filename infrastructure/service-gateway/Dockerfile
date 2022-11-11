FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} ./app.jar

ENTRYPOINT java -jar -Dspring.profiles.active=production app.jar
