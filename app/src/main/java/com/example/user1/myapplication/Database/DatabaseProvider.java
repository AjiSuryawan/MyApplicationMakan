package com.example.user1.myapplication.Database;

import android.util.Log;

import com.example.user1.myapplication.MainGroupSection.MainGroupActivity;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.QuestionResponse;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
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

    public void insert(ArrayList<QuestionResponse> questionModels) {
        realm.executeTransactionAsync(realm -> {
            ObjectSurvey objectSurvey = realm.createObject(ObjectSurvey.class, UUID.randomUUID().toString());
            for (QuestionResponse questionModel : questionModels) {
                QuestionResponse answeredQuestion = realm.createObject(QuestionResponse.class);
                answeredQuestion.setId(questionModel.getId());
                answeredQuestion.setPertanyaan(questionModel.getPertanyaan());
                answeredQuestion.setJawabanUser(questionModel.getJawabanUser());
                Log.e(TAG, "getJawabanUser: " + answeredQuestion.getJawabanUser());
                objectSurvey.addAnsweredQuestion(answeredQuestion);
            }
        }, () -> Log.e(TAG, "onSuccess: success"), error -> Log.e(TAG, "onError: " + error.getMessage()));
    }

    public void insert(JSONArray json) {
        realm.executeTransactionAsync(realm ->
                realm.createAllFromJson(MainGroupResponse.class, json),
                () -> Log.e(TAG, "onSuccess: success"),
                error -> Log.e(TAG, "onError: " + error.getMessage()));
    }

    public RealmResults<ObjectSurvey> fetchAllObjectSurvey() {
        return realm.where(ObjectSurvey.class).findAll();
    }

    public RealmResults<MainGroupResponse> fetchAllMainGroup(){
        return realm.where(MainGroupResponse.class).findAll();
    }
}
