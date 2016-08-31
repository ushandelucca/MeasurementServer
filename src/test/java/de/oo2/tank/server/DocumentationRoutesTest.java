package de.oo2.tank.server;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static spark.Spark.stop;

/**
 * Tests for the <code>DocumentationRoutes</code>.
 */
public class DocumentationRoutesTest {

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
    public void testGetSwagger() throws Exception {
        Content content = Request.Get("http://localhost:8080/apidoc/swagger")
                .execute()
                .returnContent();

        Assert.assertNotSame("", content.asString());
    }

    // @Test
    public void testGetSwaggerfile_json() throws Exception {
        Content content = Request.Get("http://localhost:8080/apidoc/swaggerfile.json")
                .execute()
                .returnContent();

        Assert.assertNotSame("", content.asString());
    }
}
