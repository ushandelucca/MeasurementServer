package de.oo2.tank.server;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static de.oo2.tank.server.MeasurementFixture.getMeasurement1;
import static de.oo2.tank.server.MeasurementFixture.getMeasurement2;
import static spark.Spark.stop;

/**
 * Tests for the server. Prerequisite: the database must be accessible.
 */
public class ServerTest {
    private Gson gson = new Gson();

    @BeforeClass
    public static void beforeClass() {
        // start the server
        Server.main(null);
    }

    @AfterClass
    public static void afterClass() {
        // stop the server
        stop();
    }

    @Test
    public void testPostTemperature_200() throws Exception {
        Measurement param = getMeasurement1();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/temperatures")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement result = gson.fromJson(content.asString(), Measurement.class);
/*
        System.out.println("Result POST Temperature:");
        System.out.println("Id    : " + result.getId());
        System.out.println("Date  : " + result.getTimestamp());
        System.out.println("Sensor: " + result.getSensor());
        System.out.println("Value : " + result.getValue());
        System.out.println("Unit  : " + result.getUnit());
        System.out.println("Valid : " + result.getValid());
        System.out.println();
*/
        Assert.assertNotNull("Id should not be null", result.getId());
    }

    @Test
    public void testGetTemperature_200() throws Exception {
        // first save a temperature
        Measurement param = getMeasurement2();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/temperatures")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement savedMeasurement = gson.fromJson(content.asString(), Measurement.class);
        content = null;

        Assert.assertNotSame("", savedMeasurement.getId());

        // then read it again
        content = Request.Get("http://localhost:8080/api/tank/temperatures/" + savedMeasurement.getId())
                .execute()
                .returnContent();

        Measurement readMeasurement = gson.fromJson(content.toString(), Measurement.class);

        Assert.assertEquals(savedMeasurement.getId(), readMeasurement.getId());
    }

    @Test
    public void testGetTemperature_400() throws Exception {
        HttpResponse httpResponse = Request.Get("http://localhost:8080/api/tank/temperatures/54651022bffebc03098b4567")
                .execute()
                .returnResponse();

        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertEquals("No temperature measurement with id '54651022bffebc03098b4567' found!", errorMessage.getMessage());
    }

    // TODO
    // Test with invalid JSON (POST 400)
    // Test PUT (200 and 400)
    // Test query for measurements (200 and 400)

}
