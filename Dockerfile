FROM adoptopenjdk/openjdk15:alpine-jre

RUN apk add --update tini

# setting the variables
ENV TANK_SERVER_PORT=80
ENV TANK_DATABASE_HOST=mongodb
ENV TANK_DATABASE_PORT=27017
ENV TANK_DATABASE_NAME=test
ENV TANK_API_KEY=ABC123

# Adding the server as a fat jar
ADD ./target/server-jar-with-dependencies.jar /
EXPOSE 80

ENTRYPOINT ["tini", "--"]

# start the server
CMD ["java", "-jar", "server-jar-with-dependencies.jar"]