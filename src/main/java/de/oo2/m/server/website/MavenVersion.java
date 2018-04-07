package de.oo2.m.server.website;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper for the Maven version.
 */
public class MavenVersion {
    private static final Logger logger = LoggerFactory.getLogger(MavenVersion.class.getName());

    private static final String UNKNOWN = "unknown";
    private static final String PATH = "META-INF/maven/de.oo2a.tank/server/pom.properties";

    /**
     * Constructor.
     */
    private MavenVersion() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Returns the version of the maven build. Currently this solution works only when the
     * server runs in the JAR file with all the dependencies.
     *
     * @return the version
     */
    public static String getVersion() {
        Properties prop = new Properties();

        // Resource will be released automatically
        try (InputStream in = ClassLoader.getSystemResourceAsStream(PATH)) {

            if (in == null) {
                return UNKNOWN;
            }

            prop.load(in);

        } catch (IOException e) {
            logger.error("Error while opening the input stream.", e);
        }

        return (String) prop.getOrDefault("version", UNKNOWN);
    }

}
