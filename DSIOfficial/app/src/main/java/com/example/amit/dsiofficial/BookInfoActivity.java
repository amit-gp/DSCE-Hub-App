package com.example.amit.dsiofficial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BookInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        Bundle extras = getIntent().getExtras();
        Toast.makeText(this, extras.getString("Name"), Toast.LENGTH_SHORT).show();
    }
}
