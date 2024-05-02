FROM --platform=$BUILDPLATFORM maven:3.9.6-eclipse-temurin-11 AS build 
WORKDIR /workdir/server
COPY pom.xml /workdir/server/pom.xml
RUN mvn dependency:go-offline
COPY src /workdir/server/src
RUN mvn --batch-mode clean compile 

FROM build AS dev-envs

RUN apt-get update && \
    apt-get install -y --no-install-recommends git

RUN useradd -s /bin/bash -m vimuser && \
    groupadd docker && \
    usermod -aG docker vimuser

# install Docker tools (cli, buildx, compose)
COPY --from=gloursdocker/docker / /
CMD ["java", "-jar", "target/app.jar" ]

ARG JAR_FILE
COPY target/ydev-0.0.1-jar-with-dependencies.jar /target/ydev-0.0.1-jar-with-dependencies.jar
CMD java -jar /target/ydev-0.0.1-jar-with-dependencies.jar
EXPOSE 8080
