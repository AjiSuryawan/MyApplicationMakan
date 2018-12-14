package com.example.user1.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.user1.myapplication.LoginSection.LoginActivity;
import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ControlClass extends Activity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("survey.realm").build();
        Realm.setDefaultConfiguration(configuration);

        sharedPreferences = getSharedPreferences("pref_user", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("user_id","").equalsIgnoreCase("")){
            Intent in=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(in);
            finish();
        }
        else{
            Intent in=new Intent(getApplicationContext(),MainGroupActivity.class);
            startActivity(in);
            finish();
        }
    }
}