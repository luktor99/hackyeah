package com.example.huber.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RESTClient {
    private final String mainEndpoint = "http://search.twitter.com/search.json?q=jQuery&result_type=recent&rpp=3";

    private RequestQueue requestQueue;

    RESTClient() {
    }

    public void sendPOST(Map<String, String> jsonParams, String api, final RESTCallback callback) throws JSONException {

        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, mainEndpoint + api,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.postResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.postError(error);
                        System.out.print(error);
                    }
                }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
            }
        };
        requestQueue.add(postRequest);
    };
}

