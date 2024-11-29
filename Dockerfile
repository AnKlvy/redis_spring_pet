FROM openjdk:17
WORKDIR /usr/src/app
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/redis_spring-0.0.1-SNAPSHOT.jar app.jar

#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]