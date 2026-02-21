FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY app/ .

RUN ./gradlew shadowJar --no-daemon

EXPOSE 7070

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar"]

