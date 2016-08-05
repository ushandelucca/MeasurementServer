package de.oo2.tank.server;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static de.oo2.tank.server.MeasurementFixture.getMeasurement1;

/**
 * Created by Peter on 29.07.2016.
 */
public class TankServerTest {
    private Gson gson = new Gson();

    @Before
    public void before() {
        // TankServer.main(null);
        // client = ClientBuilder.newClient();
    }

    @After
    public void after() {
        // stop();
        // client = null;
    }

    @Test
    public void testPostTemperature() throws Exception {
        Measurement param = getMeasurement1();
        String jsonString = gson.toJson(param);

        Content response = Request.Post("http://localhost:8080/api/tank/temperatures")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        System.out.println(response);

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

    // @Test
    public void testGetTemperature() throws Exception {
        Measurement param = getMeasurement1();
        String jsonString = gson.toJson(param);

        Content response = Request.Get("http://localhost:8080/api/tank/temperatures/1")
                .execute()
                .returnContent();

        System.out.println(response);

    }

    // Test with invalid JSON

}
