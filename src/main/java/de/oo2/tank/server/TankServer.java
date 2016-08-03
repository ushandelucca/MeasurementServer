package de.oo2.tank.server;

import de.oo2.tank.server.dao.MeasurementDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static de.oo2.tank.server.Configurator.*;
import static spark.Spark.port;

/**
 * This is the main application class.
 */
public class TankServer {
    private static final Logger logger = LoggerFactory.getLogger(TankServer.class.getName());

    /**
     * main() Method as the application entry point
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        logger.info("Starting the server.");

        port(8080);

        // String dbNamne = (String) app.getProperties().getOrDefault(KEY_DATABASE_NAME, "test");
        MeasurementDao dao = new MeasurementDao("test", "docker.local", 21017);


        new TankController(new TemperatureService(dao));

        Map<String, String> env = System.getenv();
        env.getOrDefault(KEY_CLIENT_RESSOURCE_BASE, DEFAULT_CLIENT_RESSOURCE_BASE);
        env.getOrDefault(KEY_SERVER_PORT, DEFAULT_SERVER_PORT);
        env.getOrDefault(KEY_DATABASE_NAME, DEFAULT_DATABASE_NAME);
        env.getOrDefault(KEY_DATABASE_USER, DEFAULT_DATABASE_USER);
        env.getOrDefault(KEY_DATABASE_PASSWORD, DEFAULT_DATABASE_PASSWORD);
    }

    /**
     * - http://intercoolerjs.org
     * - https://github.com/jakerella/jquery-mockjax
     *
     * - http://www.mscharhag.com/java/building-rest-api-with-spark
     * - https://github.com/mscharhag/blog-examples/tree/master/sparkdemo/src
     * - https://srlk.github.io/posts/2016/swagger_sparkjava/
     * - https://github.com/cahtisroo/jschema-example/blob/master/src/main/java/org/jschema/sample/App.java
     *
     */

}