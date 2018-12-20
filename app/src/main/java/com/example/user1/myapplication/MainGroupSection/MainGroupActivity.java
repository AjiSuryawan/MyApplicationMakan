package com.example.user1.myapplication.MainGroupSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.QuestionHeader.NextActivity;
import com.example.user1.myapplication.QuestionHeader.QuestionHeaderActivity;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.ShowDataSection.ShowDataActivity;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainGroupActivity extends AppCompatActivity implements onItemClickListener {

    private static final String TAG = DatabaseProvider.class.getSimpleName();

    public ArrayList<MainGroupResponse> mainGroups = new ArrayList<>();

    public MainGroupAdapter adapter;
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private DatabaseProvider db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_group);

        recyclerView = findViewById(R.id.recycler_view);

        db = DatabaseProvider.getInstance();

        adapter = new MainGroupAdapter(this, mainGroups);

        mainGroups.addAll(db.fetchAllMainGroup());

        adapter.setListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.showData:
                Intent intent = new Intent(this, ShowDataActivity.class);
                startActivity(intent);
            case R.id.action_update_data:
//                getMainGroups(password);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        //Intent intent = new Intent(this, QuestionHeaderActivity.class);
        Intent intent = new Intent(this, NextActivity.class);
        intent.putExtra("extra_maingroup", mainGroups.get(position));
        intent.putExtra("extra_position", position);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
