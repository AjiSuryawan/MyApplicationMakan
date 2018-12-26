package com.example.user1.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user1.myapplication.Network.SurveyHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class PerbaruiActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private MaterialBetterSpinner spinner;
    private Button updateBtn;
    private static final String[] periodList = new String[]{"1", "2", "3"};
    private String period = "";
    private String password;
    private SurveyHelper surveyHelper;
    Button btnrefreshheader;

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbarui);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = findViewById(R.id.android_material_design_spinner);


        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setEnabled(false);

        sharedPreferences = getSharedPreferences("pref_user", MODE_PRIVATE);
        password = sharedPreferences.getString("user_password", "");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, periodList);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateBtn.setEnabled(true);
                period = parent.getItemAtPosition(position).toString();
                Log.e("TAG", "onItemClick: " + period );
            }
        });
        surveyHelper = SurveyHelper.getInstance(this);
        updateBtn.setOnClickListener(v -> {
            updateBtn.setEnabled(false);
            surveyHelper.getAllQuestions(password, period, true);
        });

        btnrefreshheader = (Button) findViewById(R.id.btnupdateHeader);
        btnrefreshheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surveyHelper.getMainGroups(password,true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        surveyHelper.closeSource();
    }
}
