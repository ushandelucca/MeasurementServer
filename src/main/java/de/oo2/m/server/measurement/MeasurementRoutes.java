package de.oo2.m.server.measurement;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import de.oo2.m.server.util.ResponseError;
import de.oo2.m.server.ServerContext;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import javax.ws.rs.*;
import java.util.Map;

import static de.oo2.m.server.util.JsonUtil.json;
import static spark.Spark.*;

/**
 * This class adds the route for the measurement service and handles the REST requests an responses.
 */
@SwaggerDefinition(host = "www.oo2a.de",
        info = @Info(description = "REST API for storing and reporting the measurements of a rainwater tank.",
                version = "V1.0",
                title = "Measurement API",
                contact = @Contact(name = "ushandelucca", url = "https://github.com/ushandelucca/MeasurementServer")),
        schemes = {SwaggerDefinition.Scheme.HTTPS /*, SwaggerDefinition.Scheme.HTTP*/},
        consumes = {"application/json"},
        produces = {"application/json"},
        tags = {@Tag(name = "Description")}
        // TODO: remove this comment when the @SwaggerDefinition works. See also SwaggerController for the workaround.
        /* , // see workaround
        securityDefinition = @SecurityDefinition(
                apiKeyAuthDefintions = {
                        @ApiKeyAuthDefinition(key = "api_key", name = HEADER_API_KEY, in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)
                } ) */
)
@Path("/api/tank/measurements")
@Api(value = "/api/tank/measurements",
        description = "Operations for the tank measurements.")

@Produces({"application/json"})
public class MeasurementRoutes {
    /**
     * Name of the API authorisation key in the request header
     */
    public static final String HEADER_API_KEY = "ApiKey";

    // properties
    private static final Logger logger = LoggerFactory.getLogger(MeasurementRoutes.class.getName());
    private String expectedApiKey = null;
    private MeasurementController controller = null;

    /**
     * Constructor.
     *
     * @param serverContext the server context
     */
    public MeasurementRoutes(ServerContext serverContext) {

        controller = new MeasurementController(serverContext.getServerConfiguration());

        expectedApiKey = serverContext.getServerConfiguration().getTankApiKey();

        // the method parameters are irrelevant for the execution. They are solely used to place the
        // annotations for the swagger documentation
        postMeasurement(null);
        getMeasurementById("");
        getMeasurementByQuery();
        putMeasurement(null);
        deleteMeasurement("");
    }

    @POST
    @ApiOperation(value = "Save a measurement.", consumes = "application/json", authorizations = {@Authorization(value = "api_key")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the saved measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void postMeasurement(@ApiParam(value = "The measurement to save.", required = true) Measurement measurement) {

        post("/api/tank/measurements", (req, res) -> {
            res.type("application/json");

            Measurement m;

            try {
                checkApiAccess(req);

                m = new Gson().fromJson(req.body(), Measurement.class);

                // make sure the id is empty to create an new measurement
                // reason: in some cases the json contained a empty string ""
                m.resetId();

                m = controller.saveMeasurement(m);
            } catch (Exception e) {
                return handleException(e, res);
            }

            return m;

        }, json());
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find a measurement by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void getMeasurementById(@ApiParam(value = "Id of the measurement", required = true) @PathParam("id") String id) {

        get("/api/tank/measurements/:id", (req, res) -> {
            res.type("application/json");

            String tid = req.params(":id");

            Measurement measurement = controller.readMeasurement(tid);

            if (measurement != null) {
                return measurement;
            }

            res.status(400);
            return new ResponseError("No measurement with id '%s' found!", tid);

        }, json());
    }

    @GET
    @Path("/")
    @ApiOperation(value = "Find a measurement by query.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "Query command, set it to 'return' to get the result of the query", allowableValues = "return", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sensor", value = "Sensor name of the measurement series", example = "Precipitation", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "begin", value = "Begin date of the measurement series, format YYYY-MM-DD", example = "2000-01-01", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "end", value = "Begin date of the measurement series, format YYYY-MM-DD", example = "2001-12-31", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Sorting of the result, use '+date' for date ascending and '-date' for date descending sort", allowableValues = "+date, -date", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "max_result", value = "Max number of results", dataType = "string", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the measurements", response = Measurement[].class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void getMeasurementByQuery() {

        get("/api/tank/measurements", (req, res) -> {
            res.type("application/json");

            Map<String, String[]> query = req.queryMap().toMap();

            Measurement[] measurements;

            try {
                measurements = controller.selectMeasurements(query);
            } catch (Exception e) {
                return handleException(e, res);
            }

            return measurements;

        }, json());
    }

    @PUT
    @ApiOperation(value = "Update a measurement.", consumes = "application/json", authorizations = {@Authorization(value = "api_key")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the updated measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void putMeasurement(@ApiParam(value = "The measurement to update.", required = true) Measurement measurement) {

        put("/api/tank/measurements", (req, res) -> {
            res.type("application/json");

            Measurement m;

            try {
                checkApiAccess(req);

                m = new Gson().fromJson(req.body(), Measurement.class);
                m = controller.updateMeasurement(m);
            } catch (Exception e) {
                return handleException(e, res);
            }

            return m;

        }, json());
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete a measurement by id.", authorizations = {@Authorization(value = "api_key")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the measurement has been deleted"),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void deleteMeasurement(@ApiParam(value = "Id of the measurement", required = true) @PathParam("id") String id) {

        delete("/api/tank/measurements/:id", (req, res) -> {
            res.type("application/json");

            String tid = req.params(":id");

            try {
                checkApiAccess(req);

                controller.deleteMeasurement(tid);
            } catch (Exception e) {
                return handleException(e, res);
            }

            return "Success, the measurement has been deleted";

        }, json());
    }

    /**
     * Handles the Exceptions thrown during the request processing.
     *
     * @param e        the Exception
     * @param response the Response
     * @return the Response object
     */
    private Object handleException(Exception e, Response response) {
        if (e instanceof JsonParseException) {
            logger.error("Error while parsing the measurement!", e);

            response.status(400);
            return new ResponseError("Error while parsing the measurement!");
        } else if ((e instanceof MeasurementDaoException) || (e instanceof MeasurementException || (e instanceof NotAuthorisedException))) {
            logger.error(e.getMessage(), e);

            response.status(400);
            return new ResponseError(e.getMessage());
        } else {
            // any other Exception
            logger.error("Error while processing the request!", e);

            response.status(400);
            return new ResponseError("Error while processing the request!");
        }
    }

    /**
     * Check if the request is authorised to use the api.
     *
     * @param req the request
     * @throws NotAuthorisedException in case of a not authoriesd request
     */
    private void checkApiAccess(Request req) throws NotAuthorisedException {
        String reqKey = req.headers(HEADER_API_KEY);

        if (!expectedApiKey.equals(reqKey)) {
            throw new NotAuthorisedException("Not Authorised!");
        }
    }
}
