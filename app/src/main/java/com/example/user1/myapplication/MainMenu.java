package com.example.user1.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.Survey;

public class MainMenu extends AppCompatActivity {
    private String m_Text = "";
    MahasiswaHelper mahasiswaHelper;
    int result=0;

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
            case R.id.dataku:
                startActivity(new Intent(getApplicationContext(),ListData.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mahasiswaHelper = new MahasiswaHelper(MainMenu.this);

        CardView cardkuspa2=(CardView)findViewById(R.id.cardkuspa1);
        cardkuspa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                builder.setTitle("Input name");
                final EditText input = new EditText(MainMenu.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        Survey survey = new Survey();
                        survey.setNamaguru(m_Text);
                        mahasiswaHelper.open();
                        mahasiswaHelper.beginTransaction();
                        result=mahasiswaHelper.inserttbuser(survey);
                        mahasiswaHelper.setTransactionSuccess();
                        mahasiswaHelper.endTransaction();
                        mahasiswaHelper.close();
                        Intent in = new Intent(getApplicationContext(),SlideActivity.class);
                        in.putExtra("id",result);
                        startActivity(in);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                //
            }
        });
    }
}
