package com.example.user1.myapplication.Database;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class ObjectSurvey extends RealmObject {

//    @PrimaryKey
//    private String idUser;

    @Required
    private String name = UUID.randomUUID().toString();
    private RealmList<AnsweredQuestionByObject> answeredQuestions;

    public ObjectSurvey() {

    }

//    public String getIdUser() {
//        return idUser;
//    }

    public String getName() {
        return name;
    }

    public RealmList<AnsweredQuestionByObject> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(RealmList<AnsweredQuestionByObject> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
}
