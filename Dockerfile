
#Freelancer-0.0.1-SNAPSHOT.jar
# Use latest openjdk image as the base image
FROM adoptopenjdk/openjdk17
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -f pom.xml
#RUN mvn clean install

# Expose port 8080
EXPOSE 8080

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "target/Freelancer-0.0.1-SNAPSHOT.jar"]


#FROM openjdk:17-jdk-alpine
#RUN apk add --no-cache maven
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean install
## Compile the application
#RUN javac -d ./bin ./src/main/java/src/main.java
#
## Expose port 8080
#EXPOSE 8080
#
## Set the entrypoint to run the application
#ENTRYPOINT ["java", "-cp", "./bin", "src.main"]