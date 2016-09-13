package de.oo2.tank.server.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class.
 */
public class TankTest {
    private Tank tank = null;

    @Before
    public void setUp() throws Exception {
        tank = new Tank();
    }

    @After
    public void tearDown() throws Exception {
        tank = null;
    }

    @Test
    public void testConstructor() throws Exception {
        Assert.assertNotNull(tank);
    }

    @Test
    public void testGetVersion() throws Exception {
        Assert.assertNotNull(tank.getVersion());
        Assert.assertNotNull(tank.getVersion());
    }

    @Test
    public void testGetHostname() throws Exception {
        Assert.assertNotNull(tank.getHostname());
        Assert.assertNotNull(tank.getHostname());
    }

    @Test
    public void testGetMeasurementService() throws Exception {
        Assert.assertNotNull(tank.getMeasurementService());
        Assert.assertNotNull(tank.getMeasurementService());
    }

    @Test
    public void testGetSwaggerService() throws Exception {
        Assert.assertNotNull(tank.getSwaggerService());
        Assert.assertNotNull(tank.getSwaggerService());
    }
}
