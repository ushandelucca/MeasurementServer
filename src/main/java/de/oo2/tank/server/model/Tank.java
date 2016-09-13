package de.oo2.tank.server.model;

import de.oo2.tank.server.Configuration;
import de.oo2.tank.server.service.MeasurementService;
import de.oo2.tank.server.service.SwaggerService;
import de.oo2.tank.server.util.MavenUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class is the main model for the application.
 */
public class Tank {

    // configuration
    private Configuration configuration = null;

    // properties
    private String version = null;
    private String hostname = null;

    // services
    private MeasurementService measurementService = null;
    private SwaggerService swaggerService = null;

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
            version = MavenUtil.getVersion();
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
    public MeasurementService getMeasurementService() {
        if (measurementService == null) {
            measurementService = new MeasurementService(getConfiguration());
        }
        return measurementService;
    }

    /**
     * Returns the Swagger service.
     *
     * @return the Swagger service
     */
    public SwaggerService getSwaggerService() {
        if (swaggerService == null) {
            swaggerService = new SwaggerService();
        }
        return swaggerService;
    }
}
