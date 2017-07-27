package de.oo2.tank.server.model;

import de.oo2.tank.server.util.ResponseError;
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

    @Test
    public void testGetStadandardMessage() throws Exception {
        ResponseError responseError = new ResponseError(null, "World");

        Assert.assertEquals("Error while processing the request!", responseError.getMessage());
    }
}
