package de.oo2.tank.server.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.oo2.tank.server.model.ResponseError;
import de.oo2.tank.server.model.ServerContext;
import de.oo2.tank.server.service.SwaggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

/**
 * This class adds the route for the api documentation.
 */
public class DocumentationRoutes {
    private static final Logger logger = LoggerFactory.getLogger(DocumentationRoutes.class.getName());

    /**
     * Constructor.
     *
     * @param serverContext the server context
     */
    public DocumentationRoutes(ServerContext serverContext) {

        SwaggerService swaggerService = serverContext.getSwaggerService();

        // description a route
        get("/apidoc/swagger", (req, res) -> {
            res.type("text/json");

            try {
                return swaggerService.getSwaggerJson();

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
