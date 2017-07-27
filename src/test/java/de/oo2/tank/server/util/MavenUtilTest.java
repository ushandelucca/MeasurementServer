package de.oo2.tank.server.util;

import de.oo2.tank.server.website.MavenVersion;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the maven utilities.
 */
public class MavenUtilTest {

    @Test
    public void testVersion() {
        String version = MavenVersion.getVersion();

        Assert.assertEquals("test", version);
    }
}
