package de.oo2.tank.server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.Map;

import static de.oo2.tank.server.Configurator.*;

/**
 * This class provides the configuration for the TankServer.
*/
@ApplicationPath("/webapi")
public class TankApplication extends Application  {

    private Map<String, Object> propertyMap = null;

    /**
     * Constructor
     */
    public TankApplication() {
    }

    /**
     * Returns the environment variables.
     *
     * @return the environment variables
     */
    private Map<String, String> getConfiguration() {
        return System.getenv();
    }

    @Override
    public Map<String, Object> getProperties() {

        if (propertyMap == null) {

            propertyMap = new HashMap<String, Object>();
            Map<String, String> env = System.getenv();

            env.getOrDefault(KEY_CLIENT_RESSOURCE_BASE, DEFAULT_CLIENT_RESSOURCE_BASE);
            env.getOrDefault(KEY_SERVER_PORT, DEFAULT_SERVER_PORT);
            env.getOrDefault(KEY_DATABASE_NAME, DEFAULT_DATABASE_NAME);
            env.getOrDefault(KEY_DATABASE_USER, DEFAULT_DATABASE_USER);
            env.getOrDefault(KEY_DATABASE_PASSWORD, DEFAULT_DATABASE_PASSWORD);
        }

        return propertyMap;
     }

}
