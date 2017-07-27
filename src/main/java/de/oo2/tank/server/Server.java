package de.oo2.tank.server;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import de.oo2.tank.server.util.ResponseError;
import de.oo2.tank.server.util.ServerContext;
import de.oo2.tank.server.swagger.DocumentationRoutes;
import de.oo2.tank.server.measurement.MeasurementRoutes;
import de.oo2.tank.server.website.WebsiteRoutes;
import de.oo2.tank.server.website.MavenVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.oo2.tank.server.util.JsonUtil.toJson;
import static spark.Spark.*;

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
        // create the context
        ServerContext serverContext = new ServerContext();
        Configuration configuration = serverContext.getConfiguration();

        logger.info("Starting the server. Version: " + MavenVersion.getVersion());

        // set the server configuration
        port(configuration.getServerPort());

        // define the routes
        staticFiles.location("/public");

        new MeasurementRoutes(serverContext);
        new DocumentationRoutes(serverContext);
        new WebsiteRoutes(serverContext);

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