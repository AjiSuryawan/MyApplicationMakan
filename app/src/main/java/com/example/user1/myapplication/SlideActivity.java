package com.example.user1.myapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user1.myapplication.Model.Survey;

import java.util.ArrayList;

public class SlideActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private ArrayList<Survey> surveyList = new ArrayList<>();
    private Button previousBtn;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        viewPager = findViewById(R.id.viewpager);
        initData();
        final SlideAdapter adapter = new SlideAdapter(this, surveyList, this);
        viewPager.setAdapter(adapter);

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
                if (viewPager.getCurrentItem() == surveyList.size() - 1) {
                    Toast.makeText(SlideActivity.this, "last page", Toast.LENGTH_SHORT).show();
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == surveyList.size() - 1) {
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
        surveyList.add(new Survey("question", "lorem ipsum?"));
        surveyList.add(new Survey("question", "lorem?"));
        surveyList.add(new Survey("question", "lorem ips?"));
        surveyList.add(new Survey("question", "ipsum?"));
    }

    public void getAllAnswer(){

    }

}
