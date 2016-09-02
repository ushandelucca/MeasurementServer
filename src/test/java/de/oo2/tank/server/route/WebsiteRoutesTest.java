package de.oo2.tank.server.route;

import de.oo2.tank.server.Server;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static spark.Spark.stop;

/**
 * Tests class.
 */
public class WebsiteRoutesTest {

    @BeforeClass
    public static void beforeClass() {
        // start the server
        Server.main(null);
    }

    @AfterClass
    public static void afterClass() {
        // stop the server
        stop();
    }

    @Test
    public void testWebsite() throws Exception {
        Content content = Request.Get("http://localhost:8080/")
                .execute()
                .returnContent();

        Assert.assertNotSame("", content.asString());
    }
}
