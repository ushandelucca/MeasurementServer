package de.oo2.tank.server;

import com.google.gson.Gson;
import io.swagger.annotations.*;

import javax.ws.rs.*;

import static de.oo2.tank.server.JsonUtil.json;
import static spark.Spark.*;

/**
 * This class adds the routes for the temperature service and handles the REST requests an responses.
 */
@Path("/api/tank/temperatures")
@Api(value = "/api/tank/temperatures",
        description = "Operations for the tank temperatures.")
@Produces({"application/json"})
public class TemperatureRoutes {

    private final TemperatureService temperatureService;

    public TemperatureRoutes(final TemperatureService temperatureService) {
        this.temperatureService = temperatureService;

        // the method parameters are irrelevant for the execution. They are solely used to place the
        // annotations for the swagger documentation
        postTemperature(null);
        getTemperature("");
        putTemperature(null);
    }

    @POST
    @ApiOperation(value = "Save a temperature measurement.", consumes = "application/json", authorizations = {@Authorization(value = "tankauth")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the saved temperature measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void postTemperature(@ApiParam(value = "The measurement to save.", required = true) Measurement measurement) {

        post("/api/tank/temperatures", (req, res) -> {

            // check authorisation
            String apiKey = req.headers("key");

            Measurement m = new Gson().fromJson(req.body(), Measurement.class);

            m = temperatureService.saveTemperatue(m);
            return m;

        }, json());
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find a temperature measurement by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the temperature measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void getTemperature(@ApiParam(value = "Id of the temperature measurement", required = true) @PathParam("id") String id) {

        get("/api/tank/temperatures/:id", (req, res) -> {
            String tid = req.params(":id");

            Measurement measurement = temperatureService.readTemperature(tid);

            if (measurement != null) {
                return measurement;
            }

            res.status(400);
            return new ResponseError("No temperature measurement with id '%s' found!", tid);

        }, json());
    }

    @PUT
    @ApiOperation(value = "Update a temperature measurement.", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, the updated temperature measurement", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void putTemperature(@ApiParam(value = "The temperature measurement to update.", required = true) Measurement measurement) {

        put("/api/water/temperatures", (req, res) -> {
            return null;

        }, json());
    }

}

