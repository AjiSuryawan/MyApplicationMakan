package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ObjectSurvey2 extends RealmObject implements Parcelable {

    @PrimaryKey
    private String id;
    private String categoryMainGroup;
    private RealmList<String> answerHeader = new RealmList<>();
    private RealmList<QuestionResponse2> answeredQuestions = new RealmList<>();
    private boolean status;

    public ObjectSurvey2() {

    }

    protected ObjectSurvey2(Parcel in) {
        id = in.readString();
        categoryMainGroup = in.readString();
        status = in.readByte() != 0;
        this.answerHeader.addAll(in.createStringArrayList());
    }

    public static final Creator<ObjectSurvey2> CREATOR = new Creator<ObjectSurvey2>() {
        @Override
        public ObjectSurvey2 createFromParcel(Parcel in) {
            return new ObjectSurvey2(in);
        }

        @Override
        public ObjectSurvey2[] newArray(int size) {
            return new ObjectSurvey2[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setCategoryMainGroup(String categoryMainGroup) {
        this.categoryMainGroup = categoryMainGroup;
    }

    public RealmList<QuestionResponse2> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void addAnsweredQuestion(QuestionResponse2 answeredQuestionByObject) {
        this.answeredQuestions.add(answeredQuestionByObject);
    }

    public RealmList<String> getAnswerHeader() {
        return answerHeader;
    }

    public void setAnswerHeader(ArrayList<String> answerHeader) {
        this.answerHeader.clear();
        this.answerHeader.addAll(answerHeader);
    }

    public void setAnsweredQuestions(RealmList<QuestionResponse2> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(categoryMainGroup);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeStringList(this.answerHeader);
    }
}
