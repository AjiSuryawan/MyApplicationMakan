package com.example.user1.myapplication.ShowDataSection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity;
import com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersAdapter;
import com.example.user1.myapplication.AnswerHeadersSection.DetailAnswerHeadersActivity;
import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Database.ObjectSurvey;
import com.example.user1.myapplication.MainGroupSection.MainGroupAdapter;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class ShowDataActivity extends AppCompatActivity implements onItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<MainGroupResponse> objectSurveys;
    private MainGroupAdapter adapter;
    private DatabaseProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Semua Data");


        recyclerView = findViewById(R.id.recycler_view);
        objectSurveys = new ArrayList<>();
        adapter = new MainGroupAdapter(this, objectSurveys);
        provider = DatabaseProvider.getInstance();

        objectSurveys.addAll(provider.fetchAllMainGroup());
        adapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(int position) {
        /*Intent intent = new Intent(this, DetailAnswerHeadersActivity.class);
        ArrayList<QuestionResponse> questionModels = new ArrayList<>();
        intent.putParcelableArrayListExtra("extra_objectsurvey", questionModels);
        startActivity(intent);*/

        Intent intent = new Intent(this, AnswerHeadersActivity.class);
        startActivity(intent);
    }
}
