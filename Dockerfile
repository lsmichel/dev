FROM openjdk:latest
WORKDIR /
ADD target/cardManager-1.0-SNAPSHOT.jar cardManager.jar
ADD target/dependency-jars dependency-jars
EXPOSE 8080
CMD java -jar cardManager.jar
