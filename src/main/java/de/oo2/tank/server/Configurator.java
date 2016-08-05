package de.oo2.tank.server;

/**
 * This class provides the Server configuration.
 */
public final class Configurator {

    // keys for the environment variables
    public static final String KEY_CLIENT_RESSOURCE_BASE = "TANK_CLIENT_RESSOURCE_BASE";
    public static final String KEY_SERVER_PORT = "TANK_SERVER_PORT";

    public static final String KEY_DATABASE_NAME = "TANK_DATABASE_NAME";
    public static final String KEY_DATABASE_USER = "TANK_DATABASE_USER";
    public static final String KEY_DATABASE_PASSWORD = "TANK_DATABASE_PASSWORD";

    // default values of the environment variables
    public static final String DEFAULT_CLIENT_RESSOURCE_BASE = "WebClient/index.html";
    public static final String DEFAULT_SERVER_PORT = "8080";
    public static final String DEFAULT_DATABASE_NAME = "development";
    public static final String DEFAULT_DATABASE_USER = "dev";
    public static final String DEFAULT_DATABASE_PASSWORD = "dev";

}
