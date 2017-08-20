package com.example.amit.dsiofficial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    private TextView name, email, phone, changePassword, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        year = (TextView) findViewById(R.id.myprofile_year);
        name = (TextView) findViewById(R.id.myprofile_name);
        email = (TextView) findViewById(R.id.myprofile_email);
        phone = (TextView) findViewById(R.id.myprofile_phone);
        changePassword = (TextView) findViewById(R.id.myprofile_changepassword);

        year.setText(User.getYear());
        name.setText(User.getUserName());
        email.setText(User.getEmail());
        phone.setText(User.getPhoneNum());
    }
}
