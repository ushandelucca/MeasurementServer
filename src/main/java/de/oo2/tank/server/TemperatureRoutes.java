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
@Api(value = "/api/tank/temperatures", description = "Operations for the tank temperatures.")
@Produces({"application/json"})
public class TemperatureRoutes {

    private final TemperatureService temperatureService;

    public TemperatureRoutes(final TemperatureService temperatureService) {
        this.temperatureService = temperatureService;

        // the method parameters are irrelevant for the execution. They are solely used to place the
        // annotations used for the swagger documentation
        postTemperature(null);
        getTemperature("");
        putTemperature("");
    }

    @POST
    @ApiOperation(value = "Save a temperature measurement.",
            response = Measurement.class)
    @ApiParam(value = "The measurement to save.", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void postTemperature(Measurement measurement) {

        post("/api/tank/temperatures", (req, res) -> {
            Measurement m = new Gson().fromJson(req.body(), Measurement.class);

            m = temperatureService.saveTemperatue(m);
            return m;

        }, json());
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find temperature measurements by id.",
            response = Measurement.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void getTemperature(@ApiParam(value = "Id of the temperature measurement", required = true) @PathParam("id") String id) {

        get("/api/tank/temperatures/:id", (req, res) -> {
            String tid = req.params(":id");

            Measurement measurement = temperatureService.readTemperature(tid);

            if (measurement != null) {
                return measurement;
            }

            res.status(400);
            return new ResponseError("No user with id '%s' found", tid);

        }, json());
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update a temperature measurements by id.",
            response = Measurement.class,
            responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Measurement.class),
            @ApiResponse(code = 400, message = "Error message", response = ResponseError.class)})
    public void putTemperature(@ApiParam(value = "Id of the temperature measurement", required = true) @PathParam("id") String id) {

        put("/api/water/temperatures/:id", (req, res) -> {
            return null;

        }, json());
    }

}

