package com.vientamthuong.eatsimple.loadData;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyPool {

    private static VolleyPool volleyPool;
    private final RequestQueue requestQueue;

    private VolleyPool(AppCompatActivity appCompatActivity) {
        requestQueue = Volley.newRequestQueue(appCompatActivity);
    }

    public static VolleyPool getInstance(AppCompatActivity appCompatActivity) {
        if (volleyPool == null) {
            volleyPool = new VolleyPool(appCompatActivity);
        }
        return volleyPool;
    }

    public <T> void addRequest(Request<T> request) {
        requestQueue.add(request);
    }

}
