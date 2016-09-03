package de.oo2.tank.server.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the maven utilities.
 */
public class MavenUtilTest {

    @Test
    public void testVersion() {
        String version = MavenUtil.getVersion();

        Assert.assertEquals("test", version);
    }
}
