FROM openjdk:21-jre 
RUN mkdir app 
COPY pom.xml /workdir/server/pom.xml
RUN mvn dependency:go-offline
COPY src /workdir/server/src
RUN mvn --batch-mode clean compile 
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/ydev-0.0.1-jar-with-dependencies.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "/target/ydev-0.0.1-jar-with-dependencies.jar"]
