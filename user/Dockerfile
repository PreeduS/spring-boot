# Build
FROM maven:3.6.3-jdk-14 as build

COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN mvn clean install


# Release
FROM openjdk:14-jdk-alpine as release

ARG JAR_FILE=target/*.jar

COPY --from=build ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]



# $ docker build --build-arg JAR_FILE=path/*.jar -t <tag> .