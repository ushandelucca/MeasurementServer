package de.oo2.tank.integrationtest;

import de.oo2.tank.server.Measurement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Resource dependent test for the TankServer
 * (excluded in surefire report)
 */
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

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8180/webapi/water/temperatures");

        Measurement result =
                target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post( Entity.entity("", MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                                Measurement.class);

        System.out.println(result);

        Assert.assertNotNull(result);
    }

    @Test
    public void testGetTemperatures() throws Exception {

        long startTime = System.currentTimeMillis();

        String result = client.target("http://localhost:8180/webapi/water/temperatures")
                .request(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println("time : " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println(result);

        Assert.assertNotNull(result);
    }

}
