package com.studios.amit.dsiofficial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        version = (TextView) findViewById(R.id.versionTextView);
        version.setText(BuildConfig.VERSION_NAME);
    }
}
