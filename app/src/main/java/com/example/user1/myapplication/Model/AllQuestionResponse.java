package com.example.user1.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AllQuestionResponse extends RealmObject {

    @SerializedName("MG01")
    @Expose
    private RealmList<QuestionResponse> mainGroup01;
    @SerializedName("MG02")
    @Expose
    private RealmList<QuestionResponse> mainGroup02;
    @SerializedName("MG03")
    @Expose
    private RealmList<QuestionResponse> mainGroup03;
    @SerializedName("MG04")
    @Expose
    private RealmList<QuestionResponse> mainGroup04;
    @SerializedName("MG05")
    @Expose
    private RealmList<QuestionResponse> mainGroup05;
    @SerializedName("MG06")
    @Expose
    private RealmList<QuestionResponse> mainGroup06;

    public AllQuestionResponse() {
    }

    public RealmList<QuestionResponse> getMainGroup(int position) {
        switch (position) {
            case 0:
                return mainGroup01;
            case 1:
                return mainGroup02;
            case 2:
                return mainGroup03;
            case 3:
                return mainGroup04;
            case 4:
                return mainGroup05;
            case 5:
                return mainGroup06;
            default:
                return null;
        }
    }

}
