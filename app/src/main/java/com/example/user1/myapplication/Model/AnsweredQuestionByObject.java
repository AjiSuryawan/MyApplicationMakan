package com.example.user1.myapplication.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AnsweredQuestionByObject extends RealmObject {

    private String question;
    private RealmList<String> answers;

    public AnsweredQuestionByObject() {
    }

    public String getQuestion() {
        return question;
    }

    public RealmList<String> getAnswers() {
        return answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(RealmList<String> answers) {
        this.answers = answers;
    }
}


