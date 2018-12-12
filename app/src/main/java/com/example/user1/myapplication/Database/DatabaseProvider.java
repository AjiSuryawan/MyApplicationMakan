package com.example.user1.myapplication.Database;

import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

public class DatabaseProvider {

    private static final String TAG = DatabaseProvider.class.getSimpleName();
    private static DatabaseProvider instance;
    private static Realm realm;

    private DatabaseProvider() {
    }

    public static DatabaseProvider getInstance() {
        if (instance == null)
            instance = new DatabaseProvider();
        realm = Realm.getDefaultInstance();
        return instance;
    }

    public void insert(String question, RealmList<String> answers, String idKategori) {
        realm.executeTransactionAsync(realm -> {
//            final String primaryKey = UUID.randomUUID().toString();
            ObjectSurvey objectSurvey = realm.createObject(ObjectSurvey.class);
            AnsweredQuestionByObject answeredQuestion = realm.createObject(AnsweredQuestionByObject.class);
            answeredQuestion.setQuestion(question);
            answeredQuestion.setAnswers(answers);
            answeredQuestion.setIdKategori(idKategori);
        }, () -> Log.e(TAG, "onSuccess: success"), error -> Log.e(TAG, "onError: " + error.getMessage()));
    }

    public ArrayList<ObjectSurvey> fetchAllObjectSurvey() {
        return new ArrayList<>(realm.where(ObjectSurvey.class).findAll());
    }
}
