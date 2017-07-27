package de.oo2.tank.server.swagger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.oo2.tank.server.measurement.MeasurementRoutes;
import io.swagger.annotations.Api;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import org.reflections.Reflections;

import java.util.Set;

/**
 * This class provides the functionality for the API documentation with Swagger.io.
 * <p>
 * https://srlk.github.io/posts/2016/swagger_sparkjava/
 * https://github.com/swagger-api/swagger-core/wiki/Annotations
 */
public class SwaggerService {
    private String swaggerJson = null;

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
     * @throws JsonProcessingException in case of error
     */
    private static String swaggerToJson(Swagger swagger) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String json = objectMapper.writeValueAsString(swagger);

        // TODO: remove this workaround for @SwaggerDefinition in MeasurementRoutes
        String securityDefinition = "\"securityDefinitions\": {\"api_key\": {\"type\": \"apiKey\", \"in\": \"header\", \"name\": \"" + MeasurementRoutes.HEADER_API_KEY + "\"}},";
        int beforePathSection = json.indexOf("\"paths\":");
        json = json.substring(0, beforePathSection) + securityDefinition + json.substring(beforePathSection, json.length());
        return json;
    }

    /**
     * Returns the swagger json string.
     *
     * @return the swagger json
     * @throws JsonProcessingException in case of error
     */
    public String getSwaggerJson() throws JsonProcessingException {
        if (swaggerJson == null) {
            Swagger swagger = getSwagger(MeasurementRoutes.class.getPackage().getName());
            swaggerJson = swaggerToJson(swagger);
        }

        return swaggerJson;
    }

}
