package com.example.user1.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class ListUserSurvey extends AppCompatActivity {

    public RecyclerView recycler_view_list_film;
    public ArrayList<Survey> listFilm = new ArrayList<>();
    MahasiswaHelper mahasiswaHelper;
    public SectionListDataAdapter adapterAllTipe;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sinkron:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Toast.makeText(getApplicationContext(),"sinkron data here",Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ListUserSurvey.this);
                builder.setTitle("Sinkronasi").setMessage("Sinkronkan data ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_survey);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycler_view_list_film = (RecyclerView) findViewById(R.id.recycler_view_list_film2);
        mahasiswaHelper = new MahasiswaHelper(ListUserSurvey.this); //2
        mahasiswaHelper.open(); //3
        listFilm = mahasiswaHelper.getAllDataUser();
        Log.d("ambil", "onCreate: "+listFilm.size());
        adapterAllTipe = new SectionListDataAdapter(ListUserSurvey.this, listFilm);
        recycler_view_list_film.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view_list_film.setAdapter(adapterAllTipe);
        recycler_view_list_film.setNestedScrollingEnabled(false);
        recycler_view_list_film.setVisibility(View.VISIBLE);
    }
}
