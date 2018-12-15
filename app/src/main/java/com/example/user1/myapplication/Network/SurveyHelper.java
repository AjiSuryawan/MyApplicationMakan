package com.example.user1.myapplication.Network;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;
import com.example.user1.myapplication.MainGroupSection.MainGroupAdapter;
import com.example.user1.myapplication.Model.AllQuestionResponse;
import com.example.user1.myapplication.Model.LoginResponse;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SurveyHelper {

    private static final String TAG = SurveyHelper.class.getSimpleName();
    private static SurveyHelper instance;
    private static Activity sActivity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SurveyHelper() {
    }

    public static SurveyHelper getInstance(Activity activity) {
        sActivity = activity;

        if (instance == null) instance = new SurveyHelper();
        return instance;
    }

    public void loginService(String username, String userPassword, String password) {
        try {
            sharedPreferences = sActivity.getSharedPreferences("pref_user", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("userpassword", userPassword);
            jsonObject.put("password", password);

            Log.e("jsonobject", "onClick: " + jsonObject.toString());
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
                        editor.putInt("pernah_login", 1);
                        editor.apply();
                        Toast.makeText(sActivity, "success login", Toast.LENGTH_SHORT).show();
                        Log.e("success", "onResponse: " + response.body().getEmail());

                        MainGroupActivity m = new MainGroupActivity();
                        getMainGroups(password, m.adapter);

                    } catch (Exception e) {
                        Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("1234567", "login: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(sActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getMainGroups(String password, MainGroupAdapter adapter) {
        try {
            JSONObject body = new JSONObject();
            body.put("password", password);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            Call<ArrayList<MainGroupResponse>> getMainGroupService = service.getMainGroups(body.toString());
            getMainGroupService.enqueue(new Callback<ArrayList<MainGroupResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<MainGroupResponse>> call, Response<ArrayList<MainGroupResponse>> response) {
                    try {
                        DatabaseProvider db = DatabaseProvider.getInstance();
                        db.insert(response.body());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(sActivity, "success maingroup", Toast.LENGTH_SHORT).show();
                        getAllQuestions(password);
                    } catch (Exception e) {
                        Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("1234567", "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<MainGroupResponse>> call, Throwable t) {
                    Toast.makeText(sActivity, t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("1234567", "onFailure: " + t.getMessage());
                }
            });
        } catch (JSONException e) {
            Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("1234567", "exception main: " + e.getMessage());
        }
    }

    public void getAllQuestions(String password) {
        try {
            JSONObject body = new JSONObject();
            body.put("period", 1);
            body.put("password", password);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            Call<ArrayList<AllQuestionResponse>> getAllQuestions = service.getAllQuestions(body.toString());
            getAllQuestions.enqueue(new Callback<ArrayList<AllQuestionResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<AllQuestionResponse>> call, Response<ArrayList<AllQuestionResponse>> response) {
                    try {
                        DatabaseProvider db = DatabaseProvider.getInstance();
                        db.insert(response.body());
                        sActivity.startActivity(new Intent(sActivity, MainGroupActivity.class));
                        sActivity.finish();
                    } catch (Exception e){
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<AllQuestionResponse>> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage() );
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "getAllQuestions: " + e.getMessage());
        }
    }
}
