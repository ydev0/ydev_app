# ydev
ydev backend microservice, wrote in java with maven as build tool

---
clone the main repo and run 
- `docker compose build`
- `docker compose up -d`

> it should be running on port 8080

you can check if it is working by running `docker compose ps`

## Testing
if you want to test this locally, run 

`mvn --batch-mode clean compile assembly:single`
