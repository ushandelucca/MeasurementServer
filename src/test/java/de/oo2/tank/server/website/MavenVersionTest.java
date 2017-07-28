package de.oo2.tank.server.website;

import de.oo2.tank.server.website.MavenVersion;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the maven version utilities.
 */
public class MavenVersionTest {

    @Test
    public void testVersion() {
        String version = MavenVersion.getVersion();

        Assert.assertEquals("test", version);
    }
}
