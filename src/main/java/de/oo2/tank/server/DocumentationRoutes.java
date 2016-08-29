package de.oo2.tank.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import static spark.Spark.get;

/**
 * This class adds the routes for the api documentation. The documentation ist available as:
 * - File:    http://localhost:8080/apidoc/swaggerfile.json
 * - Browser: http://localhost:8080/apidoc/swagger
 * <p>
 * https://srlk.github.io/posts/2016/swagger_sparkjava/
 * https://github.com/swagger-api/swagger-core/wiki/Annotations
 */
public class DocumentationRoutes {
    private static final Logger logger = LoggerFactory.getLogger(DocumentationRoutes.class.getName());
    private String swaggerJson = "";

    /**
     * Constructor.
     */
    public DocumentationRoutes() {
        FileWriter writer = null;

        try {
            // Build swagger json description
            swaggerJson = getSwaggerJson(MeasurementRoutes.class.getPackage().getName());

            // write the description as a file --> so it is available as external static file
            // TODO: put the tmp file location in the configuration class
            boolean success = new File(System.getProperty("java.io.tmpdir") + File.separator + "apidoc").mkdir();
            if (!success) {
                logger.error("Error while creating the directory for the 'swaggerfile.json'.");
            }

            File tmpExternalFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "apidoc", "swaggerfile.json");
            writer = new FileWriter(tmpExternalFile);
            writer.write(swaggerJson);
            writer.flush();

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        // description a route
        get("/apidoc/swagger", (req, res) -> {
            res.type("text/json");

            /*
            Optional: send a file to the browser
            res.header("Content-Disposition", "attachment; filename=\"swaggerfile.json\"");

            OutputStream outputStream = res.raw().getOutputStream();
            outputStream.write(swaggerJson.getBytes());
            outputStream.flush();
            outputStream.close();
            */

            return swaggerJson;
        });
    }

    /**
     * Returns the swagger json string.
     *
     * @param packageName the package
     * @return the swagger json
     * @throws JsonProcessingException
     */
    private static String getSwaggerJson(String packageName) throws JsonProcessingException {
        Swagger swagger = getSwagger(packageName);
        return swaggerToJson(swagger);
    }

    /**
     * Returns the swagger object.
     *
     * @param packageName the package
     * @return the swagger object
     */
    private static Swagger getSwagger(String packageName) {
        Reflections reflections = new Reflections(packageName);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage(packageName);
        beanConfig.setScan(true);
        beanConfig.scanAndRead();
        Swagger swagger = beanConfig.getSwagger();

        Reader reader = new Reader(swagger);

        Set<Class<?>> apiClasses = reflections.getTypesAnnotatedWith(Api.class);
        return reader.read(apiClasses);
    }

    /**
     * Returns the swagger json string.
     *
     * @param swagger the swagger object
     * @return the swagger json
     * @throws JsonProcessingException
     */
    private static String swaggerToJson(Swagger swagger) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String json = objectMapper.writeValueAsString(swagger);

        // Workaround for the missing "Security" in the @SwaggerDefinition annotation
        String securityDefinition = "\"securityDefinitions\": {\"tankauth\": {\"type\": \"apiKey\", \"in\": \"header\", \"name\": \"key\"}},";
        int beforePathSection = json.indexOf("\"paths\":");
        json = json.substring(0, beforePathSection) + securityDefinition + json.substring(beforePathSection, json.length());

        return json;
    }

}
