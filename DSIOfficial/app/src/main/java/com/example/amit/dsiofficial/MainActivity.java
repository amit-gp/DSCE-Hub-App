package com.example.amit.dsiofficial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    //private StringRequest stringRequest;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private String url = "http://192.168.0.138:4000/collegeNotification";
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTextView = (TextView) findViewById(R.id.messageContenTextView);
        sendAndPrintResponse();
    }

    private void sendAndPrintResponse()
    {
        requestQueue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue(this.getApplicationContext());

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("ALERT !!", response.toString());
                try
                {
                    messageTextView.setText(response.getJSONObject(0).getString("message"));
                }catch (Exception e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                messageTextView.setText(error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
