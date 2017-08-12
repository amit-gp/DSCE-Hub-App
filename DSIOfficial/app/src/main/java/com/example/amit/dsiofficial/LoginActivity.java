package com.example.amit.dsiofficial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class LoginActivity extends Activity {

    private EditText passwordEditText, emailEditText;
    private TextView signUpTextView,loginTextView, forgotPasswordTextView;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonLoginRequest;
    private String loginURL = UrlStrings.loginUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginTextView = (TextView) findViewById(R.id.lin);
        forgotPasswordTextView = (TextView) findViewById(R.id.forgotPassword);
        emailEditText = (EditText) findViewById(R.id.mail);
        passwordEditText = (EditText) findViewById(R.id.pswrdd);
        signUpTextView = (TextView) findViewById(R.id.sup);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/ColabLig.otf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/ColabLig.otf");
        loginTextView.setTypeface(custom_font1);
        signUpTextView.setTypeface(custom_font);
        emailEditText.setTypeface(custom_font);
        passwordEditText.setTypeface(custom_font);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implement login
                sendAndPrintResponse();
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implement forgot password
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(it);
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

            loginJsonObject = new JSONObject().put("Email", emailEditText.getText()).put("Password", passwordEditText.getText());
        }catch (Exception e){e.printStackTrace();}

        jsonLoginRequest = new JsonObjectRequest(Request.Method.POST, loginURL, loginJsonObject, new Response.Listener<JSONObject>() {
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

        requestQueue.add(jsonLoginRequest);
    }

    private void parseJsonResponse(JSONObject response)
    {
        String responseString = "";
        try{

            responseString = response.getString("Login");
        }catch (Exception e){e.printStackTrace();}

        if(responseString.equals("Unsuccessful")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Login UnSuccessful")
                    .setCancelable(true)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Returns back to previous page
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        try {

            User.setEmail(response.getString("Email"));
            User.setPassword(response.getString("Password"));
            User.setPhoneNum(response.getString("ContactNumber"));
            User.setUserName(response.getString("Name"));
            Toast.makeText(this, response.getString("Email"), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
