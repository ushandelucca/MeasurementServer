package de.oo2.tank.server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test
 */
public class ConfiguratorTest {

    private Configurator configurator;

    @Before
    public void setUp() throws Exception {
        configurator = new Configurator();
    }

    @After
    public void tearDown() throws Exception {
        configurator = null;
    }

    @Test
    public void testSetAndGetResourceBase() throws Exception {
        Assert.assertNotNull(configurator.getResourceBase());
    }

    @Test
    public void testSetAndGetServerPort() throws Exception {
        Assert.assertNotNull(configurator.getServerPort());
    }

    @Test
    public void testSetAndGetDatabaseName() throws Exception {
        Assert.assertNotNull(configurator.getDatabaseName());
    }

    @Test
    public void testSetAndGetDatabaseUser() throws Exception {
        Assert.assertNotNull(configurator.getDatabaseUser());
    }

    @Test
    public void testSetAndGetDatabasePassword() throws Exception {
        Assert.assertNotNull(configurator.getDatabasePassword());
    }
}
