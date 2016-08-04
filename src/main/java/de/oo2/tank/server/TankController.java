package de.oo2.tank.server;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static de.oo2.tank.server.JsonUtil.json;
import static de.oo2.tank.server.JsonUtil.toJson;
import static spark.Spark.*;

/**
 * This class adds the routes from the server to the service and handles the REST API data.
 */
@Path("/pet")
@Api(value = "/pet", description = "Operations about pets")
@Produces({"application/json", "application/xml"})
public class TankController {

    private final TemperatureService temperatureService;

    public TankController(final TemperatureService temperatureService) {
        this.temperatureService = temperatureService;

        registerReadTemperatureWithId();

        post("/webapi/water/temperatures", (req, res) -> {

            System.out.println(req.body());

            Measurement m = new Gson().fromJson(req.body(), Measurement.class);

            return m;

        }, json());

        put("/webapi/water/temperature/:id", (req, res) -> {
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

    @GET
    @Path("/findByStatus")
    @ApiOperation(value = "Finds Pets by status",
            notes = "Multiple status values can be provided with comma seperated strings",
            response = Measurement.class,
            responseContainer = "List")

    public void registerReadTemperatureWithId() {
        // http://localhost:8080/webapi/water/temperature/1
        get("/webapi/water/temperature/:id", (req, res) -> {
            String id = req.params(":id");
            Measurement measurement = temperatureService.readTemperature(id);
            if (measurement != null) {
                return measurement;
            }
            res.status(400);
            return new ResponseError("No user with id '%s' found", id);
        }, json());
    }

}

