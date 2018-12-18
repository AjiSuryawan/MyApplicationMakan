package com.example.user1.myapplication.QuestionHeader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;

import java.util.ArrayList;

public class QuestionHeaderActivity extends AppCompatActivity {

    private static final String TAG = DatabaseProvider.class.getSimpleName();
    private RecyclerView recyclerView;
    private QuestionHeaderAdapter questionHeaderAdapter;
    private Bundle extras;
    private MainGroupResponse mgResponses;
    private int position = 0;
    private Button startBtn;
    private DatabaseProvider db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionheader);

        extras = getIntent().getExtras();
        if (extras != null) {
            mgResponses = extras.getParcelable("extra_maingroup");
            position = extras.getInt("extra_position");
        }

        db = DatabaseProvider.getInstance();

        Log.e(TAG, "onCreate: " + mgResponses.getAnswerHeaderFields());
        recyclerView = findViewById(R.id.recycler);
        questionHeaderAdapter = new QuestionHeaderAdapter(mgResponses, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(questionHeaderAdapter);
        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(v -> {
            if (questionHeaderAdapter.isAllFieldAnswered()) {
                //start question activity
                Intent intent = new Intent(this, QuestionActivity.class);
                ArrayList<QuestionResponse> questionResponses = new ArrayList<>();
                questionResponses.addAll(db.fetchAllQuestions().get(position).getMainGroup(position));
                intent.putExtra("extra_category_mg", "mg" + (position + 1));
                intent.putParcelableArrayListExtra("extra_questions", questionResponses);
                intent.putExtra("extra_answers", questionHeaderAdapter.getAnswers());
                startActivity(intent);
            } else {
                Toast.makeText(this, "please fill the form", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
