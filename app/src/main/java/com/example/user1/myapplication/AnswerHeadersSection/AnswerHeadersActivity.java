package com.example.user1.myapplication.AnswerHeadersSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.ObjectSurvey;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class AnswerHeadersActivity extends AppCompatActivity implements onItemClickListener {
    public static final int REQUEST_CODE = 1;
    private static final String TAG = AnswerHeadersActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private String category;
    private ArrayList<ObjectSurvey> objectSurveys = new ArrayList<>();
    private AnswerHeadersAdapter adapter;
    private MainGroupResponse mainGroupResponse;
    private DatabaseProvider db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_headers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("extra_category_mg");
            mainGroupResponse = extras.getParcelable("extra_maingroup");
        }
        db = DatabaseProvider.getInstance();
        objectSurveys.addAll(db.fetchAllObjectSurvey(category));
        adapter = new AnswerHeadersAdapter(this, objectSurveys);
        adapter.setListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailAnswerHeadersActivity.class);
        intent.putExtra("extra_maingroup", mainGroupResponse);
        intent.putExtra("extra_objectsurvey", objectSurveys.get(position));
        intent.putParcelableArrayListExtra("extra_questions", getQuestions(objectSurveys.get(position)));
        //startActivity(intent);
        startActivityForResult(intent , REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==REQUEST_CODE)
        {
            Toast.makeText(getApplicationContext(),"makanan",Toast.LENGTH_SHORT).show();
            objectSurveys = new ArrayList<>();
            objectSurveys.addAll(db.fetchAllObjectSurvey(category));
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private ArrayList<QuestionResponse> getQuestions(ObjectSurvey objectSurvey) {
        ArrayList<QuestionResponse> questionModels = new ArrayList<>();
        questionModels.addAll(objectSurvey.getAnsweredQuestions());
        return questionModels;
    }
//
//    private ArrayList<String> getAnsweredFormField(RealmList<String> s){
//        ArrayList<String> answers = new ArrayList<>();
//        answers.addAll(s);
//        return answers;
//    }

}
