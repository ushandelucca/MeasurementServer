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

    private static final String UNKNOWN = "unknown";
    private static final String PATH = "META-INF/maven/de.oo2a.tank/server/pom.properties";

    /**
     * Returns the version of the maven build. Currently this solution works only when the
     * server runs in the JAR file with all the dependencies.
     *
     * @return the version
     */
    public static String getVersion() {
        Properties prop = new Properties();
        InputStream in = ClassLoader.getSystemResourceAsStream(PATH);
        try {
            if (in != null) {
                prop.load(in);
            } else {
                logger.error("Error while initialising the input stream.");
                return UNKNOWN;
            }
        } catch (Exception e) {
            logger.error("Error while loading the version properties.", e);

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                logger.error("Error while closing the input stream.", e);
            }
        }

        return (String) prop.getOrDefault("version", UNKNOWN);
    }

}
