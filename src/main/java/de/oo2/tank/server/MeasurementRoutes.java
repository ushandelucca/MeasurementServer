package de.oo2.tank.server;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import de.oo2.tank.server.model.Measurement;
import de.oo2.tank.server.model.ModelException;
import de.oo2.tank.server.model.ResponseError;
import de.oo2.tank.server.persistence.PersistenceException;
import io.swagger.annotations.*;

import javax.ws.rs.*;

import static de.oo2.tank.server.util.JsonUtil.json;
import static spark.Spark.*;

/**
 * This class adds the routes for the measurement service and handles the REST requests an responses.
 */
@Path("/api/tank/measurements")
@Api(value = "/api/tank/measurements",
        description = "Operations for the tank measurements.")
@Produces({"application/json"})
public class MeasurementRoutes {

    private final MeasurementService measurementService;

    public MeasurementRoutes(final MeasurementService temperatureService) {
        this.measurementService = temperatureService;

        // the method parameters are irrelevant for the execution. They are solely used to place the
        // annotations for the swagger documentation
        postTemperature(null);
        getTemperature("");
        putTemperature(null);
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
            } catch (JsonParseException e) {
                // TODO: Logging

                res.status(400);
                return new ResponseError("Error while parsing the measurement!");
            } catch (PersistenceException | ModelException e) {
                res.status(400);
                return new ResponseError(e.getMessage());
            } catch (Exception e) {
                res.status(400);
                return new ResponseError("Error while processing the request!");
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
    public void getTemperature(@ApiParam(value = "Id of the measurement", required = true) @PathParam("id") String id) {

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

    @PUT
    @ApiOperation(value = "Update a measurement.", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the updated measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void putTemperature(@ApiParam(value = "The measurement to update.", required = true) Measurement measurement) {

        put("/api/water/measurements", (req, res) -> {
            res.type("application/json");

            res.status(400);
            return new ResponseError("Not implemented yet!");

        }, json());
    }

}

