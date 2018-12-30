package com.example.user1.myapplication.MainGroupSection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.View;
import android.widget.Toast;

import com.example.user1.myapplication.ControlClass;
import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.PerbaruiActivity;
import com.example.user1.myapplication.QuestionHeader.NextActivity;
import com.example.user1.myapplication.R;
import com.example.user1.myapplication.ShowDataSection.ShowDataActivity;
import com.example.user1.myapplication.onItemClickListener;

import java.util.ArrayList;

import static com.example.user1.myapplication.AnswerHeadersSection.AnswerHeadersActivity.REQUEST_CODE;

public class MainGroupActivity extends AppCompatActivity implements onItemClickListener {

    private static final String TAG = DatabaseProvider.class.getSimpleName();

    public ArrayList<MainGroupResponse> mainGroups = new ArrayList<>();

    public MainGroupAdapter adapter;
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private DatabaseProvider db;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_group);

        recyclerView = findViewById(R.id.recycler_view);

        db = DatabaseProvider.getInstance();
        mainGroups.addAll(db.fetchAllMainGroup());
        adapter = new MainGroupAdapter(this, mainGroups);
        Log.d("grupku", "onCreate: "+mainGroups.size());

        adapter.setListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==REQUEST_CODE)
        {
            Toast.makeText(getApplicationContext(),"masuk sini", Toast.LENGTH_SHORT).show();
            mainGroups.clear();
            db = DatabaseProvider.getInstance();
            mainGroups.addAll(db.fetchAllMainGroup());
            adapter.notifyDataSetChanged();

//            finish();
//            Intent in=new Intent(getApplicationContext(),MainGroupActivity.class);
//            startActivity(in);
        }
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
                return true;
            case R.id.action_update_data:
                Intent updateIntent = new Intent(this, PerbaruiActivity.class);
                //startActivity(updateIntent);
                startActivityForResult(updateIntent , REQUEST_CODE);
                return true;
            case R.id.logout:
                new AlertDialog.Builder(this)
                        .setTitle("Really Logout?")
                        .setMessage("Are you sure you want to Logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                SharedPreferences settings = getSharedPreferences("pref_user", Context.MODE_PRIVATE);
                                settings.edit().clear().commit();
                                startActivity(new Intent(getApplicationContext(),ControlClass.class));
                                finish();
                            }
                        }).create().show();
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
