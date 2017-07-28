package de.oo2.tank.server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class.
 */
public class ServerContextTest {
    private ServerContext serverContext = null;

    @Before
    public void setUp() throws Exception {
        serverContext = new ServerContext();
    }

    @After
    public void tearDown() throws Exception {
        serverContext = null;
    }

    @Test
    public void testConstructor() throws Exception {
        Assert.assertNotNull(serverContext);
    }

    @Test
    public void testGetVersion() throws Exception {
        Assert.assertNotNull(serverContext.getVersion());
        Assert.assertNotNull(serverContext.getVersion());
    }

    @Test
    public void testGetHostname() throws Exception {
        Assert.assertNotNull(serverContext.getHostname());
        Assert.assertNotNull(serverContext.getHostname());
    }

    @Test
    public void testGetMeasurementController() throws Exception {
        Assert.assertNotNull(serverContext.getMeasurementController());
        Assert.assertNotNull(serverContext.getMeasurementController());
    }

}
