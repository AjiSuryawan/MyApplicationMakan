package com.example.user1.myapplication.QuestionHeader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1.myapplication.Model.QuestionResponse;
import com.example.user1.myapplication.QuestionSection.QuestionActivity;
import com.example.user1.myapplication.R;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {
    Button btnstart;
    private TextView tv;
    private Bundle extras;
    private String category;
    private ArrayList<QuestionResponse> questions = new ArrayList<>();
    String period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("extra_category_mg");
            questions = extras.getParcelableArrayList("extra_questions");
        }

        btnstart=(Button)findViewById(R.id.btnstart);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog period
                AlertDialog.Builder alert = new AlertDialog.Builder(NextActivity.this);
                final EditText edittext = new EditText(NextActivity.this);
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setMessage("Please input period time");
                alert.setTitle("Input period");

                alert.setView(edittext);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        period = edittext.getText().toString();
                        if (Integer.parseInt(period)<1 || Integer.parseInt(period)>3){
                            Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(NextActivity.this,QuestionActivity.class);
                            intent.putExtra("period",period);
                            intent.putExtra("extra_category_mg", category);
                            intent.putParcelableArrayListExtra("extra_questions", questions);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
                //

            }
        });

        tv = (TextView) findViewById(R.id.tv);

        for (int i = 0; i < CustomAdapter.editModelArrayList.size(); i++){

            tv.setText(tv.getText() + " " + CustomAdapter.editModelArrayList.get(i).getEditTextValue() +System.getProperty("line.separator"));

        }


    }
}
