package com.example.user1.myapplication.QuestionHeader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {
    Button btnstart;
    private TextView tv;
    private Bundle extras;
    private String category;
    private ArrayList<QuestionResponse> questions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    String period;
    String[] SPINNERLIST = {"1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("extra_category_mg");
            questions = extras.getParcelableArrayList("extra_questions");
            answers = extras.getStringArrayList("extra_answers");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);
        materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                period=SPINNERLIST[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnstart=(Button)findViewById(R.id.btngo);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),QuestionActivity.class);
                intent.putExtra("period",period);
                intent.putExtra("extra_category_mg", category);
                intent.putParcelableArrayListExtra("extra_questions", questions);
                intent.putExtra("extra_answers", answers);
                startActivity(intent);
            }
        });



        tv = (TextView) findViewById(R.id.tv);

//        for (int i = 0; i < CustomAdapter.editModelArrayList.size(); i++){
//
//            tv.setText(tv.getText() + " " + CustomAdapter.editModelArrayList.get(i).getEditTextValue() +System.getProperty("line.separator"));
//
//        }


    }
}
