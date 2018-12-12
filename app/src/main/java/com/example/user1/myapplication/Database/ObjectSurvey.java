package com.example.user1.myapplication.Database;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ObjectSurvey extends RealmObject {

    @PrimaryKey
    private String id;
    private RealmList<AnsweredQuestionByObject> answeredQuestions;

    public ObjectSurvey() {

    }

    public String getId() {
        return id;
    }

    public void addAnsweredQuestion(AnsweredQuestionByObject answeredQuestionByObject) {
        this.answeredQuestions.add(answeredQuestionByObject);
    }
}
