FROM openjdk:11-jdk
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8090
RUN apt-get update && apt-get -y install gcc
CMD java -jar /app/app.jar