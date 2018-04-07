package de.oo2.m.server.swagger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests class.
 */
public class SwaggerServiceTest {
    private SwaggerController service = null;

    @Before
    public void setUp() throws Exception {
        service = new SwaggerController();
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
