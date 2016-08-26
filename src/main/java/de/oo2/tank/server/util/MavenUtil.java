package de.oo2.tank.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Maven utilities.
 */
public class MavenUtil {
    private static final Logger logger = LoggerFactory.getLogger(MavenUtil.class.getName());

    /**
     * Returns the version of the maven build. Currently this solution works only when the
     * server runs in the JAR file with all the dependencies.
     *
     * @return the version
     */
    public static String getVersion() {
        String path = "META-INF/maven/de.oo2a.tank/server/pom.properties";
        Properties prop = new Properties();
        InputStream in = ClassLoader.getSystemResourceAsStream(path);
        try {
            prop.load(in);
        } catch (Exception e) {
            logger.error(e.getMessage());

        } finally {
            try {
                in.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        return (String) prop.getOrDefault("version", "unknown");
    }

}
