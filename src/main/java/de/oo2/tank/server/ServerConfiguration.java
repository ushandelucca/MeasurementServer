package de.oo2.tank.server;

import java.util.Map;

/**
 * This class provides the Server configuration.
 */
public final class ServerConfiguration {

    // keys for the environment variables
    private static final String ENV_SERVER_PORT = "TANK_SERVER_PORT";
    private static final String ENV_DATABASE_TYPE = "TANK_DATABASE_TYPE";
    private static final String ENV_DATABASE_HOST = "TANK_DATABASE_HOST";
    private static final String ENV_DATABASE_PORT = "TANK_DATABASE_PORT";
    private static final String ENV_DATABASE_NAME = "TANK_DATABASE_NAME";

    private static final String ENV_TANK_API_KEY = "TANK_API_KEY";

    private static final String ENV_GOOGLE_ANALYTICS_KEY = "GOOGLE_ANALYTICS_KEY";
    private static final String ENV_LOGGLY_KEY = "LOGGLY_KEY";

    // properties
    private Map<String, String> env = System.getenv();

    private Integer serverPort = null;

    private String dbType = null;
    private String dbHost = null;
    private Integer dbPort = null;
    private String dbName = null;

    private String tankApiKey = null;

    private String googleAnalyticsKey = null;
    private boolean isGoogleAnalyticsConfigured = false;

    private String logglyKey = null;
    private boolean isLogglyConfigured = false;

    /**
     * Returns the server port. Default: "8080".
     *
     * @return the port
     */
    public Integer getServerPort() {
        if (serverPort == null) {
            String portAsString = env.getOrDefault(ENV_SERVER_PORT, "8080");
            serverPort = Integer.parseInt(portAsString);
        }

        return serverPort;
    }

    /**
     * Returns the type of the db server. Default: "MongoDb".
     *
     * @return the hostname
     */
    public String getDbType() {
        if (dbType == null) {
            dbType = env.getOrDefault(ENV_DATABASE_TYPE, "MongoDb");
        }

        return dbType;
    }

    /**
     * Returns the hostname of the db server. Default: "localhost".
     *
     * @return the hostname
     */
    public String getDbHost() {
        if (dbHost == null) {
            dbHost = env.getOrDefault(ENV_DATABASE_HOST, "localhost");
        }

        return dbHost;
    }

    /**
     * Returns the port of the db server. Default: "27017".
     *
     * @return the port
     */
    public Integer getDbPort() {
        if (dbPort == null) {
            String portAsString = env.getOrDefault(ENV_DATABASE_PORT, "27017");
            dbPort = Integer.parseInt(portAsString);
        }

        return dbPort;
    }

    /**
     * Returns the name of the database. Default: "test".
     *
     * @return the name of the database
     */
    public String getDbName() {
        if (dbName == null) {
            dbName = env.getOrDefault(ENV_DATABASE_NAME, "test");
        }

        return dbName;
    }

    /**
     * Returns the key for the protected tank api operations. Default: "ABC123".
     *
     * @return the key
     */
    public String getTankApiKey() {
        if (tankApiKey == null) {
            tankApiKey = env.getOrDefault(ENV_TANK_API_KEY, "ABC123");
        }

        return tankApiKey;
    }

    /**
     * Returns the key for Google Analytics. Default: <code>null</code>. If the variable is not set then the return value is <code>null</code>.
     * In the case Google Analytics reporting is not active.
     *
     * @return the key
     */
    public String getGoogleAnalyticsKey() {
        if (!isGoogleAnalyticsConfigured) {
            googleAnalyticsKey = env.getOrDefault(ENV_GOOGLE_ANALYTICS_KEY, null);
            isGoogleAnalyticsConfigured = true;
        }

        return googleAnalyticsKey;
    }

    /**
     * Returns the key for Loggly. Default: <code>null</code>. If the variable is not set then the return value is <code>null</code>.
     * In the case Google Analytics reporting is not active.
     * https://www.loggly.com/docs/java-logback/
     *
     * @return the key
     */
    public String getLogglyKey() {
        if (!isLogglyConfigured) {
            logglyKey = env.getOrDefault(ENV_LOGGLY_KEY, null);
            isLogglyConfigured = true;
        }

        return logglyKey;
    }
}
