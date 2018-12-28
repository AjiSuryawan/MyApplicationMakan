package com.example.user1.myapplication.LoginSection;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;
import com.example.user1.myapplication.MainGroupSection.MainGroupAdapter;
import com.example.user1.myapplication.Model.LoginResponse;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.Network.SurveyClient;
import com.example.user1.myapplication.Network.SurveyHelper;
import com.example.user1.myapplication.Network.SurveyService;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = DatabaseProvider.class.getSimpleName();
    String fileName = "domainweb" + ".txt";//like 2016_01_12.txt
    File gpxfile;
    EditText etInputUsername;
    EditText etInputUserpassword;
    EditText etInputPassword;
    private SurveyHelper helper;
    File root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        root= new File(Environment.getExternalStorageDirectory()+File.separator+"surveyku", "folderku");

        if (Build.VERSION.SDK_INT >= 23) {
            //do your check here
            isStoragePermissionGranted();
        }



        helper = SurveyHelper.getInstance(this);



        etInputUsername = findViewById(R.id.txtusername);
        etInputPassword = findViewById(R.id.txtpassword);
        etInputUserpassword = findViewById(R.id.et_userpassword);

        Button btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(v -> {
            final String username = etInputUsername.getText().toString();
            final String userPassword = etInputUserpassword.getText().toString();
            final String password = etInputPassword.getText().toString();
            if(username.isEmpty() || userPassword.isEmpty() || password.isEmpty()){
                Toast.makeText(LoginActivity.this, "Form is empty", Toast.LENGTH_SHORT).show();
            } else {
                helper.loginService(username, userPassword, password);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeSource();
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("weleh","Permission is granted");
                return true;
            } else {

                Log.v("weleh","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("weleh","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("weleh","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            try
            {
                if (!root.exists())
                {
                    root.mkdirs();
                }
                gpxfile = new File(root, fileName);
                FileWriter writer = new FileWriter(gpxfile,true);
                writer.append("http://www.unicef-schoolprofiling.com/");
                writer.flush();
                writer.close();
                Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
            }
            catch(IOException e)
            {
                Log.d("weleh", "onCreate: "+e.toString());
            }


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
        }
    }
}
