package de.oo2.tank.server.service;

import de.oo2.tank.server.swagger.SwaggerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests class.
 */
public class SwaggerServiceTest {
    private SwaggerService service = null;

    @Before
    public void setUp() throws Exception {
        service = new SwaggerService();
    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    @Test
    public void testString() throws Exception {
        Assert.assertNotNull("", service.getSwaggerJson());
    }
}
