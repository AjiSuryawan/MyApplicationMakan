package com.example.user1.myapplication.Network;

import com.example.user1.myapplication.Model.AllQuestionResponse;
import com.example.user1.myapplication.Model.LoginResponse;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.Model.SendAnswersRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SurveyService {

    @Headers("Content-Type: text/html")
    @POST("mobileapi/login")
    Call<LoginResponse> loginService (@Body String requestBody);

    @Headers("Content-Type: text/html")
    @POST("mobileapi/maingroups")
    Call<ArrayList<MainGroupResponse>> getMainGroups (@Body String requestBody);

    @Headers("Content-Type: text/html")
    @POST("mobileapi/questions")
    Call<ArrayList<QuestionResponse>> getQuestions (@Body String requestBody);

    @Headers("Content-Type: text/html")
    @POST("mobileapi/allquestions")
    Call<ArrayList<AllQuestionResponse>> getAllQuestions (@Body String requestBody);

    @Headers("Content-Type: text/html")
    @POST("mobileapi/add_answer")
    Call<SendAnswersRequest> sendAnswers(@Body SendAnswersRequest requestBody);
}

