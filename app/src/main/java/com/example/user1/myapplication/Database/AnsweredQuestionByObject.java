package com.example.user1.myapplication.Database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.Required;

public class AnsweredQuestionByObject extends RealmObject {

    @Required
    private String question;
    @Required
    private RealmList<String> answers;
    @Required
    private String idKategori;
    @LinkingObjects("answeredQuestions")
    private RealmResults<ObjectSurvey> owner;

    public AnsweredQuestionByObject() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public RealmList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(RealmList<String> answers) {
        this.answers = answers;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public RealmResults<ObjectSurvey> getOwner() {
        return owner;
    }

    public void setOwner(RealmResults<ObjectSurvey> owner) {
        this.owner = owner;
    }
}


