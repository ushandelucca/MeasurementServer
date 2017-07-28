package de.oo2.tank.server;

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

    private ServerConfiguration serverConfiguration = null;
    private SwaggerController swaggerController = null;


    // properties
    private String version = null;
    private String hostname = null;

    /**
     * Returns the serverConfiguration.
     *
     * @return the serverConfiguration
     */
    public ServerConfiguration getServerConfiguration() {
        if (serverConfiguration == null) {
            serverConfiguration = new ServerConfiguration();
        }
        return serverConfiguration;
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

}
