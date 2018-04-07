package de.oo2.m.server;

import org.junit.Assert;
import org.junit.Test;

import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

/**
 * Test class.
 */
public class ServerTest {

    @Test
    public void testServerStartStop() {

        try {

            Server.main(null);
            awaitInitialization();
            stop();
            Thread.sleep(2000); // allow the server to stop

        } catch (Exception e) {
            Assert.fail();
        }
    }


}
