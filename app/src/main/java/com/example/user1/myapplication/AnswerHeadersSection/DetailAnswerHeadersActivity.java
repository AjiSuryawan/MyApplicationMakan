package com.example.user1.myapplication.AnswerHeadersSection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.ObjectSurvey;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.Network.SurveyHelper;
import com.example.user1.myapplication.QuestionSection.PreviewAdapter;
import com.example.user1.myapplication.R;

import java.util.ArrayList;

public class DetailAnswerHeadersActivity extends AppCompatActivity {

    private Bundle extras;
    private PreviewAdapter adapter;
    private RecyclerView recyclerView;
    private MainGroupResponse mainGroupResponse = null;
    private ObjectSurvey objectSurvey = null;
    private ArrayList<QuestionResponse> questionResponses = new ArrayList<>();
    private SurveyHelper helper;
    private SharedPreferences sharedPreferences;
    String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences("pref_user", Context.MODE_PRIVATE);
        password=sharedPreferences.getString("user_password","");
        extras = getIntent().getExtras();
        if (extras != null) {
            objectSurvey = extras.getParcelable("extra_objectsurvey");
            mainGroupResponse = extras.getParcelable("extra_maingroup");
            questionResponses = extras.getParcelableArrayList("extra_questions");
        }

        helper = SurveyHelper.getInstance(this);

        recyclerView = findViewById(R.id.rv);
        adapter = new PreviewAdapter(questionResponses, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_answer_header, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sync:
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            helper.sendAnswer(mainGroupResponse, objectSurvey, questionResponses,password);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure want to syncronize data?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.closeSource();
    }
}
