package com.vientamthuong.eatsimple.loadData;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyPool {

    private static VolleyPool volleyPool;
    private final RequestQueue requestQueue;

    private VolleyPool(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyPool getInstance(Context context) {
        if (volleyPool == null) {
            volleyPool = new VolleyPool(context);
        }
        return volleyPool;
    }

    public <T> void addRequest(Request<T> request) {
        requestQueue.add(request);
    }

}
