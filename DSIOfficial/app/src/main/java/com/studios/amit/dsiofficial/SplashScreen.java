package com.studios.amit.dsiofficial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

    private static int SPLASH_TIMEOUT = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Intent startIntent = new Intent(SplashScreen.this, MainActivity.class);

        Thread timer = new Thread(){

            public void run(){
                try{
                    sleep(SPLASH_TIMEOUT);
                }catch (Exception e){e.printStackTrace();}
                finally {
                    startActivity(startIntent);
                    finish();
                }
            }
        };

        timer.start();
    }
}
