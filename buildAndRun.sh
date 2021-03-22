#!/bin/sh
if [ $(docker ps -a -f name=dmit2015-assignment04-start | grep -w dmit2015-assignment04-start | wc -l) -eq 1 ]; then
  docker rm -f dmit2015-assignment04-start
fi
mvn clean package && docker build -t dmit2015/dmit2015-assignment04-start .
docker run -d -p 8080:8080 -p 8443:8443 --name dmit2015-assignment04-start dmit2015/dmit2015-assignment04-start
