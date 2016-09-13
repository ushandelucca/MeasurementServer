package de.oo2.tank.server.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the JSON utilities.
 */
public class JsonUtilTest {

    @Test
    public void testToJson() {
        String json = JsonUtil.toJson("a String");

        Assert.assertNotNull(json);
    }
}
