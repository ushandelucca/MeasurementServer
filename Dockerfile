FROM java:8

ENV TANK_SERVER_PORT=80
    TANK_DATABASE_HOST=mongodb
    TANK_DATABASE_PORT=27017
    TANK_DATABASE_NAME=test
    TANK_API_KEY=1234567

# Adding fat jar
ADD ./target/server_1.0-SNAPSHOT-jar-with-dependencies.jar server_1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 80

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "server_1.0-SNAPSHOT-jar-with-dependencies.jar"]

# docker build . -t oo2a/tankserver
# Use this image with a linked mongo database
# docker run --name tankserver --link a-mongodb-container:mongodb oo2a/tankserver
# Or use it with a indication to mongo db host
# docker run --name tankserver -e "TANK_DATABASE_HOST=a-mongodb-host" oo2a/tankserver
