FROM --platform=$BUILDPLATFORM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /workdir/server
COPY pom.xml /workdir/server/pom.xml
RUN mvn dependency:go-offline
COPY src /workdir/server/src
RUN mvn --batch-mode clean compile 

FROM build AS dev-envs

RUN apt-get update \
    && apt-get install -y --no-install-recommends git

RUN useradd -s /bin/bash -m vimuser\
    && groupadd docker\
    && usermod -aG docker vimuser

COPY --from=gloursdocker/docker / /
CMD ["java", "-jar", "target/ydev-0.0.1-jar-with-dependencies.jar" ]

FROM eclipse-temurin:17-jre-focal
ARG DEPENDENCY=/workdir/server/target
EXPOSE 8080
COPY --from=build ${DEPENDENCY}/ydev-0.0.1-jar-with-dependencies.jar /.ydev-0.0.1-jar-with-dependenciesjar
CMD ["java", "-jar", "/ydev-0.0.1-jar-with-dependencies.jar"]
