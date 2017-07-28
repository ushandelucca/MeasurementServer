package de.oo2.tank.server.measurement;

import com.google.gson.Gson;
import de.oo2.tank.server.Server;
import de.oo2.tank.server.measurement.Measurement;
import de.oo2.tank.server.util.ResponseError;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static de.oo2.tank.server.measurement.MeasurementFixture.*;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

/**
 * Integration tests class.
 */
public class MeasurementRoutesIntTest {
    private Gson gson = new Gson();

    @BeforeClass
    public static void beforeClass() throws Exception {
        // start the server
        Server.main(null);
        awaitInitialization();
        // Thread.sleep(2000); // allow the server to start
    }

    @AfterClass
    public static void afterClass() throws Exception {
        // stop the server
        stop();
        Thread.sleep(2000); // allow the server to stop
    }

    @Test
    public void testPostMeasurement_200() throws Exception {
        Measurement param = getMeasurement1();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement result = gson.fromJson(content.asString(), Measurement.class);
        Assert.assertNotNull("Id should not be null", result.getId());
    }

    @Test
    public void testPostMeasurement_400() throws Exception {
        Measurement param = new Measurement();
        String jsonString = gson.toJson(param);

        HttpResponse httpResponse = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();


        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertTrue(errorMessage.getMessage().contains("Multiple"));

        jsonString = "{\"timestamp\":\"\",\"sensor\":\"\",\"value\":0,\"unit\":\"\",\"valid\":false,\"id\":\"\"}";

        httpResponse = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
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
    public void testGetMeasurement_200() throws Exception {
        // first save a measurement
        Measurement param = getMeasurement2();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame(param.getId(), savedMeasurement.getId());

        // then read it again
        content = Request.Get("http://localhost:8080/api/tank/measurements/" + savedMeasurement.getId())
                .execute()
                .returnContent();

        Measurement readMeasurement = gson.fromJson(content.toString(), Measurement.class);

        Assert.assertEquals(savedMeasurement.getId(), readMeasurement.getId());
    }

    @Test
    public void testGetMeasurement_400() throws Exception {
        HttpResponse httpResponse = Request.Get("http://localhost:8080/api/tank/measurements/54651022bffebc03098b4567")
                .execute()
                .returnResponse();

        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertEquals("No measurement with id '54651022bffebc03098b4567' found!", errorMessage.getMessage());
    }

    @Test
    public void testGetMeasurementWithQuery_200() throws Exception {
        // first save a measurement
        Measurement param = getMeasurement2();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame(param.getId(), savedMeasurement.getId());

        // then select it with the query
        content = Request.Get("http://localhost:8080/api/tank/measurements?query=return&max_result=3")
                .execute()
                .returnContent();

        Measurement[] readMeasurements = gson.fromJson(content.toString(), Measurement[].class);

        Assert.assertTrue(readMeasurements.length > 0);
    }

    @Test
    public void testGetMeasurementWithQuery_400() throws Exception {
        HttpResponse httpResponse = Request.Get("http://localhost:8080/api/tank/measurements?query=return&noParam")
                .execute()
                .returnResponse();

        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertEquals("No search criteria defined!", errorMessage.getMessage());
    }

    @Test
    public void testGetMeasurementLastWeek() throws Exception {
        // first save measurement 1
        Measurement param = getMeasurement1();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame(param.getId(), savedMeasurement.getId());

        // measurement 2
        param = getMeasurement2();
        jsonString = gson.toJson(param);

        content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame(param.getId(), savedMeasurement.getId());

        // measurement 3
        param = getMeasurement3();
        jsonString = gson.toJson(param);

        content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame(param.getId(), savedMeasurement.getId());

        // then select it with the query
        content = Request.Get("http://localhost:8080/api/tank/measurements?query=return&sensor=Precipitation&sort=-date")
                .execute()
                .returnContent();

        Measurement[] readMeasurements = gson.fromJson(content.toString(), Measurement[].class);

        System.out.println(content);

        Assert.assertTrue(readMeasurements.length > 0);
    }

    @Test
    public void testPutMeasurement_200() throws Exception {
        // first save measurement 1
        Measurement param = getMeasurement1();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame(param.getId(), savedMeasurement.getId());

        // then update it
        savedMeasurement.setValue(savedMeasurement.getValue() + 2.5f);

        float updatedValue = savedMeasurement.getValue();

        Assert.assertNotEquals(param.getValue(), updatedValue);

        jsonString = gson.toJson(savedMeasurement);

        content = Request.Put("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement result = gson.fromJson(content.asString(), Measurement.class);
        Assert.assertEquals(updatedValue, result.getValue(), 0d);
    }

    @Test
    public void testPutMeasurement_400() throws Exception {
        Measurement param = getMeasurement1();
        setRandomId(param);
        String jsonString = gson.toJson(param);

        HttpResponse httpResponse = Request.Put("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();

        // TODO: check what does .useExpectContinue() mean... using Request instead of HttpResponse?

        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertTrue(errorMessage.getMessage().contains("Error while updating the measurement"));
    }

    @Test
    public void testDeleteMeasurement_200() throws Exception {
        // first save a measurement
        Measurement param = getMeasurement2();
        String jsonString = gson.toJson(param);

        Content content = Request.Post("http://localhost:8080/api/tank/measurements")
                .addHeader("ApiKey", "ABC123")
                .bodyString(jsonString, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        Measurement savedMeasurement = gson.fromJson(content.asString(), Measurement.class);

        Assert.assertNotSame(param.getId(), savedMeasurement.getId());

        // then delete it
        content = Request.Delete("http://localhost:8080/api/tank/measurements/" + savedMeasurement.getId())
                .addHeader("ApiKey", "ABC123")
                .execute()
                .returnContent();

        Assert.assertEquals("\"Success, the measurement has been deleted\"", content.toString());
    }

    @Test
    public void testDeleteMeasurement_400() throws Exception {
        HttpResponse httpResponse = Request.Delete("http://localhost:8080/api/tank/measurements/54651022bffebc03098b4567")
                .addHeader("ApiKey", "ABC123")
                .execute()
                .returnResponse();

        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertTrue(errorMessage.getMessage().contains("Error while deleting the measurement"));
    }
}

