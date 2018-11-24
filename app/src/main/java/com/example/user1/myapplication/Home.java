package com.example.user1.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.ModelDatabase;

public class Home extends AppCompatActivity {

    EditText txtdata1;
    EditText txtdata2;
    Button btnsave;
    MahasiswaHelper mahasiswaHelper;
    ModelDatabase data;

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(getApplicationContext(),ListData.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mahasiswaHelper = new MahasiswaHelper(Home.this);

        txtdata1 = (EditText) findViewById(R.id.txtdata1);
        txtdata2 = (EditText) findViewById(R.id.txtdata2);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mahasiswaHelper.open();
                mahasiswaHelper.beginTransaction();
                data = new ModelDatabase(txtdata1.getText().toString(), txtdata2.getText().toString());
                boolean insertreturn=mahasiswaHelper.insertTransaction(data);
                mahasiswaHelper.setTransactionSuccess();
                mahasiswaHelper.endTransaction();
                mahasiswaHelper.close();
                if (insertreturn){
                    Toast.makeText(getApplicationContext(),"sukses input",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"sukses input",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
}
