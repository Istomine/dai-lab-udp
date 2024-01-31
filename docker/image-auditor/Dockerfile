FROM openjdk:21-slim
WORKDIR /app
COPY src/target/auditorApp-1.0-jar-with-dependencies.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
