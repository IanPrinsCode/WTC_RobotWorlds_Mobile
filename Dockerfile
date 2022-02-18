FROM maven:3.8.2-jdk-11-slim
MAINTAINER tech-team <tech-team@wethinkcode.co.za>

RUN apt-get update && apt-get install -y make

COPY .libs/server-1.2.0-jar-with-dependencies.jar /

WORKDIR /
EXPOSE 5050 5051

ENTRYPOINT ["java", "-jar", "server-1.2.0-jar-with-dependencies.jar"]

