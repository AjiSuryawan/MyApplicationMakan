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

import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;
import com.example.user1.myapplication.Model.LoginResponse;
import com.example.user1.myapplication.Network.SurveyClient;
import com.example.user1.myapplication.Network.SurveyService;
import com.example.user1.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etInputUsername;
    EditText etInputUserpassword;
    EditText etInputPassword;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("pref_user", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("survey.realm").build();
        Realm.setDefaultConfiguration(configuration);

        etInputUsername = findViewById(R.id.txtusername);
        etInputPassword = findViewById(R.id.txtpassword);
        etInputUserpassword = findViewById(R.id.et_userpassword);

        Button btnlogin=(Button)findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etInputUsername.getText().toString();
                final String userPassword = etInputUserpassword.getText().toString();
                final String password = etInputPassword.getText().toString();
                if(username.isEmpty() || userPassword.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Form is empty", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("username", username);
                        jsonObject.put("userpassword", userPassword);
                        jsonObject.put("password", password);

                        Log.e("jsonobject", "onClick: " + jsonObject.toString() );
                        SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
                        Call<LoginResponse> loginService = service.loginService(jsonObject.toString());
                        loginService.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                try {
                                    editor.putString("user_id", response.body().getId());
                                    editor.putString("user_username", response.body().getUsername());
                                    editor.putString("user_name", response.body().getName());
                                    editor.putString("user_password", password);
                                    editor.putString("user_email", response.body().getEmail());
                                    editor.putString("user_status", response.body().getStatus());
                                    editor.apply();
                                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    Log.e("success", "onResponse: " + response.body().getEmail());

                                    Intent intent = new Intent(LoginActivity.this, MainGroupActivity.class);
                                    startActivity(intent);
                                    finish();

                                } catch (Exception e){
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
