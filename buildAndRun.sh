#!/bin/sh
mvn clean package && docker build -t pwblog/pwblog .
docker rm -f pwblog || true && docker run -d -p 8080:8080 -p 4848:4848 --name pwblog pwblog/pwblog 
