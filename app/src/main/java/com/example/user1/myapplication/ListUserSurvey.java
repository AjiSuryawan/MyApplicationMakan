package com.example.user1.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class ListUserSurvey extends AppCompatActivity {

    public RecyclerView recycler_view_list_film;
    public ArrayList<Survey> listFilm = new ArrayList<>();
    MahasiswaHelper mahasiswaHelper;
    public SectionListDataAdapter adapterAllTipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_survey);
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
