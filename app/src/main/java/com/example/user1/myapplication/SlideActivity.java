package com.example.user1.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user1.myapplication.Database.MahasiswaHelper;
import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class SlideActivity extends AppCompatActivity {

    Bundle extras;
    int id;

    private CustomViewPager viewPager;
    private ArrayList<Survey> surveyList = new ArrayList<>();
    private Button previousBtn;
    private Button nextBtn;
    private SlideAdapter adapter;
    MahasiswaHelper mahasiswaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        viewPager = findViewById(R.id.viewpager);

        extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            Log.e("Mahasiswa", "onCreate: " + id );
            // and get whatever type user account id is
        }

        initData();
        mahasiswaHelper = new MahasiswaHelper(SlideActivity.this);
        initFragment(surveyList);
        previousBtn = findViewById(R.id.previous_btn);
        previousBtn.setVisibility(View.GONE);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == surveyList.size()) {
                    mahasiswaHelper.open();
                    mahasiswaHelper.beginTransaction();
                    for (int i = 0; i <surveyList.size() ; i++) {
                        Log.e("Mahasiswa", "id: " + surveyList.get(i).getIdguru() );
                        mahasiswaHelper.insertTransaction(surveyList.get(i));
                    }
                    mahasiswaHelper.setTransactionSuccess();
                    mahasiswaHelper.endTransaction();
                    mahasiswaHelper.close();
                    finish();

                }
                else if(viewPager.getCurrentItem() == surveyList.size() - 1 ){
                    int position = viewPager.getCurrentItem();
                    SlideFragment previewFragment = (SlideFragment) adapter.getItem(surveyList.size());
                    SlideFragment fragment = (SlideFragment) adapter.getItem(position);
                    surveyList.get(position).setAnswer(fragment.getAnswer());
                    previewFragment.notifyDataSetChanged();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                else {
                    final int position = viewPager.getCurrentItem();
                    SlideFragment fragment = (SlideFragment) adapter.getItem(position);
                    final Survey survey = surveyList.get(position);
                    final String answer = fragment.getAnswer();
                    survey.setAnswer(answer);
                    Log.e("answer", "onClick: " + answer);
                    viewPager.setCurrentItem(position + 1);
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == surveyList.size()) {
                    nextBtn.setText("Finish");
                    for (Survey survey: surveyList) {
                        Log.e("SlideActivity", "onPageSelected: " + survey.getAnswer());
                    }
                } else {
                    nextBtn.setText("Next");
                }

                if (i == 0) {
                    previousBtn.setVisibility(View.GONE);
                } else {
                    previousBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void initData() {
        surveyList.add(new Survey(id,"question", "Sekolah dimana ?","a"));
        surveyList.add(new Survey(id,"question", "sudah makan ?","a"));
        surveyList.add(new Survey(id,"question", "uang saku berapa ?","a"));
        surveyList.add(new Survey(id,"question", "tinggal dimana ?","a"));
        surveyList.add(new Survey(id,"question", "apa makanan favorit ?","a"));
    }

    void initFragment(ArrayList<Survey> surveyList) {
        adapter = new SlideAdapter(getSupportFragmentManager());
        Log.e("SlideActivity", "initFragment: " + surveyList.size() );
        for (int i = 0; i <= surveyList.size(); i++) {
            if(i == surveyList.size()) {
                adapter.initFragment(SlideFragment.newInstance(R.layout.preview, surveyList));
            }
            else {
                Survey survey = surveyList.get(i);
                adapter.initFragment(SlideFragment.newInstance(survey.getQuestion(), i + 1, surveyList.size(), R.layout.survey));
            }
            Log.e("SlideActivity", "initFragment: " + i );
        }
        viewPager.setAdapter(adapter);
    }

    public void changeToDesiredQuestion(int position){
        viewPager.setCurrentItem(position);
    }
}
