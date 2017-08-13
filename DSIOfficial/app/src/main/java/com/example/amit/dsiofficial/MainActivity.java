package com.example.amit.dsiofficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //private StringRequest stringRequest;
    private TextView messageTextView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_notifications:
                    Log.i("ALERT !!", "HOME CLICKED");
                    fragmentTransaction.replace(R.id.notificationFrame, new NotificationFragment()).commit();
                    return true;
                case R.id.navigation_textbooks_resale:
                    Log.i("ALERT !!", "dash CLICKED");
                    fragmentTransaction.replace(R.id.notificationFrame, new BookFragment()).commit();
                    return true;
            }
            return false;
        }

    };
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

        Boolean test = sp1.getBoolean("isLoggedIn", false);
        Log.i("ALERT !!", test.toString());

        if(sp1.getBoolean("isLoggedIn", false)) {

            User.setUserName(sp1.getString("Name", null));
            User.setPassword(sp1.getString("Password", null));
            User.setPhoneNum(sp1.getString("PhoneNum", null));
            User.setEmail(sp1.getString("Email", null));
            User.setIsLoggedin(true);
        }

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if(isFirstRun || !User.getIsLoggedin())
        {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            //Toast.makeText(this, "FIRST RUN t", Toast.LENGTH_LONG).show();
        }


        // -------CLIPBOARD
        /*
        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("Name",User.getUserName() );
        Ed.putString("Password",User.getPassword());
        Ed.putString("Email", User.getEmail());
        Ed.putString("PhoneNum", User.getPhoneNum());
        Ed.putBoolean("isLoggedIn", User.getIsLoggedin());
        Ed.commit();
        */

        // -------CLIPBOARD

        Log.i("ALERT !!", "STARTED !!!!!");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notificationFrame, new NotificationFragment()).commit();

    }

}

