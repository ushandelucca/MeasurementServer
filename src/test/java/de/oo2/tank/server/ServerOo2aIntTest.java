package de.oo2.tank.server;

import com.google.gson.Gson;
import de.oo2.tank.server.model.ResponseError;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Request;
import org.junit.Assert;

public class ServerOo2aIntTest {
    private Gson gson = new Gson();

    // @Test
    public void testGet() throws Exception {
        HttpResponse httpResponse = Request.Get("https://www.oo2a.de/api/tank/temperatures/54651022bffebc03098b4567")
                .execute()
                .returnResponse();

        int code = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(400, code);

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        Assert.assertEquals("No temperature measurement with id '54651022bffebc03098b4567' found!", errorMessage.getMessage());

    }


}
