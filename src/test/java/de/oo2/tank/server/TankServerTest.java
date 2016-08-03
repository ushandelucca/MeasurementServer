package de.oo2.tank.server;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.oo2.tank.server.MeasurementFixture.getMeasurement1;

/**
 * Created by Peter on 29.07.2016.
 */
public class TankServerTest {
    private static Client client;

    @BeforeClass
    public static void beforeClass() {
        // TankServer.main(null);
        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void afterClass() {
        // stop();
        client = null;
    }

    @Test
    public void testPostTemperature() throws Exception {

        WebTarget target = client.target("http://localhost:8080/webapi/water/temperatures");

        Measurement param = getMeasurement1();


        // GsonBuilder builder = new GsonBuilder();
/*
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
*/
        // Gson gson = builder.create();
        Gson gson = new Gson();
        String jsonString = gson.toJson(param);
        // Country[] countryArray = gson.fromJson(myJsonString, Country[].class);


        Response result =
                target.request(MediaType.APPLICATION_JSON)
                        .post(Entity.entity(jsonString, MediaType.APPLICATION_JSON),
                                Response.class);
/*
        System.out.println("Result POST Temperature:");
        System.out.println("Sensor: " + result.getSensor());
        System.out.println("Value : " + result.getValue());
        System.out.println("Unit  : " + result.getUnit());
        System.out.println("Valid : " + result.getValid());
        System.out.println("Date  : " + result.getTimestamp());
        System.out.println();

*/
        // Assert.assertNotNull("Id should not be null", result.getId());
    }

    public void testPostTemperature2() throws Exception {

        WebTarget target = client.target("http://localhost:8080/webapi/water/temperatures");

        Measurement param = getMeasurement1();

        Measurement result =
                target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.entity(param, MediaType.APPLICATION_JSON_TYPE),
                                Measurement.class);

        System.out.println("Result POST Temperature:");
        System.out.println("Sensor: " + result.getSensor());
        System.out.println("Value : " + result.getValue());
        System.out.println("Unit  : " + result.getUnit());
        System.out.println("Valid : " + result.getValid());
        System.out.println("Date  : " + result.getTimestamp());
        System.out.println();


        // Assert.assertNotNull("Id should not be null", result.getId());
    }

    // @Test
    public void testGetTemperaturesAndDate() throws Exception {

        String result = client.target("http://localhost:8080/webapi/water/temperatures?query=return&&max_result=30")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        Measurement[] resi = new Gson().fromJson(result, Measurement[].class);

        System.out.println("Result GET Temperature");

        for (Measurement element : resi) {
            System.out.println("Sensor: " + element.getSensor());
            System.out.println("Value : " + element.getValue());
            System.out.println("Unit  : " + element.getUnit());
            System.out.println("Valid : " + element.getValid());
            System.out.println("Date  : " + element.getTimestamp());
            System.out.println();
        }

        Assert.assertNotNull(result);
    }

    // Test with invalid JSON

}
