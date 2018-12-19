package com.example.user1.myapplication.QuestionHeader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1.myapplication.Database.DatabaseProvider;
import com.example.user1.myapplication.Model.MainGroupResponse;
import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {
    Button btnstart;
    private TextView tv;
    private Bundle extras;
    String period;
    String[] SPINNERLIST = {"1", "2", "3"};
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
                //kalau yang dipilih satu, langsung aja, tapi kalau 2 dan 3 yang belum pernah diklik,
                //harus nge get API dulu

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
