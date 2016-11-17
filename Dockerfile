FROM java:8

# setting the variables
ENV TANK_SERVER_PORT=80
ENV TANK_DATABASE_HOST=mongodb
ENV TANK_DATABASE_PORT=27017
ENV TANK_DATABASE_NAME=test
ENV TANK_API_KEY=ABC123

# Adding the server as a fat jar
ADD ./target/server-jar-with-dependencies.jar server-jar-with-dependencies.jar
EXPOSE 80

# start the server
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "server-jar-with-dependencies.jar"]