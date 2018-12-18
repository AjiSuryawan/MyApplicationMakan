package com.example.user1.myapplication.Database;

import android.util.Log;

import com.example.user1.myapplication.Model.AllQuestionResponse;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.ObjectSurvey;
import com.example.user1.myapplication.Model.QuestionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

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

    public void insert(String category, ArrayList<QuestionResponse> questionModels, ArrayList<String> answers) {
        realm.executeTransactionAsync(realm -> {
            ObjectSurvey objectSurvey = realm.createObject(ObjectSurvey.class, UUID.randomUUID().toString());
            objectSurvey.setAnswerHeader(answers);
            objectSurvey.setCategoryMainGroup(category);
            for (QuestionResponse questionModel : questionModels) {
                QuestionResponse answeredQuestion = realm.createObject(QuestionResponse.class);
                answeredQuestion.setId(questionModel.getId());
                answeredQuestion.setPertanyaan(questionModel.getPertanyaan());
                answeredQuestion.setJawabanUser(questionModel.getJawabanUser());
                Log.e(TAG, "getJawabanUser: " + answeredQuestion.getJawabanUser());
                objectSurvey.addAnsweredQuestion(answeredQuestion);
            }
            objectSurvey.setStatus(false);
        }, () -> Log.e(TAG, "onSuccess: success"), error -> Log.e(TAG, "onError: " + error.getMessage()));
    }

    public void insert(List<? extends RealmObject> response) {
        realm.executeTransactionAsync(realm ->
                        realm.copyToRealmOrUpdate(response),
                () -> Log.e(TAG, "onSuccess: success"),
                error -> Log.e(TAG, "onError: " + error.getMessage()));
    }

    public void update(ObjectSurvey objectSurvey) {
        realm.executeTransactionAsync(realm -> {
            objectSurvey.setStatus(true);
            realm.insertOrUpdate(objectSurvey);
        }, () -> {
            Log.e(TAG, "update: success");
        }, error -> {
            Log.e(TAG, "update: " + error.getMessage());
        });
    }

    public RealmResults<ObjectSurvey> fetchAllObjectSurvey(String category) {
        return realm.where(ObjectSurvey.class).equalTo("categoryMainGroup", category).findAll();
    }

    public RealmResults<MainGroupResponse> fetchAllMainGroup() {
        return realm.where(MainGroupResponse.class).findAll();
    }

    public RealmResults<AllQuestionResponse> fetchAllQuestions() {
        return realm.where(AllQuestionResponse.class).findAll();
    }

    public void close(){
        realm.close();
    }
}
