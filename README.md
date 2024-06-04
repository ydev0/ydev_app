# ydev
ydev backend microservice, wrote in java with maven as build tool

---
clone the <a href="https://github.com/ydev0/ydev">Main Repo</a> ,<a href="https://github.com/ydev0/ydev_db"> and use the docker-compose.yml to run all of them

- `docker compose build`
- `docker compose up -d`

> it should be running on port 8080

you can check if it is working by running `docker compose ps`

## Testing
if you want to test this locally, run 

`mvn --batch-mode clean compile assembly:single`

`java -jar target/ydev-0.0.1-jar-with-dependencies.jar`
