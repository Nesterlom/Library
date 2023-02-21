#FROM eclipse-temurin:17-jdk-jammy

FROM openjdk:18
#COPY ./src/main/java/com/library/Application.java /tmp
#WORKDIR /tmp
#ENTRYPOINT ["java", "Application.java"]

#FROM openjdk:18
#VOLUME /tmp
#EXPOSE 8080
##ARG JAR_FILE=target/Library-1.0-SNAPSHOT.jar
#ARG JAR_FILE=./target/Library-1.0-SNAPSHOT.jar
#ADD ./target/Library-1.0-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]

ADD /target/Library-1.0-SNAPSHOT.jar backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]

#WORKDIR /app
#
#COPY pom.xml pom.xml
#
#RUN ./mvnw dependency:resolve
#
#COPY src ./src
#
#CMD ["./mvnw", "spring-boot:run"]