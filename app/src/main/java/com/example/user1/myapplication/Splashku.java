package com.example.user1.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splashku extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashku);
        int SPLASH_TIME_OUT = 1500;
        new Handler().postDelayed(new Runnable() {

            /*
             * showing splash screen with a timer. This will be useful when you
             * want to show case your app logo/company
             */
            @Override
            public void run() {
                Intent i = new Intent(Splashku.this.getApplicationContext(), ControlClass.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
