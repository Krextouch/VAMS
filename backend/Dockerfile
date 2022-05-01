FROM eclipse-temurin:17
EXPOSE 8443
COPY build/libs/*.jar /app/app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]