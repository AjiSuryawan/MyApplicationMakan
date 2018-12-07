package com.example.user1.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView cardkuspa2=(CardView)findViewById(R.id.cardkuspa1);
        cardkuspa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),ListUserSurvey.class);
                startActivity(in);
            }
        });
    }
}
