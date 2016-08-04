package de.oo2.tank.server;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;

import static de.oo2.tank.server.JsonUtil.json;
import static de.oo2.tank.server.JsonUtil.toJson;
import static spark.Spark.*;

/**
 * This class adds the routes from the server to the service and handles the REST API data.
 */
@Path("/api/tank/temperatures")
@Api(value = "/api/tank/temperatures", description = "Operations for the tank temperatures.")
@Produces({"application/json"})
public class TankController {

    private final TemperatureService temperatureService;

    public TankController(final TemperatureService temperatureService) {
        this.temperatureService = temperatureService;

        getTemperature("");
        postTemperature(null);


        put("/api/water/temperatures/:id", (req, res) -> {
            return null;


        }, json());

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });

    }

    @POST
    @ApiOperation(value = "Save a temperature measurement.",
            response = Measurement.class)
    @ApiParam(value = "The measurement to save.", required = true)
    public void postTemperature(Measurement measurement) {

        post("/api/tank/temperatures", (req, res) -> {

            System.out.println(req.body());

            Measurement m = new Gson().fromJson(req.body(), Measurement.class);

            return m;

        }, json());
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find temperature measurements by id.",
            response = Measurement.class,
            responseContainer = "List")
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

}

