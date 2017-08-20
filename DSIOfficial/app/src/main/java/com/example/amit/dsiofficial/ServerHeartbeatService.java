package com.example.amit.dsiofficial;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.media.RingtoneManager.getDefaultUri;

/**
 * Created by Amit on 20-08-2017.
 */



public class ServerHeartbeatService extends Service {

    int count = 0;
    String year, modifiedUrl;

    final class HeartBeatThreadClass implements Runnable{
        int SERVICE_ID;
        private RequestQueue requestQueue;
        private JsonObjectRequest jsonLoginRequest;

        HeartBeatThreadClass(int serviceId){
            this.SERVICE_ID = serviceId;
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        private void sendAndPrintResponse()
        {
            jsonLoginRequest = new JsonObjectRequest(Request.Method.GET, modifiedUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    parseJsonResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    showCannotConnectDialog();
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
            requestQueue.add(jsonLoginRequest);
        }

        private void showCannotConnectDialog()
        {
            run();
        }

        private void parseJsonResponse(JSONObject response)
        {
            try {
                if(Integer.parseInt(response.getString("Count")) > count){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.hub_logo)
                                    .setContentTitle("DSCE Hub")
                                    .setContentText("You have a new Notification");

                    Uri alarmSound = getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    mBuilder.setContentIntent(pIntent);
                    mBuilder.setSound(alarmSound);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, mBuilder.build());
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            synchronized (this){
                while (true){
                    try {
                        wait(10000);    //10 seconds
                        sendAndPrintResponse();
                    }catch (Exception e){e.printStackTrace();}
                }
            }
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if(intent == null){
            SharedPreferences p = getSharedPreferences("Login", MODE_PRIVATE);
            year = p.getString("year", null);
            count = p.getInt("num", 0);
        }

        if(intent != null){

            Bundle extras = intent.getExtras();
            count = extras.getInt("num");
            year = extras.getString("year");
        }

        Thread thread = new Thread(new HeartBeatThreadClass(startId));
        thread.start();
        modifiedUrl = UrlStrings.heartBeatUrl;
        modifiedUrl += "?year=" + year;
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
