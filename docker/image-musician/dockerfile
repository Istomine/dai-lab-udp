FROM openjdk:21-slim
WORKDIR /app
COPY src/target/musicianApp-1.0-jar-with-dependencies.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
