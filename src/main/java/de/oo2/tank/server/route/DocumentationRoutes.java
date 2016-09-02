package de.oo2.tank.server.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.oo2.tank.server.model.ResponseError;
import de.oo2.tank.server.model.Tank;
import de.oo2.tank.server.service.SwaggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

/**
 * This class adds the route for the api documentation.
 */
public class DocumentationRoutes {
    private static final Logger logger = LoggerFactory.getLogger(DocumentationRoutes.class.getName());
    private SwaggerService swaggerService = null;

    /**
     * Constructor.
     *
     * @param tank the tank model
     */
    public DocumentationRoutes(Tank tank) {

        swaggerService = tank.getSwaggerService();

        // description a route
        get("/apidoc/swagger", (req, res) -> {
            res.type("text/json");

            try {
                String swaggerJson = swaggerService.getSwaggerJson();
                return swaggerJson;

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
