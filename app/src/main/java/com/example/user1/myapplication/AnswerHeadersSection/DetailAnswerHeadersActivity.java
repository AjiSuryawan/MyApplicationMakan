package com.example.user1.myapplication.AnswerHeadersSection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.QuestionSection.PreviewAdapter;
import com.example.user1.myapplication.R;

import java.util.ArrayList;

public class DetailAnswerHeadersActivity extends AppCompatActivity {

    private static final String TAG = AnswerHeadersActivity.class.getSimpleName();

    private Bundle extras;
    private PreviewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<QuestionResponse> questionsModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        extras = getIntent().getExtras();
        if (extras != null) {
            questionsModel = extras.getParcelableArrayList("extra_objectsurvey");
        }
        recyclerView = findViewById(R.id.rv);
        adapter = new PreviewAdapter(questionsModel, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < questionsModel.size(); i++) {
            Log.e(TAG, "onCreate: " + questionsModel.get(i).getPertanyaan() );
            Log.e(TAG, "onCreate: " + questionsModel.get(i).getJawabanUser() );
        }

    }
}
