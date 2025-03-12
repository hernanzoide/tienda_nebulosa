FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .

RUN gradle --no-daemon dependencies

COPY src /app/src

RUN gradle --no-daemon build

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/merchant-transactions-*.jar /app/merchant-transactions.jar

EXPOSE 8080
EXPOSE 8083
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8083", "-jar", "/app/merchant-transactions.jar"]
