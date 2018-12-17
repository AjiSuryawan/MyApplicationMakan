package com.example.user1.myapplication.ShowDataSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity;
import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.MainGroupSection.MainGroupAdapter;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Network.SurveyHelper;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class ShowDataActivity extends AppCompatActivity implements onItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<MainGroupResponse> objectSurveys;
    private MainGroupAdapter adapter;
    private DatabaseProvider db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Semua Data");


        recyclerView = findViewById(R.id.recycler_view);
        objectSurveys = new ArrayList<>();
        adapter = new MainGroupAdapter(this, objectSurveys);
        db = DatabaseProvider.getInstance();

        objectSurveys.addAll(db.fetchAllMainGroup());
        adapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                SurveyHelper helper = SurveyHelper.getInstance(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, AnswerHeadersActivity.class);
        intent.putExtra("extra_category_mg", "mg" + (position + 1));
        startActivity(intent);
    }
}
