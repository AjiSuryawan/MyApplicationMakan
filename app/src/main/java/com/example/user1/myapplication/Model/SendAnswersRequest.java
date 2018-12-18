package com.example.user1.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class SendAnswersRequest {

    private HashMap<String, String> answerheader;
    private ArrayList<HashMap<String, String>> answerlines;
    private String password;
    @SerializedName("exceptionMsg")
    @Expose
    private String message;

    public SendAnswersRequest(HashMap<String, String> answerheader, ArrayList<HashMap<String, String>> answerlines, String password) {
        this.answerheader = answerheader;
        this.answerlines = answerlines;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }
}
