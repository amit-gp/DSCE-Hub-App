package com.example.amit.dsiofficial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostNotificationActivity extends Activity {

    private TextView title, description, submit;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    String notifUrl = UrlStrings.notificationUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_notification);

        title = (TextView) findViewById(R.id.newNotifTitle);
        description = (TextView) findViewById(R.id.newNotifDescription);
        submit = (TextView) findViewById(R.id.submitNotifTextView);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendAndPrintResponse();
            }
        });
    }

    private void sendAndPrintResponse()
    {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue(this);
        JSONObject loginJsonObject = null;
        try {

            Log.d("ALERT !!", title.getText().toString());

            loginJsonObject = new JSONObject().put("messageTitle", title.getText()).put("message", description.getText()).put("messageLevel", "college"); //----------HARDCODED VALUE !!!!!!--------------
        }catch (Exception e){e.printStackTrace();}

        Log.d("ALERT !!", notifUrl);
        Log.d("ALERT !!", loginJsonObject.toString());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, notifUrl, loginJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("ALERT !!", response.toString());
                loading.dismiss();
                parseJsonResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                error.printStackTrace();
                Log.i("ALERT ERROR!!", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void parseJsonResponse(JSONObject response)
    {
        String responseString = "";
        try{

            responseString = response.getString("Title");
            Toast.makeText(this, responseString, Toast.LENGTH_SHORT).show();
        }catch (Exception e){e.printStackTrace();}

        finish();
    }
}
