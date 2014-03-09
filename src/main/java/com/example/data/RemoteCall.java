package com.example.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class RemoteCall {
    private final String nameUrl = "http://www.sheknows.com/baby-names/search/";
    private final String agent = "User-Agent\tMozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.71 (KHTML, like Gecko) Version/6.1 Safari/537.71";

    public String getDescription(String name) {
        try {
            Document doc = Jsoup.connect(nameUrl + name).get();
            // System.out.println("Doc: " + doc.toString());
            Element description = doc.select("div.blurb").first();
            return description == null ? "" : description.text().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static void main(String [] args) {
        RemoteCall call = new RemoteCall();
        String result = call.getDescription("rory");
        System.out.println("Result: " + result);
    }
}