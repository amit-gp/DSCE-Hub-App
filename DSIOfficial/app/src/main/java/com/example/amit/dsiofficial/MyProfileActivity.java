package com.example.amit.dsiofficial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    private TextView name, email, phone, changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name = (TextView) findViewById(R.id.myprofile_name);
        email = (TextView) findViewById(R.id.myprofile_email);
        phone = (TextView) findViewById(R.id.myprofile_phone);
        changePassword = (TextView) findViewById(R.id.myprofile_changepassword);

        name.setText(User.getUserName());
        email.setText(User.getEmail());
        phone.setText(User.getPhoneNum());
    }
}
