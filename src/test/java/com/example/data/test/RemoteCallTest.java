package com.example.data.test;

import com.example.data.RemoteCall;
import junit.framework.Assert;
import org.junit.Test;

public class RemoteCallTest {
    @Test
    public void testRestCall() {
        RemoteCall call = new RemoteCall();
        String result = call.getDescription("rory");
        System.out.println("Result: " + result);
        Assert.assertTrue(result.equals("Famous ruler."));
    }
}