package com.example.user1.myapplication.AnswerHeadersSection;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Database.ObjectSurvey;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.ShowDataSection.ShowDataActivity;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class AnswerHeadersActivity extends AppCompatActivity implements onItemClickListener {

    private static final String TAG = AnswerHeadersActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private String category;
    private ArrayList<ObjectSurvey> objectSurveys = new ArrayList<>();
    private AnswerHeadersAdapter adapter;
    private AlertDialog.Builder builder;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.send:
//                //dialog
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
//                } else {
//                    builder = new AlertDialog.Builder(this);
//                }
//
//                builder.setTitle("Simpan Data")
//                        .setMessage("Apakah Anda Yakin untuk menyimpan data anda ? ")
//                        .setPositiveButton("Ya", (dialog, which) -> {
//                            Toast.makeText(getApplicationContext(),"sinkron data",Toast.LENGTH_SHORT).show();
//                        }).setNegativeButton("Tidak", (dialog, which) -> dialog.cancel()).
//                        show();
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_headers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            category = extras.getString("extra_category_mg");
        }
        DatabaseProvider db = DatabaseProvider.getInstance();
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
        ArrayList<QuestionResponse> questionModels = new ArrayList<>();
        questionModels.addAll(objectSurveys.get(position).getAnsweredQuestions());
        intent.putParcelableArrayListExtra("extra_objectsurvey", questionModels);
        startActivity(intent);
    }
}
