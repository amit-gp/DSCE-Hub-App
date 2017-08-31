package com.studios.amit.dsiofficial;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BookInfoActivity extends AppCompatActivity {

    private TextView title, author, subject, price, name, phone, email, call;
    private Boolean myBook;
    private JsonObjectRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private String bookUrl = UrlStrings.bookUrl;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(myBook)
            getMenuInflater().inflate(R.menu.delete_book, menu);
        return true;
    }

    //--------------------------------------------------------------TODO-----------------------------------------------------------------------
    public static String SHA256 (String text) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        byte[] digest = md.digest();
        return Base64.encodeToString(digest, Base64.DEFAULT);
    }
    //--------------------------------------------------------------TODO-----------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.delete_mybook){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure that you want to delete your book ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Returns back to previous page
                            try{
                               // Log.d("ALERT !!", SHA256(User.getUserName()));
                            }catch (Exception e){e.printStackTrace();}
                            sendAndPrintResponse();
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Returns back to previous page
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        return true;
    }

    private void sendAndPrintResponse()
    {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue(this);

        Log.d("ALERT !!", bookUrl);
        jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, bookUrl + "?delete=true&name=" + User.getUserName() + "&book=" + title.getText(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("ALERT !!", response.toString());
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Your book has been deleted.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                //showCannotConnectDialog();
                Toast.makeText(getApplicationContext(), "Your book could not be deleted !", Toast.LENGTH_SHORT).show();
                Log.i("ALERT ERROR!!", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void showCannotConnectDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cannot connect")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Returns back to previous page
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        title = (TextView) findViewById(R.id.infoTitle);
        author = (TextView) findViewById(R.id.infoAuthor);
        subject = (TextView) findViewById(R.id.infoSubject);
        price = (TextView) findViewById(R.id.infoPrice);
        name = (TextView) findViewById(R.id.infoBookName);
        phone = (TextView) findViewById(R.id.infoPhone);
        email = (TextView) findViewById(R.id.infoEmail);
        call = (TextView) findViewById(R.id.callSeller);

        Bundle extras = getIntent().getExtras();

        myBook = Boolean.parseBoolean(extras.getString("MyBook"));
        title.setText(extras.getString("Title"));
        author.setText(extras.getString("Author"));
        subject.setText(extras.getString("Subject"));
        price.setText(extras.getString("Price"));
        name.setText(extras.getString("Name"));
        phone.setText(extras.getString("Phone"));
        email.setText(extras.getString("Email"));
        Log.d("ALERT !!", phone.getText().toString());
        Log.d("ALERT !!", extras.getString("Phone"));

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = "tel:" + phone.getText().toString();
                Log.d("ALERT !!", phone.getText().toString());

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(number));
                startActivity(intent);
            }
        });
    }
}
