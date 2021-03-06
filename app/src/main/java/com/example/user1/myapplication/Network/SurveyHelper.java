package com.example.user1.myapplication.Network;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.example.user1.myapplication.QuestionHeader.NextActivity;
import com.example.user1.myapplication.QuestionHeader.QuestionHeaderActivity;

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
    ProgressDialog progress;
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
            progress = new ProgressDialog(sActivity);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();

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

                        if (response.body().getId()==null){
                            progress.dismiss();
                            Toast.makeText(sActivity, "login gagal", Toast.LENGTH_SHORT).show();
                        }else {
                            editor.putString("user_id", response.body().getId());
                            editor.putString("user_username", response.body().getUsername());
                            editor.putString("user_name", response.body().getName());
                            editor.putString("user_password", password);
                            editor.putString("user_email", response.body().getEmail());
                            editor.putString("user_status", response.body().getStatus());
                            editor.putInt("pernah_login", 1);
                            editor.apply();
                            Log.e("success", "onResponse: " + response.body().getEmail());
                            //save password
                            if (db.fetchAllMainGroup().size()==0 ||db.fetchAllMainGroup().isEmpty()
                                    ||db.fetchAllMainGroup()==null) {
                                Log.d("getmaingroup", "onResponse: "+response.body().getId());
                                getMainGroups(password, false);
                            }else{
                                sActivity.startActivity(new Intent(sActivity, MainGroupActivity.class));
                                sActivity.finish();
                                progress.dismiss();
                            }

                        }
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

    public void getMainGroups(String password,boolean isrefresh) {
        if (isrefresh){
            progress = new ProgressDialog(sActivity);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
        }
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
                        if (!isrefresh){
                            db.insert(response.body());
                            getAllQuestions(password,"1", false);
                        }else{
                            db.deleteAllMainGroup();
                            db.insert(response.body());
                            //
                            Intent i = new Intent();
                            sActivity.setResult(REQUEST_CODE, i);
                            progress.dismiss();
                            sActivity.finish();
                        }
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

    public void getAllQuestions(String password , String period, boolean isUpdate){
        if (isUpdate){
            progress = new ProgressDialog(sActivity);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
        }
        try {
            JSONObject body = new JSONObject();
            body.put("password", password);
            body.put("period", period);
            SurveyService service = SurveyClient.getRetrofit().create(SurveyService.class);
            service.getAllQuestions(body.toString()).enqueue(new Callback<ArrayList<QuestionResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<QuestionResponse>> call, Response<ArrayList<QuestionResponse>> response) {
                    try{
                        if(isUpdate) db.deleteAllQuestionsByPeriod(period);
                        db.insert(response.body());
                        sActivity.startActivity(new Intent(sActivity, MainGroupActivity.class));
                        progress.dismiss();
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

    public void getAllQuestions(String password , String period, MainGroupResponse mgResponses, int position){
        progress = new ProgressDialog(sActivity);
        progress.setMessage("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
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
                        Intent intent = new Intent(sActivity, QuestionHeaderActivity.class);
                        Log.e(TAG, "Period: " + period);
                        intent.putExtra("extra_period", period);
                        intent.putExtra("extra_maingroup", mgResponses);
                        intent.putExtra("extra_position", position);
                        progress.dismiss();
                        sActivity.startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(sActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<QuestionResponse>> call, Throwable t) {
                    Toast.makeText(sActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e){
            Log.e(TAG, "getAllQuestions: " + e.getMessage() );
        }
    }

    public void sendAnswer(MainGroupResponse mgResponse, ObjectSurvey objectSurvey, ArrayList<QuestionResponse> questionsModel , String password) {
        progress = new ProgressDialog(sActivity);
        progress.setMessage("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        if (!objectSurvey.isStatus()) {
            HashMap<String, String> answerHeaderData = new HashMap<>();
            for (int i = 0; i < mgResponse.getAnswerHeaderFields().size(); i++) {
                String field = mgResponse.getAnswerHeaderFields().get(i);
                String answer = objectSurvey.getAnswerHeader().get(i);
                Log.d("loloku", "sendAnswer: "+field+" , "+answer);
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
                            db = DatabaseProvider.getInstance();
                            db.update(objectSurvey);
                            /*Intent intent = new Intent(sActivity, ShowDataActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            sActivity.startActivity(intent);*/
                            Intent i = new Intent();
                            sActivity.setResult(REQUEST_CODE, i);
                            progress.dismiss();
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
            progress.dismiss();
            Toast.makeText(sActivity, "This header is already syncronized", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeSource(){
        db.close();
    }
}
