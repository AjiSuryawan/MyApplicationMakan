package com.example.user1.myapplication.LoginSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = DatabaseProvider.class.getSimpleName();

    EditText etInputUsername;
    EditText etInputUserpassword;
    EditText etInputPassword;
    private SurveyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                btnlogin.setEnabled(false);
                helper.loginService(username, userPassword, password);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeSource();
    }
}
