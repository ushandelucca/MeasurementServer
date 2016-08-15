package de.oo2.tank.server;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import de.oo2.tank.server.model.ResponseError;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.oo2.tank.server.util.JsonUtil.toJson;
import static spark.Spark.*;

@SwaggerDefinition(// host = "localhost:8080", //
        info = @Info(description = "REST API for the tank in OO2a", //
                version = "V1.0", //
                title = "Tank API", //
                contact = @Contact(name = "ushandelucca", url = "https://github.com/ushandelucca/TankServer")), //
        schemes = {SwaggerDefinition.Scheme.HTTPS}, //
        consumes = {"application/json"}, //
        produces = {"application/json"}, //
        tags = {@Tag(name = "Description")})
/**
 * This is the main application class.
 */
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class.getName());

    /**
     * Main method as the application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Configurator config = new Configurator();

        logger.info("Starting the server.");

        port(config.getServerPort());

        staticFiles.location("/public");

        new TemperatureRoutes(new TemperatureService(config));
        new DocumentationRoutes();

        // after each route
        after((req, res) -> {

            //
            if (config.getGoogleAnalyticsKey() != null) {
                GoogleAnalytics ga = new GoogleAnalytics(config.getGoogleAnalyticsKey());
                ga.postAsync(new PageViewHit(req.url(), req.requestMethod()));
            }

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
     * - https://github.com/rajasegar/JADE-Bootstrap
     *
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