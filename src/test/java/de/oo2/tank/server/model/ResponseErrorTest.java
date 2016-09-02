package de.oo2.tank.server.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class.
 */
public class ResponseErrorTest {

    @Test
    public void testConstructor() throws Exception {
        Assert.assertNotNull(new ResponseError("", ""));
    }

    @Test
    public void testGetMessage() throws Exception {
        ResponseError responseError = new ResponseError("Hello %s!", "World");

        Assert.assertEquals("Hello World!", responseError.getMessage());
    }
}
