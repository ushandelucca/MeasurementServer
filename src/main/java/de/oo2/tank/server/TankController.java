package de.oo2.tank.server;

import com.google.gson.Gson;

import static de.oo2.tank.server.JsonUtil.json;
import static de.oo2.tank.server.JsonUtil.toJson;
import static spark.Spark.*;

/**
 * This class routes the requests from the server to the service.
 */
public class TankController {

    public TankController(final TemperatureService temperatureService) {

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
}

