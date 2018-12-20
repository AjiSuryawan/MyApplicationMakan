package com.example.user1.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AllQuestionResponse extends RealmObject {

    @SerializedName("MG01")
    @Expose
    private RealmList<QuestionResponse> MG01;
    @SerializedName("MG02")
    @Expose
    private RealmList<QuestionResponse> MG02;
    @SerializedName("MG03")
    @Expose
    private RealmList<QuestionResponse> MG03;
    @SerializedName("MG04")
    @Expose
    private RealmList<QuestionResponse> MG04;
    @SerializedName("MG05")
    @Expose
    private RealmList<QuestionResponse> MG05;
    @SerializedName("MG06")
    @Expose
    private RealmList<QuestionResponse> MG06;

    public AllQuestionResponse() {
    }

    public RealmList<QuestionResponse> getMainGroup(int position) {
        switch (position) {
            case 0:
                return MG01;
            case 1:
                return MG02;
            case 2:
                return MG03;
            case 3:
                return MG04;
            case 4:
                return MG05;
            case 5:
                return MG06;
            default:
                return null;
        }
    }
}
