package com.example.user1.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ObjectSurvey extends RealmObject implements Parcelable {

    @PrimaryKey
    private String id;
    private String categoryMainGroup;
    private RealmList<String> answerHeader = new RealmList<>();
    private RealmList<QuestionResponse> answeredQuestions = new RealmList<>();
    private boolean status;

    public ObjectSurvey() {

    }

    protected ObjectSurvey(Parcel in) {
        id = in.readString();
        categoryMainGroup = in.readString();
        status = in.readByte() != 0;
        this.answerHeader.addAll(in.createStringArrayList());
    }

    public static final Creator<ObjectSurvey> CREATOR = new Creator<ObjectSurvey>() {
        @Override
        public ObjectSurvey createFromParcel(Parcel in) {
            return new ObjectSurvey(in);
        }

        @Override
        public ObjectSurvey[] newArray(int size) {
            return new ObjectSurvey[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setCategoryMainGroup(String categoryMainGroup) {
        this.categoryMainGroup = categoryMainGroup;
    }

    public RealmList<QuestionResponse> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void addAnsweredQuestion(QuestionResponse answeredQuestionByObject) {
        this.answeredQuestions.add(answeredQuestionByObject);
    }

    public RealmList<String> getAnswerHeader() {
        return answerHeader;
    }

    public void setAnswerHeader(ArrayList<String> answerHeader) {
        this.answerHeader.clear();
        this.answerHeader.addAll(answerHeader);
    }

    public void setAnsweredQuestions(RealmList<QuestionResponse> answeredQuestions) {
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
