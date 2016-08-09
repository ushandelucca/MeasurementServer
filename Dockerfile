FROM java:8

# Adding fat jar
ADD ./target/server_1.0-SNAPSHOT-jar-with-dependencies.jar server_1.0-SNAPSHOT-jar-with-dependencies.jar

EXPOSE 80
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "server_1.0-SNAPSHOT-jar-with-dependencies.jar"]

# docker build . -t oo2a/tankserver
# docker run -p tankserver