package de.oo2.m.server.website;

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
