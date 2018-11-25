package com.example.huber.myapplication;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RESTClient {
    private final static String mainEndpoint = "http://10.250.193.123:80";

    private RequestQueue mRequestQueue;

    private Context mContext;

    RESTClient(Context context) {
        NukeSSLCerts.nuke();
        // Instantiate the cache
        mContext = context;
        Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();
    }

    public void sendPOST(JSONObject jsonObj, String api, final RESTCallback callback) throws JSONException {

        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, mainEndpoint + api,
                jsonObj,
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
        mRequestQueue.add(postRequest);
    };
}

