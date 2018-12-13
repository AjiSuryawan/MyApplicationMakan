package com.example.user1.myapplication.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ServicePassword {
    private static SharedPreferences preferences;

    private ServicePassword(){}
    public static String getPassword(Context context){
        preferences = context.getSharedPreferences("pref_user", Context.MODE_PRIVATE);
        String password = preferences.getString("user_password", "");
        Log.d("ServicePassword", "getPassword: " + password);
        return password;
    }
}
