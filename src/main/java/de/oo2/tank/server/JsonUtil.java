package de.oo2.tank.server;

/**
 * Created by Peter on 29.07.2016.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.oo2.tank.model.Measurement;
import spark.ResponseTransformer;

public class JsonUtil {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

    public static Measurement[] map(String json) {
        Measurement[] measurements = new Measurement[0];

        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
/*
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
*/
        Gson gson = builder.create();

        measurements = gson.fromJson(json, Measurement[].class);

        return measurements;
    }

    public static Measurement map2(String json) {
        Measurement measurement = new Measurement();

        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();
/*
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
*/
        Gson gson = builder.create();

        measurement = gson.fromJson(json, Measurement.class);

        return measurement;
    }
}