package de.oo2.m.server.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.oo2.m.server.util.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

/**
 * This class adds the route for the api documentation.
 */
public class SwaggerRoutes {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerRoutes.class.getName());

    /**
     * Constructor.
     */
    public SwaggerRoutes() {

        SwaggerController swaggerController = new SwaggerController();

        // description a route
        get("/apidoc/swagger", (req, res) -> {
            res.type("text/json");

            try {
                return swaggerController.getSwaggerJson();

            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);

                res.status(400);
                return new ResponseError(e.getMessage());
            }

            /*
            Optional: send a file to the browser
            res.header("Content-Disposition", "attachment; filename=\"swaggerfile.json\"");

            OutputStream outputStream = res.raw().getOutputStream();
            outputStream.write(swaggerJson.getBytes());
            outputStream.flush();
            outputStream.close();
            */
        });
    }
}
