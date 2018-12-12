package com.example.user1.myapplication.AnswerHeadersSection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Database.ObjectSurvey;
import com.example.user1.myapplication.R;

import java.util.ArrayList;

public class AnswerHeadersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ObjectSurvey> objectSurveys = new ArrayList<>();
    private AnswerHeadersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_headers);

        adapter = new AnswerHeadersAdapter(this, objectSurveys);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadObjectSurvey();
    }

    private void loadObjectSurvey(){
        DatabaseProvider db = DatabaseProvider.getInstance();
        objectSurveys.clear();
        objectSurveys.addAll(db.fetchAllObjectSurvey());
        adapter.notifyDataSetChanged();
    }
}
