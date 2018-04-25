package de.oo2.m.server;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
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

        // TODO: add loggly configuration
        // https://stackoverflow.com/questions/47299109/programatically-add-appender-in-logback-slf4j
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        ple.setContext(lc);
        ple.start();
        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
        String logFile = "new.log";
        fileAppender.setFile(logFile);
        fileAppender.setEncoder(ple);
        fileAppender.setContext(lc);
        fileAppender.start();

        ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Server.class);
        logbackLogger.addAppender(fileAppender);
        logbackLogger.setLevel(Level.DEBUG);
        logbackLogger.setAdditive(false);

        logbackLogger = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logbackLogger.addAppender(fileAppender);

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