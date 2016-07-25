package de.oo2.tank.integrationtest;

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
import java.util.List;

/**
 * Resource dependent test for the TankServer
 * (excluded in surefire report)
 */
public class TankServeMeasurementIT {

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

        Measurement result =
                target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post( Entity.entity(param, MediaType.APPLICATION_JSON_TYPE),
                                Measurement.class);

        System.out.println("Result POST Temperature: " + result.toString());

        // Assert.assertNotNull("Id should not be null", result.getId());
    }

    @Test
    public void testGetTemperatures() throws Exception {

        List<Measurement> result = client.target("http://localhost:8080/webapi/water/temperatures?query=return&&max_result=30")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Measurement>>() {});

        System.out.println("Result GET Temperature: " + result.toString());


        Assert.assertNotNull(result);
    }

}
