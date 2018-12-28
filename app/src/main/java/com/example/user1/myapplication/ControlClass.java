package com.example.user1.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.user1.myapplication.LoginSection.LoginActivity;
import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;
import com.example.user1.myapplication.Network.SurveyClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ControlClass extends Activity {
    private SharedPreferences sharedPreferences;
    File root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root= new File(Environment.getExternalStorageDirectory()+File.separator+"surveyku", "folderku");

        //
        File file = new File(root,"domainweb" + ".txt");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        SurveyClient.BASE_URL=text.toString();
        //

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("survey.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        sharedPreferences = getSharedPreferences("pref_user", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("user_id","").equalsIgnoreCase("")){
            Intent in=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(in);
            finish();
        }
        else{
            Intent in = new Intent(this, MainGroupActivity.class);
            startActivity(in);
            finish();
        }
    }



}