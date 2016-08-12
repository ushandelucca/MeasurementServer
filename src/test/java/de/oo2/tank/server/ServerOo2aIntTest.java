package de.oo2.tank.server;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

public class ServerOo2aIntTest {
    private Gson gson = new Gson();

    @Test
    public void testGet() throws Exception {
        HttpResponse httpResponse = Request.Get("https://confluence.baloisenet.com/confluence/display/TAM/Architekturweiche+Java+8+Migration")
                .execute()
                .returnResponse();

        int code = httpResponse.getStatusLine().getStatusCode();

        Content content = new ContentResponseHandler().handleEntity(httpResponse.getEntity());
        // ResponseError errorMessage = gson.fromJson(content.toString(), ResponseError.class);

        System.out.println(content.toString());

    }


}
