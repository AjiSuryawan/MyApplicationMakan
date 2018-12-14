package com.example.user1.myapplication.AnswerHeadersSection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Database.ObjectSurvey;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class AnswerHeadersActivity extends AppCompatActivity implements onItemClickListener {

    private static final String TAG = AnswerHeadersActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ArrayList<ObjectSurvey> objectSurveys = new ArrayList<>();
    private AnswerHeadersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_headers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseProvider db = DatabaseProvider.getInstance();
        objectSurveys.addAll(db.fetchAllObjectSurvey());
        adapter = new AnswerHeadersAdapter(this, objectSurveys);
        adapter.setListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//        for (int i = 0; i < objectSurveys.get(0).getAnsweredQuestions().size(); i++) {
//            Log.e(TAG, "onCreate: " + objectSurveys.get(0).getAnsweredQuestions().get(i).getPertanyaan() );
//            Log.e(TAG, "onCreate: " + objectSurveys.get(0).getAnsweredQuestions().get(i).getJawabanUser() );
//        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailAnswerHeadersActivity.class);
        ArrayList<QuestionResponse> questionModels = new ArrayList<>();
        questionModels.addAll(objectSurveys.get(position).getAnsweredQuestions());
        for (int i = 0; i < questionModels.size(); i++) {
            Log.e(TAG, "onCreate: " + questionModels.get(i).getPertanyaan() );
            Log.e(TAG, "onCreate: " + questionModels.get(i).getJawabanUser() );
        }
        intent.putParcelableArrayListExtra("extra_objectsurvey", questionModels);
        startActivity(intent);
    }
}
