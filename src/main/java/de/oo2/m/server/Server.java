package de.oo2.m.server;

import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.PageViewHit;
import de.oo2.m.server.measurement.MeasurementRoutes;
import de.oo2.m.server.swagger.SwaggerRoutes;
import de.oo2.m.server.util.JsonUtil;
import de.oo2.m.server.util.ResponseError;
import de.oo2.m.server.website.MavenVersion;
import de.oo2.m.server.website.WebsiteRoutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        ServerConfiguration serverConfiguration = serverContext.getServerConfiguration();

        logger.info("Starting the server. Version: " + MavenVersion.getVersion());

        // set the server serverConfiguration
        port(serverConfiguration.getServerPort());

        // define the routes
        staticFiles.location("/public");

        new MeasurementRoutes(serverContext);
        new SwaggerRoutes();
        new WebsiteRoutes(serverContext);

        // after each route
        after((req, res) -> {

            // enable Google Analytics
            if (serverConfiguration.getGoogleAnalyticsKey() != null) {
                GoogleAnalytics ga = new GoogleAnalytics(serverConfiguration.getGoogleAnalyticsKey());
                ga.postAsync(new PageViewHit(req.url(), req.requestMethod()));
            }

        });

        // handle the exceptions during routing
        exception(Exception.class, (e, req, res) -> {
            logger.error(e.getMessage(), e);
            res.status(400);
            res.type("application/json");
            res.body(JsonUtil.toJson(new ResponseError("Error while processing the request!")));
        });

    }

}