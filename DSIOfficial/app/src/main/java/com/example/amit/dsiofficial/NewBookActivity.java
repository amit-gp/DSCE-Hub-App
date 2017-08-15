package com.example.amit.dsiofficial;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class NewBookActivity extends AppCompatActivity {

    private EditText  title, author, price, description;
    private TextView submitBookButton;
    private String subject;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    String bookUrl = UrlStrings.bookUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        title = (EditText) findViewById(R.id.newBookTitle);
        author = (EditText) findViewById(R.id.newBookAuthor);
        price = (EditText) findViewById(R.id.newBookPrice);
        description = (EditText) findViewById(R.id.newBookDescription);
        submitBookButton = (TextView) findViewById(R.id.submitBookTextView);
        Spinner spinner = (Spinner) findViewById(R.id.subjectSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.subjects_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        submitBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendAndPrintResponse();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                subject = (String) adapterView.getItemAtPosition(i);
                //Toast.makeText(NewBookActivity.this, subject, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

            loginJsonObject = new JSONObject().put("Title", title.getText()).put("Author", author.getText()).put("Price", price.getText()).put("Subject", subject).put("Description", "false").put("Email", User.getEmail()).put("Name", User.getUserName()).put("ContactNumber", User.getPhoneNum());
        }catch (Exception e){e.printStackTrace();}

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, bookUrl, loginJsonObject, new Response.Listener<JSONObject>() {
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
