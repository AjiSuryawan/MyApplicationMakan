package com.example.user1.myapplication.QuestionHeader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.R;

import java.util.ArrayList;

public class QuestionHeader extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    public ArrayList<EditModel> editModelArrayList;
    private Bundle extras;
    private String category;
    private ArrayList<QuestionResponse> questions = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.showData:
                Intent intent = new Intent(QuestionHeader.this,NextActivity.class);

                intent.putExtra("extra_category_mg", category);
                intent.putParcelableArrayListExtra("extra_questions", questions);

                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionheader);

        extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("extra_category_mg");
            questions = extras.getParcelableArrayList("extra_questions");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        editModelArrayList = populateList();
        customAdapter = new CustomAdapter(this,editModelArrayList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(String.valueOf(i));
            list.add(editModel);
        }

        return list;
    }

}
