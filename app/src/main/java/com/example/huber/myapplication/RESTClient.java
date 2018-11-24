package com.example.huber.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RESTClient {
    private URL mainEndpoint;
    private HttpGetRequest getRequest;

    RESTClient() {
        try {
            mainEndpoint = new URL("http://search.twitter.com/search.json?q=jQuery&result_type=recent&rpp=3");
            getRequest = new HttpGetRequest();
        } catch (java.io.IOException ex) {
            String msg = "Wring URL" + ex.toString();
            Log.e("ERROR", msg);
        }
    }

    public String getData() {
        try {
            return getRequest.execute(mainEndpoint.toString()).get();
        } catch (java.util.concurrent.ExecutionException | java.lang.InterruptedException ex) {
            return "lol";
        }
    }
}

