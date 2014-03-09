package com.example.data.test;

import com.example.data.AsyncResource;
import com.example.data.RemoteCall;
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

public class RemoteCallTest {
    @Test
    public void testRestCall() {
        RemoteCall call = new RemoteCall();
        String result = call.getDescription("rory");
        System.out.println("Result: " + result);
        Assert.assertTrue(result.equals("Famous ruler."));
    }
}