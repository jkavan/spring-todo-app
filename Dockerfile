FROM maven:3.8.6 as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src/
RUN mvn package

FROM openjdk:17-alpine
WORKDIR /app
ARG JAR_FILE=TodoApp.jar
COPY --from=build /build/target/${JAR_FILE} /app/
ENTRYPOINT ["java","-jar","TodoApp.jar"]
