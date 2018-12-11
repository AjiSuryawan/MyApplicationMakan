package com.example.user1.myapplication.Network;

import com.example.user1.myapplication.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SurveyClient {
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://www.unicef-schoolprofiling.com/";
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();


    public static Retrofit getRetrofit(){
        if(retrofit == null){
            if(BuildConfig.DEBUG){
                okHttpClient = okHttpClient.addInterceptor(logging);
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }
        return retrofit;
    }
}
