package com.example.amit.dsiofficial;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationInfoActivity extends AppCompatActivity {

    private Button button;
    private TextView title, description;
    private String attachmentName, attachmentType, hasAttachment;
    private DownloadManager downloadManager;
    private String uri = UrlStrings.downloadAttachmentUri;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_info);

        linearLayout = (LinearLayout) findViewById(R.id.notif_linear_layout);


        button = new Button(this);
        //button.setText("Download");
        button.setBackgroundResource(R.drawable.ic_attach_file);

        title = (TextView) findViewById(R.id.title_notif_info);
        description = (TextView) findViewById(R.id.desc_notif_info);

        Bundle extras = getIntent().getExtras();
        hasAttachment = extras.getString("hasAttachment");
        title.setText(extras.getString("messageTitle"));
        description.setText(extras.getString("message"));


        if(hasAttachment.equals("true")){

            linearLayout.addView(button);
            attachmentName = extras.getString("attachmentName");
            attachmentType = extras.getString("attachmentType");
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enableDownloadButton();
    }

    private void enableDownloadButton(){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(uri + "?filename=" + attachmentName + "&extension=" + attachmentType);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "file" + attachmentType);
                Long reference = downloadManager.enqueue(request);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            enableDownloadButton();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        }
    }
}
