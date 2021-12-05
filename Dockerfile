FROM openjdk/openjdk:16-slim

RUN apk add --update tini

# setting the variables
ENV TANK_SERVER_PORT=80
ENV TANK_DATABASE_HOST=mongodb
ENV TANK_DATABASE_PORT=27017
ENV TANK_DATABASE_NAME=test
ENV TANK_API_KEY=ABC123

# Adding the server as a fat jar
ADD ./target/measurementserver-jar-with-dependencies.jar /
EXPOSE 80

ENTRYPOINT ["tini", "--"]

# start the server
CMD ["java", "-jar", "measurementserver-jar-with-dependencies.jar"]
