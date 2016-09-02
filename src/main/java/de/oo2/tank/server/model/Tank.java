package de.oo2.tank.server.model;

import de.oo2.tank.server.Configuration;
import de.oo2.tank.server.service.MeasurementService;
import de.oo2.tank.server.service.SwaggerService;
import de.oo2.tank.server.util.MavenUtil;

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

    public Tank() {
        configuration = new Configuration();

        version = MavenUtil.getVersion();
        hostname = "unknown";

        measurementService = new MeasurementService(configuration);
        swaggerService = new SwaggerService();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public MeasurementService getMeasurementService() {
        return measurementService;
    }

    public void setMeasurementService(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    public SwaggerService getSwaggerService() {
        return swaggerService;
    }

    public void setSwaggerService(SwaggerService swaggerService) {
        this.swaggerService = swaggerService;
    }
}
