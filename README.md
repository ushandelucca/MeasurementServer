# MeasurementServer
[![Java CI](https://github.com/ushandelucca/MeasurementServer/actions/workflows/build.yml/badge.svg)](https://github.com/ushandelucca/MeasurementServer/actions/workflows/build.yml) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/010fc94a6e1e4dc79d1b356f088555bd)](https://www.codacy.com/app/ushandelucca/MeasurementServer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ushandelucca/MeasurementServer&amp;utm_campaign=Badge_Grade) [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/010fc94a6e1e4dc79d1b356f088555bd)](https://www.codacy.com/app/ushandelucca/MeasurementServer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ushandelucca/MeasurementServer&amp;utm_campaign=Badge_Coverage) [![www.oo2a.de](https://img.shields.io/website-up-down-green-red/http/www.oo2a.de.svg)](https://www.oo2a.de)

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
Build a docker image an tag it (e. g. oo2a//measurement-server):
```bash
$ docker build . -t oo2a//measurement-server
```
Start this image with a linked mongo database:
```bash
$ docker run --name MeasurementServer --link a-mongodb-container:mongodb oo2a//measurement-server
```
Or use it with a indication of a mongo db host
```bash
$ docker run --name MeasurementServer -e "TANK_DATABASE_HOST=a-mongodb-host" oo2a//measurement-server
```