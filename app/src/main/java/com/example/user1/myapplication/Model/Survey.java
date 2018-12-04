package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Survey implements Parcelable {
    private String type;
    private String question;
    private String answer;

    public Survey(String type, String question) {
        this.type = type;
        this.question = question;
    }

    protected Survey(Parcel in) {
        type = in.readString();
        question = in.readString();
        answer = in.readString();
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

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(question);
        dest.writeString(answer);
    }
}
