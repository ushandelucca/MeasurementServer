package de.oo2.m.server.util;

import java.text.DateFormat;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

/**
 * Utilities for JSON
 */
public class JsonUtil {

    /**
     * Constructor.
     */
    private JsonUtil() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Returns the JSON representation of the given object.
     *
     * @param object the object
     * @return the JSON representation
     */
    public static String toJson(Object object) {

        Gson gson = new GsonBuilder()
                // .setPrettyPrinting()
                // .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                // .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        return gson.toJson(object);
    }

    /**
     * Returns the <code>ResponseTransformer</code>.
     *
     * @return the <code>ResponseTransformer</code>
     */
    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }

}