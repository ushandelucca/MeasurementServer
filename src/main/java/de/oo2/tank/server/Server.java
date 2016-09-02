package de.oo2.tank.server;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import de.oo2.tank.server.model.ResponseError;
import de.oo2.tank.server.model.Tank;
import de.oo2.tank.server.route.DocumentationRoutes;
import de.oo2.tank.server.route.MeasurementRoutes;
import de.oo2.tank.server.route.WebsiteRoutes;
import de.oo2.tank.server.util.MavenUtil;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.oo2.tank.server.util.JsonUtil.toJson;
import static spark.Spark.*;

/**
 * This is the main application class.
 */
@SwaggerDefinition(host = "www.oo2a.de",
        info = @Info(description = "REST API for the tank in OO2a",
                version = "V1.0",
                title = "Tank measurement API",
                contact = @Contact(name = "ushandelucca", url = "https://github.com/ushandelucca/TankServer")),
        schemes = {SwaggerDefinition.Scheme.HTTPS /*, SwaggerDefinition.Scheme.HTTP*/},
        consumes = {"application/json"},
        produces = {"application/json"},
        tags = {@Tag(name = "Description")})
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class.getName());

    /**
     * Main method as the application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // create the model
        Tank tank = new Tank();
        Configuration configuration = tank.getConfiguration();

        logger.info("Starting the server. Version: " + MavenUtil.getVersion());

        // set the server configuration
        port(configuration.getServerPort());

        // define the routes
        staticFiles.location("/public");

        new MeasurementRoutes(tank);
        new DocumentationRoutes(tank);
        new WebsiteRoutes(tank);

        // TODO: Move the routes in separate class
        // after each route
        after((req, res) -> {

            // enable Google Analytics
            if (configuration.getGoogleAnalyticsKey() != null) {
                GoogleAnalytics ga = new GoogleAnalytics(configuration.getGoogleAnalyticsKey());
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

}