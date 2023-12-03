package de.oo2.m.server.util;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for the JSON utilities.
 */
public class JsonUtilTest {

    @Test
    public void testToJson() {
        String json = JsonUtil.toJson("a String");

        Assert.assertNotNull(json);
    }

    @Test
    public void testDateToJson() {
        String json = JsonUtil.toJson(new Date(1701298800000L));

        Assert.assertEquals("\"2023-11-30T00:00:00.000+01\"", json);
    }

    @Test
    public void testJsonToDate() {
        Date d = new Gson().fromJson("\"2023-11-30T00:00:00.000+01\"", Date.class);

        Assert.assertEquals(new Date(1701298800000L), d);
    }
}
