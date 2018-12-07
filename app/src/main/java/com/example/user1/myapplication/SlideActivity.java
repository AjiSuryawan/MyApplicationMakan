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
                //finish
                if (viewPager.getCurrentItem() == surveyList.size()) {
                    mahasiswaHelper.open();
                    mahasiswaHelper.beginTransaction();
                    for (int i = 0; i <surveyList.size() ; i++) {
                        mahasiswaHelper.insertTransaction(surveyList.get(i));
                    }
                    mahasiswaHelper.setTransactionSuccess();
                    mahasiswaHelper.endTransaction();
                    mahasiswaHelper.close();
                    finish();

                }
                //preview
                else if(viewPager.getCurrentItem() == surveyList.size() - 1 ){
                    int position = viewPager.getCurrentItem();
                    SlideFragment previewFragment = (SlideFragment) adapter.getItem(surveyList.size());
                    SlideFragment fragment = (SlideFragment) adapter.getItem(position);
                    surveyList.get(position).addAnswer(fragment.getAnswer());
                    previewFragment.notifyDataSetChanged();
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                //ma
                else if(surveyList.get(viewPager.getCurrentItem()).getType().equals("ma")){
                    final int position = viewPager.getCurrentItem();
                    SlideFragment fragment = (SlideFragment) adapter.getItem(position);
                    final Survey survey = surveyList.get(position);
                    survey.addAnswer(fragment.getAnswers());
                    viewPager.setCurrentItem(position + 1);
                }
                else {
                    final int position = viewPager.getCurrentItem();
                    SlideFragment fragment = (SlideFragment) adapter.getItem(position);
                    final Survey survey = surveyList.get(position);
                    final String answer = fragment.getAnswer();
                    survey.addAnswer(answer);
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
        ArrayList<String> answers2 = new ArrayList<>();
        answers2.add("Motorik");
        answers2.add("Bahasa dan literasi");
        answers2.add("Moral dan spiritual");

        ArrayList<String> answers3 = new ArrayList<>();
        answers3.add("Ya");
        answers3.add("Tidak");
        surveyList.add(new Survey(id,"ketik", "Apa pemahaman anda tentang Pendidikan Anak Usia Dini (PAUD) ?",  new ArrayList<String>()));
        surveyList.add(new Survey(id,"ma", "Perkembangan apa saja yang dibutuhkan anak untuk dipelajari di PAUD",answers2));
        surveyList.add(new Survey(id,"sa", "Menurut Anda, apakah lembaga PAUD di desa ini telah mengajarkan perkembangan yang sesuai dibutuhkan anak anak di desa ini ?", answers3));
        surveyList.add(new Survey(id,"ketik", "Apakah pemahaman anda tentang layanan PAUD HI",new ArrayList<String>()));
    }

    void initFragment(ArrayList<Survey> surveyList) {
        adapter = new SlideAdapter(getSupportFragmentManager());
        Log.e("SlideActivity", "initFragment: " + surveyList.size() );
        for (int i = 0; i <= surveyList.size(); i++) {
            if(i == surveyList.size()) {
                adapter.initFragment(SlideFragment.newInstance(R.layout.preview, surveyList));
            }
            else if (surveyList.get(i).getType().equals("sa")){
                Survey survey = surveyList.get(i);
                adapter.initFragment(SlideFragment.newInstance(survey.getQuestion(), i + 1, surveyList.size(), surveyList.get(i).getAnswers(), R.layout.survey_singleanswer));
            }
            else if (surveyList.get(i).getType().equals("ma")){
                Survey survey = surveyList.get(i);
                adapter.initFragment(SlideFragment.newInstance(survey.getQuestion(), i + 1, surveyList.size(), surveyList.get(i).getAnswers(), R.layout.survey_multipleanswer));
            }
            else if(surveyList.get(i).getType().equals("ketik")){
                Survey survey = surveyList.get(i);
                adapter.initFragment(SlideFragment.newInstance(survey.getQuestion(), i + 1, surveyList.size(), R.layout.survey_default));
            }
            Log.e("SlideActivity", "initFragment: " + i );
        }
        viewPager.setAdapter(adapter);
    }

    public void changeToDesiredQuestion(int position){
        viewPager.setCurrentItem(position);
    }
}
