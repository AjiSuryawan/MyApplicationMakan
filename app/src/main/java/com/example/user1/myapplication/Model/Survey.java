package com.example.user1.myapplication.Model;

public class Survey {
    private String type;
    private String question;
    private String answer;

    public Survey(String type, String question) {
        this.type = type;
        this.question = question;
    }

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
}
