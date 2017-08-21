package com.example.amit.dsiofficial;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class PostNotificationActivity extends AppCompatActivity {

    private TextView title, description, submit;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    String notifUrl = UrlStrings.notificationUrl;
    String notifAttachUrl = UrlStrings.notificationAttachmentUrl;
    private ImageButton attachButton, cameraFileButton;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;
    private File fileChosen;
    private String absoluteFileChosen;
    private TextView fileChosenTextView;
    private String finalFileName;
    private int REQUEST_CODE = 1;
    public boolean isUploaded = false;
    private String year;
    //private ArrayList<String> selectedDocs;
    //private ArrayList<String> photoPaths;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_notification);

        cameraFileButton = (ImageButton) findViewById(R.id.cameraButton);
        fileChosenTextView = (TextView) findViewById(R.id.fileChosenTextView);
        relativeLayout = (RelativeLayout) findViewById(R.id.post_relative_layout);
        title = (TextView) findViewById(R.id.newNotifTitle);
        description = (TextView) findViewById(R.id.newNotifDescription);
        submit = (TextView) findViewById(R.id.submitNotifTextView);
        attachButton = (ImageButton) findViewById(R.id.attachButton);

        Spinner spinner = (Spinner) findViewById(R.id.yearSpinnerPostNotif);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.years_array_post, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                year = (String) adapterView.getItemAtPosition(i);
                if(year.equals("All years")){
                    year = "college";
                }
                //Toast.makeText(NewBookActivity.this, subject, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.getText().equals("")){
                    Toast.makeText(PostNotificationActivity.this, "Cannot leave title empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(fileChosen != null){

                    //final ProgressDialog progress = new ProgressDialog(PostNotificationActivity.this);
                    //progress.setTitle("Uploading");
                    //progress.setMessage("Please wait...");
                    //progress.show();
                    //Toast.makeText(PostNotificationActivity.this, "Uploading", Toast.LENGTH_SHORT).show();

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String content_type  = getMimeType(fileChosen.getPath());
                            RequestBody file_body = RequestBody.create(MediaType.parse(content_type),fileChosen);

                            OkHttpClient client = new OkHttpClient();
                            RequestBody request_body = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("type",content_type)
                                    .addFormDataPart("profileImage",absoluteFileChosen, file_body)
                                    .build();

                            okhttp3.Request request = new okhttp3.Request.Builder()
                                    .url(notifAttachUrl + "?extension=" + absoluteFileChosen.substring(absoluteFileChosen.lastIndexOf(".")) + "&attachmentName=" + finalFileName)
                                    .post(request_body)
                                    .build();

                            try {
                                okhttp3.Response response = client.newCall(request).execute();

                                if(!response.isSuccessful()){
                                    throw new IOException("Error : "+response);
                                }

                                //Toast.makeText(PostNotificationActivity.this, "File uploaded", Toast.LENGTH_SHORT).show();
                                isUploaded = true;
                                //progress.dismiss();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                }

                sendAndPrintResponse();
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enableAttachButton();

    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void enableAttachButton() {

        cameraFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.amit.dsiofficial",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                }
            }
        });

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(PostNotificationActivity.this)
                        .withRequestCode(10)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == 10 && resultCode == RESULT_OK){

            fileChosen  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            String file_path = fileChosen.getAbsolutePath();
            absoluteFileChosen = file_path.substring(file_path.lastIndexOf("/")+1);
            fileChosenTextView.setText(absoluteFileChosen);
            finalFileName = Long.toString(Calendar.getInstance().getTimeInMillis());
        }

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            Log.d("ALERT !!", "All good");
            fileChosen = new File(mCurrentPhotoPath);
            String file_path = fileChosen.getAbsolutePath();
            absoluteFileChosen = file_path.substring(file_path.lastIndexOf("/")+1);
            fileChosenTextView.setText(absoluteFileChosen);
            finalFileName = Long.toString(Calendar.getInstance().getTimeInMillis());
            //title.setText(mCurrentPhotoPath);

        }
    }

    private String getMimeType(String path) {

        //String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        String extension = absoluteFileChosen.substring(absoluteFileChosen.lastIndexOf(".") + 1);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            enableAttachButton();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to go back?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PostNotificationActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void sendAndPrintResponse()
    {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue(this);
        JSONObject loginJsonObject = null;
        try {

            Log.d("ALERT !!", title.getText().toString());

            loginJsonObject = new JSONObject().put("messageTitle", title.getText()).put("message", description.getText()).put("messageLevel", year);  //----------HARDCODED VALUE !!!!!!--------------
            if(fileChosen == null){
                loginJsonObject.put("hasAttachment", "false");
            }
            else {
                loginJsonObject.put("hasAttachment", "true").put("attachmentName", finalFileName).put("attachmentType", absoluteFileChosen.substring(absoluteFileChosen.lastIndexOf(".")));
                //loginJsonObject.put("attachmentType", absoluteFileChosen.substring(absoluteFileChosen.lastIndexOf(".")));
            }
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
                finish();
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

            responseString = response.getString("messageTitle");
            //Toast.makeText(this, responseString, Toast.LENGTH_SHORT).show();
        }catch (Exception e){e.printStackTrace();}

        finish();
    }
}
