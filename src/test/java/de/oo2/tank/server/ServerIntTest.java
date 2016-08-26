package de.oo2.tank.server;

import com.google.gson.Gson;
import de.oo2.tank.server.model.Measurement;
import de.oo2.tank.server.model.ResponseError;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static de.oo2.tank.server.model.MeasurementFixture.getMeasurement1;
import static de.oo2.tank.server.model.MeasurementFixture.getMeasurement2;
import static spark.Spark.stop;

/**
 * Integration tests for the <code>Server</code>. Prerequisite: the database must be accessible.
 */
public class ServerIntTest {
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

        Content content = Request.Post("http://localhost:80/api/tank/measurements")
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
    public void testPostTemperature_400() throws Exception {
        Measurement param = new Measurement();
        String jsonString = gson.toJson(param);

        HttpResponse httpResponse = Request.Post("http://localhost:80/api/tank/measurements")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();


        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertTrue(errorMessage.getMessage().contains("Multiple"));

        jsonString = "{\"timestamp\":\"\",\"sensor\":\"\",\"value\":0,\"unit\":\"\",\"valid\":false,\"id\":\"\"}";

        httpResponse = Request.Post("http://localhost:80/api/tank/measurements")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();


        code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertTrue(errorMessage.getMessage().contains("parsing the measurement"));

    }

    @Test
    public void testGetTemperature_200() throws Exception {
        // first save a temperature
        Measurement param = getMeasurement2();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:80/api/tank/measurements")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame("", savedMeasurement.getId());

        // then read it again
        content = Request.Get("http://localhost:80/api/tank/measurements/" + savedMeasurement.getId())
                .execute()
                .returnContent();

        Measurement readMeasurement = gson.fromJson(content.toString(), Measurement.class);

        Assert.assertEquals(savedMeasurement.getId(), readMeasurement.getId());
    }

    @Test
    public void testGetTemperature_400() throws Exception {
        HttpResponse httpResponse = Request.Get("http://localhost:80/api/tank/measurements/54651022bffebc03098b4567")
                .execute()
                .returnResponse();

        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertEquals("No measurement with id '54651022bffebc03098b4567' found!", errorMessage.getMessage());
    }

    // TODO: http://stackoverflow.com/questions/2606572/junit-splitting-integration-test-and-unit-tests

}
