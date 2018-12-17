package com.example.user1.myapplication.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class SendAnswersRequest {

    private HashMap<String, String> answerheader;
    private ArrayList<HashMap<String, String>> answerlines;
    private String password;

    public SendAnswersRequest(HashMap<String, String> answerheader, ArrayList<HashMap<String, String>> answerlines, String password) {
        this.answerheader = answerheader;
        this.answerlines = answerlines;
        this.password = password;
    }
}
