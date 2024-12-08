FROM openjdk:17-jdk-slim

WORKDIR /usr/src/app
CMD ["./gradlew", "clean", "build"]
COPY build/libs/redis_spring-0.0.1-SNAPSHOT.jar app.jar

#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]