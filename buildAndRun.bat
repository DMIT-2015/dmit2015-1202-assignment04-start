@echo off
call mvn clean package
call docker build -t dmit2015/dmit2015-assignment04-start .
call docker rm -f dmit2015-assignment04-start
call docker run -d -p 8080:8080 -p 8443:8443 --name dmit2015-assignment04-start dmit2015/dmit2015-assignment04-start