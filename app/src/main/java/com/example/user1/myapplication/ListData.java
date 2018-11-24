package com.example.user1.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.ModelDatabase;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {

    public RecyclerView recycler_view_list_film;
    public ArrayList<ModelDatabase> listFilm = new ArrayList<>();
    public SectionListDataAdapter3 adapterAllTipe;
    MahasiswaHelper mahasiswaHelper;

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sinkron:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_LONG).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ListData.this);
                builder.setMessage("Are you sure want to sync?").setPositiveButton("Yes", dialogClickListener)
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
        setContentView(R.layout.activity_list_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_view_list_film = (RecyclerView) findViewById(R.id.recycler_view_list_film);
        mahasiswaHelper = new MahasiswaHelper(getApplicationContext());
        mahasiswaHelper.open();
        listFilm = mahasiswaHelper.getAllData();
        recycler_view_list_film.setHasFixedSize(true);
        adapterAllTipe = new SectionListDataAdapter3(getApplicationContext(), listFilm);
        recycler_view_list_film.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view_list_film.setAdapter(adapterAllTipe);
    }
}
