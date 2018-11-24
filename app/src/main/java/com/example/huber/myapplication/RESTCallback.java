package com.example.huber.myapplication;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface RESTCallback {
    public void postResponse(JSONObject response);
    public void postError(VolleyError error);
}
