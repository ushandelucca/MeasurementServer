package de.oo2.tank.server.route;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import de.oo2.tank.server.model.Measurement;
import de.oo2.tank.server.model.ModelException;
import de.oo2.tank.server.model.ResponseError;
import de.oo2.tank.server.model.Tank;
import de.oo2.tank.server.persistence.PersistenceException;
import de.oo2.tank.server.service.MeasurementService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Response;

import javax.ws.rs.*;
import java.util.Map;

import static de.oo2.tank.server.util.JsonUtil.json;
import static spark.Spark.*;

/**
 * This class adds the route for the measurement service and handles the REST requests an responses.
 */
@Path("/api/tank/measurements")
@Api(value = "/api/tank/measurements",
        description = "Operations for the tank measurements.")
@Produces({"application/json"})
public class MeasurementRoutes {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementRoutes.class.getName());
    private final MeasurementService measurementService;

    /**
     * Constructor.
     *
     * @param tank the tank model
     */
    public MeasurementRoutes(Tank tank) {
        this.measurementService = tank.getMeasurementService();

        // the method parameters are irrelevant for the execution. They are solely used to place the
        // annotations for the swagger documentation
        postTemperature(null);
        getMeasurementById("");
        getMeasurementByQuery();
        putMeasurement(null);
        deleteMeasurement("");
    }

    @POST
    @ApiOperation(value = "Save a measurement.", consumes = "application/json", authorizations = {@Authorization(value = "tankauth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the saved measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void postTemperature(@ApiParam(value = "The measurement to save.", required = true) Measurement measurement) {

        post("/api/tank/measurements", (req, res) -> {
            res.type("application/json");

            // TODO: check authorisation in POST request
            // String apiKey = req.headers("key");

            Measurement _measurement = null;

            try {
                _measurement = new Gson().fromJson(req.body(), Measurement.class);
                _measurement = measurementService.saveMeasurement(_measurement);
            } catch (Exception e) {
                return handleException(e, res);
            }

            return _measurement;

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

            Measurement measurement = measurementService.readMeasurement(tid);

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
            @ApiImplicitParam(name = "begin", value = "Begin date of the measurement series, format YYYY-MM-DD", example = "2000-01-01", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "end", value = "Begin date of the measurement series, format YYYY-MM-DD", example = "2001-12-31", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Sorting of the result, use '+date' for date ascending and '-date' for date descending sort", allowableValues = "+date, -date", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "max_result", value = "Max number of results", required = false, dataType = "string", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the measurements", response = Measurement[].class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void getMeasurementByQuery() {

        get("/api/tank/measurements", (req, res) -> {
            res.type("application/json");

            Map<String, String[]> query = req.queryMap().toMap();

            Measurement[] measurements = null;

            try {
                measurements = measurementService.selectMeasurements(query);
            } catch (Exception e) {
                return handleException(e, res);
            }

            return measurements;

        }, json());
    }

    @PUT
    @ApiOperation(value = "Update a measurement.", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the updated measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void putMeasurement(@ApiParam(value = "The measurement to update.", required = true) Measurement measurement) {

        put("/api/water/measurements", (req, res) -> {
            res.type("application/json");

            res.status(400);
            return new ResponseError("Not implemented yet!");

        }, json());
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete a measurement by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the measurement has been deleted"),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void deleteMeasurement(@ApiParam(value = "Id of the measurement", required = true) @PathParam("id") String id) {

        delete("/api/water/measurements", (req, res) -> {
            res.type("application/json");

            res.status(400);
            return new ResponseError("Not implemented yet!");

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
        } else if ((e instanceof PersistenceException) || (e instanceof ModelException)) {
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
}
