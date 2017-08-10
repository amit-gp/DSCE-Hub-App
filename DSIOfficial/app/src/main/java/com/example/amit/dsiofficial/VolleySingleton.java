package com.example.amit.dsiofficial;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Amit on 10-08-2017.
 */

public class VolleySingleton {

    private static VolleySingleton volleySingletonInstance;
    private RequestQueue requestQueue;

    public static VolleySingleton getInstance(Context context) {

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
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}
