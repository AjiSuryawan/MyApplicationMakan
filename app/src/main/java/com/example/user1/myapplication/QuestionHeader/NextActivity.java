package com.example.user1.myapplication.QuestionHeader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class NextActivity extends AppCompatActivity {

    Button btnstart;
    String period;
    String[] periodList = {"1", "2", "3"};
    private TextView tv;
    private Bundle extras;
    private DatabaseProvider db;
    private int position = 0;
    private MainGroupResponse mgResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        db = DatabaseProvider.getInstance();
        extras = getIntent().getExtras();
        if (extras != null) {
            mgResponses = extras.getParcelable("extra_maingroup");
            position = extras.getInt("extra_position");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, periodList);
        MaterialBetterSpinner materialDesignSpinner = findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);
        materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                period = periodList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnstart = (Button) findViewById(R.id.btngo);
        btnstart.setOnClickListener(v -> {
            Intent intent = new Intent(NextActivity.this, QuestionHeaderActivity.class);
            intent.putExtra("extra_period", period);
            intent.putExtra("extra_maingroup", mgResponses);
            intent.putExtra("extra_position", position);
            startActivity(intent);
        });


    }
}
