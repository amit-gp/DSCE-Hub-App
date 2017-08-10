package com.example.amit.dsiofficial;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private StringRequest stringRequest;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private String url = "http://192.168.43.244:4000/collegeNotification";
    private TextView messageTextView;
    private ArrayList<MessageNotification> messageNotifications;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("ALERT !!", "STARTED !!!!!");
        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        messageNotifications = new ArrayList<>();
        //messageTextView = (TextView) findViewById(R.id.messageContenTextView);
        sendAndPrintResponse();
    }

    private void sendAndPrintResponse()
    {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);

        requestQueue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue(this.getApplicationContext());

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("ALERT !!", response.toString());
                loading.dismiss();
                parseJsonArrayResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ALERT ERROR!!", error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void parseJsonArrayResponse(JSONArray jsonArray)
    {
        for (int i = 0; i < jsonArray.length(); i++)
        {
            MessageNotification messageNotification = new MessageNotification();
            JSONObject jsonObject = null;
            try{
                jsonObject = jsonArray.getJSONObject(i);
                messageNotification.setNotificationTitle(jsonObject.getString("messageTitle"));
                messageNotification.setNotificationBody(jsonObject.getString("message"));
            }catch (Exception e){e.printStackTrace();}
            messageNotifications.add(messageNotification);
        }

        //Finally initializing our adapter
        adapter = new CardAdapter(messageNotifications, this);
        //Adding adapter to recyclerView
        recyclerView.setAdapter(adapter);
    }
}

