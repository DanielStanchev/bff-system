FROM amazoncorretto:21-alpine

WORKDIR /app

COPY rest/target/rest-0.0.1-SNAPSHOT.jar /app/bff.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/bff.jar"]


#docker file for image creation