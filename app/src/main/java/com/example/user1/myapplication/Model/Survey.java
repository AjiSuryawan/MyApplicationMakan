package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Survey implements Parcelable {
    private int id;
    private String type;
    private String question;
    private String answer;
    private String namaguru;
    private int idguru;

    public Survey(int namaguru, String type, String question, String answer) {
        this.setType(type);
        this.setQuestion(question);
        this.setIdguru(namaguru);
        this.setAnswer(answer);
    }

    public Survey(String namaguru){
        this.setNamaguru(namaguru);
    }

    public Survey(){

    }

    protected Survey(Parcel in) {
        setId(in.readInt());
        setQuestion(in.readString());
        setAnswer(in.readString());
        setType(in.readString());
        setIdguru(in.readInt());
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
        dest.writeInt(getId());
        dest.writeString(getQuestion());
        dest.writeString(getAnswer());
        dest.writeString(getType());
        dest.writeInt(getIdguru());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdguru() {
        return idguru;
    }

    public void setIdguru(int idguru) {
        this.idguru = idguru;
    }

    public String getNamaguru() {
        return namaguru;
    }

    public void setNamaguru(String namaguru) {
        this.namaguru = namaguru;
    }
}
