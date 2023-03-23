FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Srping_EC.jar
ENTRYPOINT ["java","-jar","/Srping_EC.jar"]