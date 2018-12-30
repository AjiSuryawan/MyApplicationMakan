package com.example.user1.myapplication.Database;

import android.util.Log;

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
    boolean tanda;
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
        Log.d("lolo6", "insert: "+questionModels.size());
        realm.executeTransactionAsync(realm -> {
            ObjectSurvey objectSurvey = realm.createObject(ObjectSurvey.class, UUID.randomUUID().toString());
            objectSurvey.setAnswerHeader(answers);
            objectSurvey.setCategoryMainGroup(category);
            for (QuestionResponse questionModel : questionModels) {
                QuestionResponse answeredQuestion = realm.createObject(QuestionResponse.class);
                answeredQuestion.setId(questionModel.getId());
                answeredQuestion.setPertanyaan(questionModel.getPertanyaan());
                answeredQuestion.setPeriod(questionModel.getPeriod());
                Log.d("lolo1", "insert: "+questionModel.getPeriod());
                answeredQuestion.setJawabanUser(questionModel.getJawabanUser());
                Log.e("lolo2", "getJawabanUser: " + answeredQuestion.getJawabanUser());
                objectSurvey.addAnsweredQuestion(answeredQuestion);
            }
            objectSurvey.setStatus(false);
        }, () -> Log.e("lolo3", "onSuccess: success"), error -> Log.e("lolo4", "onError: " + error.getMessage()));
    }

    public void insert(List<? extends RealmObject> response) {
        realm.executeTransactionAsync(realm ->
                        realm.copyToRealmOrUpdate(response),
                () -> Log.e(TAG, "onSuccess: success"),
                error -> Log.e(TAG, "onError: " + error.getMessage()));
    }



    public void update(ObjectSurvey objectSurvey) {
        realm.executeTransactionAsync(realm -> {
            ObjectSurvey obj = realm.where(ObjectSurvey.class)
                    .equalTo("id", objectSurvey.getId()).findFirst();
            obj.setStatus(true);
        }, () -> {
            Log.e(TAG, "update: success");
        }, error -> {
            Log.e(TAG, "update: " + error.getMessage());
        });
    }

    public RealmResults<ObjectSurvey> fetchAllObjectSurvey(String category) {
        return realm.where(ObjectSurvey.class).equalTo("categoryMainGroup", category).findAll();
    }

    public boolean delete(String category){
        RealmResults<ObjectSurvey> makan=realm.where(ObjectSurvey.class)
                .equalTo("categoryMainGroup", category)
                .findAll();
        realm.beginTransaction();
        makan.deleteAllFromRealm();
        realm.commitTransaction();
        return true;
    }

    public boolean deleteSyncronizedData(String category){
        RealmResults<ObjectSurvey> makan=realm.where(ObjectSurvey.class)
                .equalTo("categoryMainGroup", category)
                .and()
                .equalTo("status", true)
                .findAll();
        realm.beginTransaction();
        makan.deleteAllFromRealm();
        realm.commitTransaction();
        return true;
    }

    public boolean deleteAllQuestionsByPeriod(String period){
        RealmResults<QuestionResponse> results = realm.where(QuestionResponse.class)
                .equalTo("period", period)
                .isNotNull("mgId")
                .findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        return true;
    }

    public boolean deleteAllMainGroup() {
        RealmResults<MainGroupResponse> makan=realm.where(MainGroupResponse.class).findAll();
        realm.beginTransaction();
        makan.deleteAllFromRealm();
        realm.commitTransaction();
        return true;
    }

    public RealmResults<MainGroupResponse> fetchAllMainGroup() {
        return realm.where(MainGroupResponse.class).findAll();
    }

    public RealmResults<QuestionResponse> fetchAllQuestions(String mgId, String period) {
        return realm.where(QuestionResponse.class)
                .equalTo("mgId", mgId)
                .equalTo("period", period)
                .findAll();
    }

    public boolean isQuestionsEmpty(String period){
        long count = realm.where(QuestionResponse.class).equalTo("period", period).count();
        return count <= 0;
    }
    public void close(){
        realm.close();
    }
}
