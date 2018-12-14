package com.example.user1.myapplication.MainGroupSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity;
import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.ShowDataSection.ShowDataActivity;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

public class MainGroupActivity extends AppCompatActivity implements onItemClickListener {

    public ArrayList<MainGroupResponse> mainGroups = new ArrayList<>();
    public MainGroupAdapter adapter = new MainGroupAdapter(this, mainGroups);
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_group);

        recyclerView = findViewById(R.id.recycler_view);
        preferences = getSharedPreferences("pref_user", MODE_PRIVATE);

        password = preferences.getString("user_password", "");

        DatabaseProvider db = DatabaseProvider.getInstance();

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
                //Intent intent = new Intent(this, MainGroupActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                /*intent.putExtra("show_data", "show_data");
                finish();
                startActivity(intent);*/
                Intent intent = new Intent(this, ShowDataActivity.class);
                startActivity(intent);
            case R.id.sync:
//                getMainGroups(password);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getString("show_data").equalsIgnoreCase("show_data")) {
            Log.e("MainGroupActivity", "onItemClick: ");
            Intent intent = new Intent(this, AnswerHeadersActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, QuestionActivity.class);
            Log.e("MainGroupActivity", "onItemClick: Question Activiy   ");
            intent.putExtra("extra_maingroup_id", mainGroups.get(position).getId());
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getString("show_data").equalsIgnoreCase("show_data")) {
            Intent intent = new Intent(this, MainGroupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
