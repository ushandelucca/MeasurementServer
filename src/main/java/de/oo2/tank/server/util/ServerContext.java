package de.oo2.tank.server.util;

import de.oo2.tank.server.Configuration;
import de.oo2.tank.server.measurement.MeasurementController;
import de.oo2.tank.server.swagger.SwaggerController;
import de.oo2.tank.server.website.MavenVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class is the main model for the server.
 */
public class ServerContext {
    private static final Logger logger = LoggerFactory.getLogger(ServerContext.class.getName());


    // configuration
    private Configuration configuration = null;

    // properties
    private String version = null;
    private String hostname = null;

    /**
     * Returns the configuration.
     *
     * @return the configuration
     */
    public Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public String getVersion() {
        if (version == null) {
            version = MavenVersion.getVersion();
        }
        return version;
    }

    /**
     * Returns the hostname.
     *
     * @return the hostname
     */
    public String getHostname() {
        if (hostname == null) {
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                logger.error(e.getMessage(), e); // NOSONAR
                hostname = "unknown";  // NOSONAR
            }
        }
        return hostname;
    }

    /**
     * Returns the measurement service.
     *
     * @return the measurement service
     */
    // TODO: remove
    public MeasurementController getMeasurementService() {
        return new MeasurementController(getConfiguration());
    }

    /**
     * Returns the Swagger service.
     *
     * @return the Swagger service
     */
    // TODO: remove
    public SwaggerController getSwaggerService() {
        return new SwaggerController();
    }
}
