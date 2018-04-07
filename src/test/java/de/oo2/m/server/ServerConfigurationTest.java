package de.oo2.m.server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class.
 */
public class ServerConfigurationTest {
    private ServerConfiguration config;

    @Before
    public void setUp() {
        config = new ServerConfiguration();
    }

    @After
    public void tearDown() {
        config = null;
    }

    @Test
    public void testServerPort() {
        Assert.assertEquals(new Integer(8080), config.getServerPort());
    }

    @Test
    public void testDbType() {
        Assert.assertEquals("MongoDb", config.getDbType());
    }

    @Test
    public void testDbHost() {
        Assert.assertEquals("localhost", config.getDbHost());
    }

    @Test
    public void testDbPort() {
        Assert.assertEquals(new Integer(27017), config.getDbPort());
    }

    @Test
    public void testDbName() {
        Assert.assertEquals("test", config.getDbName());
    }

    @Test
    public void testTankApiKey() {
        Assert.assertEquals("ABC123", config.getTankApiKey());
    }

    @Test
    public void testGoogleAnalyticsKey() {
        Assert.assertNull(config.getGoogleAnalyticsKey());
    }

    @Test
    public void testLogglyKey() {
        Assert.assertNull(config.getLogglyKey());
    }
}
