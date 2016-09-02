package de.oo2.tank.server;

import org.junit.Assert;
import org.junit.Test;

import static spark.Spark.stop;

/**
 * Test class.
 */
public class ServerTest {

    @Test
    public void testServerStartStop() {

        try {

            Server.main(null);
            stop();

        } catch (Exception e) {
            Assert.fail();
        }
    }


}
