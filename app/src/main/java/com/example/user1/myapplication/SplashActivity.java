package com.example.user1.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user1.myapplication.LoginSection.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("splash_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getInt("first", 0) == 1){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1200);
                        if(sharedPreferences.getInt("first", 0) == 0){
                            editor.putInt("first", 1);
                            editor.apply();
                        }
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
