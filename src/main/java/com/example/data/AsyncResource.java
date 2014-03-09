package com.example.data;

import com.sun.javafx.iio.ImageStorage;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Path("/resource")
public class AsyncResource extends Application {
    private ExecutorService threadExecutor = Executors.newFixedThreadPool(3);

    public AsyncResource() {
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(AsyncResource.class);
        return s;
    }

    private void initProcessing(final AsyncResponse asyncResponse, int timeout) {
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(timeout, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                if (throwable == null) {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("Successful response");
                } else {
                    System.out.println("Un-successful response, exception: " + throwable.getMessage());
                }
            }
        });
    }
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public void asyncGetWithTimeout(@Suspended final AsyncResponse asyncResponse, @QueryParam(value = "name") final String name) {
        initProcessing(asyncResponse, 20);

        threadExecutor.submit(new Runnable() {
            @Override
            public void run() {
                ResponseMessage msg = new ResponseMessage();

                if (name != null) {
                    RemoteCall call = new RemoteCall();
                    msg.setMessage(call.getDescription(name));
                }

                asyncResponse.resume(Response.ok(msg).build());
            }
        });
    }
}
