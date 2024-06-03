FROM maven:3.9.6-eclipse-temurin-22 AS build 
WORKDIR /workdir/server

COPY pom.xml /workdir/server/pom.xml

RUN mvn dependency:go-offline

COPY src /workdir/server/src

  RUN mvn --batch-mode clean compile assembly:single

FROM build AS dev-envs

RUN apt-get update && \
    apt-get install -y --no-install-recommends git

RUN useradd -s /bin/bash -m vimuser && \
    groupadd docker && \
    usermod -aG docker vimuser

ENV JAR_FILE=${JAR_FILE} 

COPY --from=build /workdir/server/target/${JAR_FILE} /${JAR_FILE}

CMD java -jar /${JAR_FILE}

EXPOSE 8080

