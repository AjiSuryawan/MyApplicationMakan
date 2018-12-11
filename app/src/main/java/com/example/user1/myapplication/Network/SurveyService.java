package com.example.user1.myapplication.Network;

import com.example.user1.myapplication.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SurveyService {

    @Headers("Content-Type: text/html")
    @POST("mobileapi/login")
    Call<LoginResponse> loginService (@Body String requestBody);
}
