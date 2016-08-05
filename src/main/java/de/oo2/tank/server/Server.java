package de.oo2.tank.server;

import de.oo2.tank.server.dao.MeasurementDao;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static de.oo2.tank.server.Configurator.*;
import static de.oo2.tank.server.JsonUtil.toJson;
import static spark.Spark.*;

@SwaggerDefinition(// host = "localhost:8080", //
        info = @Info(description = "REST API for the tank in OO2a", //
                version = "V1.0", //
                title = "Tank API", //
                contact = @Contact(name = "ushandelucca", url = "https://github.com/ushandelucca/Server")), //
        schemes = {SwaggerDefinition.Scheme.HTTP /*, SwaggerDefinition.Scheme.HTTPS*/}, //
        consumes = {"application/json"}, //
        produces = {"application/json"}, //
        tags = {@Tag(name = "Description")})
/**
 * This is the main application class.
 */
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class.getName());

    /**
     * main() Method as the application entry point
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        logger.info("Starting the server.");

        Map<String, String> env = System.getenv();
        env.getOrDefault(KEY_CLIENT_RESSOURCE_BASE, DEFAULT_CLIENT_RESSOURCE_BASE);
        env.getOrDefault(KEY_SERVER_PORT, DEFAULT_SERVER_PORT);
        env.getOrDefault(KEY_DATABASE_NAME, DEFAULT_DATABASE_NAME);
        env.getOrDefault(KEY_DATABASE_USER, DEFAULT_DATABASE_USER);
        env.getOrDefault(KEY_DATABASE_PASSWORD, DEFAULT_DATABASE_PASSWORD);

        port(8080);

        staticFiles.location("/dist");

        // String dbNamne = (String) app.getProperties().getOrDefault(KEY_DATABASE_NAME, "test");
        // MeasurementDao dao = new MeasurementDao("test", "docker.local", 21017);
        MeasurementDao dao = new MeasurementDao("test", "localhost", 27017);

        new TemperatureRoutes(new TemperatureService(dao));


        try {
            // Build swagger json description
            final String swaggerJson = SwaggerParser.getSwaggerJson(TemperatureRoutes.class.getPackage().getName());
            get("/swagger", (req, res) -> {
                return swaggerJson;
            });

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        // after each route
        after((req, res) -> {
            res.type("application/json");
        });

        // handle the exceptions during routing
        exception(Exception.class, (e, req, res) -> {
            logger.error(e.getMessage(), e);
            res.status(400);
            res.type("application/json");
            res.body(toJson(new ResponseError("Error while processing the request!")));
        });


    }

    /**
     * - http://intercoolerjs.org
     * - https://github.com/jakerella/jquery-mockjax
     *
     * - http://www.mscharhag.com/java/building-rest-api-with-spark
     * - https://github.com/mscharhag/blog-examples/tree/master/sparkdemo/src
     *
     *
     * - https://github.com/fabiomaffioletti/jsondoc-samples/blob/master/jsondoc-sample-spark/src/main/java/org/jsondoc/sample/spark/controller/CityController.java
     * - https://srlk.github.io/posts/2016/swagger_sparkjava/
     *
     *
     * - https://github.com/cahtisroo/jschema-example/blob/master/src/main/java/org/jschema/sample/App.java
     *
     */

}