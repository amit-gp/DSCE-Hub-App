package com.studios.amit.dsiofficial;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by Amit on 10-08-2017.
 */

public class VolleySingleton {

    private static VolleySingleton volleySingletonInstance;
    private RequestQueue requestQueue;

    public static synchronized VolleySingleton getInstance(Context context) {

        if (volleySingletonInstance == null) {
            volleySingletonInstance = new VolleySingleton(context);
        }
        return volleySingletonInstance;
    }

    private VolleySingleton(Context context) {
        getRequestQueue(context);
    }

    public RequestQueue getRequestQueue(Context context) {

        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return requestQueue;
    }
}
