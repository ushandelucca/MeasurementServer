# TankServer
[![Build Status](https://travis-ci.org/ushandelucca/TankServer.png?branch=master)](https://travis-ci.org/ushandelucca/TankServer) [![Coveralls Status](https://coveralls.io/repos/github/ushandelucca/TankServer/badge.svg?branch=master)](https://coveralls.io/github/ushandelucca/TankServer?branch=master) [![SonarQube tech debt](https://img.shields.io/sonar/https/sonarqube.com/de.oo2a.tank%3Aserver/tech_debt.svg)](https://sonarqube.com/overview?id=de.oo2a.tank%3Aserver) [![Dependency Status](https://dependencyci.com/github/ushandelucca/TankServer/badge)](https://dependencyci.com/github/ushandelucca/TankServer) [![www.oo2a.de](https://img.shields.io/website-up-down-green-red/http/www.oo2a.de.svg)](https://www.oo2a.de)

This small REST server is used to store measurements of a water tank. Moreover, it has also the capabilities to evaluate measurements and represent with nice reports. 

## Features
* REST API to store and retrieve the measurements
* Various sensors and measurements can be stored
* automatic API documentation with [Swagger](http://swagger.io/)
* Website for measurement reporting is included ([Demo](https://www.oo2a.de))

## Installation
* Clone the source code
* Build it with [Maven](http://maven.apache.org)
* Start a local [MongoDB](https://www.mongodb.com) on port 27017
* Start the server in ```Server.java```
* Navigate to ```localhost:8080``` or ```http://localhost:8080/swagger-ui```

## Usage with Docker
Build a docker image an tag it (e. g. oo2a/tankserver):
```bash
$ docker build . -t oo2a/tankserver
```    
Start this image with a linked mongo database:
```bash
$ docker run --name tankserver --link a-mongodb-container:mongodb oo2a/tankserver
```
Or use it with a indication of a mongo db host
```bash
$ docker run --name tankserver -e "TANK_DATABASE_HOST=a-mongodb-host" oo2a/tankserver
```