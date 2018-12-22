package com.example.user1.myapplication.Network;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;
import com.example.user1.myapplication.Model.LoginResponse;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.ObjectSurvey;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.Model.SendAnswersRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity.REQUEST_CODE;

public class SurveyHelper {

    boolean makanan=false;
    private static final String TAG = SurveyHelper.class.getSimpleName();
    private static SurveyHelper instance;
    private static Activity sActivity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static DatabaseProvider db;


    private SurveyHelper() {
        sharedPreferences = sActivity.getSharedPreferences("pref_user", MODE_PRIVATE);
    }

    public static SurveyHelper getInstance(Activity activity) {
        sActivity = activity;

        if (instance == null) instance = new SurveyHelper();
        db = DatabaseProvider.getInstance();
        return instance;
    }

    public void loginService(String username, String userPassword, String password) {
        try {
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
                        //save password

                        getMainGroups(password);

                    } catch (Exception e) {
                        Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void getMainGroups(String password) {
        try {
            JSONObject body = new JSONObject();
            body.put("password", password);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            Call<ArrayList<MainGroupResponse>> getMainGroupService = service.getMainGroups(body.toString());
            getMainGroupService.enqueue(new Callback<ArrayList<MainGroupResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<MainGroupResponse>> call, Response<ArrayList<MainGroupResponse>> response) {
                    try {
                        db = DatabaseProvider.getInstance();
                        db.insert(response.body());
                        Toast.makeText(sActivity, "success maingroup", Toast.LENGTH_SHORT).show();
                        getAllQuestions(password,"1");
                    } catch (Exception e) {
                        Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("1234567", "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<MainGroupResponse>> call, Throwable t) {
                    Toast.makeText(sActivity, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException e) {
            Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void getAllQuestions(String password , String period){
        try {
            JSONObject body = new JSONObject();
            body.put("password", password);
            body.put("period", period);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            service.getAllQuestions(body.toString()).enqueue(new Callback<ArrayList<QuestionResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<QuestionResponse>> call, Response<ArrayList<QuestionResponse>> response) {
                    try{
                        db.insert(response.body());
                        sActivity.startActivity(new Intent(sActivity, MainGroupActivity.class));
                        sActivity.finish();
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<QuestionResponse>> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage() );
                }
            });
        } catch (JSONException e){
            Log.e(TAG, "getAllQuestions: " + e.getMessage() );
        }
    }

    public boolean getAllQuestions23(String password , String period , String mgid){
        Log.d("lala4", "onCreate: " + makanan);
        try {
            Log.d("lala5", "onCreate: "+ makanan);
            JSONObject body = new JSONObject();
            body.put("password", password);
            body.put("period", period);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            service.getAllQuestions(body.toString()).enqueue(new Callback<ArrayList<QuestionResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<QuestionResponse>> call, Response<ArrayList<QuestionResponse>> response) {
                    try{
                        Log.d("lala6", "onCreate: "+ makanan);
                        db.insert(response.body());
                        makanan=true;

                    } catch (Exception e) {
                        Log.d("lala7", "onCreate: "+ makanan);
                        makanan=false;
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<QuestionResponse>> call, Throwable t) {
                    Log.d("lala8", "onCreate: "+ makanan);
                    makanan=false;
                    Log.e(TAG, "onFailure: " + t.getMessage() );
                }
            });
        } catch (JSONException e){
            Log.d("lala9", "onCreate: "+ makanan);
            makanan=false;
            Log.e(TAG, "getAllQuestions: " + e.getMessage() );
        }
        Log.d("lala10", "onCreate: "+ makanan);
        return makanan;
    }


    public void sendAnswer(MainGroupResponse mgResponse, ObjectSurvey objectSurvey, ArrayList<QuestionResponse> questionsModel , String password) {
        if (!objectSurvey.isStatus()) {
            HashMap<String, String> answerHeaderData = new HashMap<>();
            for (int i = 0; i < mgResponse.getAnswerHeaderFields().size(); i++) {
                String field = mgResponse.getAnswerHeaderFields().get(i);
                String answer = objectSurvey.getAnswerHeader().get(i);
                answerHeaderData.put(field, answer);
            }

            answerHeaderData.put("user_id", sharedPreferences.getString("user_id","")); //ada di pref
            answerHeaderData.put("period", questionsModel.get(0).getPeriod()); // ada di realm

            ArrayList<HashMap<String, String>> answerLinesData = new ArrayList<>();

            for (int i = 0; i < questionsModel.size(); i++) {
                String questionId = questionsModel.get(i).getId();
                String answer = questionsModel.get(i).getJawabanUserAsString();
                HashMap<String, String> map = new HashMap<>();
                map.put("question_id", questionId);
                map.put("answer", answer);
                answerLinesData.add(map);
            }
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            Call<SendAnswersRequest> call = service.sendAnswers(new SendAnswersRequest(answerHeaderData, answerLinesData, password));
            call.enqueue(new Callback<SendAnswersRequest>() {
                @Override
                public void onResponse(Call<SendAnswersRequest> call, Response<SendAnswersRequest> response) {
                    try {
                        if (response.body().getMessage().equals("")) {
                            Toast.makeText(sActivity, "Success", Toast.LENGTH_SHORT).show();
                            db = DatabaseProvider.getInstance();
                            db.update(objectSurvey);
                            /*Intent intent = new Intent(sActivity, ShowDataActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            sActivity.startActivity(intent);*/
                            Intent i = new Intent();
                            sActivity.setResult(REQUEST_CODE, i);
                            sActivity.finish();
                        } else {
                            Toast.makeText(sActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SendAnswersRequest> call, Throwable t) {
                    Toast.makeText(sActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(sActivity, "This header is already syncronized", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeSource(){
        db.close();
    }
}
