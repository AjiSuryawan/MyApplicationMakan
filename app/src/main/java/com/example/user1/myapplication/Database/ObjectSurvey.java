package com.example.user1.myapplication.Database;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.user1.myapplication.Model.QuestionResponse;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ObjectSurvey extends RealmObject implements Parcelable {

    @PrimaryKey
    private String id;
    private RealmList<QuestionResponse> answeredQuestions;

    public ObjectSurvey() {

    }

    protected ObjectSurvey(Parcel in) {
        id = in.readString();
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

    public RealmList<QuestionResponse> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void addAnsweredQuestion(QuestionResponse answeredQuestionByObject) {
        this.answeredQuestions.add(answeredQuestionByObject);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}
