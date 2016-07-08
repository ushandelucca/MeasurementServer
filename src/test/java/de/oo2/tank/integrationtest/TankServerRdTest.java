package de.oo2.tank.integrationtest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.oo2.tank.model.Measurement;
import de.oo2.tank.model.MeasurementFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Resource dependent test for the TankServer
 * (excluded in surefire report)
 */

class MyMeasurement {
    private Date timestamp;

    private String sensor;

    private Float value;

    private String unit;

    private Boolean valid;
}

public class TankServerRdTest {

    private Client client;

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        client = null;
    }

   @Test
    public void testSampleTemperature() throws Exception {

        WebTarget target = client.target("http://localhost:8080/webapi/water/temperatures");

        Measurement param = MeasurementFixture.MEASUREMENT_1;

        String result =
                target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post( Entity.entity(param, MediaType.APPLICATION_JSON_TYPE), String.class);




        System.out.println("Result POST Temperature: " + result.toString());

        // Assert.assertNotNull("Id should not be null", result.getId());
    }

    @Test
    public void testGetTemperatures() throws Exception {

        String result = client.target("http://localhost:8080/webapi/water/temperatures?query=return&&max_result=30")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println("Result GET Temperature: " + result.toString());

        // 2016-02-24T00:00:00
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        MyMeasurement[] resi = gson.fromJson(result, MyMeasurement[].class);

        System.out.println("Result GET Temperature Date: " + resi);




        Assert.assertNotNull(result);
    }

}
