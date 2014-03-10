package com.example.data.test;

import com.example.data.AsyncResource;
import junit.framework.Assert;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.concurrent.Future;

public class AsyncResourceTest extends JerseyTest {
    private static final String LOCALHOST = "http://localhost:9998/";
    private static final String EXPECTED_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><responseMessage><message>Famous ruler.</message></responseMessage>";

    @Override
    protected Application configure() {
        return new ResourceConfig(AsyncResource.class);
    }

    @Test
    public void testRestCall() {
        try {
            Client client = ClientBuilder.newClient();
            Future<Response> responseFuture = client.target(LOCALHOST + "resource?name=Rory")
                    .request().async().get(new InvocationCallback<Response>() {
                        @Override
                        public void completed(Response response) {
                            System.out.println("Response status code "
                                    + response.getStatus() + " received.");
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            System.out.println("Invocation failed.");
                            throwable.printStackTrace();
                        }
                    });
            String responseMsg = responseFuture.get().readEntity(String.class);
            System.out.println("Response: " + responseMsg);
            Assert.assertTrue(responseMsg != null);
            Assert.assertTrue(EXPECTED_RESPONSE.equals(responseMsg));
        } catch (Throwable e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
}