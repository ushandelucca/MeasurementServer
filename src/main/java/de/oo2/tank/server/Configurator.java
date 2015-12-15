package de.oo2.tank.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * This class provides the TankServer configuration.
 */
public final class Configurator {
    // logger
    private static final Logger logger = LoggerFactory.getLogger(Configurator.class.getName());

    // keys for the environment variables
    private static final String KEY_CLIENT_RESSOURCE_BASE = "TANK_CLIENT_RESSOURCE_BASE";
    private static final String KEY_SERVER_PORT = "TANK_SERVER_PORT";

    private static final String KEY_DATABASE_NAME = "TANK_DATABASE_NAME";
    private static final String KEY_DATABASE_USER = "TANK_DATABASE_USER";
    private static final String KEY_DATABASE_PASSWORD = "TANK_DATABASE_PASSWORD";

    // default values of the environment variables
    private static final String DEFAULT_CLIENT_RESSOURCE_BASE = "WebClient/index.html";
    private static final String DEFAULT_SERVER_PORT = "8080";
    private static final String DEFAULT_DATABASE_NAME = "development";
    private static final String DEFAULT_DATABASE_USER = "dev";
    private static final String DEFAULT_DATABASE_PASSWORD = "dev";


    /**
     * Returns the environment variables.
     *
     * @return the environment variables
     */
    private Map<String, String> getConfiguration() {
        return System.getenv();
    }

    /**
     * Return the path to the static web client.
     *
     * @return the path to the static web client
     */
    public String getResourceBase() {
        return getConfiguration().getOrDefault(KEY_CLIENT_RESSOURCE_BASE, DEFAULT_CLIENT_RESSOURCE_BASE);
    }

    /**
     * Returns the server port.
     *
     * @return the port
     */
    public String getServerPort() {
        return getConfiguration().getOrDefault(KEY_SERVER_PORT, DEFAULT_SERVER_PORT);
    }

    /**
     * Returns the database name. E. g. "test" or "prod".
     *
     * @return the database name
     */
    public String getDatabaseName() {
        return getConfiguration().getOrDefault(KEY_DATABASE_NAME, DEFAULT_DATABASE_NAME);
    }

    /**
     * Returns the database user.
     *
     * @return the database user
     */
    public String getDatabaseUser() {
        return getConfiguration().getOrDefault(KEY_DATABASE_USER, DEFAULT_DATABASE_USER);
    }

    /**
     * Returns the database password for the given user.
     *
     * @return the database password
     */
    public String getDatabasePassword() {
        return getConfiguration().getOrDefault(KEY_DATABASE_PASSWORD, DEFAULT_DATABASE_PASSWORD);
    }
}
