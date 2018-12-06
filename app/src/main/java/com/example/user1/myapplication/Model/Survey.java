package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Survey implements Parcelable {
    private String type;
    private String question;
    private ArrayList<String> answers = new ArrayList<>();

    public Survey(String type, String question) {
        this.type = type;
        this.question = question;
    }

    protected Survey(Parcel in) {
        type = in.readString();
        question = in.readString();
        answers = in.createStringArrayList();
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void addAnswer(String answer) {
        answers.clear();
        this.answers.add(answer);
    }

    public void addAnswer(ArrayList<String> answers){
        this.answers.clear();
        this.answers.addAll(answers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(question);
        dest.writeStringList(answers);
    }
}
