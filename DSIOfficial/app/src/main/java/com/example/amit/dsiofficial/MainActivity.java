package com.example.amit.dsiofficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
            User.setIsAdmin(sp1.getBoolean("isAdmin", false));
            User.setYear(sp1.getString("year", null));
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


        Log.i("ALERT !!", "STARTED !!!!!");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 19, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notificationFrame, new NotificationFragment()).commit();

    }

}

