package com.example.user1.myapplication.Database;

import android.util.Log;

import com.example.user1.myapplication.Model.QuestionResponse;

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

    public void insert(ArrayList<QuestionResponse> questionModels) {
        realm.executeTransactionAsync(realm -> {
            ObjectSurvey objectSurvey = realm.createObject(ObjectSurvey.class, UUID.randomUUID().toString());
            for (QuestionResponse questionModel: questionModels) {
                AnsweredQuestionByObject answeredQuestion = realm.createObject(AnsweredQuestionByObject.class);
                RealmList<String> listJawabanUser = new RealmList<>();
                listJawabanUser.addAll(questionModel.getJawabanUser());
                answeredQuestion.setQuestion(questionModel.getPertanyaan());
                answeredQuestion.setAnswers(listJawabanUser);
                objectSurvey.addAnsweredQuestion(answeredQuestion);
            }
        }, () -> Log.e(TAG, "onSuccess: success"), error -> Log.e(TAG, "onError: " + error.getMessage()));
    }

    public ArrayList<ObjectSurvey> fetchAllObjectSurvey() {
        //masih error
        return new ArrayList<>(realm.where(ObjectSurvey.class).findAll());
    }
}
