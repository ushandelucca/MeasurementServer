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

import java.util.Set;

import static spark.Spark.get;

/**
 * This class adds the routes for the temperature service and handles the REST requests an responses.
 * http://localhost:8080/swagger
 * https://srlk.github.io/posts/2016/swagger_sparkjava/
 * https://github.com/swagger-api/swagger-core/wiki/Annotations
 */
public class DocumentationRoutes {
    private static final Logger logger = LoggerFactory.getLogger(DocumentationRoutes.class.getName());
    private String swaggerJson = "";

    public DocumentationRoutes() {

        try {
            // Build swagger json description
            swaggerJson = getSwaggerJson(TemperatureRoutes.class.getPackage().getName());
            get("/swagger", (req, res) -> {
                return swaggerJson;
            });

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        get("/apidoc/swagger", (req, res) -> {
            return swaggerJson;
        });
    }

    public static String getSwaggerJson(String packageName) throws JsonProcessingException {
        Swagger swagger = getSwagger(packageName);
        String json = swaggerToJson(swagger);
        return json;
    }

    public static Swagger getSwagger(String packageName) {
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

    public static String swaggerToJson(Swagger swagger) throws JsonProcessingException {
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
