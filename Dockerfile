##
## Build stage
##
#FROM maven:3.6.0-jdk-17-slim AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#
##
## Package stage
## test
#FROM openjdk:17-jre-slim
#COPY --from=build /home/app/target/Freelancer-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]

# Use latest openjdk image as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the necessary files to the container
COPY pom.xml .
COPY src ./src
RUN ./mvn clean package
RUN jar:jar -f pom.xml

# Expose port 8080
EXPOSE 8080

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "target/*.jar"]