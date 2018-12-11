package com.example.user1.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user1.myapplication.Model.LoginResponse;
import com.example.user1.myapplication.Network.SurveyClient;
import com.example.user1.myapplication.Network.SurveyService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    EditText etInputUsername;
    EditText etInputUserpassword;
    EditText etInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                    Toast.makeText(login.this, "Form is empty", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(login.this, "success", Toast.LENGTH_SHORT).show();
                                Log.e("success", "onResponse: " + response.body().getEmail());
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Toast.makeText(login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
