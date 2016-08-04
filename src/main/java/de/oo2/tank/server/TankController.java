package de.oo2.tank.server;

import com.google.gson.Gson;
import io.swagger.annotations.*;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static de.oo2.tank.server.JsonUtil.json;
import static de.oo2.tank.server.JsonUtil.toJson;
import static spark.Spark.*;

/**
 * This class adds the routes from the server to the service and handles the REST API data.
 */
@Api
@Path("/user")
@Produces("application/json")
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

    @ApiOperation(httpMethod = "GET", value = "Creates a new user", nickname = "CreateUserRoute")
    @ApiImplicitParams({ //
            @ApiImplicitParam(required = true, dataType = "string", name = "auth", paramType = "header"), //
            @ApiImplicitParam(required = true, dataType = "me.serol.spark_swagger.route.request.CreateUserRequest", paramType = "body") //
    }) //
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success", response = Measurement.class), //
            @ApiResponse(code = 400, message = "Invalid input data", response = ResponseError.class), //
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseError.class), //
            @ApiResponse(code = 404, message = "User not found", response = ResponseError.class) //
    })

    private void registerReadTemperatureWithId() {
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

