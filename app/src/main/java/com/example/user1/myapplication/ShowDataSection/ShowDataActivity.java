package com.example.user1.myapplication.ShowDataSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity;
import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowDataActivity extends AppCompatActivity implements onItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<MainGroupResponse> mgResponses;
    private ShowDataAdapter adapter;
    private DatabaseProvider db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Semua Data");


        recyclerView = findViewById(R.id.recycler_view);
        mgResponses = new ArrayList<>();
        adapter = new ShowDataAdapter(this, mgResponses);
        db = DatabaseProvider.getInstance();

        mgResponses.addAll(db.fetchAllMainGroup());
        adapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, AnswerHeadersActivity.class);
        intent.putExtra("extra_category_mg", "mg" + (position + 1));
        intent.putExtra("extra_maingroup", mgResponses.get(position));
        startActivity(intent);
    }
}
