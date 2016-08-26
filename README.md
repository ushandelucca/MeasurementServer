# TankServer
[![Build Status](https://travis-ci.org/ushandelucca/TankServer.png?branch=master)](https://travis-ci.org/ushandelucca/TankServer) [![Coverage Status](https://coveralls.io/repos/github/ushandelucca/TankServer/badge.svg?branch=master)](https://coveralls.io/github/ushandelucca/TankServer?branch=master) [SonarQube](https://sonarqube.com/overview?id=de.oo2a.tank%3Aserver)

Server for the level measurements of a water tank. [Demo](http://www.oo2a.de).

## Features
* Website
* REST API

## Installation
* Clone the source code
* Build the server with [Maven](http://maven.apache.org)

## Usage with [Docker](https://docs.docker.com/)
Build a docker image an tag it (e. g. oo2a/tankserver):
```bash
docker build . -t oo2a/tankserver
```
Start this image with a linked mongo database:
```bash
docker run --name tankserver --link a-mongodb-container:mongodb oo2a/tankserver
```
Or use it with a indication of a mongo db host
```bash
docker run --name tankserver -e "TANK_DATABASE_HOST=a-mongodb-host" oo2a/tankserver
```
